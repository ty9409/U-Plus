package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import model.AdministratorVO;

public class AdministratorController implements Initializable {

	@FXML
	private TextField txtId;
	@FXML
	private PasswordField txtPw;
	@FXML
	private PasswordField txtPwRe;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnCancel;

	AdministratorVO administratorVO = new AdministratorVO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnRegister.setDisable(false);
		txtPw.setEditable(true);
		txtPwRe.setEditable(true);

		// 관리자 등록
		btnRegister.setOnAction(event -> handlerBtnRegisterAction(event));
		// 등록창 닫기
		btnCancel.setOnAction(event -> handlerBtnCancelAction(event));
	}

	public void handlerBtnRegisterAction(ActionEvent event) {
		AdministratorVO avo = null;
		AdministratorDAO adao = null;

		boolean administratorOk = false;

		// 패스워드 확인
		if (txtPw.getText().trim().equals(txtPwRe.getText().trim())) {
			avo = new AdministratorVO(txtId.getText().trim(), txtPw.getText().trim());
			adao = new AdministratorDAO();
			try {
				administratorOk = adao.getAdministratorRegiste(avo);
				if (administratorOk) {
					handlerBtnCancelAction(event);
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("U+ 관리 프로그램");
					alert.setHeaderText("관리자 등록에 성공하였습니다!");
					alert.setResizable(false);
					alert.showAndWait();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			txtPw.clear();
			txtPwRe.clear();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("U+ 관리 프로그램");
			alert.setHeaderText("관리자 등록 실패!");
			alert.setContentText("패스워드가 일치하지 않습니다!");
			alert.setResizable(false);
			alert.showAndWait();
		}

	}

	// 관리자 등록창 닫기
	public void handlerBtnCancelAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			mainStage.setResizable(false);
			Stage oldStage = (Stage) btnRegister.getScene().getWindow();
			oldStage.close();
			mainStage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}
}
