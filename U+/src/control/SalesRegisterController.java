package control;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.SalesVO;
import model.StockVO;

public class SalesRegisterController implements Initializable {

	@FXML
	private TextField txtName;
	@FXML
	private TextField txtphone1;
	@FXML
	private TextField txtphone2;
	@FXML
	private TextField txtphone3;
	@FXML
	private ComboBox<String> cbModel;
	@FXML
	private TextField txtSerial;
	@FXML
	private TextField txtPlan;
	@FXML
	private ComboBox<String> cbContract;
	@FXML
	private TextField txtInct;
	@FXML
	private ComboBox<String> cbName;
	@FXML
	private DatePicker dpDate;
	@FXML
	private TextField txtMemo;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnCancel;

	SalesVO stock = new SalesVO();
	ObservableList<SalesVO> data = FXCollections.observableArrayList();
	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		SalesDAO sDao = new SalesDAO();
		
		// �𵨸�, �Ǹ��� ����� �޾ƿ´�
		cbModel.setItems(FXCollections.observableArrayList(sDao.getSModel()));
		cbName.setItems(FXCollections.observableArrayList(sDao.getSName()));
		
		// ��ȭ��ȣ, �����, �μ�Ƽ�꿡 ���ڸ� �Է�
		DecimalFormat format = new DecimalFormat("###");

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

		// ���ξ��� �޺��ڽ� ����
		cbContract.setItems(FXCollections.observableArrayList("����������", "���þ�������"));

		// ��� ��ư �޼ҵ�
		btnRegister.setOnAction(event -> {
			try {
				data.removeAll(data);
				SalesVO svo = null;
				SalesDAO sdao = null;
				boolean result1 = false;
				boolean result2 = false;
				int plan; // �����
				int inct; // �Ǹ������� �Էµ� �μ�Ƽ��
				int incentive; // ������ ���޵� ������
				
				
				String phone = txtphone1.getText() + "-" + txtphone2.getText() + "-" + txtphone3.getText();

				if (event.getSource().equals(btnRegister)) {
					svo = new SalesVO(txtName.getText(), phone, cbModel.getValue().toString(), txtSerial.getText(),
							Integer.parseInt(txtPlan.getText()), cbContract.getValue().toString(),
							Integer.parseInt(txtInct.getText()), cbName.getValue().toString(),
							dpDate.getValue().toString(), txtMemo.getText());
					sdao = new SalesDAO();
					result1 = sdao.getSalesRegister(svo);
					
					plan = Integer.parseInt(txtPlan.getText());
					inct = Integer.parseInt(txtInct.getText());

					if(result1==true) {
						// �Ǹ����� -> ������ -
						result2=sdao.stockAmountMinus(cbModel.getValue().toString());
						// �Ǹ����� -> �ǸŰǼ� +
						sdao.staffSalesPlus(cbName.getValue().toString());
						if (plan >= 50000) {
							incentive =(int)(inct * 0.5); // ����� 50000�̻� �� �μ�Ƽ�� 50% ����
							sdao.staffInctPlus(incentive,cbName.getValue().toString());
							// �ѱ޿� = �⺻�� + ������
							//sdao.staffTotal(cbName.getValue().toString());
						} else {
							incentive = (int)(inct * 0.3); // ����� 50000�̸� �� �μ�Ƽ�� 30% ����
							sdao.staffInctPlus(incentive,cbName.getValue().toString());
							// �ѱ޿� = �⺻�� + ������
							//sdao.staffTotal(cbName.getValue().toString());
						}
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("U+ ���� ���α׷�");
						alert.setHeaderText("�Ǹ� ���� ��� ����!");
						alert.showAndWait();
					}
					
				/*	if(result2==true) {
						//�ߵ�ϵǾ����ϴ�.
					} else {
						//��Ͽ� �ǻv�����ϴ�.
					}*/

					Stage oldStage = (Stage) btnCancel.getScene().getWindow();
					oldStage.close();

				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("�Ǹ� ���� ���");
				alert.setContentText(" ��� ����! \n �Ǹ������� ����� �Է��ϼ���!");
				alert.showAndWait();
			}
		}); // ��� �޼ҵ� ��

		// ��� ��ư �޼ҵ�
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // ��� ��ư �޼ҵ� ��

	} // initialize ��

}
