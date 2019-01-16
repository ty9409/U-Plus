package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.StockVO;

public class StockDAO {

	// �ű� ������� ���
	public StockVO getStockRegister(StockVO svo) throws Exception {
		// ������ ó���� ���� SQL��
		String day = svo.getSc_Date();
		String sql = "insert into stock " + "(sc_Num, sc_Model, sc_Amount, sc_Price, sc_Company, sc_Date, sc_Image)"
				+ " values " + "(round(dbms_random.value(100000,999999),0), ?, ?, ?, ?,  to_date('" + day
				+ "', 'YYYY-MM-DD'), ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		StockVO retval = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			// �Է¹��� ��������� ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, svo.getSc_Model());
			pstmt.setInt(2, svo.getSc_Amount());
			pstmt.setInt(3, svo.getSc_Price());
			pstmt.setString(4, svo.getSc_Company());
			pstmt.setString(5, svo.getSc_Image());

			// SQL�� ���� �� ó������� ���´�
			int i = pstmt.executeUpdate();
			retval = new StockVO();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� ���� ��� ����!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� ���� ��� ����!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return retval;
	} // �ű� ����� ��

	// ����� �𵨸��� �Է¹޾� ���� �˻�
	public StockVO getStockSearch(String sc_Model) throws Exception {

		// ������ ó���� ���� SQL��
		String sql = "select * from stock where sc_model like ?";

		/*
		 * StringBuffer sql = new StringBuffer(); sql.append("select * from stock " +
		 * "where sc_model like "); sql.append("? ");
		 */

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StockVO retval = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection() �޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, sc_Model);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				retval = new StockVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getDate(6).toString(), rs.getString(7));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return retval;
	} // �����˻� ��

	// ������� ����
	public StockVO getStockModify(StockVO sVo, int sc_Num) throws Exception {

		String editday = sVo.getSc_Date();

		// ������ ó���� ���� SQL��
		String sql = "update stock set " + "sc_model=?, sc_amount=?, sc_Price=?, sc_Company=?," + " sc_date=to_date(' "
				+ editday + " ' , 'YYYY-MM-DD'), sc_image=?  where sc_num = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		StockVO retval = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// ������ ��������� �����ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sVo.getSc_Model());
			pstmt.setInt(2, sVo.getSc_Amount());
			pstmt.setInt(3, sVo.getSc_Price());
			pstmt.setString(4, sVo.getSc_Company());
			pstmt.setString(5, sVo.getSc_Image());
			pstmt.setInt(6, sVo.getSc_Num());

			// SQL���� ���� �� ó�� ����� ���´�
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� ���� ���� ����!");
				alert.showAndWait();

				retval = new StockVO();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� ���� ���� ����!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return retval;
	} // ������ ��

	// ������� ����
	public void getStockDelete(StockVO sVo, int sc_Num) throws Exception {

		// ������ ó���� ���� SQL��
		String sql = "delete from stock where sc_Num = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sc_Num);

			// SQL���� ���� �� ó������� ���´�
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� ���� ���� ����!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("��� ���� ���� ����!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	} // ������ ��

	// ��ü �������
	public ArrayList<StockVO> getStockAll() {

		// ������ ó���� ���� SQL��
		ArrayList<StockVO> list = new ArrayList<StockVO>();
		String sql = "select * from stock";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StockVO emVo = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new StockVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getDate(6).toString(), rs.getString(7));
				list.add(emVo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	} // ��ü ������� ��

	// �����ͺ��̽����� ������̺��� �÷��� ����
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from stock";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMeta ��ü ���� ����
		ResultSetMetaData rsmd = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {

			}
		}
		return columnName;
	} // �÷��� ���� ��

	// �Ｚ ����Ʈ
	public ArrayList<StockVO> SamsungCompany() {
		ArrayList<StockVO> SamsungList = new ArrayList<StockVO>();
		StockVO svo = new StockVO();
		String sql = "select * from stock where sc_company = 'SAMSUNG'";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new StockVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getDate(6).toString(), rs.getString(7));

				SamsungList.add(svo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return SamsungList;
	} // �Ｚ ����Ʈ ��

	// LG ����Ʈ
	public ArrayList<StockVO> LGCompany() {
		ArrayList<StockVO> LGList = new ArrayList<StockVO>();
		StockVO svo = new StockVO();
		String sql = "select * from stock where sc_company = 'LG'";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new StockVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getDate(6).toString(), rs.getString(7));

				LGList.add(svo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return LGList;
	} // LG ����Ʈ ��

	// APPLE ����Ʈ
	public ArrayList<StockVO> APPLECompany() {
		ArrayList<StockVO> APPLEList = new ArrayList<StockVO>();
		StockVO svo = new StockVO();
		String sql = "select * from stock where sc_company = 'APPLE'";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				svo = new StockVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
						rs.getDate(6).toString(), rs.getString(7));

				APPLEList.add(svo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return APPLEList;
	} // APPLE ����Ʈ ��

	// ETC ����Ʈ
		public ArrayList<StockVO> ETCCompany() {
			ArrayList<StockVO> ETCList = new ArrayList<StockVO>();
			StockVO svo = new StockVO();
			String sql = "select * from stock where sc_company = 'HWAWEI' or sc_company = 'PENTECH' or sc_company = 'XIAOMI' or sc_company =  'BLACK BERRY' ";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					svo = new StockVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
							rs.getDate(6).toString(), rs.getString(7));

					ETCList.add(svo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
					if (con != null)
						con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return ETCList;
		} // ETC ����Ʈ ��
	
}
