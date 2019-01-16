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
	private TextField txtModel; // �𵨸�
	@FXML
	private Button btnSearch; // �˻� ��ư
	@FXML
	private Button btnRegister; // ��� ��ư
	@FXML
	private Button btnModify; // ���� ��ư
	@FXML
	private Button btnSamsung;
	@FXML
	private Button btnLG;
	@FXML
	private Button btnAPPLE;
	@FXML
	private Button btnETC;
	@FXML
	private Button btnDelete; // ���� ��ư
	@FXML
	private Button btnClose; // ��Ʈ ��� ��ư
	@FXML
	private Button btnAll; // ��ü ����Ʈ ��ư
	@FXML
	private Button btnBack; // �ڷΰ��� ��ư
	@FXML
	private ImageView imageView; // ��� �̹���

	StockVO stock = new StockVO();
	ObservableList<StockVO> data = FXCollections.observableArrayList();
	ObservableList<StockVO> selectStock; // ���̺��� ������ ���� ����
	int selectedIndex; // ���̺��� ������ ��� ���� �ε��� ����

	String selectFileName = ""; // �̹��� ���ϸ�
	String localUrl = ""; // �̹��� ���� ���
	Image localImage;

	// �̹��� ó��
	File selectedFile = null;

	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/Images/Stock");

	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnModify.setDisable(true);
		btnDelete.setDisable(true);

		// ���̺� �� �÷��̸� ����
		TableColumn colsc_Num = new TableColumn("����ȣ");
		colsc_Num.setPrefWidth(90);
		colsc_Num.setStyle("-fx-allignment: CENTER");
		colsc_Num.setCellValueFactory(new PropertyValueFactory<>("sc_Num"));

		TableColumn colsc_Model = new TableColumn("�𵨸�");
		colsc_Model.setPrefWidth(120);
		colsc_Model.setCellValueFactory(new PropertyValueFactory<>("sc_Model"));

		TableColumn colsc_Amount = new TableColumn("����");
		colsc_Amount.setPrefWidth(50);
		colsc_Amount.setCellValueFactory(new PropertyValueFactory<>("sc_Amount"));

		TableColumn colsc_Price = new TableColumn("���");
		colsc_Price.setPrefWidth(90);
		colsc_Price.setCellValueFactory(new PropertyValueFactory<>("sc_Price"));

		TableColumn colsc_Company = new TableColumn("������");
		colsc_Company.setPrefWidth(90);
		colsc_Company.setCellValueFactory(new PropertyValueFactory<>("sc_Company"));

		TableColumn colsc_Date = new TableColumn("�԰���");
		colsc_Date.setPrefWidth(90);
		colsc_Date.setCellValueFactory(new PropertyValueFactory<>("sc_Date"));

		TableColumn colsc_Image = new TableColumn("�̹���");
		colsc_Image.setPrefWidth(270);
		colsc_Image.setCellValueFactory(new PropertyValueFactory<>("sc_Image"));

		tableView.setItems(data);
		tableView.getColumns().addAll(colsc_Num, colsc_Model, colsc_Amount, colsc_Price, colsc_Company, colsc_Date,
				colsc_Image);


		// ��ü ��� ����
		all();

		// �˻����� EnterŰ ���� ��
		txtModel.setOnKeyPressed(event -> handlerTxtModelKeyPressed(event));
		// �˻� ��ư ��
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));
		// ��� ��ư ��
		btnRegister.setOnAction(event -> handlerBtnRegisterAction(event));
		// ���� ��ư ��
		btnModify.setOnAction(event -> handlerBtnModifyAction(event));
		// �Ｚ ��Ʈ ��ư
		btnSamsung.setOnAction(event -> handlerBtnSamsungAction(event));
		// LG ��Ʈ ��ư
		btnLG.setOnAction(event -> handlerBtnLGAction(event));
		// APPLE ��Ʈ ��ư
		btnAPPLE.setOnAction(event -> handlerBtnAPPLEAction(event));
		// ��Ÿȸ�� ��Ʈ ��ư
		btnETC.setOnAction(event -> handlerBtnETCAction(event));
		// ���� ��ư ��
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// �ڷΰ��� ��ư ��
		btnBack.setOnAction(event -> handlerBtnBackAction(event));

		// Ŭ������ �� �̹����信 ���
		tableView.setOnMouseClicked(event -> handlerTableViewMouseClicked(event));

		// ��ü ��� ��ư
		btnAll.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					// ��ü ��� ����
					all();
					btnModify.setDisable(true);
					btnDelete.setDisable(true);
					imageView.setImage(new Image("image/ImageChoice.png", false));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	} // initialize ��

	// �Ｚ ��Ʈ �޼ҵ�
	public void handlerBtnSamsungAction(ActionEvent event) {
		StockDAO sdao = new StockDAO();
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnRegister.getScene().getWindow());
			dialog.setResizable(false);
			dialog.setTitle(" U+ ���� ���α׷�");
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/StockBarChart.fxml"));
			BarChart barChart = (BarChart) parent.lookup("#barChart");
			
			XYChart.Series seriesSamsung = new XYChart.Series();
			seriesSamsung.setName("�Ｚ ��� ����");
			ObservableList SamsungList = FXCollections.observableArrayList();
			ArrayList<StockVO> SAMSUNG = new ArrayList<StockVO>();
			SAMSUNG = sdao.SamsungCompany();
			for (int i = 0; i < SAMSUNG.size(); i++) {
				SamsungList.add(new XYChart.Data(SAMSUNG.get(i).getSc_Model().trim(), SAMSUNG.get(i).getSc_Amount()));
			}
			
			seriesSamsung.setData(SamsungList);
			barChart.getData().add(seriesSamsung);
			
			// ��� ��ư �޼ҵ�
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e -> dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // �Ｚ ��� ��Ʈ ��

	// LG ��Ʈ �޼ҵ�
	public void handlerBtnLGAction(ActionEvent event) {
		StockDAO sdao = new StockDAO();
		try {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(btnRegister.getScene().getWindow());
			dialog.setResizable(false);
			dialog.setTitle(" U+ ���� ���α׷�");
			
			Parent parent = FXMLLoader.load(getClass().getResource("/view/StockBarChart.fxml"));
			BarChart barChart = (BarChart) parent.lookup("#barChart");
			
			XYChart.Series seriesLG = new XYChart.Series();
			seriesLG.setName("LG ��� ����");
			ObservableList LGList = FXCollections.observableArrayList();
			ArrayList<StockVO> LG = new ArrayList<StockVO>();
			LG = sdao.LGCompany();
			for (int i = 0; i < LG.size(); i++) {
				LGList.add(new XYChart.Data(LG.get(i).getSc_Model().trim(), LG.get(i).getSc_Amount()));
			}
			
			seriesLG.setData(LGList);
			barChart.getData().add(seriesLG);
			
			// ��� ��ư �޼ҵ�
			Button btnClose = (Button) parent.lookup("#btnClose");
			btnClose.setOnAction(e -> dialog.close());
			
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // LG ��� ��Ʈ ��
	
	// APPLE ��Ʈ �޼ҵ�
		public void handlerBtnAPPLEAction(ActionEvent event) {
			StockDAO sdao = new StockDAO();
			try {
				Stage dialog = new Stage(StageStyle.UTILITY);
				dialog.initModality(Modality.WINDOW_MODAL);
				dialog.initOwner(btnRegister.getScene().getWindow());
				dialog.setResizable(false);
				dialog.setTitle(" U+ ���� ���α׷�");
				
				Parent parent = FXMLLoader.load(getClass().getResource("/view/StockBarChart.fxml"));
				BarChart barChart = (BarChart) parent.lookup("#barChart");
				
				XYChart.Series seriesAPPLE = new XYChart.Series();
				seriesAPPLE.setName("APPLE ��� ����");
				ObservableList APPLEList = FXCollections.observableArrayList();
				ArrayList<StockVO> LG = new ArrayList<StockVO>();
				LG = sdao.APPLECompany();
				for (int i = 0; i < LG.size(); i++) {
					APPLEList.add(new XYChart.Data(LG.get(i).getSc_Model().trim(), LG.get(i).getSc_Amount()));
				}
				
				seriesAPPLE.setData(APPLEList);
				barChart.getData().add(seriesAPPLE);
				
				// ��� ��ư �޼ҵ�
				Button btnClose = (Button) parent.lookup("#btnClose");
				btnClose.setOnAction(e -> dialog.close());
				
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.show();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // APPLE ��� ��Ʈ ��
		
		// ETC ��Ʈ �޼ҵ�
				public void handlerBtnETCAction(ActionEvent event) {
					StockDAO sdao = new StockDAO();
					try {
						Stage dialog = new Stage(StageStyle.UTILITY);
						dialog.initModality(Modality.WINDOW_MODAL);
						dialog.initOwner(btnRegister.getScene().getWindow());
						dialog.setResizable(false);
						dialog.setTitle(" U+ ���� ���α׷�");
						
						Parent parent = FXMLLoader.load(getClass().getResource("/view/StockBarChart.fxml"));
						BarChart barChart = (BarChart) parent.lookup("#barChart");
						
						XYChart.Series seriesETC = new XYChart.Series();
						seriesETC.setName(" ETC ��� ����");
						ObservableList ETCList = FXCollections.observableArrayList();
						ArrayList<StockVO> ETC = new ArrayList<StockVO>();
						ETC = sdao.ETCCompany();
						for (int i = 0; i < ETC.size(); i++) {
							ETCList.add(new XYChart.Data(ETC.get(i).getSc_Model().trim(), ETC.get(i).getSc_Amount()));
						}
						
						seriesETC.setData(ETCList);
						barChart.getData().add(seriesETC);
						
						// ��� ��ư �޼ҵ�
						Button btnClose = (Button) parent.lookup("#btnClose");
						btnClose.setOnAction(e -> dialog.close());
						
						Scene scene = new Scene(parent);
						dialog.setScene(scene);
						dialog.show();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // ETC ��� ��Ʈ ��
	
				
				
	// �˻����� EnterŰ ���� �޼ҵ�
	public void handlerTxtModelKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			search();
		}
	}

	// �˻�
	public void handlerBtnSearchAction(ActionEvent event) {
		search();
	}

	// �˻� �޼ҵ�
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
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� �˻� ����");
				alert.setContentText("�𵨸��� �Է��ϼ���!");
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
						alert.setTitle("U+ ���� ���α׷�");
						alert.setHeaderText("��� ���� �˻�");
						alert.setContentText(searchModel+ " ��(��) �˻��Ǿ����ϴ�!");
						alert.showAndWait();
					}
				}
			}
			if (!searchResult) {
				txtModel.clear();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� ���� �˻�");
				alert.setContentText(searchModel+ " ��(��) ����Ʈ�� �����ϴ�!");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("U+ ���� ���α׷�");
			alert.setHeaderText("��� ���� �˻�");
			alert.setContentText("�˻� ����!");
			alert.showAndWait();
			e.printStackTrace();

		}
	} // �˻� ��

	// ���� ��ư�� ������ ���� Ȯ��â ���´�
	public void handlerBtnDeleteAction(ActionEvent event) {
		try {
			StockVO stockDelete = tableView.getSelectionModel().getSelectedItem();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StockDelete.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ ���� ���α׷�");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnDelete.getScene().getWindow();
			mainStage.setResizable(false);
			mainStage.show();
			
			Label txtNum = (Label) mainView.lookup("#txtNumber");
			txtNum.setText(stockDelete.getSc_Num()+"");
			
			
			
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

	// ������ư�� ������ ������ â�� ���´�
	public void handlerBtnModifyAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StockModify.fxml"));
			Parent parentModify = (Parent) loader.load();
			Scene scene = new Scene(parentModify);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ ���� ���α׷�");
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
	} // ���� ��ư �޼ҵ� ��

	// ���̺� �� Ŭ���� �̹��� �信 ���
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
	} // Ŭ�� ��

	// ��Ϲ�ư�� ������ ����� â�� ���´�
	public void handlerBtnRegisterAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StockRegister.fxml"));
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

	// ��ü ��� ����Ʈ
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
