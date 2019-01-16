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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.StaffVO;
import model.StockVO;

public class StaffController implements Initializable {

	@FXML
	private TableView<StaffVO> tableView;
	@FXML
	private Button btnRegister; // 등록 버튼
	@FXML
	private Button btnModify; // 수정 버튼
	@FXML
	private Button btnDelete; // 삭제 버튼
	@FXML
	private Button btnBarChart; // 판매 차트 버튼
	@FXML
	private Button btnBarChart2; // 급여 차트 버튼
	@FXML
	private Button btnAll; // 전체 리스트 버튼
	@FXML
	private Button btnBack; // 뒤로가기 버튼
	@FXML
	private ImageView imageView; // 직원 이미지
	@FXML
	private Button btnClose; // 차트 취소 버튼

	StaffVO staff = new StaffVO();
	ObservableList<StaffVO> data = FXCollections.observableArrayList();
	ObservableList<StaffVO> selectStaff; // 테이블에서 선택한 정보 저장
	int selectedIndex; // 테이블에서 선택한 직원 정보 인덱스 저장

	String selectFileName = ""; // 이미지 파일명
	String localUrl = ""; // 이미지 파일 경로
	Image localImage;

	// 이미지 처리
	File selectedFile = null;

	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/Images/Staff");

	// 이미지 불러올 파일을 저장할 파일 객체 선언
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnModify.setDisable(true);
		btnDelete.setDisable(true);

		// 테이블 뷰 컬럼이름 설정
		TableColumn colsf_Num = new TableColumn("직원번호");
		colsf_Num.setPrefWidth(65);
		colsf_Num.setStyle("-fx-allignment: CENTER");
		colsf_Num.setCellValueFactory(new PropertyValueFactory<>("sf_Num"));

		TableColumn colsf_Rank = new TableColumn("직급");
		colsf_Rank.setPrefWidth(60);
		colsf_Rank.setCellValueFactory(new PropertyValueFactory<>("sf_Rank"));

		TableColumn colsf_Name = new TableColumn("이름");
		colsf_Name.setPrefWidth(80);
		colsf_Name.setCellValueFactory(new PropertyValueFactory<>("sf_Name"));

		TableColumn colsf_Birth = new TableColumn("생년월일");
		colsf_Birth.setPrefWidth(90);
		colsf_Birth.setCellValueFactory(new PropertyValueFactory<>("sf_Birth"));

		TableColumn colsf_Phone = new TableColumn("전화번호");
		colsf_Phone.setPrefWidth(110);
		colsf_Phone.setCellValueFactory(new PropertyValueFactory<>("sf_Phone"));

		TableColumn colsf_Addre = new TableColumn("주소");
		colsf_Addre.setPrefWidth(150);
		colsf_Addre.setCellValueFactory(new PropertyValueFactory<>("sf_Addre"));

		TableColumn colsf_Basic = new TableColumn("기본급");
		colsf_Basic.setPrefWidth(90);
		colsf_Basic.setCellValueFactory(new PropertyValueFactory<>("sf_Basic"));

		TableColumn colsf_Inct = new TableColumn("성과급");
		colsf_Inct.setPrefWidth(80);
		colsf_Inct.setCellValueFactory(new PropertyValueFactory<>("sf_Inct"));

		TableColumn colsf_Total = new TableColumn("총 급여");
		colsf_Total.setPrefWidth(90);
		colsf_Total.setCellValueFactory(new PropertyValueFactory<>("sf_Total"));

		TableColumn colsf_Sales = new TableColumn("판매건수");
		colsf_Sales.setPrefWidth(80);
		colsf_Sales.setCellValueFactory(new PropertyValueFactory<>("sf_Sales"));

		TableColumn colsf_Date = new TableColumn("입사일");
		colsf_Date.setPrefWidth(95);
		colsf_Date.setCellValueFactory(new PropertyValueFactory<>("sf_Date"));
		
		TableColumn colsf_OutDate = new TableColumn("퇴사일");
		colsf_OutDate.setPrefWidth(95);
		colsf_OutDate.setCellValueFactory(new PropertyValueFactory<>("sf_OutDate"));

		TableColumn colsf_Image = new TableColumn("이미지");
		colsf_Image.setPrefWidth(120);
		colsf_Image.setCellValueFactory(new PropertyValueFactory<>("sf_Image"));

