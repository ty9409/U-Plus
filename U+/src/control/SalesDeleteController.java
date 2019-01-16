package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.SalesVO;

public class SalesDeleteController implements Initializable {

	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancel;
	@FXML
	private Label txtNumber;
	@FXML
	private Label txtModel;
	@FXML
	private Label txtName;
	@FXML
	private Label txtInct;
	@FXML
	private Label txtPlan;

	ObservableList<SalesVO> data = FXCollections.observableArrayList();
	int sa_Num; // 판매번호
	int sa_Inct; // 판매정보에 등록된 인센티브
	int Incentive; // 직원정보에서 차감될 성과급
	int sa_Plan; // 요금제
	String sc_Model; // 모델명
	String sf_Name; // 판매자명
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtName.setVisible(false);
		txtNumber.setVisible(false);
		txtModel.setVisible(false);
		txtInct.setVisible(false);
		txtPlan.setVisible(false);
		
		// 삭제 확인 버튼 메소드
		btnOk.setOnAction(event -> {

			try {
				SalesVO sVo = new SalesVO();
				SalesDAO sDao = new SalesDAO();
				boolean result1 = false;
				boolean result2 = false;
				
				sa_Num = Integer.parseInt(txtNumber.getText());
				sc_Model = txtModel.getText();
				sf_Name = txtName.getText();
				sa_Inct = Integer.parseInt(txtInct.getText());
				sa_Plan = Integer.parseInt(txtPlan.getText());
				
				
				result1 = sDao.getSalesDelete(sVo, sa_Num, sc_Model);

				if (result1 == true) {
					// 판매정보 -> 재고수량 +
					sDao.stockAmountPlus(sc_Model);
					// 판매정보 -> 판매건수 -
					sDao.staffSalesMinus(sf_Name);
					if (sa_Plan >= 50000) {
						// 판매정보 요금제가 50000이상 시 인센티브의 50% 차감
						Incentive = (int)(sa_Inct * 0.5); 
						sDao.staffInctMinus(Incentive, sf_Name);
					} else {
						// 판매정보 요금제가 50000미만 시 인센티브의30% 차감 
						Incentive = (int)(sa_Inct * 0.3); 
						sDao.staffInctMinus(Incentive, sf_Name);
					}
				}
			
				

				Stage oldStage = (Stage) btnCancel.getScene().getWindow();
				oldStage.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}); // 삭제 확인 버튼 메소드 끝

		
		// 삭제 취소 메소드
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // 삭제 취소 메소드 끝

	} // initialize 메소드 끝

}
