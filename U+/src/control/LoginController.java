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

		// 아이디 입력에서 Enter키 적용
		txtId.setOnKeyPressed(event -> handerTxtIdKeyPressed(event));
		// 비밀번호 입력에서 Enter키 이벤트 적용
		txtPw.setOnKeyPressed(event -> handerTxtPwKeyPressed(event));
		// 선생님 등록창으로 전환
		btnRegister.setOnAction(event -> handerBtnRegisterAction(event));
		// 로그인
		btnLogin.setOnAction(event -> handerBtnLoginAction(event));
		// 로그인창 닫기
		btnCancel.setOnAction(event -> handerBtnCancelAction(event));
	}

	// 로그인창 닫기
	public void handerBtnCancelAction(ActionEvent event) {
		Platform.exit();
	}

	// 로그인
	public void handerBtnLoginAction(ActionEvent event) {
		login();
	}

	// 로그인 메소드
	private void login() {

		AdministratorDAO ado = new AdministratorDAO();

		boolean success = false;

		try {
			success= ado.getLogin(txtId.getText().trim(), txtPw.getText().trim());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// 로그인 성공시 메인 페이지로 이동
		if (success) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Main.fxml"));
				Parent mainView = (Parent) loader.load();
				Scene scene = new Scene(mainView);
				Stage mainStage = new Stage();
				mainStage.setTitle("U+ 관리 프로그램");
				mainStage.setResizable(false);
				mainStage.setScene(scene);
				Stage oldStage = (Stage) btnLogin.getScene().getWindow();
				oldStage.close();
				mainStage.show();
			} catch (IOException e) {
				System.out.println("오류" + e);
			}
		} else {
			// 아이디 패스워드 확인하라는 창
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("U+ 관리프로그램");
			alert.setHeaderText("로그인 실패!");
			alert.setContentText("아이디 혹은 비밀번호가 일치하지 않습니다.");
			alert.setResizable(false);
			alert.showAndWait();

			txtId.clear();
			txtPw.clear();
		}

	}

	// 패스워드 입력에서 Enter키 이벤트 적용
	public void handerTxtPwKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			login();
		}
	}

	// 아이디 입력에서 엔터키 이벤트 적용
	public void handerTxtIdKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			txtPw.requestFocus();
		}
	}

	// 관리자 등록창으로 전환
	public void handerBtnRegisterAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Administrator.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			Stage oldStage = (Stage) btnLogin.getScene().getWindow();
			oldStage.close();
			mainStage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

}
