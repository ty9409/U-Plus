package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController implements Initializable {

	@FXML
	private Button btnStock;
	@FXML
	private Button btnStaff;
	@FXML
	private Button btnSales;

	@Override

	public void initialize(URL location, ResourceBundle resources) {

		// 재고관리 창으로
		btnStock.setOnAction(event -> handlerBtnStockAction(event));
		// 직원관리 창으로
		btnStaff.setOnAction(event -> handlerBtnStaffAction(event));
		// 판매정보관리 창으로
		btnSales.setOnAction(event -> handlerBtnSalesAction(event));
	}

	// 재고관리 창
	private void handlerBtnStockAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Stock.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnStock.getScene().getWindow();
			oldStage.close();
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	// 직원관리 창
	private void handlerBtnStaffAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Staff.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnStaff.getScene().getWindow();
			oldStage.close();
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}

	// 판매관리 창
	private void handlerBtnSalesAction(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Sales.fxml"));
			Parent mainView = (Parent) loader.load();
			Scene scene = new Scene(mainView);
			Stage mainStage = new Stage();
			mainStage.setTitle("U+ 관리 프로그램");
			mainStage.setScene(scene);
			Stage oldStage = (Stage) btnStock.getScene().getWindow();
			oldStage.close();
			mainStage.setResizable(false);
			mainStage.show();
		} catch (IOException e) {
			System.out.println("오류" + e);
		}
	}
}
