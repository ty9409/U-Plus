package control;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.SalesVO;
import model.StockVO;

public class SalesController implements Initializable {

	@FXML
	private TableView<SalesVO> tableView;
	@FXML
	private TextField txtName;
	@FXML
	private Button btnSearch;
	/*@FXML
	private Button btnBarChart;*/
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnDelete;
	@FXML
	private Button btnAll;
	@FXML
	private Button btnBack;
	@FXML
	private ImageView imageView;

	SalesVO sales = new SalesVO();
	ObservableList<SalesVO> data = FXCollections.observableArrayList();
	ObservableList<SalesVO> selectSales; // 테이블에서 선택한 정보 저장
	int selectedIndex; // 테이블에서 선택한 재고 정보 인덱스 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnDelete.setDisable(true);

		// 테이블 뷰 컬럼이름 설정
		TableColumn colsa_Num = new TableColumn("판매번호");
		colsa_Num.setPrefWidth(80);
		colsa_Num.setStyle("-fx-allignment: CENTER");
		colsa_Num.setCellValueFactory(new PropertyValueFactory<>("sa_Num"));

		TableColumn colsa_Name = new TableColumn("고객명");
		colsa_Name.setPrefWidth(70);
		colsa_Name.setCellValueFactory(new PropertyValueFactory<>("sa_Name"));

		TableColumn colsa_Phone = new TableColumn("전화번호");
		colsa_Phone.setPrefWidth(110);
		colsa_Phone.setCellValueFactory(new PropertyValueFactory<>("sa_Phone"));

		TableColumn colsc_Model = new TableColumn("모델명");
		colsc_Model.setPrefWidth(120);
		colsc_Model.setCellValueFactory(new PropertyValueFactory<>("sc_Model"));

		TableColumn colsa_Serial = new TableColumn("일련번호");
		colsa_Serial.setPrefWidth(120);
		colsa_Serial.setCellValueFactory(new PropertyValueFactory<>("sa_Serial"));

		TableColumn colsa_Plan = new TableColumn("요금제");
		colsa_Plan.setPrefWidth(70);
		colsa_Plan.setCellValueFactory(new PropertyValueFactory<>("sa_Plan"));

		TableColumn colsa_Contract = new TableColumn("할인약정");
		colsa_Contract.setPrefWidth(100);
		colsa_Contract.setCellValueFactory(new PropertyValueFactory<>("sa_Contract"));

		TableColumn colsa_Inct = new TableColumn("인센티브");
		colsa_Inct.setPrefWidth(100);
		colsa_Inct.setCellValueFactory(new PropertyValueFactory<>("sa_Inct"));

		TableColumn colsf_Name = new TableColumn("판매자");
		colsf_Name.setPrefWidth(100);
		colsf_Name.setCellValueFactory(new PropertyValueFactory<>("sf_Name"));

		TableColumn colsa_Date = new TableColumn("판매일");
		colsa_Date.setPrefWidth(95);
		colsa_Date.setCellValueFactory(new PropertyValueFactory<>("sa_Date"));

		TableColumn colsa_Memo = new TableColumn("기타메모");
		colsa_Memo.setPrefWidth(250);
		colsa_Memo.setCellValueFactory(new PropertyValueFactory<>("sa_Memo"));

		tableView.setItems(data);
		tableView.getColumns().addAll(colsa_Num, colsa_Name, colsa_Phone, colsc_Model, colsa_Serial, colsa_Plan,
				colsa_Contract, colsa_Inct, colsf_Name, colsa_Date, colsa_Memo);

		// 전체 판매 정보
		all();

