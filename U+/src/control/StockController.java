package control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.StockVO;

public class StockController implements Initializable {

	@FXML
	private TableView<StockVO> tableView;
	@FXML
	private TextField txtModel; // 모델명
	@FXML
	private Button btnSearch; // 검색 버튼
	@FXML
	private Button btnRegister; // 등록 버튼
	@FXML
	private Button btnModify; // 수정 버튼
	@FXML
	private Button btnSamsung;
	@FXML
	private Button btnLG;
	@FXML
	private Button btnAPPLE;
	@FXML
	private Button btnETC;
	@FXML
	private Button btnDelete; // 삭제 버튼
	@FXML
	private Button btnClose; // 차트 취소 버튼
	@FXML
	private Button btnAll; // 전체 리스트 버튼
	@FXML
	private Button btnBack; // 뒤로가기 버튼
	@FXML
	private ImageView imageView; // 재고 이미지

	StockVO stock = new StockVO();
	ObservableList<StockVO> data = FXCollections.observableArrayList();
	ObservableList<StockVO> selectStock; // 테이블에서 선택한 정보 저장
	int selectedIndex; // 테이블에서 선택한 재고 정보 인덱스 저장

	String selectFileName = ""; // 이미지 파일명
	String localUrl = ""; // 이미지 파일 경로
	Image localImage;

	// 이미지 처리
	File selectedFile = null;

	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/Images/Stock");

	// 이미지 불러올 파일을 저장할 파일 객체 선언
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnModify.setDisable(true);
		btnDelete.setDisable(true);

		// 테이블 뷰 컬럼이름 설정
		TableColumn colsc_Num = new TableColumn("재고번호");
		colsc_Num.setPrefWidth(90);
		colsc_Num.setStyle("-fx-allignment: CENTER");
		colsc_Num.setCellValueFactory(new PropertyValueFactory<>("sc_Num"));

		TableColumn colsc_Model = new TableColumn("모델명");
		colsc_Model.setPrefWidth(120);
		colsc_Model.setCellValueFactory(new PropertyValueFactory<>("sc_Model"));

		TableColumn colsc_Amount = new TableColumn("수량");
		colsc_Amount.setPrefWidth(50);
		colsc_Amount.setCellValueFactory(new PropertyValueFactory<>("sc_Amount"));

		TableColumn colsc_Price = new TableColumn("출고가");
		colsc_Price.setPrefWidth(90);
		colsc_Price.setCellValueFactory(new PropertyValueFactory<>("sc_Price"));

		TableColumn colsc_Company = new TableColumn("제조사");
		colsc_Company.setPrefWidth(90);
		colsc_Company.setCellValueFactory(new PropertyValueFactory<>("sc_Company"));

		TableColumn colsc_Date = new TableColumn("입고일");
		colsc_Date.setPrefWidth(90);
		colsc_Date.setCellValueFactory(new PropertyValueFactory<>("sc_Date"));

		TableColumn colsc_Image = new TableColumn("이미지");
		colsc_Image.setPrefWidth(270);
		colsc_Image.setCellValueFactory(new PropertyValueFactory<>("sc_Image"));

		tableView.setItems(data);
		tableView.getColumns().addAll(colsc_Num, colsc_Model, colsc_Amount, colsc_Price, colsc_Company, colsc_Date,
				colsc_Image);


		// 전체 재고 정보
		all();

