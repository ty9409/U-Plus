package control;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.StaffVO;

public class StaffRegisterController implements Initializable {

	@FXML
	private ComboBox<String> cbRank;
	@FXML
	private TextField txtName;
	@FXML
	private DatePicker dpBirth;
	@FXML
	private TextField txtphone1;
	@FXML
	private TextField txtphone2;
	@FXML
	private TextField txtphone3;
	@FXML
	private TextField txtAddre;
	@FXML
	private TextField txtBasic;
	@FXML
	private TextField txtInct;
	@FXML
	private TextField txtTotal;
	@FXML
	private TextField txtSales;
	@FXML
	private DatePicker dpDate;
	@FXML
	private TextField txtImage;
	@FXML
	private Button btnImage;
	@FXML
	private Button btnRegister;
	@FXML
	private Button btnCancel;

	StaffVO staff = new StaffVO();
	ObservableList<StaffVO> data = FXCollections.observableArrayList();
	private Stage primaryStage;
	String selectFileName = ""; // 이미지 파일명
	String localUrl = ""; // 이미지 파일 경로
	Image localImage;
	int no; // 삭제 시 테이블에서 선택한 번호 저장
	File selectedFile = null; // 이미지 처리
	int Basic; // 기본급
	String Name; // 직원이름
	
	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C:/Images/Staff");
	// 이미지 불러올 파일을 저장할 파일 객체 선언
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 이미지 텍스트필드 직접 입력 불가
		txtImage.setDisable(true);

		// 이미지 선택 버튼
		btnImage.setOnAction(event -> handlerBtnImageAction(event));

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

		// 직급 콤보박스설정
		cbRank.setItems(FXCollections.observableArrayList("점장", "과장", "팀장", "사원", "인턴"));

		// 등록 버튼 메소드
		btnRegister.setOnAction(event -> {
			try {
				data.removeAll(data);
				StaffVO svo = null;
				StaffDAO sdao = null;

				String fileName = imageSave(selectedFile);

				String phone = txtphone1.getText() + "-" + txtphone2.getText() + "-" + txtphone3.getText();

				if (event.getSource().equals(btnRegister)) {
					
					String outDate = "21000101";
					
					svo = new StaffVO(cbRank.getValue().toString(), txtName.getText(), dpBirth.getValue().toString(),
							phone, txtAddre.getText(), Integer.parseInt(txtBasic.getText().trim()), 0, 0, 0,
							dpDate.getValue().toString(), outDate, txtImage.getText());

					sdao = new StaffDAO();
					sdao.getStaffRegister(svo);
					
					Basic = Integer.parseInt(txtBasic.getText());
					Name = txtName.getText();
					sdao.staffBasicPlus(Basic, Name);

					Stage oldStage = (Stage) btnCancel.getScene().getWindow();
					oldStage.close();
				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("직원 정보 등록");
				alert.setContentText(" 등록 실패! \n 직원정보를 제대로 입력하세요!");
				alert.showAndWait();
			}
		}); // 등록 메소드 끝

		// 취소 버튼 메소드
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // 취소 버튼 메소드 끝

	} // Initialize 끝

	// 이미지 선택 버튼 메소드
	public String handlerBtnImageAction(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));
		try {
			selectedFile = fileChooser.showOpenDialog(primaryStage);
			if (selectedFile != null) {
				// 이미지 파일 경로
				localUrl = selectedFile.toURI().toURL().toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (selectedFile != null) {
			txtImage.setText(selectedFile.getPath());
		}
		return localUrl;
	}

	// imageSave() 이미지 저장 메소드.
	// @param 읽어올 파일 객체
	private String imageSave(File file) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// 이미지 파일명 생성
			fileName = file.getName();
			bis = new BufferedInputStream(new FileInputStream(file));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));

			// 선택한 이미지 파일 InputStream의 마지막에 이르렀을 경우는 -1
			while ((data = bis.read()) != 1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
		return fileName;

	}

}
