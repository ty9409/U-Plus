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

	// 재고의 모델명을 입력받아 정보 검색
	public SalesVO getSalesSearch(String sa_Name) throws Exception {

		// 데이터 처리를 위한 SQL문
		String sql = "select * from sales where sa_name = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO retval = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
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
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
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
	} // 정보검색 끝

	// 신규 판매정보 등록
	public boolean getSalesRegister(SalesVO svo) throws Exception {
		// 데이터 처리를 위한 SQL문
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
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 입력받은 재고정보를 처리하기 위하여 SQL문장을 생성
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

			// SQL문 수행 후 처리결과를 얻어온다
			int i = pstmt.executeUpdate();

			retval = new SalesVO();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("판매 정보 등록 성공!");
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
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return result;
	} // 판매등록 끝

	/*
	 * // 판매 정보 수정 public SalesVO getSalesModify(SalesVO svo, int sa_Num) throws
	 * Exception {
	 * 
	 * String editday = svo.getSa_Date();
	 * 
	 * // 데이터 처리를 위한 SQL문 String sql = "update sales set " +
	 * " sa_name=?, sa_phone=?, sc_model=?, sa_serial=?, sa_plan=?, sa_contract=?, sa_inct=?, sf_name=?, sa_date=to_date(' "
	 * + editday + " ' , 'YYYY-MM-DD'), sa_memo=?  where sa_num = ?";
	 * 
	 * Connection con = null; PreparedStatement pstmt = null; SalesVO retval = null;
	 * 
	 * try { // DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결 con =
	 * DBUtil.getConnection();
	 * 
	 * // 수정한 재고정보를 저장하기 위하여 SQL문장을 생성 pstmt = con.prepareStatement(sql);
	 * pstmt.setString(1, svo.getSa_Name()); pstmt.setString(2, svo.getSa_Phone());
	 * pstmt.setString(3, svo.getSc_Model()); pstmt.setString(4,
	 * svo.getSa_Serial()); pstmt.setInt(5, svo.getSa_Plan()); pstmt.setString(6,
	 * svo.getSa_Contract()); pstmt.setInt(7, svo.getSa_Inct()); pstmt.setString(8,
	 * svo.getSf_Name()); pstmt.setString(9, svo.getSa_Memo()); pstmt.setInt(10,
	 * svo.getSa_Num());
	 * 
	 * // SQL문을 수행 후 처리 결과를 얻어온다 int i = pstmt.executeUpdate();
	 * 
	 * if (i == 1) { Alert alert = new Alert(AlertType.INFORMATION);
	 * alert.setTitle("U+ 관리 프로그램"); alert.setHeaderText("판매 정보 수정 성공!");
	 * alert.showAndWait();
	 * 
	 * retval = new SalesVO();
	 * 
	 * } else { Alert alert = new Alert(AlertType.WARNING);
	 * alert.setTitle("U+ 관리 프로그램"); alert.setHeaderText("판매 정보 수정 실패!");
	 * alert.showAndWait(); } } catch (SQLException e) { e.printStackTrace(); }
	 * catch (Exception e) { e.printStackTrace(); } finally { try { // 데이터베이스와의 연결에
	 * 사용되었던 오브젝트를 해제 if (pstmt != null) pstmt.close(); if (con != null)
	 * con.close(); } catch (SQLException e) { } } return retval; } // 판매 수정 끝
	 */
	// 판매 정보 삭제
	public boolean getSalesDelete(SalesVO sVo, int sa_Num, String sc_Model) throws Exception {

		boolean result = false;

		// 데이터 처리를 위한 SQL문
		String sql = "delete from sales where sa_Num = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sa_Num);

			// SQL문을 수행 후 처리결과를 얻어온다
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("판매 정보 삭제 성공!");
				alert.showAndWait();
				result = true;
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("판매 정보 삭제 실패!");
				alert.showAndWait();
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return result;
	} // 판매 정보 삭제 끝

	// 전체 판매 정보
	public ArrayList<SalesVO> getSalesAll() {

		// 데이터 처리를 위한 SQL문
		ArrayList<SalesVO> list = new ArrayList<SalesVO>();
		String sql = "select * from sales";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SalesVO emVo = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
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
	} // 전체 재고정보 끝

	// 데이터베이스에서 재고테이블의 컬럼의 갯수
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from sales";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// ResultSetMeta 객체 변수 선언
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
	} // 컬럼의 갯수 끝

	// 재고 정보 받아오는 메소드
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
				// 데이터베이스 연결에 사용되었던 오브젝트를 해제
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
	} // 재고 정보 받아오는 메소드 끝

	// 재고 정보 받아오는 메소드
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
				// 데이터베이스 연결에 사용되었던 오브젝트를 해제
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
	} // 재고 정보 받아오는 메소드 끝

	// 판매정보 등록하면 재고수량 -1 하는 메소드 (모델명)
	public boolean stockAmountMinus(String model) throws Exception {

		boolean result = false;
		// 데이터 처리를 위한 SQL문
		String sql = "update stock set sc_amount = sc_amount - 1 where sc_model = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
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
	} // 판매정보 등록하면 재고수량 -1 하는 메소드 끝

	// 판매정보 삭제하면 재고수량 +1 하는 메소드 (모델명)
	public boolean stockAmountPlus(String sc_Model) throws Exception {

		boolean result = false;
		// 데이터 처리를 위한 SQL문
		String sql = "update stock set sc_amount = sc_amount + 1 where sc_model = ?";

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
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
	} // 판매정보 삭제하면 재고수량 +1 하는 메소드 끝

	// 판매정보 등록하면 판매건수 +1 하는 메소드 (직원이름)
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
	} // 판매정보 등록하면 판매건수 +1 하는 메소드 끝

	// 판매정보 삭제하면 판매건수 -1 하는 메소드 (직원이름)
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
	} // 판매정보 삭제하면 판매건수 -1 하는 메소드 끝

	// 판매정보에 인센티브 등록하면 직원 성과급으로 들어가는 메소드
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
	} // 판매정보에 인센티브 등록하면 직원 성과급으로 들어가는 메소드 끝

	// 판매정보를 삭제하면 직원의 성과급가 총급여에서 금액이 빠진다
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
	} // 판매정보를 삭제하면 직원의 성과급가 총급여에서 금액이 빠진다
}
