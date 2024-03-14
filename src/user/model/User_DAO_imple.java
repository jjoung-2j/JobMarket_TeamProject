package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	     
	   if(rs.next()) {
	      user = new User_DTO();
	        
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




	// ◆◆◆ === 나의 정보 메뉴 === ◆◆◆ //
	@Override
	public void user_info_menu(Scanner sc, User_DTO user) {
		String u_Choice = "";
	      
		do {
		    
			System.out.println("\n>>> ---- " + user.getUser_name() + "님의 정보 메뉴 ---- <<<\n"
		                   + "1. 나의 정보 보기\n"
		                   + "2. 나의 정보 수정\n"
		                   + "3. 이전 메뉴로 돌아가기" );
		      
		    System.out.print("▶ 메뉴번호 선택 : ");
		    u_Choice = sc.nextLine();
		      
		    switch (u_Choice) {
		        
				case "1": // 로그인된 나의 정보 보기
				   System.out.print(user);	// toString 메소드 override
				   break;
				case "2": // 나의 정보 수정
				   change_information(sc, user);
				   break;
				case "3": // 이전 메뉴로 돌아가기
				   break;   
				default:
				   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				   break;
		    } // end of switch (u_Choice)-----------------
		} while (!"3".equalsIgnoreCase(u_Choice));	// end of do~while------------------	
		
	}	// end of user_info(Scanner sc, User_DTO user)-----------------




	// ◆◆◆ === 나의 정보 수정 === ◆◆◆ //
	private void change_information(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void change_information(Scanner sc, User_DTO user)------
	
	
	
	
	// ◆◆◆ === 이력서 관리 메뉴 === ◆◆◆ //
	@Override
	public void Paper_menu(Scanner sc, User_DTO user) {
		String u_Choice = "";
	      
		do {
		    
			System.out.println("\n>>> ---- " + user.getUser_name() + "님의 이력서 관리 메뉴 ---- <<<\n"
		                   + "1. 이력서 조회\n"
		                   + "2. 이력서 작성\n"
		                   + "3. 이력서 수정\n"
		                   + "4. 이전 메뉴로 돌아가기" );
		      
		    System.out.print("▶ 메뉴번호 선택 : ");
		    u_Choice = sc.nextLine();
		      
		    switch (u_Choice) {
		        
				case "1": // 이력서 조회
					paper_info(sc, user);
				   	break;
				case "2": // 이력서 작성
					write_paper(sc, user);
				   	break;
				case "3":	// 이력서 수정
					change_paper(sc, user);
					break;
				case "4": // 이전 메뉴로 돌아가기
				   break;   
				default:
				   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				   break;
		    } // end of switch (u_Choice)-----------------
		} while (!"4".equalsIgnoreCase(u_Choice));	// end of do~while------------------	
	}	// end of public void Paper_menu(Scanner sc, User_DTO user, Paper_DTO paper, License_DTO license)---------

	



	// ◆◆◆ === 이력서 조회 === ◆◆◆ //
	private void paper_info(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void paper_info(Scanner sc, User_DTO user, Paper_DTO paper)------
	
	
	
	
	
	// ◆◆◆ === 이력서 작성 === ◆◆◆ //
	private void write_paper(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void write_paper(Scanner sc, User_DTO user, Paper_DTO paper, License_DTO license)----
	
	
	
	
	
	// ◆◆◆ === 이력서 수정 === ◆◆◆ //
	private void change_paper(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void change_paper(Scanner sc, User_DTO user, Paper_DTO paper, License_DTO license)----

	
	
	
	
	
	
	
	

	// ◆◆◆ === 입사지원 제안 관리 === ◆◆◆ //
	Recruit_apply_DAO rdao = new Recruit_apply_DAO_imple();
	@Override
	public void Recruit_apply_menu(Scanner sc, User_DTO user, Company_DTO company) {
		String u_Choice = "";
	      
		do {
		    
			System.out.println("\n>>> ---- " + user.getUser_name() + "님의 구인 메뉴 ---- <<<\n"
		                   + "1. 구인회사 조회\n"
		                   + "2. 채용공고 조회\n"
		                   + "3. 채용지원\n"
		                   + "4. 지원현황\n"
		                   + "5. 이전 메뉴로 돌아가기" );
		      
		    System.out.print("▶ 메뉴번호 선택 : ");
		    u_Choice = sc.nextLine();
		      
		    switch (u_Choice) {
		        
				case "1": // 구인회사 조회
					rdao.company_search(sc, user, company);
				   	break;
				case "2": // 채용공고 조회
					rdao.recruit_search(sc, user, company);
				   	break;
				case "3":	// 채용지원
					rdao.recruit_apply(sc, user, company);
					break;
				case "4":	// 지원현황
					rdao.recruit_apply_situation(sc, company);
					break;
				case "5": // 이전 메뉴로 돌아가기
				   break;   
				default:
				   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				   break;
		    } // end of switch (u_Choice)-----------------
		} while (!"5".equalsIgnoreCase(u_Choice));	// end of do~while------------------	
		
	}	// end of public void Recruit_apply(Scanner sc, User_DTO user, Company_DTO company) ------

}
