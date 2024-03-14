package Comment;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MYDBConnection {

	// static 변수
	private static Connection conn = null;
	
	// static 초기화 블럭
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@211.238.142.186:1521:xe", "MINI_ORAUSER1", "gclass");
		
		} catch(ClassNotFoundException e) {
			System.out.println(">>> ojdbc8.jar 파일이 없습니다. <<<");
		} catch(SQLException e) {
			e.printStackTrace();
		}	// end of try~catch----------------
	}	// end of static---------------------
	
	private MYDBConnection() { }
	
	// static 메소드 생성
	public static Connection getConn() {
		return conn;
	}	// end of public static Connection getConn()-------------
	
	// == 자원 반납하기 == //
	public static void closeConnection() {
		try {
			if(conn != null)
				conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}	// end of try~catch----------------
	}	// end of public static void closeConnection()----------------
}
