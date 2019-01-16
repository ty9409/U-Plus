package control;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import model.SalesVO;
import model.StaffVO;

public class SalesModifyController implements Initializable {

	@FXML
	private TextField txtNum;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPhone;
	@FXML
	private ComboBox<String> cbModel;
	@FXML
	private TextField txtSerial;
	@FXML
	private TextField txtPlan;
	@FXML
	private ComboBox<String> cbContract;
	@FXML
	private ComboBox<String> cbName;
	@FXML
	private TextField txtInct;
	@FXML
	private DatePicker dpDate;
	@FXML
	private TextField txtMemo;
	@FXML
	private Button btnModify;
	@FXML
	private Button btnCancel;

	ObservableList<SalesVO> data = FXCollections.observableArrayList();
	int selectedIndex; // ���̺��� ������ ��� ���� �ε��� ����

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// ���ξ��� �޺��ڽ� ����
		cbContract.setItems(FXCollections.observableArrayList("����������", "���þ�������"));

		SalesDAO sDao = new SalesDAO();

		// �𵨸�, �Ǹ��� ����� �޾ƿ´�
		cbModel.setItems(FXCollections.observableArrayList(sDao.getSModel()));
		cbName.setItems(FXCollections.observableArrayList(sDao.getSName()));

		// �����, �μ�Ƽ�꿡 ���ڸ� �Է�
		DecimalFormat format = new DecimalFormat("###");

		// �����
		txtPlan.setTextFormatter(new TextFormatter<>(event -> {
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(event.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));

		// �μ�Ƽ��
		txtInct.setTextFormatter(new TextFormatter<>(event -> {
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

		// ��� ��ư �޼ҵ�
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // ��� ��ư �޼ҵ� ��

		// ���� ��ư �޼ҵ�
		btnModify.setOnAction(event -> {
			try {
				SalesVO sVo = new SalesVO();
				SalesDAO sdao = new SalesDAO();

				sVo = new SalesVO(Integer.parseInt(txtNum.getText().trim()), txtName.getText(), txtPhone.getText(),
						cbModel.getValue().toString(), txtSerial.getText(), Integer.parseInt(txtPlan.getText()),
						cbContract.getValue().toString(), Integer.parseInt(txtInct.getText().trim()),
						cbName.getValue().toString(), dpDate.getValue().toString(), txtMemo.getText());

				sdao.getSalesModify(sVo, sVo.getSa_Num());

				Stage oldStage = (Stage) btnCancel.getScene().getWindow();
				oldStage.close();

			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println(e1);
			}

		}); // ���� ��ư �޼ҵ� ��

	} // initialize ��

}
