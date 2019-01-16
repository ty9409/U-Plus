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
	int selectedIndex; // 테이블에서 선택한 재고 정보 인덱스 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 할인약정 콤보박스 설정
		cbContract.setItems(FXCollections.observableArrayList("공시지원금", "선택약정할인"));

		SalesDAO sDao = new SalesDAO();

		// 모델명, 판매자 목록을 받아온다
		cbModel.setItems(FXCollections.observableArrayList(sDao.getSModel()));
		cbName.setItems(FXCollections.observableArrayList(sDao.getSName()));

		// 요금제, 인센티브에 숫자만 입력
		DecimalFormat format = new DecimalFormat("###");

		// 요금제
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

		// 인센티브
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

		// 취소 버튼 메소드
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // 취소 버튼 메소드 끝

		// 수정 버튼 메소드
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

		}); // 수정 버튼 메소드 끝

	} // initialize 끝

}
