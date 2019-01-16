package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.StaffVO;

public class StaffDAO {

	// 신규 직원정보 등록
	public StaffVO getStaffRegister(StaffVO svo) throws Exception {

		// 데이터 처리를 위한 SQL문
		String day = svo.getSf_Birth();
		String day2 = svo.getSf_Date();
		String sql = "insert into staff "
				+ "(sf_Num, sf_Rank, sf_Name, sf_Birth, sf_Phone, sf_Addre, sf_basic, sf_inct, sf_Total, sf_Sales, sf_Date, sf_OutDate,  "
				+ "sf_Image)" + " values " + "(round(dbms_random.value(1000,9999),0), ?,?, to_date(' " + day
				+ " ', 'YYYY-MM-DD'), ?, ?, ?, ?, ?, ?, to_date(' " + day2 + " ', 'YYYY-MM-DD'), ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		StaffVO retval = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			// 입력받은 재고정보를 처리하기 위하여 SQl문장을 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, svo.getSf_Rank());
			pstmt.setString(2, svo.getSf_Name());
			pstmt.setString(3, svo.getSf_Phone());
			pstmt.setString(4, svo.getSf_Addre());
			pstmt.setInt(5, svo.getSf_Basic());
			pstmt.setInt(6, svo.getSf_Inct());
			pstmt.setInt(7, svo.getSf_Total());
			pstmt.setInt(8, svo.getSf_Sales());
			pstmt.setString(9, svo.getSf_OutDate());
			pstmt.setString(10, svo.getSf_Image());

			// SQL문 수행 후 처리결과를 얻어온다
			int i = pstmt.executeUpdate();

			retval = new StaffVO();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("직원 정보 등록 성공!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("직원 정보 등록 실패!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
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
	}

	// 직원 정보 수정
	public StaffVO getStaffModify(StaffVO sVo, int sf_Num) throws Exception {

		String day = sVo.getSf_Birth();
		String day2 = sVo.getSf_OutDate();

		// 데이터 처리를 위한 SQL문
		String sql = "update staff set " + " sf_rank=?, sf_name=?,  sf_birth=to_date(' " + day + " ' , 'YYYY-MM-DD'), "
				+ " sf_phone=?, sf_addre=?, sf_basic=?, sf_outdate=to_date(' " + day2
				+ " ' , 'YYYY-MM-DD' ), sf_image=? where sf_num = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		StaffVO retval = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// 수정한 재고정보를 저장하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sVo.getSf_Rank());
			pstmt.setString(2, sVo.getSf_Name());
			pstmt.setString(3, sVo.getSf_Phone());
			pstmt.setString(4, sVo.getSf_Addre());
			pstmt.setInt(5, sVo.getSf_Basic());
			pstmt.setString(6, sVo.getSf_Image());
			pstmt.setInt(7, sVo.getSf_Num());

			// SQL문을 수행 후 처리 결과를 얻어온다
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("직원 정보 수정 성공!");
				alert.showAndWait();

				retval = new StaffVO();

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("직원 정보 수정 실패!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
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
	}

	// 재고정보 삭제
	public void getStaffDelete(StaffVO sVo, int sf_Num) throws Exception {

		// 데이터 처리를 위한 SQL문
		String sql = "delete from staff where sf_Num = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sf_Num);

			// SQL문을 수행 후 처리결과를 얻어온다
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("직원 정보 삭제 성공!");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("U+ 관리 프로그램");
				alert.setHeaderText("직원 정보 삭제 실패!");
				alert.showAndWait();
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
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
	}
	
	// 전체 직원 정보
	public ArrayList<StaffVO> getStaffAll() {

		// 데이터 처리를 위한 SQL문
		ArrayList<StaffVO> list = new ArrayList<StaffVO>();
		String sql = "select * from staff";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StaffVO emVo = null;

		try {
			// DBUtil이라는 클래스의 getConnection()메소드로 데이터베이스와 연결
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new StaffVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4).toString(),
						rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10),
						rs.getDate(11).toString(), rs.getDate(12).toString(), rs.getString(13));
				list.add(emVo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
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
	}

	// 데이터베이스에서 직원 테이블의 컬럼의 갯수
	public ArrayList<String> getColumnName() {

		ArrayList<String> columnName = new ArrayList<String>();
		String sql = "select * from staff";

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
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
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
	}
	
	// 직원정보에 기본급 변경하면 총급여 변경되는 메소드
		public void staffBasicPlus(int Basic, String Name) throws Exception {
			
			String sql = "update staff set sf_total = sf_inct + ? where sf_name = ?";
			
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = DBUtil.getConnection();
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Basic);
				pstmt.setString(2, Name);
				
		int i = pstmt.executeUpdate();
				
				if (i == 1) {
				} else {
				}
			} catch (Exception e) {
				System.out.println(e);
			} 
		} // 판매정보에 인센티브 등록하면 직원 성과급으로 들어가는 메소드 끝

}
