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
	private TextField txtNum; // 재고번호
	@FXML
	private TextField txtModel; // 모델명
	@FXML
	private TextField txtAmount; // 수량
	@FXML
	private TextField txtPrice; // 출고가
	@FXML
	private ComboBox<String> cbCompany; // 제조사
	@FXML
	private DatePicker dpDate; // 입고일
	@FXML
	private TextField txtImage; // 이미지 주소
	@FXML
	private Button btnImage; // 이미지 버튼
	@FXML
	private Button btnModify; // 수정 버튼
	@FXML
	private Button btnCancel; // 취소 버튼

	ObservableList<StockVO> data = FXCollections.observableArrayList();
	int selectedIndex; // 테이블에서 선택한 재고 정보 인덱스 저장

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 이미지 버튼 잠금
		btnImage.setDisable(true);
		
		// 수량, 출고가에 숫자만 입력
		DecimalFormat format = new DecimalFormat("###");
		
		// 수량
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

		// 출고가
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

		// 수정 버튼 메소드
		btnModify.setOnAction(event -> handlerBtnModifyAction(event));

		// 취소 버튼
		btnCancel.setOnAction(e -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		});
	}

	// 수정 버튼 메소드 시작
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
	} // 수정 버튼 메소드 끝
}


