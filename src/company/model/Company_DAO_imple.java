package company.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import common.MYDBConnection;
import company.domain.Company_DTO;



public class Company_DAO_imple implements Company_DAO {
	
	// field
	// Company_DAO udao = new Company_DAO_imple();
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
	
	
	
	
	
	
	//◆◆◆ === company 가입 === ◆◆◆ //
	@Override
	public int companyRegister(Company_DTO company) {
		 int result = 0;
         
         try {
            
            String sql = " insert into tbl_company(company_id, company_passwd, company_name, company_address, businees_number, ceo_name) "
                  + " values(?, ?, ?,?, ?, ?) ";      
            
            pstmt = conn.prepareStatement(sql);         
            pstmt.setString(1, company.getCompany_id());   
            pstmt.setString(2, company.getCompany_passwd());   
            pstmt.setString(3, company.getCompany_name());   
            pstmt.setString(4, company.getCompany_address());
            pstmt.setString(5, company.getBusiness_number());
            pstmt.setString(6, company.getCeo_name());
            
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

	}	// end of public int companyRegister(Company_DTO company)------------------
		
		
		
		
		
	
	
	//◆◆◆ === company 탈퇴 === ◆◆◆ //
	@Override
	public int Withdrawal(Scanner sc, Company_DTO company) {
		int result = 0;
		String yn = "";
		
		do {
			System.out.print(">>> 정말로 탈퇴하시겠습니까? [Y/N] : ");
			yn = sc.nextLine();
		
			if("y".equalsIgnoreCase(yn)) {	// conn 은 연결되어있다.
				try {
					// SQL 문
					String sql = " update tbl_company set status = 0 "
							+ " where company_id = ? ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, company.getCompany_id());
					
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
		}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
		return result;
	}	// end of public int Withdrawal(Scanner sc, Company_DTO company)-----------------------

	
	
	



	//◆◆◆ === company 로그인 === ◆◆◆ //
	@Override
	public Company_DTO login(Map<String, String> paraMap) {
		Company_DTO company = null;
	      
	    try {
	         // conn = MyDBConnection.getConn(); 데이터베이스 연결하기
	
	         String sql = " select company_id , company_name , businees_number , company_type, ceo_name , company_address "
	                  + " from tbl_company " 
	                  + " where company_id = ? and company_passwd = ? " ;  
	         
	         pstmt = conn.prepareStatement(sql); // 우편배달부 
	         
	         pstmt.setString(1, paraMap.get("company_id") ); // 
	         pstmt.setString(2, paraMap.get("company_passwd") ); // 
	
	         rs = pstmt.executeQuery(); // SQL문 실행  
	         
	         if(rs.next()) { // select 결과가 있다면~
	            company = new Company_DTO(); 
	            
	            company.setCompany_id(rs.getString("company_id")); // 
	            company.setCompany_name(rs.getString("company_name"));
	            company.setBusiness_number(rs.getString("businees_number"));
	            company.setCompany_type(rs.getString("company_type"));
	            company.setCeo_name(rs.getString("ceo_name"));
	            company.setCompany_address(rs.getString("company_address"));
	            }	// end of if----------    
	         } catch (SQLException e) {
	            if(e.getErrorCode() == 1 ) { // 에러코드 1은 중복되어진다면이다
	               System.out.println(">> 아이디가 중복되었습니다. 새로운 아이디를 입력하세요!! <<");
	            }
	            else { // 중복되지않는 오류라면
	               
	               e.printStackTrace();
	            }  
	         } finally { 
	            close();	// 자원 반납
	         } // end of finally
	      return company;
	}	// end of public Company_DTO login(Map<String, String> paraMap)--------------------
	
	
	
	
	
	//◆◆◆ === company 정보메뉴 === ◆◆◆ //
	@Override
	public void company_menu2(Scanner sc, Company_DTO company) {
		 String c_Choice = "";
	      
	     do {
	        
		     System.out.println("\n>>> ---- 기업메뉴 ---- <<<\n"
		                   + "1.기업정보 보기 \n"
		                   + "2.추가정보 입력 \n"
		                   + "3.돌아가기 \n" );
		      
		     System.out.print("▶ 메뉴번호 선택 : ");
		     c_Choice = sc.nextLine();
		      
	         switch (c_Choice) {
	            
				case "1": // 로그인된 기업정보 보기
				   System.out.print(company);
				   break;
				case "2": // 기업 추가정보 입력
				   // company_Detail_Info_Input(sc, company); ★★★★★ 미완성 !! ★★★★★
				   break;
				case "3": // 이전메뉴로 되돌아가기
				
				   break;   
				default:
				   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				   break;
				} // end of switch (c_Choice)-----------------
	      } while (!"3".equalsIgnoreCase(c_Choice));	// end of do~while------------------	
	}	// end of public void company_menu2(Scanner sc, Company_DTO company)--------------

}
