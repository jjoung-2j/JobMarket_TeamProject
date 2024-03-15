package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import common.MYDBConnection;
import company.domain.Company_DTO;
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
            if(e.getErrorCode() == 1)    // 유니크 제약(userid)에 중복되어지면,
               System.out.println(">> 아이디가 중복되었습니다. 새로운 아이디를 입력하세요!! <<");
            else 
               e.printStackTrace();
         } finally {
            close();
         }
         
         return result;
	}	// end of public int userRegister(User_DTO user)-----------------
		
		
		
		
		
		
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
				}
				return result;
				
			} else if("n".equalsIgnoreCase(yn)) {
				System.out.println(">>> 회원탈퇴를 취소하셨습니다. <<<\n");
				return result;
			} else {
				System.out.println(">>> Y 또는 N 만 입력하세요. <<<\n");
				continue;
			}	// end of if~else(회원탈퇴 유무 확인)---------------
		}while(false);
		return result;
	}	// end of public int Withdrawal(Scanner sc, User_DTO user)-----------------------

	

	// ◆◆◆ === user 로그인 === ◆◆◆ //
	@Override
	public User_DTO user_login(Map<String, String> paraMap) {
		User_DTO user = null;
	      
	try {
	     
	   String sql = " select user_id, user_name, user_address, user_tel, user_security_num, user_email "
	              + " from tbl_user_info "
	              + " where status = 1 and user_id = ? and user_passwd = ? ";
	     
	   pstmt = conn.prepareStatement(sql);
	
	   pstmt.setString(1, paraMap.get("user_id"));
	   pstmt.setString(2, paraMap.get("user_passwd"));
	     
	   rs = pstmt.executeQuery(); // SQL문 실행
	     
	   if(rs.next()) {	// 행이 있으면
	      user = new User_DTO();	// user 에 정보를 넣을 수 있게 해준다.
	        
	      user.setUser_id(rs.getString("user_id"));
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




	
	// ◆◆◆ === 나의 정보 보기 === ◆◆◆ //
	@Override
	public User_DTO view_userinfo(User_DTO user) {
		 User_DTO userdto = null;
         try {
            String sql = "select user_id, user_name, user_passwd, user_address, user_tel, user_security_num, user_email  "
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
            }
            
            
         } catch (SQLException e) {
            
            e.printStackTrace();
         } finally {
            close();   
         }      
         
         return userdto;

		
	}	// end of public void view_userinfo(Scanner sc, User_DTO user)-------
	
	


	// ◆◆◆ === 나의 정보 수정 === ◆◆◆ //
	public void change_information(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void change_information(Scanner sc, User_DTO user)------
	
	
	
	
	

	



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




	Recruit_apply_DAO rdao = new Recruit_apply_DAO_imple();
	@Override
	public void company_search(Scanner sc, User_DTO user, Company_DTO company) {
		System.out.println("\n >>> --- 구인회사 조회 --- <<<");
	      
	      System.out.print("▶ 기업명 : ");
	      String search_company_name = sc.nextLine();
	      
	      Map<String, String> paraMap = new HashMap<>();
	      paraMap.put("company_name", search_company_name);
	      
	      Company_DTO cdto = rdao.company_search(paraMap);
	      
	      if(cdto != null) {
	         
	         System.out.println("-".repeat(30));
	         System.out.println("▶ 기업명 : " + cdto.getCompany_name() + "\n"
	                      + "▶ 설립일자 : " +cdto.getCompany_type_detail().getBegin_day() + "\n"
	                      + "▶ 대표자명 : " + cdto.getCeo_name() + "\n"
	                      + "▶ 기업형태 : " + cdto.getCompany_type_detail().getCompanylist_num() + "\n"
	                      + "▶ 주소 : " + cdto.getCompany_address() + "\n"
	                      + "▶ 사원수 : " + cdto.getCompany_type_detail().getEmployee_num() + "\n"
	                      + "▶ 상장여부 : " + cdto.getCompany_type_detail().getPublic_status() + "\n"
	                      + "▶ 자본금 : " + cdto.getCompany_type_detail().getCapital_money() + "\n"
	                      + "▶ 계열회사수 : " + cdto.getCompany_type_detail().getCompanylist_num());
	         System.out.println("-".repeat(30));
	      }
	      else {
	         System.out.println(">> 기업명 " + search_company_name + "은(는) 존재하지 않습니다. <<\n");
	      }

		
	}





	
	
	
	
	

}
