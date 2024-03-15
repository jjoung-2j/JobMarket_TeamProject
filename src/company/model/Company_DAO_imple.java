package company.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import common.MYDBConnection;
import company.domain.Company_DTO;
import user.domain.User_DTO;

public class Company_DAO_imple implements Company_DAO {
	
	// field
	Recruit_DAO rdao = new Recruit_DAO_imple();

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
            			+ " values(?, ?, ?, ?, ?, ?) ";      
            
            pstmt = conn.prepareStatement(sql);         
            pstmt.setString(1, company.getCompany_id());   
            pstmt.setString(2, company.getCompany_passwd());   
            pstmt.setString(3, company.getCompany_name());   
            pstmt.setString(4, company.getCompany_address());
            pstmt.setString(5, company.getBusiness_number());
            pstmt.setString(6, company.getCeo_name());
            
            result = pstmt.executeUpdate();      // SQL문 실행
            
         } catch (SQLException e) {
            if(e.getErrorCode() == 1) {   // 유니크 제약(userid)에 중복되어지면,
               System.out.println(">> 아이디가 중복되었습니다. 새로운 아이디를 입력하세요!! <<");
            }
            else {
               e.printStackTrace();
            }	// end of if~else------------
         } finally {
            close();
         }	// end try~catch~finally--------------------
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
          
			String sql = " select company_id , company_passwd ,company_name , businees_number , ceo_name , company_address "
                    + " from tbl_company " 
                    + " where company_id = ? and company_passwd = ? " ;  
          
            pstmt = conn.prepareStatement(sql); // 우편배달부 
               
            pstmt.setString(1, paraMap.get("company_id") ); 
            pstmt.setString(2, paraMap.get("company_passwd") ); 
  
            rs = pstmt.executeQuery(); // SQL문 실행  
           
            if(rs.next()) { // select 결과가 있다면~
            	company = new Company_DTO(); 
              
            	company.setCompany_id(rs.getString("company_id")); 
            	company.setCompany_passwd(rs.getString("company_passwd"));
            	company.setCompany_name(rs.getString("company_name"));
            	company.setBusiness_number(rs.getString("businees_number"));
            	company.setCeo_name(rs.getString("ceo_name"));
            	company.setCompany_address(rs.getString("company_address"));
           
            }   // end of if----------    
        } catch (SQLException e) {
        	if(e.getErrorCode() == 1 ) { // 에러코드 1은 중복되어진다면이다
        		System.out.println(">> 아이디가 중복되었습니다. 새로운 아이디를 입력하세요!! <<");
        	} else  // 중복되지않는 오류라면
        		e.printStackTrace();
        } finally { 
           close();   // 자원 반납
        } // end of finally
     return company;
	}	// end of public Company_DTO login(Map<String, String> paraMap)--------------------
	
	
	
	
	
	
	
	
	
	// ◆◆◆ === 기업 추가정보 입력 === ◆◆◆ // ~~ 아직 작업중
	public void company_detail_info(Scanner sc, Company_DTO company) {
		System.out.println("\n--------------------------------------"); 
		System.out.println(">>> 기업 추가정보 입력하기 <<<");
        System.out.println("--------------------------------------");
        
        int employee_num = 0;
        
        do {
        	///////////////////////////////////////////////////////////
	        System.out.print("▶ 사원수[숫자만 입력] : ");
	        String c_employee_num = sc.nextLine();
			
	        try {
	        	employee_num = Integer.parseInt(c_employee_num);
	        	
				if(employee_num < 1) {
					System.out.println(">>[경고] 사원수는 1이상인 정수로만 입력하셔야 합니다. <<\n");
					continue;
				} else {	
					System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
					break;
				}	// end of if~else--------------
	        } catch (NumberFormatException e) {
	        	System.out.println(">>[경고] 사원수는 정수로만 입력하셔야 합니다. <<\n");
	        	continue;
	        }	// end of try~catch--------------------
			//////////////////////////////////////////////////////////
        } while(true);	// end of do~while-------------------------
		
        
        
		do {
			///////////////////////////////////////////////////////////
			String public_status = "";
			System.out.print("▶ 상장여부[상장은 1, 비상장은 0을 입력] : ");
			public_status = sc.nextLine();
			
			if("1".equals(public_status)) {
				System.out.println(">>상장여부에 '상장'을 선택하셨습니다.");
				System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
				break;
			}
			else if("0".equals(public_status)) {
				System.out.println(">> 상장여부에 '비상장'을 선택하셨습니다.");
				System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
				break;
			}
			else {
				System.out.println(">>[경고] 상장여부는 0 또는 1만 입력하셔야 합니다. <<\n");
				continue;
			}	// end of if~else------------------	
			///////////////////////////////////////////////////////////
		} while(true);	// end of do~while----------------------------
        
		
		
		do {
			///////////////////////////////////////////////////////////
			String begin_day = "";
			System.out.print("▶ 설립일자[예 : 20240315] : ");
			begin_day = sc.nextLine();
			
				if( begin_day.isBlank() ) {	// 그냥 엔터 또는 공백으로 입력한 경우
					System.out.println(">>[경고] 설립일자는 필수로 입력하셔야 합니다. <<\n");
					continue;
				}
				else {	// 숫자 아닌 문자입력시 "숫자만 입력하세요" 체크제약필요
					System.out.println(">> 다음 추가정보입력으로 넘어갑니다. <<\n");
					break;
				}	// end of if~else--------------------
			///////////////////////////////////////////////////////////
		} while(true);	// end of do~while--------------------------------
        
		
		
		
		int capital_money = 0;
	    do {
	    	///////////////////////////////////////////////////////////
	    	System.out.print("▶ 자본금[숫자만 입력] : ");
	    	String c_capital_money = sc.nextLine();
	    	try {
	    		capital_money = Integer.parseInt(c_capital_money);
		
	    		if(capital_money <= 0) {
	    			System.out.println(">>[경고] 자본금은 0이 아닌 정수로만 입력하셔야 합니다. <<\n");
	    			continue;
	    		}
	    		else {	
	    			System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
	    			break;
	    		}	// end of if~else---------------------
			} catch (NumberFormatException e) {
				System.out.println(">>[경고] 자본금은 정수로만 입력하셔야 합니다. <<\n");
				continue;
			}	// end of try~catch--------------------------
	    	//////////////////////////////////////////////////////////
		} while(true);	// end of do~while------------
	    
	    
	    
	    
	    int companylist_num = 0;
        do {
        	///////////////////////////////////////////////////////////
	        System.out.print("▶ 계열회사수[숫자만 입력] : ");
	        String c_companylist_num = sc.nextLine();
			
	        try {
	        	companylist_num = Integer.parseInt(c_companylist_num);
	        	
				if(companylist_num < 1) {
					System.out.println(">>[경고] 계열회사수는 1이상인 정수로만 입력하셔야 합니다. <<\n");
					continue;
				}
				else {	
					System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
					break;
				}	// end of if~else--------------
	        } catch (NumberFormatException e) {
	        	System.out.println(">>[경고] 계열회사수는 정수로만 입력하셔야 합니다. <<\n");
	        	continue;
	        }	// end of try~catch----------------
			//////////////////////////////////////////////////////////
        } while(true);	// end of do~while----------------
	}	// end of private void company_detail_info(Scanner sc, Company_DTO company)--------

	



	
	// ◆◆◆ === 모든 구직자 조회 === ◆◆◆ //
	public List<User_DTO> All_user() {
	      
		User_DTO userlist = new User_DTO();
		      
		List<User_DTO> memberList = new ArrayList<>(); // 이거 설명
		      
		try {
			// conn = MyDBConnection.getConn();
			
			// SQL 문 작성
			String sql = " select user_name , user_address , user_tel , user_security_num , user_email "
					+ " from tbl_user_info ";
		         
		    pstmt = conn.prepareStatement(sql); // 우편배달부 = 서버.prepareStatement(전달할sql문)
	
		    rs = pstmt.executeQuery(); // SQL문 실행  
		         
		    while (rs.next()) { // select 결과가 있다면~
		            
	            userlist = new User_DTO(); // new member 해서
	               
	            userlist.setUser_name(rs.getString("user_name")); 
	            userlist.setUser_address(rs.getString("user_address"));
	            userlist.setUser_tel(rs.getString("user_tel"));
	            userlist.setUser_name(rs.getString("user_name"));
	            userlist.setUser_security_num(rs.getString("user_security_num"));
	            userlist.setUser_email(rs.getString("user_email"));
		            
	            memberList.add(userlist);            
		    } // end of while        
	   } catch (SQLException e) {
		        e.printStackTrace();
	   } finally {	 // 성공하든 안하든 무조건! 
		   close();	// 자원반납 하기
	   } // end of finally
	      
		return memberList;
	}   // end of private void All_user(Scanner sc, User_DTO user)---------
	
	
	
	
	
	
}
