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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javax.swing.plaf.FileChooserUI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.StockVO;

public class StockRegisterController implements Initializable {

	@FXML
	private TextField txtModel;
	@FXML
	private TextField txtAmount;
	@FXML
	private TextField txtPrice;
	@FXML
	private ComboBox<String> cbCompany;
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

	StockVO stock = new StockVO();
	ObservableList<StockVO> data = FXCollections.observableArrayList();
	private Stage primaryStage;
	String selectFileName = ""; // 이미지 파일명
	String localUrl = ""; // 이미지 파일 경로
	Image localImage;
	int no; // 삭제시 테이블에서 선택한 번호 저장
	File selectedFile = null; // 이미지 처리

	// 이미지 저장할 폴더를 매개변수로 파일 객체 생성
	private File dirSave = new File("C/Images/Stock");
	// 이미지 불러올 파일을 저장할 파일 객체 선언
	private File file = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 이미지 텍스트필드 직접 입력 불가
		txtImage.setDisable(true);

		// 이미지 선택 버튼
		btnImage.setOnAction(event -> handlerBtnImageAction(event));

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

		// 제조사 콤보박스 설정
		cbCompany.setItems(FXCollections.observableArrayList("SAMSUNG", "LG", "PENTECH", "APPLE", "HWAWEI", "XIAOMI",
				"BLACK BERRY"));

		// 등록 버튼 메소드
		btnRegister.setOnAction(event -> {
			try {
				data.removeAll(data);
				StockVO svo = null;
				StockDAO sdao = null;

				String fileName = imageSave(selectedFile);

				if (event.getSource().equals(btnRegister)) {
					svo = new StockVO(txtModel.getText(), Integer.parseInt(txtAmount.getText().trim()),
							Integer.parseInt(txtPrice.getText().trim()),
							cbCompany.getSelectionModel().getSelectedItem(), dpDate.getValue().toString(),
							txtImage.getText());
					sdao = new StockDAO();
					sdao.getStockRegister(svo);

					Stage oldStage = (Stage) btnCancel.getScene().getWindow();
					oldStage.close();

				}

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 정보 등록");
				alert.setContentText(" 등록 실패! \n 재고정보를 제대로 입력하세요!");
				alert.showAndWait();
			}
		}); // 등록 메소드 끝

		// 취소 버튼 메소드
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		});

	} // Initialize 끝

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
}
