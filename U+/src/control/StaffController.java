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
	private Button btnRegister; // ��� ��ư
	@FXML
	private Button btnModify; // ���� ��ư
	@FXML
	private Button btnDelete; // ���� ��ư
	@FXML
	private Button btnBarChart; // �Ǹ� ��Ʈ ��ư
	@FXML
	private Button btnBarChart2; // �޿� ��Ʈ ��ư
	@FXML
	private Button btnAll; // ��ü ����Ʈ ��ư
	@FXML
	private Button btnBack; // �ڷΰ��� ��ư
	@FXML
	private ImageView imageView; // ���� �̹���
	@FXML
	private Button btnClose; // ��Ʈ ��� ��ư

	StaffVO staff = new StaffVO();
	ObservableList<StaffVO> data = FXCollections.observableArrayList();
	ObservableList<StaffVO> selectStaff; // ���̺��� ������ ���� ����
	int selectedIndex; // ���̺��� ������ ���� ���� �ε��� ����

	String selectFileName = ""; // �̹��� ���ϸ�
	String localUrl = ""; // �̹��� ���� ���
	Image localImage;

	// �̹��� ó��
	File selectedFile = null;

	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/Images/Staff");

	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnModify.setDisable(true);
		btnDelete.setDisable(true);

		// ���̺� �� �÷��̸� ����
		TableColumn colsf_Num = new TableColumn("������ȣ");
		colsf_Num.setPrefWidth(65);
		colsf_Num.setStyle("-fx-allignment: CENTER");
		colsf_Num.setCellValueFactory(new PropertyValueFactory<>("sf_Num"));

		TableColumn colsf_Rank = new TableColumn("����");
		colsf_Rank.setPrefWidth(60);
		colsf_Rank.setCellValueFactory(new PropertyValueFactory<>("sf_Rank"));

		TableColumn colsf_Name = new TableColumn("�̸�");
		colsf_Name.setPrefWidth(80);
		colsf_Name.setCellValueFactory(new PropertyValueFactory<>("sf_Name"));

		TableColumn colsf_Birth = new TableColumn("�������");
		colsf_Birth.setPrefWidth(90);
		colsf_Birth.setCellValueFactory(new PropertyValueFactory<>("sf_Birth"));

		TableColumn colsf_Phone = new TableColumn("��ȭ��ȣ");
		colsf_Phone.setPrefWidth(110);
		colsf_Phone.setCellValueFactory(new PropertyValueFactory<>("sf_Phone"));

		TableColumn colsf_Addre = new TableColumn("�ּ�");
		colsf_Addre.setPrefWidth(150);
		colsf_Addre.setCellValueFactory(new PropertyValueFactory<>("sf_Addre"));

		TableColumn colsf_Basic = new TableColumn("�⺻��");
		colsf_Basic.setPrefWidth(90);
		colsf_Basic.setCellValueFactory(new PropertyValueFactory<>("sf_Basic"));

		TableColumn colsf_Inct = new TableColumn("������");
		colsf_Inct.setPrefWidth(80);
		colsf_Inct.setCellValueFactory(new PropertyValueFactory<>("sf_Inct"));

		TableColumn colsf_Total = new TableColumn("�� �޿�");
		colsf_Total.setPrefWidth(90);
		colsf_Total.setCellValueFactory(new PropertyValueFactory<>("sf_Total"));

		TableColumn colsf_Sales = new TableColumn("�ǸŰǼ�");
		colsf_Sales.setPrefWidth(80);
		colsf_Sales.setCellValueFactory(new PropertyValueFactory<>("sf_Sales"));

		TableColumn colsf_Date = new TableColumn("�Ի���");
		colsf_Date.setPrefWidth(95);
		colsf_Date.setCellValueFactory(new PropertyValueFactory<>("sf_Date"));
		
		TableColumn colsf_OutDate = new TableColumn("�����");
		colsf_OutDate.setPrefWidth(95);
		colsf_OutDate.setCellValueFactory(new PropertyValueFactory<>("sf_OutDate"));

		TableColumn colsf_Image = new TableColumn("�̹���");
		colsf_Image.setPrefWidth(120);
		colsf_Image.setCellValueFactory(new PropertyValueFactory<>("sf_Image"));

		tableView.setItems(data);
		tableView.getColumns().addAll(colsf_Num, colsf_Rank, colsf_Name, colsf_Birth, colsf_Phone, colsf_Addre,
				colsf_Basic, colsf_Inct, colsf_Total, colsf_Sales, colsf_Date, colsf_OutDate, colsf_Image);

		// ��ü ��� ����
		all();

		// ��� ��ư ��
		btnRegister.setOnAction(event -> handlerBtnRegisterAction(event));
		// ���� ��ư ��
		btnModify.setOnAction(event -> handlerBtnModifyAction(event));
		// ���� ��ư ��
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// �Ǹŷ� ��Ʈ
		btnBarChart.setOnAction(event -> handlerBtnBarChartAction(event));
		// �޿� ��Ʈ
		btnBarChart2.setOnAction(event -> handlerBtnBarChart2Action(event));
		// �ڷΰ��� ��ư ��
		btnBack.setOnAction(event -> handlerBtnBackAction(event));
		// Ŭ������ �� �̹����信 ���
		tableView.setOnMouseClicked(event -> handlerTableViewMouseClicked(event));

		// ��ü ��� ��ư
		btnAll.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					// ���� ��ü ����
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

	// ���̺� �� Ŭ���� �̹��� �信 ���
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

	// ��ü ��� ����Ʈ
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

	
	// �Ǹ� ��Ʈ �޼ҵ�
	public void handlerBtnBarChartAction(ActionEvent event) {
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnRegister.getScene().getWindow());
			dialog.setTitle(" �Ǹ� �׷���");
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/StaffBarchart.fxml"));
			
			BarChart barChart = (BarChart) parent.lookup("#barChart");
			
			XYChart.Series seriesSales = new XYChart.Series();
			seriesSales.setName("�Ǹ� �Ǽ�");
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
	} // �Ǹ� ��Ʈ ��

	// �޿� ��Ʈ �޼ҵ�
		public void handlerBtnBarChart2Action(ActionEvent event) {
			try {
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnRegister.getScene().getWindow());
				dialog.setTitle(" �޿� �׷���");
				
				Parent parent = FXMLLoader.load(getClass().getResource("/view/StaffBarChart.fxml"));
				
				BarChart barChart = (BarChart) parent.lookup("#barChart");
				
				XYChart.Series seriesBasic = new XYChart.Series();
				seriesBasic.setName("�⺻��");
				ObservableList BasicList = FXCollections.observableArrayList();
				for (int i = 0; i < data.size(); i++) {
					BasicList.add(new XYChart.Data(data.get(i).getSf_Name(), Integer.parseInt(data.get(i).getSf_Basic()+"")));
				}
				seriesBasic.setData(BasicList);
				barChart.getData().add(seriesBasic);
				
				XYChart.Series seriesInct = new XYChart.Series();
				seriesInct.setName("������");
				ObservableList InctList = FXCollections.observableArrayList();
				for (int i = 0; i < data.size(); i++) {
					InctList.add(new XYChart.Data(data.get(i).getSf_Name(), Integer.parseInt(data.get(i).getSf_Inct()+"")));
				}
				seriesInct.setData(InctList);
				barChart.getData().add(seriesInct);
				
				XYChart.Series seriesTotal = new XYChart.Series();
				seriesTotal.setName("�� �޿�");
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
		} // �޿� ��Ʈ ��
	
	// ��Ϲ�ư�� ������ ����� â�� ���´�
	public void handlerBtnRegisterAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffRegister.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ ���� ���α׷�");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnRegister.getScene().getWindow();
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ������ư�� ������ ������ â�� ���´�
	public void handlerBtnModifyAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffModify.fxml"));
			Parent parentModify = (Parent) loader.load();
			Scene scene = new Scene(parentModify);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ ���� ���α׷�");
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

			// ������ȣ, �̸�, �������, �Ի���, �̹��� �����Ұ�
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
	} // ���� ��ư �޼ҵ� ��

	// ���� ��ư�� ������ ���� Ȯ��â ���´�
	public void handlerBtnDeleteAction(ActionEvent event) {
		try {
			StaffVO staffDelete = tableView.getSelectionModel().getSelectedItem();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffDelete.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ ���� ���α׷�");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnDelete.getScene().getWindow();
			mainStage.setResizable(false);
			mainStage.show();

			Label txtNum = (Label) mainView.lookup("#txtNumber");
			txtNum.setText(staffDelete.getSf_Num() + "");

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // ���� �޼ҵ� ��

	// �ڷΰ��� ��ư�� ������ �۾����� â���� ���ư���
	public void handlerBtnBackAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ ���� ���α׷�");
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