		tableView.setItems(data);
		tableView.getColumns().addAll(colsf_Num, colsf_Rank, colsf_Name, colsf_Birth, colsf_Phone, colsf_Addre,
				colsf_Basic, colsf_Inct, colsf_Total, colsf_Sales, colsf_Date, colsf_OutDate, colsf_Image);

		// 전체 재고 정보
		all();

		// 등록 버튼 ㅇ
		btnRegister.setOnAction(event -> handlerBtnRegisterAction(event));
		// 수정 버튼 ㅇ
		btnModify.setOnAction(event -> handlerBtnModifyAction(event));
		// 삭제 버튼 ㅇ
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// 판매량 차트
		btnBarChart.setOnAction(event -> handlerBtnBarChartAction(event));
		// 급여 차트
		btnBarChart2.setOnAction(event -> handlerBtnBarChart2Action(event));
		// 뒤로가기 버튼 ㅇ
		btnBack.setOnAction(event -> handlerBtnBackAction(event));
		// 클릭했을 때 이미지뷰에 출력
		tableView.setOnMouseClicked(event -> handlerTableViewMouseClicked(event));

		// 전체 재고 버튼
		btnAll.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					// 직원 전체 정보
					all();
					btnModify.setDisable(true);
					btnDelete.setDisable(true);
					imageView.setImage(new Image("image/ImageChoice.png", false));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 테이블 뷰 클릭시 이미지 뷰에 출력
	public void handlerTableViewMouseClicked(MouseEvent event) {
		try {
			if (event.getClickCount() == 1) {
				File file = new File(tableView.getSelectionModel().getSelectedItem().getSf_Image());
				Image img = new Image(file.toURI().toString());
				imageView.setImage(img);

				btnModify.setDisable(false);
				btnDelete.setDisable(false);
				
				selectedIndex = selectStaff.get(0).getSf_Num();
			}
		} catch (Exception e) {
		}
	}

	// 전체 재고 리스트
	public void all() {

		Object[][] allData;

		StaffDAO sDao = new StaffDAO();
		StaffVO sVo = null;
		ArrayList<String> title;
		ArrayList<StaffVO> list;

		title = sDao.getColumnName();
		int columnCount = title.size();

		list = sDao.getStaffAll();
		int rowCount = list.size();

		allData = new Object[rowCount][columnCount];

		for (int index = 0; index < rowCount; index++) {
			sVo = list.get(index);
			data.add(sVo);
		}
	}

	
	// 판매 차트 메소드
	public void handlerBtnBarChartAction(ActionEvent event) {
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnRegister.getScene().getWindow());
			dialog.setTitle(" 판매 그래프");
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/StaffBarchart.fxml"));
			
			BarChart barChart = (BarChart) parent.lookup("#barChart");
			
			XYChart.Series seriesSales = new XYChart.Series();
			seriesSales.setName("판매 건수");
			ObservableList SalesList = FXCollections.observableArrayList();
			for (int i = 0; i < data.size(); i++) {
				SalesList.add(new XYChart.Data(data.get(i).getSf_Name(), Integer.parseInt(data.get(i).getSf_Sales()+"")));
			}
			seriesSales.setData(SalesList);
			barChart.getData().add(seriesSales);
			
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e -> dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // 판매 차트 끝

