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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import model.StaffVO;

public class StaffModifyController implements Initializable {

	@FXML
	private TextField txtNum; // ������ȣ
	@FXML
	private ComboBox<String> cbRank; // ����
	@FXML
	private TextField txtName; // �̸�
	@FXML
	private DatePicker dpBirth; // �������
	@FXML
	private TextField txtPhone; // ��ȭ��ȣ
	@FXML
	private TextField txtAddre; // �ּ�
	@FXML
	private TextField txtBasic; // �⺻��
	@FXML
	private DatePicker dpDate; // �Ի���
	@FXML
	private DatePicker dpOutDate; // �����
	@FXML
	private TextField txtImage; // �̹��� �ּ�
	@FXML
	private Label txtInct;
	@FXML
	private Label txtSales;
	@FXML
	private Label txtTotal;
	@FXML
	private Button btnImage; // �̹��� ��ư
	@FXML
	private Button btnModify; // ���� ��ư
	@FXML
	private Button btnCancel; // ��� ��ư

	ObservableList<StaffVO> data = FXCollections.observableArrayList();
	int selectedIndex; // ���̺��� ������ ��� ���� �ε��� ����
	int Basic; // �⺻��
	String Name; // �����̸�
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// ������, �ǸŰǼ�, �ѱ޿� ����
		txtInct.setVisible(false);
		txtSales.setVisible(false);
		txtTotal.setVisible(false);

		// �̹��� ��ư ���
		btnImage.setDisable(true);

		// ���� �޺��ڽ�����
		cbRank.setItems(FXCollections.observableArrayList("����", "����", "����", "���", "����"));

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

		// ��� ��ư �޼ҵ�
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // ��� ��ư �޼ҵ� ��

		// ���� ��ư �޼ҵ�
		btnModify.setOnAction(event -> {
			try {
				StaffVO sVo = new StaffVO();
				StaffDAO sDao = new StaffDAO();

				sVo = new StaffVO(Integer.parseInt(txtNum.getText().trim()),
						cbRank.getSelectionModel().getSelectedItem(), txtName.getText(), dpBirth.getValue().toString(),
						txtPhone.getText(), txtAddre.getText(), Integer.parseInt(txtBasic.getText().trim()),
						Integer.parseInt(txtInct.getText()), Integer.parseInt(txtTotal.getText()),
						Integer.parseInt(txtSales.getText()), dpDate.getValue().toString(),
						dpOutDate.getValue().toString(), txtImage.getText());

				sDao.getStaffModify(sVo, sVo.getSf_Num());
				
				Basic = Integer.parseInt(txtBasic.getText());
				Name = txtName.getText();
				
				sDao.staffBasicPlus(Basic, Name);
				
				
				
				Stage oldStage = (Stage) btnCancel.getScene().getWindow();
				oldStage.close();
				
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println(e1);
			}

		}); // ���� ��ư �޼ҵ� ��

	} // initialize ��

}
