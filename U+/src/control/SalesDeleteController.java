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
	int sa_Num; // �ǸŹ�ȣ
	int sa_Inct; // �Ǹ������� ��ϵ� �μ�Ƽ��
	int Incentive; // ������������ ������ ������
	int sa_Plan; // �����
	String sc_Model; // �𵨸�
	String sf_Name; // �Ǹ��ڸ�
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtName.setVisible(false);
		txtNumber.setVisible(false);
		txtModel.setVisible(false);
		txtInct.setVisible(false);
		txtPlan.setVisible(false);
		
		// ���� Ȯ�� ��ư �޼ҵ�
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
					// �Ǹ����� -> ������ +
					sDao.stockAmountPlus(sc_Model);
					// �Ǹ����� -> �ǸŰǼ� -
					sDao.staffSalesMinus(sf_Name);
					if (sa_Plan >= 50000) {
						// �Ǹ����� ������� 50000�̻� �� �μ�Ƽ���� 50% ����
						Incentive = (int)(sa_Inct * 0.5); 
						sDao.staffInctMinus(Incentive, sf_Name);
					} else {
						// �Ǹ����� ������� 50000�̸� �� �μ�Ƽ����30% ���� 
						Incentive = (int)(sa_Inct * 0.3); 
						sDao.staffInctMinus(Incentive, sf_Name);
					}
				}
			
				

				Stage oldStage = (Stage) btnCancel.getScene().getWindow();
				oldStage.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}); // ���� Ȯ�� ��ư �޼ҵ� ��

		
		// ���� ��� �޼ҵ�
		btnCancel.setOnAction(event -> {
			Stage oldStage = (Stage) btnCancel.getScene().getWindow();
			oldStage.close();
		}); // ���� ��� �޼ҵ� ��

	} // initialize �޼ҵ� ��

}
