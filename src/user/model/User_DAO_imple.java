package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common.MYDBConnection;

public class User_DAO_imple implements User_DAO {

	// field
	private Connection conn = MYDBConnection.getConn();		// 데이터베이스 서버 연결
	private PreparedStatement pstmt;	// 우편배달부
	private ResultSet rs;
	
	// ◆◆◆ === 자원반납을 해주는 메소드 === ◆◆◆ //
	private void close() {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if(pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	// end of try~catch----------
	}	// end of private void close()---------------
	
	
	// ◆◆◆ === user 탈퇴 === ◆◆◆ //
	@Override
	public int memberDelete(Scanner sc, String user_id) {
		// conn = MyDBConnection.getConn();		// 연결해두었던 Connection 사용 / 이미 선언되서 필요없다.
		
		return 0;
	}	// end of public int memberDelete(Scanner sc, String user_id)------

}
