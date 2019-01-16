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

	// 신규 재고정보 등록
	public StockVO getStockRegister(StockVO svo) throws Exception {
		// 데이터 처리를 위한 SQL문
		String day = svo.getSc_Date();
		String sql = "insert into stock " + "(sc_Num, sc_Model, sc_Amount, sc_Price, sc_Company, sc_Date, sc_Image)"
				+ " values " + "(round(dbms_random.value(100000,999999),0), ?, ?, ?, ?,  to_date('" + day
				+ "', 'YYYY-MM-DD'), ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		StockVO retval = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 입력받은 재고정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, svo.getSc_Model());
			pstmt.setInt(2, svo.getSc_Amount());
			pstmt.setInt(3, svo.getSc_Price());
			pstmt.setString(4, svo.getSc_Company());
			pstmt.setString(5, svo.getSc_Image());

			// SQL문 수행 후 처리결과를 얻어온다
			int i = pstmt.executeUpdate();
			retval = new StockVO();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 정보 등록 성공!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 정보 등록 실패!");
				alert.showAndWait();
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
		return retval;
	} // 신규 재고등록 끝

	// 재고의 모델명을 입력받아 정보 검색
	public StockVO getStockSearch(String sc_Model) throws Exception {

		// 데이터 처리를 위한 SQL문
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
			// DBUtil이라는 클래스의 getConnection() 메소드로 데이터베이스와 연결
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

	// 재고정보 수정
	public StockVO getStockModify(StockVO sVo, int sc_Num) throws Exception {

		String editday = sVo.getSc_Date();

		// 데이터 처리를 위한 SQL문
		String sql = "update stock set " + "sc_model=?, sc_amount=?, sc_Price=?, sc_Company=?," + " sc_date=to_date(' "
				+ editday + " ' , 'YYYY-MM-DD'), sc_image=?  where sc_num = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		StockVO retval = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 수정한 재고정보를 저장하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sVo.getSc_Model());
			pstmt.setInt(2, sVo.getSc_Amount());
			pstmt.setInt(3, sVo.getSc_Price());
			pstmt.setString(4, sVo.getSc_Company());
			pstmt.setString(5, sVo.getSc_Image());
			pstmt.setInt(6, sVo.getSc_Num());

			// SQL문을 수행 후 처리 결과를 얻어온다
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 정보 수정 성공!");
				alert.showAndWait();

				retval = new StockVO();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 정보 수정 실패!");
				alert.showAndWait();
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
		return retval;
	} // 재고수정 끝

	// 재고정보 삭제
	public void getStockDelete(StockVO sVo, int sc_Num) throws Exception {

		// 데이터 처리를 위한 SQL문
		String sql = "delete from stock where sc_Num = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sc_Num);

			// SQL문을 수행 후 처리결과를 얻어온다
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 정보 삭제 성공!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("재고 정보 삭제 실패!");
				alert.showAndWait();
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
	} // 재고삭제 끝

	// 전체 재고정보
	public ArrayList<StockVO> getStockAll() {

		// 데이터 처리를 위한 SQL문
		ArrayList<StockVO> list = new ArrayList<StockVO>();
		String sql = "select * from stock";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StockVO emVo = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
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
	} // 전체 재고정보 끝

	// 데이터베이스에서 재고테이블의 컬럼의 갯수
	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from stock";
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

			}
		}
		return columnName;
	} // 컬럼의 갯수 끝

	// 삼성 리스트
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
	} // 삼성 리스트 끝

	// LG 리스트
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
	} // LG 리스트 끝

	// APPLE 리스트
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
	} // APPLE 리스트 끝

	// ETC 리스트
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
		} // ETC 리스트 끝
	
}
