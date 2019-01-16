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
	ObservableList<SalesVO> selectSales; // ���̺��� ������ ���� ����
	int selectedIndex; // ���̺��� ������ ��� ���� �ε��� ����

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnDelete.setDisable(true);

		// ���̺� �� �÷��̸� ����
		TableColumn colsa_Num = new TableColumn("�ǸŹ�ȣ");
		colsa_Num.setPrefWidth(80);
		colsa_Num.setStyle("-fx-allignment: CENTER");
		colsa_Num.setCellValueFactory(new PropertyValueFactory<>("sa_Num"));

		TableColumn colsa_Name = new TableColumn("����");
		colsa_Name.setPrefWidth(70);
		colsa_Name.setCellValueFactory(new PropertyValueFactory<>("sa_Name"));

		TableColumn colsa_Phone = new TableColumn("��ȭ��ȣ");
		colsa_Phone.setPrefWidth(110);
		colsa_Phone.setCellValueFactory(new PropertyValueFactory<>("sa_Phone"));

		TableColumn colsc_Model = new TableColumn("�𵨸�");
		colsc_Model.setPrefWidth(120);
		colsc_Model.setCellValueFactory(new PropertyValueFactory<>("sc_Model"));

		TableColumn colsa_Serial = new TableColumn("�Ϸù�ȣ");
		colsa_Serial.setPrefWidth(120);
		colsa_Serial.setCellValueFactory(new PropertyValueFactory<>("sa_Serial"));

		TableColumn colsa_Plan = new TableColumn("�����");
		colsa_Plan.setPrefWidth(70);
		colsa_Plan.setCellValueFactory(new PropertyValueFactory<>("sa_Plan"));

		TableColumn colsa_Contract = new TableColumn("���ξ���");
		colsa_Contract.setPrefWidth(100);
		colsa_Contract.setCellValueFactory(new PropertyValueFactory<>("sa_Contract"));

		TableColumn colsa_Inct = new TableColumn("�μ�Ƽ��");
		colsa_Inct.setPrefWidth(100);
		colsa_Inct.setCellValueFactory(new PropertyValueFactory<>("sa_Inct"));

		TableColumn colsf_Name = new TableColumn("�Ǹ���");
		colsf_Name.setPrefWidth(100);
		colsf_Name.setCellValueFactory(new PropertyValueFactory<>("sf_Name"));

		TableColumn colsa_Date = new TableColumn("�Ǹ���");
		colsa_Date.setPrefWidth(95);
		colsa_Date.setCellValueFactory(new PropertyValueFactory<>("sa_Date"));

		TableColumn colsa_Memo = new TableColumn("��Ÿ�޸�");
		colsa_Memo.setPrefWidth(250);
		colsa_Memo.setCellValueFactory(new PropertyValueFactory<>("sa_Memo"));

		tableView.setItems(data);
		tableView.getColumns().addAll(colsa_Num, colsa_Name, colsa_Phone, colsc_Model, colsa_Serial, colsa_Plan,
				colsa_Contract, colsa_Inct, colsf_Name, colsa_Date, colsa_Memo);

		// ��ü �Ǹ� ����
		all();

		// �˻����� EnterŰ ���� ��
		txtName.setOnKeyPressed(event -> handlerTxtModelKeyPressed(event));
		// �˻� ��ư ��
		btnSearch.setOnAction(event -> handlerBtnSearchAction(event));
		// ��� ��ư ��
		btnRegister.setOnAction(event -> handlerBtnRegisterAction(event));
		// ���� ��ư ��
		btnDelete.setOnAction(event -> handlerBtnDeleteAction(event));
		// �Ǹ� �׷��� ��ư
		//btnBarChart.setOnAction(event -> handlerBtnBarChartAction(event));
		// �ڷΰ��� ��ư ��
		btnBack.setOnAction(event -> handlerBtnBackAction(event));
		// ���̺� �� Ŭ���� ����,���� ����
		tableView.setOnMouseClicked(event -> handlerTableViewMouseClicked(event));

		// ��ü ��� ��ư
		btnAll.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				try {
					data.removeAll(data);
					// ��ü ��� ����
					all();
					btnDelete.setDisable(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} // initialize ��

	// ���� ��ư�� ������ ���� Ȯ��â ���´�
	public void handlerBtnDeleteAction(ActionEvent event) {
		try {
			SalesVO salesDelete = tableView.getSelectionModel().getSelectedItem();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SalesDelete.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ ���� ���α׷�");
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
	} // ���� �޼ҵ� ��

	// ���̺� �� Ŭ���� ����,���� ����
	public void handlerTableViewMouseClicked(MouseEvent event) {
		try {
			if (event.getClickCount() == 1) {
				btnDelete.setDisable(false);
				selectedIndex = selectSales.get(0).getSa_Num();
			}
		} catch (Exception e) {
		}
	} // Ŭ�� ��

	// ��Ϲ�ư�� ������ ����� â�� ���´�
	public void handlerBtnRegisterAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SalesRegister.fxml"));
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
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("�Ǹ����� �˻� ����");
				alert.setContentText("������ �Է��ϼ���!");
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
						alert.setTitle("U+ ���� ���α׷�");
						alert.setHeaderText("�Ǹ� ���� �˻�");
						alert.setContentText(searchName + "��(��) �˻��Ǿ����ϴ�! ����");
						alert.showAndWait();
					}
				}
			}
			if (!searchResult) {
				txtName.clear();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("�Ǹ� ���� �˻�");
				alert.setContentText(searchName + " ��(��) ����Ʈ�� �����ϴ�! ����");
				alert.showAndWait();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("U+ ���� ���α׷�");
			alert.setHeaderText("�Ǹ� ���� �˻�");
			alert.setContentText("�˻� ����!");
			alert.showAndWait();
			e.printStackTrace();

		}
	} // �˻� ��

	// ��ü ��� ����Ʈ
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
