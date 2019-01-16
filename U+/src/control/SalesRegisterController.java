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
		
		// 모델명, 판매자 목록을 받아온다
		cbModel.setItems(FXCollections.observableArrayList(sDao.getSModel()));
		cbName.setItems(FXCollections.observableArrayList(sDao.getSName()));
		
		// 전화번호, 요금제, 인센티브에 숫자만 입력
		DecimalFormat format = new DecimalFormat("###");

		// 전화번호1
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

		// 전화번호2
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

		// 전화번호3
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

		// 할인약정 콤보박스 설정
		cbContract.setItems(FXCollections.observableArrayList("공시지원금", "선택약정할인"));

		// 등록 버튼 메소드
		btnRegister.setOnAction(event -> {
			try {
				data.removeAll(data);
				SalesVO svo = null;
				SalesDAO sdao = null;
				boolean result1 = false;
				boolean result2 = false;
				int plan; // 요금제
				int inct; // 판매정보에 입력된 인센티브
				int incentive; // 직원에 지급될 성과급
				
				
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
						// 판매정보 -> 재고수량 -
						result2=sdao.stockAmountMinus(cbModel.getValue().toString());
						// 판매정보 -> 판매건수 +
						sdao.staffSalesPlus(cbName.getValue().toString());
						if (plan >= 50000) {
							incentive =(int)(inct * 0.5); // 요금제 50000이상 시 인센티브 50% 지급
							sdao.staffInctPlus(incentive,cbName.getValue().toString());
							// 총급여 = 기본급 + 성과급
							//sdao.staffTotal(cbName.getValue().toString());
						} else {
							incentive = (int)(inct * 0.3); // 요금제 50000미만 시 인센티브 30% 지급
							sdao.staffInctPlus(incentive,cbName.getValue().toString());
							// 총급여 = 기본급 + 성과급
							//sdao.staffTotal(cbName.getValue().toString());
						}
					} else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("U+ 관리 프로그램");
						alert.setHeaderText("판매 정보 등록 실패!");
						alert.showAndWait();
					}
					
				/*	if(result2==true) {
						//잘등록되었습니다.
					} else {
						//등록에 실퍃샜습니다.
					}*/

					Stage oldStage = (Stage) btnCancel.getScene().getWindow();
					oldStage.close();

				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("판매 정보 등록");
				alert.setContentText(" 등록 실패! \n 판매정보를 제대로 입력하세요!");
				alert.showAndWait();
			}
		}); // 등록 메소드 끝

		// 취소 버튼 메소드
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // 취소 버튼 메소드 끝

	} // initialize 끝

}