		// 검색에서 Enter키 적용 ㅇ
		txtName.setOnKeyPressed(event -> handlerTxtModelKeyPressed(event));
		// 검색 버튼 ㅇ
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));
		// 등록 버튼 ㅇ
		btnRegister.setOnAction(event -> handlerBtnRegisterAction(event));
		// 삭제 버튼 ㅇ
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// 판매 그래프 버튼
		//btnBarChart.setOnAction(event -> handlerBtnBarChartAction(event));
		// 뒤로가기 버튼 ㅇ
		btnBack.setOnAction(event -> handlerBtnBackAction(event));
		// 테이블 뷰 클릭시 수정,삭제 가능
		tableView.setOnMouseClicked(event -> handlerTableViewMouseClicked(event));

		// 전체 재고 버튼
		btnAll.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					// 전체 재고 정보
					all();
					btnDelete.setDisable(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} // initialize 끝

	// 삭제 버튼을 누르면 삭제 확인창 나온다
	public void handlerBtnDeleteAction(ActionEvent event) {
		try {
			SalesVO salesDelete = tableView.getSelectionModel().getSelectedItem();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SalesDelete.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnDelete.getScene().getWindow();
			mainStage.setResizable(false);
			mainStage.show();

			Label txtNumber = (Label) mainView.lookup("#txtNumber");
			Label txtModel = (Label) mainView.lookup("#txtModel");
			Label txtName = (Label) mainView.lookup("#txtName");
			Label txtInct = (Label) mainView.lookup("#txtInct");
			Label txtPlan = (Label) mainView.lookup("#txtPlan");
			
			txtNumber.setText(salesDelete.getSa_Num()+"");
			txtModel.setText(salesDelete.getSc_Model());
			txtName.setText(salesDelete.getSf_Name());
			txtInct.setText(salesDelete.getSa_Inct()+"");
			txtPlan.setText(salesDelete.getSa_Plan()+"");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // 삭제 메소드 끝

	// 테이블 뷰 클릭시 수정,삭제 가능
	public void handlerTableViewMouseClicked(MouseEvent event) {
		try {
			if (event.getClickCount() == 1) {
				btnDelete.setDisable(false);
				selectedIndex = selectSales.get(0).getSa_Num();
			}
		} catch (Exception e) {
		}
	} // 클릭 끝

	// 등록버튼을 누르면 재고등록 창이 나온다
	public void handlerBtnRegisterAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SalesRegister.fxml"));
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
		SalesVO sVo = new SalesVO();
		SalesDAO sDao = null;

		Object[][] totalData = null;

		String searchName = "";
		boolean searchResult = false;

		try {
			searchName = txtName.getText().trim();
			sDao = new SalesDAO();
			sVo = sDao.getSalesSearch(searchName);

			if (searchName.equals("")) {
				searchResult = true;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("판매정보 검색 실패");
				alert.setContentText("고객명을 입력하세요!");
				alert.showAndWait();
			}
			if (!searchName.equals("") && (sVo != null)) {
				ArrayList<String> title;
				ArrayList<SalesVO> list;
				title = sDao.getColumnName();
				int columnCount = title.size();

				list = sDao.getSalesAll();
				int rowCount = list.size();

				totalData = new Object[rowCount][columnCount];
				if (sVo.getSa_Name().equals(searchName)) {
					txtName.clear();
					data.removeAll(data);
					if (sVo.getSa_Name().equals(searchName)) {
						data.add(sVo);
						searchResult = true;
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("U+ 관리 프로그램");
						alert.setHeaderText("판매 정보 검색");
						alert.setContentText(searchName + "이(가) 검색되었습니다! 성공");
						alert.showAndWait();
					}
				}
			}
			if (!searchResult) {
				txtName.clear();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("판매 정보 검색");
				alert.setContentText(searchName + " 이(가) 리스트에 없습니다! 실패");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("U+ 관리 프로그램");
			alert.setHeaderText("판매 정보 검색");
			alert.setContentText("검색 오류!");
			alert.showAndWait();
			e.printStackTrace();

		}
	} // 검색 끝

	// 전체 재고 리스트
	public void all() {

		Object[][] allData;

		SalesDAO sDao = new SalesDAO();
		SalesVO sVo = null;
		ArrayList<String> title;
		ArrayList<SalesVO> list;

		title = sDao.getColumnName();
		int columnCount = title.size();

		list = sDao.getSalesAll();
		int rowCount = list.size();

		allData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			sVo = list.get(index);
			data.add(sVo);
		}
	}

}
