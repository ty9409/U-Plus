package control;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import model.StockVO;

public class StockModifyController implements Initializable {

	@FXML
	private TextField txtNum; // ����ȣ
	@FXML
	private TextField txtModel; // �𵨸�
	@FXML
	private TextField txtAmount; // ����
	@FXML
	private TextField txtPrice; // ���
	@FXML
	private ComboBox<String> cbCompany; // ������
	@FXML
	private DatePicker dpDate; // �԰���
	@FXML
	private TextField txtImage; // �̹��� �ּ�
	@FXML
	private Button btnImage; // �̹��� ��ư
	@FXML
	private Button btnModify; // ���� ��ư
	@FXML
	private Button btnCancel; // ��� ��ư

	ObservableList<StockVO> data = FXCollections.observableArrayList();
	int selectedIndex; // ���̺��� ������ ��� ���� �ε��� ����

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// �̹��� ��ư ���
		btnImage.setDisable(true);
		
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

		// ���� ��ư �޼ҵ�
		btnModify.setOnAction(event -> handlerBtnModifyAction(event));

		// ��� ��ư
		btnCancel.setOnAction(e -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		});
	}

	// ���� ��ư �޼ҵ� ����
	public void handlerBtnModifyAction(ActionEvent event) {
		try {
			StockVO sVo = new StockVO();
			StockDAO sDao = new StockDAO();

			sVo = new StockVO(Integer.parseInt(txtNum.getText()), txtModel.getText(),
					Integer.parseInt(txtAmount.getText().trim()), Integer.parseInt(txtPrice.getText().trim()),
					cbCompany.getSelectionModel().getSelectedItem(), dpDate.getValue().toString(), txtImage.getText());

			sDao.getStockModify(sVo, sVo.getSc_Num());

			txtModel.clear();
			txtAmount.clear();
			txtPrice.clear();
			txtImage.clear();

			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	} // ���� ��ư �޼ҵ� ��
}


