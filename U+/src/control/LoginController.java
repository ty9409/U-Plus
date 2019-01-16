package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private TextField txtId;
	@FXML
	private PasswordField txtPw;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnLogin;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// ���̵� �Է¿��� EnterŰ ����
		txtId.setOnKeyPressed(event -> handerTxtIdKeyPressed(event));
		// ��й�ȣ �Է¿��� EnterŰ �̺�Ʈ ����
		txtPw.setOnKeyPressed(event -> handerTxtPwKeyPressed(event));
		// ������ ���â���� ��ȯ
		btnRegister.setOnAction(event -> handerBtnRegisterAction(event));
		// �α���
		btnLogin.setOnAction(event -> handerBtnLoginAction(event));
		// �α���â �ݱ�
		btnCancel.setOnAction(event -> handerBtnCancelAction(event));
	}

	// �α���â �ݱ�
	public void handerBtnCancelAction(ActionEvent event) {
		Platform.exit();
	}

	// �α���
	public void handerBtnLoginAction(ActionEvent event) {
		login();
	}

	// �α��� �޼ҵ�
	private void login() {

		AdministratorDAO ado = new AdministratorDAO();

		boolean success = false;

		try {
			success= ado.getLogin(txtId.getText().trim(), txtPw.getText().trim());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// �α��� ������ ���� �������� �̵�
		if (success) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainStage = new Stage();
				mainStage.setTitle("U+ ���� ���α׷�");
				mainStage.setResizable(false);
				mainStage.setScene(scene);
				Stage oldStage = (Stage) btnLogin.getScene().getWindow();
				oldStage.close();
				mainStage.show();
			} catch (IOException e) {
				System.out.println("����" + e);
			}
		} else {
			// ���̵� �н����� Ȯ���϶�� â
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("U+ �������α׷�");
			alert.setHeaderText("�α��� ����!");
			alert.setContentText("���̵� Ȥ�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			alert.setResizable(false);
			alert.showAndWait();

			txtId.clear();
			txtPw.clear();
		}

	}

	// �н����� �Է¿��� EnterŰ �̺�Ʈ ����
	public void handerTxtPwKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	// ���̵� �Է¿��� ����Ű �̺�Ʈ ����
	public void handerTxtIdKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			txtPw.requestFocus();
		}
	}

	// ������ ���â���� ��ȯ
	public void handerBtnRegisterAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Administrator.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ ���� ���α׷�");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			Stage oldStage = (Stage) btnLogin.getScene().getWindow();
			oldStage.close();
			mainStage.show();
		} catch (IOException e) {
			System.out.println("����" + e);
		}
	}

}
