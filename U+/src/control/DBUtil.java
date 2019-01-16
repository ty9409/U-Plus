package control;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	static final String diver = "oracle.jdbc.driver.OracleDriver";
	static final String url = "jdbc:oracle:thin:127.0.0.1:1521:orcl";

	public static Connection getConnection() throws Exception {

		Class.forName(diver);
		Connection con = DriverManager.getConnection(url, "scott", "tiger");
		return con;
	}
}
