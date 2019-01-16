package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.SalesVO;
import model.StockVO;

public class SalesDAO {

	// ����� �𵨸��� �Է¹޾� ���� �˻�
	public SalesVO getSalesSearch(String sa_Name) throws Exception {

		// ������ ó���� ���� SQL��
		String sql = "select * from sales where sa_name = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO retval = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sa_Name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				retval = new SalesVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getDate(10).toString(),
						rs.getString(11));
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

	// �ű� �Ǹ����� ���
	public boolean getSalesRegister(SalesVO svo) throws Exception {
		// ������ ó���� ���� SQL��
		boolean result = false;
		String day = svo.getSa_Date();
		String sql = "insert into sales "
				+ "(sa_num, sa_name, sa_phone, sc_model, sa_serial, sa_plan, sa_contract, sa_inct, sf_name, sa_date, sa_memo) "
				+ " values " + "(round(dbms_random.value(10000000,99999999),0), ?, ?, ?, ?, ?, ?, ?, ?, to_date(' "
				+ day + " ', 'YYYY-MM-DD'), ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		SalesVO retval = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			// �Է¹��� ��������� ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, svo.getSa_Name());
			pstmt.setString(2, svo.getSa_Phone());
			pstmt.setString(3, svo.getSc_Model());
			pstmt.setString(4, svo.getSa_Serial());
			pstmt.setInt(5, svo.getSa_Plan());
			pstmt.setString(6, svo.getSa_Contract());
			pstmt.setInt(7, svo.getSa_Inct());
			pstmt.setString(8, svo.getSf_Name());
			pstmt.setString(9, svo.getSa_Memo());

			// SQL�� ���� �� ó������� ���´�
			int i = pstmt.executeUpdate();

			retval = new SalesVO();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("�Ǹ� ���� ��� ����!");
				alert.showAndWait();
				result = true;
			} else {
				result = false;
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
		return result;
	} // �Ǹŵ�� ��

	/*
	 * // �Ǹ� ���� ���� public SalesVO getSalesModify(SalesVO svo, int sa_Num) throws
	 * Exception {
	 * 
	 * String editday = svo.getSa_Date();
	 * 
	 * // ������ ó���� ���� SQL�� String sql = "update sales set " +
	 * " sa_name=?, sa_phone=?, sc_model=?, sa_serial=?, sa_plan=?, sa_contract=?, sa_inct=?, sf_name=?, sa_date=to_date(' "
	 * + editday + " ' , 'YYYY-MM-DD'), sa_memo=?  where sa_num = ?";
	 * 
	 * Connection con = null; PreparedStatement pstmt = null; SalesVO retval = null;
	 * 
	 * try { // DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ���� con =
	 * DBUtil.getConnection();
	 * 
	 * // ������ ��������� �����ϱ� ���Ͽ� SQL������ ���� pstmt = con.prepareStatement(sql);
	 * pstmt.setString(1, svo.getSa_Name()); pstmt.setString(2, svo.getSa_Phone());
	 * pstmt.setString(3, svo.getSc_Model()); pstmt.setString(4,
	 * svo.getSa_Serial()); pstmt.setInt(5, svo.getSa_Plan()); pstmt.setString(6,
	 * svo.getSa_Contract()); pstmt.setInt(7, svo.getSa_Inct()); pstmt.setString(8,
	 * svo.getSf_Name()); pstmt.setString(9, svo.getSa_Memo()); pstmt.setInt(10,
	 * svo.getSa_Num());
	 * 
	 * // SQL���� ���� �� ó�� ����� ���´� int i = pstmt.executeUpdate();
	 * 
	 * if (i == 1) { Alert alert = new Alert(AlertType.INFORMATION);
	 * alert.setTitle("U+ ���� ���α׷�"); alert.setHeaderText("�Ǹ� ���� ���� ����!");
	 * alert.showAndWait();
	 * 
	 * retval = new SalesVO();
	 * 
	 * } else { Alert alert = new Alert(AlertType.WARNING);
	 * alert.setTitle("U+ ���� ���α׷�"); alert.setHeaderText("�Ǹ� ���� ���� ����!");
	 * alert.showAndWait(); } } catch (SQLException e) { e.printStackTrace(); }
	 * catch (Exception e) { e.printStackTrace(); } finally { try { // �����ͺ��̽����� ���ῡ
	 * ���Ǿ��� ������Ʈ�� ���� if (pstmt != null) pstmt.close(); if (con != null)
	 * con.close(); } catch (SQLException e) { } } return retval; } // �Ǹ� ���� ��
	 */
	// �Ǹ� ���� ����
	public boolean getSalesDelete(SalesVO sVo, int sa_Num, String sc_Model) throws Exception {

		boolean result = false;

		// ������ ó���� ���� SQL��
		String sql = "delete from sales where sa_Num = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sa_Num);

			// SQL���� ���� �� ó������� ���´�
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("�Ǹ� ���� ���� ����!");
				alert.showAndWait();
				result = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ ���� ���α׷�");
				alert.setHeaderText("�Ǹ� ���� ���� ����!");
				alert.showAndWait();
				result = false;
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
		return result;
	} // �Ǹ� ���� ���� ��

