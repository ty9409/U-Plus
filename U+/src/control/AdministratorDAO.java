package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.AdministratorVO;

public class AdministratorDAO {

	public boolean getAdministratorRegiste(AdministratorVO avo) throws Exception {

		String sql = "insert into Administrator" + "(ad_id,ad_pw)" + " values " + "(?,?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean administratorOk = false;

		try {
			// �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, avo.getAd_Id());
			pstmt.setString(2, avo.getAd_Pw());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				/*A	lert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ �������α׷�");
				alert.setHeaderText("������ ���");
				alert.setContentText("������ ��� ����!");
				alert.setResizable(false);
				alert.showAndWait();*/
				administratorOk = true;
				} else {
				/*	Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ �������α׷�");
				alert.setHeaderText("������ ���");
				alert.setContentText("������ ��� ����!");
				alert.setResizable(false);
				alert.showAndWait();*/
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// 6. �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return administratorOk;
	}

	public boolean getLogin(String ad_Id, String ad_Pw) {

		String sql = "select * from administrator where ad_id = ? and ad_pw = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean loginResult = false;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ad_Id);
			pstmt.setString(2, ad_Pw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				loginResult = true; // ���̵�� �н����尡 ��ġ�Ѵ�.
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return loginResult;
	}
}
