package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.StaffVO;

public class StaffDeleteController implements Initializable {

	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancel;
	@FXML
	private Label txtNumber;

	ObservableList<StaffVO> data = FXCollections.observableArrayList();
	int sc_Num;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtNumber.setVisible(false);

		// ���� Ȯ�� ��ư �޼ҵ�
		btnOk.setOnAction(event -> handlerBtnOkAction(event));
		// ���� ��� �޼ҵ�
		btnCancel.setOnAction(event -> handlerBtnCancelAction(event));

	} // initialize �޼ҵ� ��

	// ���� Ȯ�� ��ư �޼ҵ�
	public void handlerBtnOkAction(ActionEvent event) {
		try {
			StaffVO sVo = new StaffVO();
			StaffDAO sDao = new StaffDAO();

			sc_Num = Integer.parseInt(txtNumber.getText());

			sDao.getStaffDelete(sVo, sc_Num);

			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	} // ���� Ȯ�� ��ư �޼ҵ� ��

	// ���� ��� �޼ҵ�
	public void handlerBtnCancelAction(ActionEvent event) {
		Stage oldStage = (Stage) btnCancel.getScene().getWindow();
		oldStage.close();
	} // ���� ��� �޼ҵ� ��

}
