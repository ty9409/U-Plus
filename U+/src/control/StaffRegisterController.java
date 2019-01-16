package control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.StaffVO;

public class StaffRegisterController implements Initializable {

	@FXML
	private ComboBox<String> cbRank;
	@FXML
	private TextField txtName;
	@FXML
	private DatePicker dpBirth;
	@FXML
	private TextField txtphone1;
	@FXML
	private TextField txtphone2;
	@FXML
	private TextField txtphone3;
	@FXML
	private TextField txtAddre;
	@FXML
	private TextField txtBasic;
	@FXML
	private TextField txtInct;
	@FXML
	private TextField txtTotal;
	@FXML
	private TextField txtSales;
	@FXML
	private DatePicker dpDate;
	@FXML
	private TextField txtImage;
	@FXML
	private Button btnImage;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnCancel;

	StaffVO staff = new StaffVO();
	ObservableList<StaffVO> data = FXCollections.observableArrayList();
	private Stage primaryStage;
	String selectFileName = ""; // �̹��� ���ϸ�
	String localUrl = ""; // �̹��� ���� ���
	Image localImage;
	int no; // ���� �� ���̺��� ������ ��ȣ ����
	File selectedFile = null; // �̹��� ó��
	int Basic; // �⺻��
	String Name; // �����̸�
	
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/Images/Staff");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// �̹��� �ؽ�Ʈ�ʵ� ���� �Է� �Ұ�
		txtImage.setDisable(true);

		// �̹��� ���� ��ư
		btnImage.setOnAction(event -> handlerBtnImageAction(event));

		// �⺻�޿� ���ڸ� �Է�
		DecimalFormat format = new DecimalFormat("###");
		
		// �⺻��
		txtBasic.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 8) {
				return null;
			} else {
				return event;
			}
		}));

		// ��ȭ��ȣ1
		txtphone1.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 4) {
				return null;
			} else {
				return event;
			}
		}));

		// ��ȭ��ȣ2
		txtphone2.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 5) {
				return null;
			} else {
				return event;
			}
		}));

		// ��ȭ��ȣ3
		txtphone3.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 5) {
				return null;
			} else {
				return event;
			}
		}));

		// ���� �޺��ڽ�����
		cbRank.setItems(FXCollections.observableArrayList("����", "����", "����", "���", "����"));

		// ��� ��ư �޼ҵ�
		btnRegister.setOnAction(event -> {
			try {
				data.removeAll(data);
				StaffVO svo = null;
				StaffDAO sdao = null;

				String fileName = imageSave(selectedFile);

				String phone = txtphone1.getText() + "-" + txtphone2.getText() + "-" + txtphone3.getText();

				if (event.getSource().equals(btnRegister)) {
					
					String outDate = "21000101";
					
					svo = new StaffVO(cbRank.getValue().toString(), txtName.getText(), dpBirth.getValue().toString(),
							phone, txtAddre.getText(), Integer.parseInt(txtBasic.getText().trim()), 0, 0, 0,
							dpDate.getValue().toString(), outDate, txtImage.getText());

					sdao = new StaffDAO();
					sdao.getStaffRegister(svo);
					
					Basic = Integer.parseInt(txtBasic.getText());
					Name = txtName.getText();
					sdao.staffBasicPlus(Basic, Name);

					Stage oldStage = (Stage) btnCancel.getScene().getWindow();
					oldStage.close();
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("���� ���� ���");
				alert.setContentText(" ��� ����! \n ���������� ����� �Է��ϼ���!");
				alert.showAndWait();
			}
		}); // ��� �޼ҵ� ��

		// ��� ��ư �޼ҵ�
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // ��� ��ư �޼ҵ� ��

	} // Initialize ��

	// �̹��� ���� ��ư �޼ҵ�
	public String handlerBtnImageAction(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				// �̹��� ���� ���
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (selectedFile != null) {
			txtImage.setText(selectedFile.getPath());
		}
		return localUrl;
	}

	// imageSave() �̹��� ���� �޼ҵ�.
	// @param �о�� ���� ��ü
	private String imageSave(File file) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// �̹��� ���ϸ� ����
			fileName = file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));

			// ������ �̹��� ���� InputStream�� �������� �̸����� ���� -1
			while ((data = bis.read()) != 1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
		return fileName;

	}

}