	// 급여 차트 메소드
		public void handlerBtnBarChart2Action(ActionEvent event) {
			try {
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnRegister.getScene().getWindow());
				dialog.setTitle(" 급여 그래프");
				
				Parent parent = FXMLLoader.load(getClass().getResource("/view/StaffBarChart.fxml"));
				
				BarChart barChart = (BarChart) parent.lookup("#barChart");
				
				XYChart.Series seriesBasic = new XYChart.Series();
				seriesBasic.setName("기본급");
				ObservableList BasicList = FXCollections.observableArrayList();
				for (int i = 0; i < data.size(); i++) {
					BasicList.add(new XYChart.Data(data.get(i).getSf_Name(), Integer.parseInt(data.get(i).getSf_Basic()+"")));
				}
				seriesBasic.setData(BasicList);
				barChart.getData().add(seriesBasic);
				
				XYChart.Series seriesInct = new XYChart.Series();
				seriesInct.setName("성과급");
				ObservableList InctList = FXCollections.observableArrayList();
				for (int i = 0; i < data.size(); i++) {
					InctList.add(new XYChart.Data(data.get(i).getSf_Name(), Integer.parseInt(data.get(i).getSf_Inct()+"")));
				}
				seriesInct.setData(InctList);
				barChart.getData().add(seriesInct);
				
				XYChart.Series seriesTotal = new XYChart.Series();
				seriesTotal.setName("총 급여");
				ObservableList TotalList = FXCollections.observableArrayList();
				for (int i = 0; i < data.size(); i++) {
					TotalList.add(new XYChart.Data(data.get(i).getSf_Name(), Integer.parseInt(data.get(i).getSf_Total()+"")));
				}
				seriesTotal.setData(TotalList);
				barChart.getData().add(seriesTotal);
				
				Button btnClose = (Button) parent.lookup("#btnClose");
				btnClose.setOnAction(e -> dialog.close());
				
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.show();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // 급여 차트 끝
	
	// 등록버튼을 누르면 재고등록 창이 나온다
	public void handlerBtnRegisterAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffRegister.fxml"));
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

	// 수정버튼을 누르면 재고수정 창이 나온다
	public void handlerBtnModifyAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffModify.fxml"));
			Parent parentModify = (Parent) loader.load();
			Scene scene = new Scene(parentModify);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			mainStage.show();

			StaffVO StaffModify = tableView.getSelectionModel().getSelectedItem();
			selectedIndex = tableView.getSelectionModel().getSelectedIndex();

			TextField modifyNum = (TextField) parentModify.lookup("#txtNum");
			ComboBox<String> cbRank = (ComboBox<String>) parentModify.lookup("#cbRank");
			TextField modifyName = (TextField) parentModify.lookup("#txtName");
			DatePicker modifyDpBirth = (DatePicker) parentModify.lookup("#dpBirth");
			TextField modifyPhone = (TextField) parentModify.lookup("#txtPhone");
			TextField modifyAddre = (TextField) parentModify.lookup("#txtAddre");
			TextField modifyBasic = (TextField) parentModify.lookup("#txtBasic");
			DatePicker modifyDpDate = (DatePicker) parentModify.lookup("#dpDate");
			DatePicker modifyDpOutDate = (DatePicker) parentModify.lookup("#dpOutDate");
			TextField modifyImage = (TextField) parentModify.lookup("#txtImage");
			Label modifyInct = (Label) parentModify.lookup("#txtInct");
			Label modifyTotal = (Label) parentModify.lookup("#txtTotal");
			Label modifySales = (Label) parentModify.lookup("#txtSales");

			// 직원번호, 이름, 생년월일, 입사일, 이미지 수정불가
			modifyNum.setDisable(true);
			modifyName.setDisable(true);
			modifyDpBirth.setDisable(true);
			modifyDpDate.setDisable(true);
			modifyImage.setDisable(true);
			
			
			modifyNum.setText(StaffModify.getSf_Num() + "");
			cbRank.setValue(StaffModify.getSf_Rank());
			modifyName.setText(StaffModify.getSf_Name());
			modifyDpBirth.setValue(LocalDate.parse(StaffModify.getSf_Birth()));
			modifyPhone.setText(StaffModify.getSf_Phone() + "");
			modifyAddre.setText(StaffModify.getSf_Addre());
			modifyBasic.setText(StaffModify.getSf_Basic()+"");
			modifyDpDate.setValue(LocalDate.parse(StaffModify.getSf_Date()));
			modifyDpOutDate.setValue(LocalDate.parse(StaffModify.getSf_OutDate()));
			modifyImage.setText(StaffModify.getSf_Image());
			modifyInct.setText(StaffModify.getSf_Inct()+"");
			modifyTotal.setText(StaffModify.getSf_Total()+"");
			modifySales.setText(StaffModify.getSf_Sales()+"");
			
		} catch (IOException e) {
			System.out.println(e);
		}
	} // 수정 버튼 메소드 끝

	// 삭제 버튼을 누르면 삭제 확인창 나온다
	public void handlerBtnDeleteAction(ActionEvent event) {
		try {
			StaffVO staffDelete = tableView.getSelectionModel().getSelectedItem();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffDelete.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnDelete.getScene().getWindow();
			mainStage.setResizable(false);
			mainStage.show();

			Label txtNum = (Label) mainView.lookup("#txtNumber");
			txtNum.setText(staffDelete.getSf_Num() + "");

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

}
