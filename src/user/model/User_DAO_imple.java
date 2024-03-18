package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import common.MYDBConnection;
import user.domain.User_DTO;

public class User_DAO_imple implements User_DAO {

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
	
	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	// ◆◆◆ === user 회원가입 === ◆◆◆ //
	@Override
	public int userRegister(User_DTO user) {
		 int result = 0;
         
         try {
            String sql = " insert into tbl_user_info(user_id, user_passwd, user_name, user_address, user_tel, user_security_num, user_email) "
                  + "values(?, ?, ?, ?, ?, ?, ?) ";      
            
            pstmt = conn.prepareStatement(sql);         
            pstmt.setString(1, user.getUser_id());   
            pstmt.setString(2, user.getUser_passwd());   
            pstmt.setString(3, user.getUser_name());   
            pstmt.setString(4, user.getUser_address());
            pstmt.setString(5, user.getUser_tel());
            pstmt.setString(6, user.getUser_security_num());
            pstmt.setString(7, user.getUser_email());
            
            result = pstmt.executeUpdate();      // SQL문 실행
         } catch (SQLException e) {
            if(e.getErrorCode() == 1) {    // 유니크 제약(userid)에 중복되어지면,
               System.out.println(">> 아이디가 중복되었습니다. 새로운 아이디를 입력하세요!! <<");
            }
            else {
               e.printStackTrace();
            }	// end of if~else------------
         } finally {
            close();
         }	// end of try~catch~finally-------------------
         return result;
	}	// end of public int userRegister(User_DTO user)-----------------
		
		
		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆		
		
		
	// ◆◆◆ === 회원 가입시 입력한 아이디가 존재하는지 존재하지 않는지 알아오기 === ◆◆◆ //
	@Override
	public boolean is_exist_user_id(String user_id) {
		boolean is_exist = false;  // 기업회원 id 가 존재하지 않으면 false 
	      
		try {
	         String sql = " select user_id "
	                + " from tbl_user_info "
	                + " where user_id = ? ";
	
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, user_id);
	         	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	        	 is_exist = true;
	         }
	             
		}catch (SQLException e) {
            e.printStackTrace();
	    }finally {
            close();
	    } // end of finally
		
		return is_exist;   
	}	// end of public boolean is_exist_user_id(String user_id)---------

		
		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
		
		
		
		
	// ◆◆◆ === user 탈퇴 === ◆◆◆ //
	@Override
	public int Withdrawal(Scanner sc, User_DTO user) {
		int result = 0;
		String yn = "";
		
		do {
			System.out.print(">>> 정말로 탈퇴하시겠습니까? [Y/N] : ");
			yn = sc.nextLine();
		
			if("y".equalsIgnoreCase(yn)) {	// conn 은 연결되어있다.
				try {
					// SQL 문
					String sql = " update tbl_user_info set status = 0 "
							+ " where user_id = ? ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, user.getUser_id());
					
					result = pstmt.executeUpdate();	// sql 문 실행
				} catch(SQLException e) {
					e.printStackTrace();
				} finally {
					close();	// 자원 반납
				}	// end of try~catch~finally----------------
			} else if("n".equalsIgnoreCase(yn)) {
				System.out.println(">>> 회원탈퇴를 취소하셨습니다. <<<\n");
			} else {
				System.out.println(">>> Y 또는 N 만 입력하세요. <<<\n");
			}	// end of if~else(회원탈퇴 유무 확인)---------------
		}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
		return result;
	}	// end of public int Withdrawal(Scanner sc, User_DTO user)-----------------------

	

// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	
	
	
	
	// ◆◆◆ === user 로그인 === ◆◆◆ //
	@Override
	public User_DTO user_login(Map<String, String> paraMap) {
		User_DTO user = null;
	      
	try {
	     
	   String sql = " select user_id, user_passwd, user_name, user_address, user_tel, user_security_num, user_email "
	              + " from tbl_user_info "
	              + " where status = 1 and user_id = ? and user_passwd = ? ";
	     
	   pstmt = conn.prepareStatement(sql);
	
	   pstmt.setString(1, paraMap.get("user_id"));
	   pstmt.setString(2, paraMap.get("user_passwd"));
	     
	   rs = pstmt.executeQuery(); // SQL문 실행
	     
	   if(rs.next()) {	// 행이 있으면
	      user = new User_DTO();	// user 에 정보를 넣을 수 있게 해준다.
	        
	      user.setUser_id(rs.getString("user_id"));
	      user.setUser_passwd(rs.getString("user_passwd"));
	      user.setUser_name(rs.getString("user_name"));
	      user.setUser_address(rs.getString("user_address"));
	      user.setUser_tel(rs.getString("user_tel"));
	      user.setUser_security_num(rs.getString("user_security_num"));
	      user.setUser_email(rs.getString("user_email"));
	   }
	} catch (SQLException e) {
	     e.printStackTrace();
	} finally {
	     close();
	}
	return user;
	}	// end of public User_DTO user_login(Map<String, String> paraMap)---------------



// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	

	
	// ◆◆◆ === 나의 정보 보기 === ◆◆◆ //
	@Override
	public User_DTO view_userinfo(User_DTO user) {
		 User_DTO userdto = null;
         try {
            String sql = " select user_id, user_name, user_passwd, user_address, user_tel, user_security_num, user_email  "
                  + " from tbl_user_info "
                  + " where user_id = ? " ;
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUser_id()); 
            
            rs = pstmt.executeQuery(); // sql문 실행
            
            while(rs.next()) {
               userdto = new User_DTO();
               
               userdto.setUser_id(rs.getString("user_id"));
               userdto.setUser_passwd(rs.getString("user_passwd"));
               userdto.setUser_name(rs.getString("user_name"));
               userdto.setUser_address(rs.getString("user_address"));
               userdto.setUser_tel(rs.getString("user_tel"));
               userdto.setUser_address(rs.getString("user_address"));
               userdto.setUser_security_num(rs.getString("User_security_num"));
               userdto.setUser_email(rs.getString("User_email"));
            }	// end of while------------
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            close();   
         }    // end of try~catch~fianlly------------------  
         return userdto;
	}	// end of public void view_userinfo(Scanner sc, User_DTO user)-------
	
	

// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	
	// ◆◆◆ === 나의 정보 수정 === ◆◆◆ //
	@Override
	public int fix_info(User_DTO user, String infoview, String fix_info) {
		
		int result = 0;
		
		try {
	       	 // Transaction 처리를 위해 수동 commit 전환 하기
	       	 conn = MYDBConnection.getConn();
	       	 
	       	 conn.setAutoCommit(false);
	       	 
	       	 String sql = " update tbl_user_info set " 
	       			 + infoview + " = ? "
	       			 + " where user_id = ? ";
	
	       	 pstmt = conn.prepareStatement(sql);
	           
	       	 pstmt.setString(1, fix_info);
	       	 pstmt.setString(2, user.getUser_id());
	       	 
	       	 if(pstmt.executeUpdate() == 1) { 	// 나의 정보 한줄 수정이 되면 => update 니까 1 이면
	       		 conn.commit();		// 수정이 되면 커밋
	       	 }
	       	 else {
	       		 conn.rollback(); 	// 다른 것들을 위해 
	       	 }
	   		 result = pstmt.executeUpdate(); 	// sql문 실행하기 
	   		 conn.setAutoCommit(true);
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
           close(); // 자원반납하기 
        }	// end of try~catch~finally----------------------
        return result;
	}	// end of public int fix_info(User_DTO user, String infoview, String fix_info)--------


	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	
	// ◆◆◆ === 나의 정보 수정(전체) === ◆◆◆ //
	@Override
	public int updateBoard(Map<String, String> paraMap) {
		 int result = 0;

         try {
        	 
        	 String sql = " update tbl_user_info set user_passwd = ?, user_name = ?, user_address = ? , user_tel = ?, user_security_num = ? , user_email = ? "
        			 + " where user_id = ? ";

        	 pstmt = conn.prepareStatement(sql);
            
        	 pstmt.setString(1, paraMap.get("user_passwd"));
        	 pstmt.setString(2, paraMap.get("user_name"));
        	 pstmt.setString(3, paraMap.get("user_address"));
        	 pstmt.setString(4, paraMap.get("user_tel"));
        	 pstmt.setString(5, paraMap.get("user_security_num"));
        	 pstmt.setString(6, paraMap.get("user_email"));
        	 pstmt.setString(7, paraMap.get("user_id"));
            
    		 result = pstmt.executeUpdate(); 	// sql문 실행하기 
    		 
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            close(); // 자원반납하기 
         }	// end of try~catch~finally----------------------
         return result;
	}	// end of private int updateBoard(Map<String, String> paraMap)-------



// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	

	// ◆◆◆ === 이력서 조회 === ◆◆◆ //
	public void paper_info(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void paper_info(Scanner sc, User_DTO user, Paper_DTO paper)------
	
	
	
	
	
	// ◆◆◆ === 이력서 작성 === ◆◆◆ //
	public void write_paper(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void write_paper(Scanner sc, User_DTO user, Paper_DTO paper, License_DTO license)----
	
	
	
	
	
	// ◆◆◆ === 이력서 수정 === ◆◆◆ //
	public void change_paper(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void change_paper(Scanner sc, User_DTO user, Paper_DTO paper, License_DTO license)----

	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆

	

	// ◆◆◆ === 나의 추가 정보 입력(학력, 취업우대) === ◆◆◆ //
	public int insert_anotherinfo(String academy_code, String priority_code, String user_id) {
		   
	      int result = 0; 
	       try {
	         String sql = " update tbl_user_info set fk_academy_code = ? , fk_priority_code = ? "
	                + " where user_id = ? ";
	   
	         pstmt = conn.prepareStatement(sql);
	          
	         pstmt.setString(1, academy_code);
	         pstmt.setString(2, priority_code);
	         pstmt.setString(3, user_id);
	            
	       result = pstmt.executeUpdate();   
	         
	       } catch (SQLException e) {  
	             e.printStackTrace();
	       } finally {
	          close();
	       }   // end try~catch~finally--------------------   
	      return result;
	   } // end of public int insert_anotherinfo(String academy_code, String priority_code, String user_id)
	
	
	
	
	

}