		// 검색에서 Enter키 적용 ㅇ
		txtModel.setOnKeyPressed(event -> handlerTxtModelKeyPressed(event));
		// 검색 버튼 ㅇ
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));
		// 등록 버튼 ㅇ
		btnRegister.setOnAction(event -> handlerBtnRegisterAction(event));
		// 수정 버튼 ㅇ
		btnModify.setOnAction(event -> handlerBtnModifyAction(event));
		// 삼성 차트 버튼
		btnSamsung.setOnAction(event -> handlerBtnSamsungAction(event));
		// LG 차트 버튼
		btnLG.setOnAction(event -> handlerBtnLGAction(event));
		// APPLE 차트 버튼
		btnAPPLE.setOnAction(event -> handlerBtnAPPLEAction(event));
		// 기타회사 차트 버튼
		btnETC.setOnAction(event -> handlerBtnETCAction(event));
		// 삭제 버튼 ㅇ
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// 뒤로가기 버튼 ㅇ
		btnBack.setOnAction(event -> handlerBtnBackAction(event));

		// 클릭했을 때 이미지뷰에 출력
		tableView.setOnMouseClicked(event -> handlerTableViewMouseClicked(event));

		// 전체 재고 버튼
		btnAll.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					// 전체 재고 정보
					all();
					btnModify.setDisable(true);
					btnDelete.setDisable(true);
					imageView.setImage(new Image("image/ImageChoice.png", false));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	} // initialize 끝

	// 삼성 차트 메소드
	public void handlerBtnSamsungAction(ActionEvent event) {
		StockDAO sdao = new StockDAO();
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnRegister.getScene().getWindow());
			dialog.setResizable(false);
			dialog.setTitle(" U+ 관리 프로그램");
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/StockBarChart.fxml"));
			BarChart barChart = (BarChart) parent.lookup("#barChart");
			
			XYChart.Series seriesSamsung = new XYChart.Series();
			seriesSamsung.setName("삼성 재고 수량");
			ObservableList SamsungList = FXCollections.observableArrayList();
			ArrayList<StockVO> SAMSUNG = new ArrayList<StockVO>();
			SAMSUNG = sdao.SamsungCompany();
			for (int i = 0; i < SAMSUNG.size(); i++) {
				SamsungList.add(new XYChart.Data(SAMSUNG.get(i).getSc_Model().trim(), SAMSUNG.get(i).getSc_Amount()));
			}
			
			seriesSamsung.setData(SamsungList);
			barChart.getData().add(seriesSamsung);
			
			// 취소 버튼 메소드
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e -> dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // 삼성 재고 차트 끝

	// LG 차트 메소드
	public void handlerBtnLGAction(ActionEvent event) {
		StockDAO sdao = new StockDAO();
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnRegister.getScene().getWindow());
			dialog.setResizable(false);
			dialog.setTitle(" U+ 관리 프로그램");
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/StockBarChart.fxml"));
			BarChart barChart = (BarChart) parent.lookup("#barChart");
			
			XYChart.Series seriesLG = new XYChart.Series();
			seriesLG.setName("LG 재고 수량");
			ObservableList LGList = FXCollections.observableArrayList();
			ArrayList<StockVO> LG = new ArrayList<StockVO>();
			LG = sdao.LGCompany();
			for (int i = 0; i < LG.size(); i++) {
				LGList.add(new XYChart.Data(LG.get(i).getSc_Model().trim(), LG.get(i).getSc_Amount()));
			}
			
			seriesLG.setData(LGList);
			barChart.getData().add(seriesLG);
			
			// 취소 버튼 메소드
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e -> dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // LG 재고 차트 끝
	
	// APPLE 차트 메소드
		public void handlerBtnAPPLEAction(ActionEvent event) {
			StockDAO sdao = new StockDAO();
			try {
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnRegister.getScene().getWindow());
				dialog.setResizable(false);
				dialog.setTitle(" U+ 관리 프로그램");
				
				Parent parent = FXMLLoader.load(getClass().getResource("/view/StockBarChart.fxml"));
				BarChart barChart = (BarChart) parent.lookup("#barChart");
				
				XYChart.Series seriesAPPLE = new XYChart.Series();
				seriesAPPLE.setName("APPLE 재고 수량");
				ObservableList APPLEList = FXCollections.observableArrayList();
				ArrayList<StockVO> LG = new ArrayList<StockVO>();
				LG = sdao.APPLECompany();
				for (int i = 0; i < LG.size(); i++) {
					APPLEList.add(new XYChart.Data(LG.get(i).getSc_Model().trim(), LG.get(i).getSc_Amount()));
				}
				
				seriesAPPLE.setData(APPLEList);
				barChart.getData().add(seriesAPPLE);
				
				// 취소 버튼 메소드
				Button btnClose = (Button) parent.lookup("#btnClose");
				btnClose.setOnAction(e -> dialog.close());
				
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.show();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // APPLE 재고 차트 끝
		
		// ETC 차트 메소드
				public void handlerBtnETCAction(ActionEvent event) {
					StockDAO sdao = new StockDAO();
					try {
						Stage dialog = new Stage(StageStyle.UTILITY);
						dialog.initModality(Modality.WINDOW_MODAL);
						dialog.initOwner(btnRegister.getScene().getWindow());
						dialog.setResizable(false);
						dialog.setTitle(" U+ 관리 프로그램");
						
						Parent parent = FXMLLoader.load(getClass().getResource("/view/StockBarChart.fxml"));
						BarChart barChart = (BarChart) parent.lookup("#barChart");
						
						XYChart.Series seriesETC = new XYChart.Series();
						seriesETC.setName(" ETC 재고 수량");
						ObservableList ETCList = FXCollections.observableArrayList();
						ArrayList<StockVO> ETC = new ArrayList<StockVO>();
						ETC = sdao.ETCCompany();
						for (int i = 0; i < ETC.size(); i++) {
							ETCList.add(new XYChart.Data(ETC.get(i).getSc_Model().trim(), ETC.get(i).getSc_Amount()));
						}
						
						seriesETC.setData(ETCList);
						barChart.getData().add(seriesETC);
						
						// 취소 버튼 메소드
						Button btnClose = (Button) parent.lookup("#btnClose");
						btnClose.setOnAction(e -> dialog.close());
						
						Scene scene = new Scene(parent);
						dialog.setScene(scene);
						dialog.show();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // ETC 재고 차트 끝
	
				
				
	// 검색에서 Enter키 적용 메소드
	public void handlerTxtModelKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			search();
		}
	}

	// 검색
	public void handlerBtnSearchAction(ActionEvent event) {
		search();
	}

	// 검색 메소드
	public void search() {
		StockVO sVo = new StockVO();
		StockDAO sDao = null;

		Object[][] totalData = null;

		String searchModel = "";
		boolean searchResult = false;

		try {
			searchModel = txtModel.getText().trim();
			sDao = new StockDAO();
			sVo = sDao.getStockSearch(searchModel);

			if (searchModel.equals("")) {
				searchResult = true;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 검색 실패");
				alert.setContentText("모델명을 입력하세요!");
				alert.showAndWait();
			}
			if (!searchModel.equals("") && (sVo != null)) {
				ArrayList<String> title;
				ArrayList<StockVO> list;
				title = sDao.getColumnName();
				int columnCount = title.size();

				list = sDao.getStockAll();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];
				if (sVo.getSc_Model().equals(searchModel)) {
					txtModel.clear();
					data.removeAll(data);
					if (sVo.getSc_Model().equals(searchModel)) {
						data.add(sVo);
						searchResult = true;
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("U+ 관리 프로그램");
						alert.setHeaderText("재고 정보 검색");
						alert.setContentText(searchModel+ " 이(가) 검색되었습니다!");
						alert.showAndWait();
					}
				}
			}
			if (!searchResult) {
				txtModel.clear();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 정보 검색");
				alert.setContentText(searchModel+ " 이(가) 리스트에 없습니다!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("U+ 관리 프로그램");
			alert.setHeaderText("재고 정보 검색");
			alert.setContentText("검색 오류!");
			alert.showAndWait();
			e.printStackTrace();

		}
	} // 검색 끝

	// 삭제 버튼을 누르면 삭제 확인창 나온다
	public void handlerBtnDeleteAction(ActionEvent event) {
		try {
			StockVO stockDelete = tableView.getSelectionModel().getSelectedItem();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StockDelete.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnDelete.getScene().getWindow();
			mainStage.setResizable(false);
			mainStage.show();
			
			Label txtNum = (Label) mainView.lookup("#txtNumber");
			txtNum.setText(stockDelete.getSc_Num()+"");
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // 삭제 메소드 끝

	// 뒤로가기 버튼을 누르면 작업선택 창으로 돌아간다
	public void handlerBtnBackAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setResizable(false);
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnBack.getScene().getWindow();
			oldStage.close();
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 수정버튼을 누르면 재고수정 창이 나온다
	public void handlerBtnModifyAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StockModify.fxml"));
			Parent parentModify = (Parent) loader.load();
			Scene scene = new Scene(parentModify);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();

			StockVO StockModify = tableView.getSelectionModel().getSelectedItem();
			selectedIndex = tableView.getSelectionModel().getSelectedIndex();

			TextField modifyNum = (TextField) parentModify.lookup("#txtNum");
			TextField modifyModel = (TextField) parentModify.lookup("#txtModel");
			TextField modifyAmount = (TextField) parentModify.lookup("#txtAmount");
			TextField modifyPrice = (TextField) parentModify.lookup("#txtPrice");
			ComboBox<String> cbCompany = (ComboBox<String>) parentModify.lookup("#cbCompany");
			TextField modifyImage = (TextField) parentModify.lookup("#txtImage");
			DatePicker modifyDpDate = (DatePicker) parentModify.lookup("#dpDate");

			modifyNum.setDisable(true);
			modifyModel.setDisable(true);
			cbCompany.setDisable(true);
			modifyImage.setDisable(true);

			modifyNum.setText(StockModify.getSc_Num() + "");
			modifyModel.setText(StockModify.getSc_Model());
			modifyAmount.setText(StockModify.getSc_Amount() + "");
			modifyPrice.setText(StockModify.getSc_Price() + "");
			cbCompany.setValue(StockModify.getSc_Company());
			modifyImage.setText(StockModify.getSc_Image());
			modifyDpDate.setValue(LocalDate.parse(StockModify.getSc_Date()));

		} catch (IOException e) {
			System.out.println(e);
		}
	} // 수정 버튼 메소드 끝

	// 테이블 뷰 클릭시 이미지 뷰에 출력
	public void handlerTableViewMouseClicked(MouseEvent event) {
		try {
			if (event.getClickCount() == 1) {
				File file = new File(tableView.getSelectionModel().getSelectedItem().getSc_Image());
				Image img = new Image(file.toURI().toString());
				imageView.setImage(img);
				
				btnModify.setDisable(false);
				btnDelete.setDisable(false);
				
				selectedIndex = selectStock.get(0).getSc_Num();
			}
		} catch (Exception e) {
		}
	} // 클릭 끝

	// 등록버튼을 누르면 재고등록 창이 나온다
	public void handlerBtnRegisterAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StockRegister.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnRegister.getScene().getWindow();
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 전체 재고 리스트
	public void all() {

		Object[][] allData;

		StockDAO sDao = new StockDAO();
		StockVO sVo = null;
		ArrayList<String> title;
		ArrayList<StockVO> list;

		title = sDao.getColumnName();
		int columnCount = title.size();

		list = sDao.getStockAll();
		int rowCount = list.size();

		allData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			sVo = list.get(index);
			data.add(sVo);
		}
	}

}