	// ��ü �Ǹ� ����
	public ArrayList<SalesVO> getSalesAll() {

		// ������ ó���� ���� SQL��
		ArrayList<SalesVO> list = new ArrayList<SalesVO>();
		String sql = "select * from sales";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO emVo = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new SalesVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getDate(10).toString(),
						rs.getString(11));
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

		String sql = "select * from sales";
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
				se.printStackTrace();
			}
		}
		return columnName;
	} // �÷��� ���� ��

	// ��� ���� �޾ƿ��� �޼ҵ�
	public ArrayList<String> getSModel() {
		ArrayList<String> listSModel = new ArrayList<String>();

		String sql = "select sc_model from stock";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sName;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sName = rs.getString(1);
				listSModel.add(sName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// �����ͺ��̽� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return listSModel;
	} // ��� ���� �޾ƿ��� �޼ҵ� ��

	// ��� ���� �޾ƿ��� �޼ҵ�
	public ArrayList<String> getSName() {
		ArrayList<String> listSName = new ArrayList<String>();

		String sql = "select sf_name from staff";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sName;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				sName = rs.getString(1);
				listSName.add(sName);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// �����ͺ��̽� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return listSName;
	} // ��� ���� �޾ƿ��� �޼ҵ� ��

	// �Ǹ����� ����ϸ� ������ -1 �ϴ� �޼ҵ� (�𵨸�)
	public boolean stockAmountMinus(String model) throws Exception {

		boolean result = false;
		// ������ ó���� ���� SQL��
		String sql = "update stock set sc_amount = sc_amount - 1 where sc_model = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, model);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} // �Ǹ����� ����ϸ� ������ -1 �ϴ� �޼ҵ� ��

	// �Ǹ����� �����ϸ� ������ +1 �ϴ� �޼ҵ� (�𵨸�)
	public boolean stockAmountPlus(String sc_Model) throws Exception {

		boolean result = false;
		// ������ ó���� ���� SQL��
		String sql = "update stock set sc_amount = sc_amount + 1 where sc_model = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil�̶�� Ŭ������ getConnection()�޼ҵ�� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sc_Model);

			int i = pstmt.executeUpdate();

			if (i == 1) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} // �Ǹ����� �����ϸ� ������ +1 �ϴ� �޼ҵ� ��

	// �Ǹ����� ����ϸ� �ǸŰǼ� +1 �ϴ� �޼ҵ� (�����̸�)
	public void staffSalesPlus(String Name) throws Exception {

		String sql = "update staff set sf_sales = sf_sales + 1 where sf_name =?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Name);

			int i = pstmt.executeUpdate();

			if (i == 1) {
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // �Ǹ����� ����ϸ� �ǸŰǼ� +1 �ϴ� �޼ҵ� ��

	// �Ǹ����� �����ϸ� �ǸŰǼ� -1 �ϴ� �޼ҵ� (�����̸�)
	public void staffSalesMinus(String Name) throws Exception {

		String sql = "update staff set sf_sales = sf_sales - 1 where sf_name =?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, Name);

			int i = pstmt.executeUpdate();

			if (i == 1) {
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // �Ǹ����� �����ϸ� �ǸŰǼ� -1 �ϴ� �޼ҵ� ��

	// �Ǹ������� �μ�Ƽ�� ����ϸ� ���� ���������� ���� �޼ҵ�
	public void staffInctPlus(int inct, String Name) throws Exception {

		String sql = "update staff set sf_inct = sf_inct + ?, sf_total = sf_basic + sf_inct + ? where sf_name = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, inct);
			pstmt.setInt(2, inct);
			pstmt.setString(3, Name);

			int i = pstmt.executeUpdate();

			if (i == 1) {
			} else {
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	} // �Ǹ������� �μ�Ƽ�� ����ϸ� ���� ���������� ���� �޼ҵ� ��

	// �Ǹ������� �����ϸ� ������ �����ް� �ѱ޿����� �ݾ��� ������
	public void staffInctMinus(int inct, String Name) throws Exception {

		String sql = "update staff set sf_inct = sf_inct - ?, sf_total = sf_basic + sf_inct - ? where sf_name = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, inct);
			pstmt.setInt(2, inct);
			pstmt.setString(3, Name);

			int i = pstmt.executeUpdate();

			if (i == 1) {
			} else {
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	} // �Ǹ������� �����ϸ� ������ �����ް� �ѱ޿����� �ݾ��� ������
}
