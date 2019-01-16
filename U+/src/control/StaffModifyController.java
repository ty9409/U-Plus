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
	private TextField txtNum; // 직원번호
	@FXML
	private ComboBox<String> cbRank; // 직급
	@FXML
	private TextField txtName; // 이름
	@FXML
	private DatePicker dpBirth; // 생년월일
	@FXML
	private TextField txtPhone; // 전화번호
	@FXML
	private TextField txtAddre; // 주소
	@FXML
	private TextField txtBasic; // 기본급
	@FXML
	private DatePicker dpDate; // 입사일
	@FXML
	private DatePicker dpOutDate; // 퇴사일
	@FXML
	private TextField txtImage; // 이미지 주소
	@FXML
	private Label txtInct;
	@FXML
	private Label txtSales;
	@FXML
	private Label txtTotal;
	@FXML
	private Button btnImage; // 이미지 버튼
	@FXML
	private Button btnModify; // 수정 버튼
	@FXML
	private Button btnCancel; // 취소 버튼

	ObservableList<StaffVO> data = FXCollections.observableArrayList();
	int selectedIndex; // 테이블에서 선택한 재고 정보 인덱스 저장
	int Basic; // 기본급
	String Name; // 직원이름
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 성과급, 판매건수, 총급여 숨김
		txtInct.setVisible(false);
		txtSales.setVisible(false);
		txtTotal.setVisible(false);

		// 이미지 버튼 잠금
		btnImage.setDisable(true);

		// 직급 콤보박스설정
		cbRank.setItems(FXCollections.observableArrayList("점장", "과장", "팀장", "사원", "인턴"));

		// 기본급에 숫자만 입력
		DecimalFormat format = new DecimalFormat("###");

		// 기본급
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

		// 취소 버튼 메소드
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // 취소 버튼 메소드 끝

		// 수정 버튼 메소드
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

		}); // 수정 버튼 메소드 끝

	} // initialize 끝

}
