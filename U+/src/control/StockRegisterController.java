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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.swing.plaf.FileChooserUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.StockVO;

public class StockRegisterController implements Initializable {

	@FXML
	private TextField txtModel;
	@FXML
	private TextField txtAmount;
	@FXML
	private TextField txtPrice;
	@FXML
	private ComboBox<String> cbCompany;
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

	StockVO stock = new StockVO();
	ObservableList<StockVO> data = FXCollections.observableArrayList();
	private Stage primaryStage;
	String selectFileName = ""; // �̹��� ���ϸ�
	String localUrl = ""; // �̹��� ���� ���
	Image localImage;
	int no; // ������ ���̺��� ������ ��ȣ ����
	File selectedFile = null; // �̹��� ó��

	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C/Images/Stock");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// �̹��� �ؽ�Ʈ�ʵ� ���� �Է� �Ұ�
		txtImage.setDisable(true);

		// �̹��� ���� ��ư
		btnImage.setOnAction(event -> handlerBtnImageAction(event));

		// ����, ����� ���ڸ� �Է�
		DecimalFormat format = new DecimalFormat("###");
		// ����
		txtAmount.setTextFormatter(new TextFormatter<>(event -> {
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

		// ���
		txtPrice.setTextFormatter(new TextFormatter<>(event -> {
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

		// ������ �޺��ڽ� ����
		cbCompany.setItems(FXCollections.observableArrayList("SAMSUNG", "LG", "PENTECH", "APPLE", "HWAWEI", "XIAOMI",
				"BLACK BERRY"));

		// ��� ��ư �޼ҵ�
		btnRegister.setOnAction(event -> {
			try {
				data.removeAll(data);
				StockVO svo = null;
				StockDAO sdao = null;

				String fileName = imageSave(selectedFile);

				if (event.getSource().equals(btnRegister)) {
					svo = new StockVO(txtModel.getText(), Integer.parseInt(txtAmount.getText().trim()),
							Integer.parseInt(txtPrice.getText().trim()),
							cbCompany.getSelectionModel().getSelectedItem(), dpDate.getValue().toString(),
							txtImage.getText());
					sdao = new StockDAO();
					sdao.getStockRegister(svo);

					Stage oldStage = (Stage) btnCancel.getScene().getWindow();
					oldStage.close();

				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� ���� ���");
				alert.setContentText(" ��� ����! \n ��������� ����� �Է��ϼ���!");
				alert.showAndWait();
			}
		}); // ��� �޼ҵ� ��

		// ��� ��ư �޼ҵ�
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		});

	} // Initialize ��

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
}
