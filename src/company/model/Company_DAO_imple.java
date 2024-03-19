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
import company.domain.Company_type_DTO;
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
	
	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	
	
	
	
	//◆◆◆ === company 가입 === ◆◆◆ //
	@Override
	public int companyRegister(Company_DTO company, Map<String, String> paraMap) {
		 int result = 0;

         try {
        	 String selectsql = "select local_code from tbl_local "
        	 		+ " where local_name = ? and city_name = ? ";
        	 
        	 pstmt = conn.prepareStatement(selectsql);
        	 
        	 pstmt.setString(1, paraMap.get("local"));
        	 pstmt.setString(2, paraMap.get("city"));
        	 
        	 rs = pstmt.executeQuery();
        	 
        	 int fk_local_code = 0;
        	 
        	 if(rs.next()) {
        		 fk_local_code = rs.getInt("local_code");
        	 }

        	 String sql = " insert into tbl_company(company_id, company_passwd, company_name, company_address, businees_number, ceo_name"
        			 	+ " , fk_jobtype_code, fk_local_code) "
            			+ " values(?, ?, ?, ?, ?, ?, ?, ?) ";      
            
        	 pstmt = conn.prepareStatement(sql);         
        	 pstmt.setString(1, company.getCompany_id());   
        	 pstmt.setString(2, company.getCompany_passwd());   
        	 pstmt.setString(3, company.getCompany_name());   
        	 pstmt.setString(4, company.getCompany_address());
        	 pstmt.setString(5, company.getBusiness_number());
        	 pstmt.setString(6, company.getCeo_name());
        	 pstmt.setInt(7, company.getFk_jobtype_code());
        	 pstmt.setInt(8, fk_local_code);
            
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
		
		
		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	// ◆◆◆ === 기업회원 가입시 입력한 기업 아이디가 존재하는지 존재하지 않는지 알아오기 === ◆◆◆ //
	@Override
	public boolean is_exist_company_id(String company_id) {
		
		boolean is_exist = false;  // 기업회원 id 가 존재하지 않으면 false 
	      
		try {
	         String sql = " select company_id "
	                + " from tbl_company "
	                + " where company_id = ? ";
	
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, company_id);
	         	         
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
		
	}// end of public boolean is_exist_company_id(String company_id)------------------
		

	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	// ◆◆◆ === 입력한 도시명이 테이블에 존재하는지 확인 === ◆◆◆ //
	@Override
	public boolean list_cityname_check(String local, String city) {
		boolean result = false;
	      
		try {
	         String sql = " select city_name "
	                + " from tbl_local "
	                + " where local_name = ? and city_name = ? ";
	
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, local);
	         pstmt.setString(2, city);
	         
	         rs = pstmt.executeQuery();
	         
	         String city_name = "";
	         
	         if(rs.next()) {
	            city_name = rs.getString("city_name");
	         }
	                  
	         if(city.equals(city_name)) {
	            result = true;
	         }    
		}catch (SQLException e2) {
            e2.printStackTrace();
	    }finally {
            close();
	    } // end of finally
		return result;   
	}   // end of public boolean list_cityname_check(String local, String city)---------

	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	
	//◆◆◆ === 기업 로그인 === ◆◆◆ //
	@Override
	public Company_DTO login(Map<String, String> paraMap) {
		Company_DTO company = null;
        
		try {
          
			String sql = " select company_id , company_passwd ,company_name , businees_number , ceo_name , company_address "
                    + " from tbl_company " 
                    + " where status =1 and company_id = ? and company_passwd = ? " ;  
          
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
		
	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	

	//◆◆◆ === 기업 탈퇴 === ◆◆◆ //
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
			} else if("n".equalsIgnoreCase(yn)) {
				System.out.println(">>> 회원탈퇴를 취소하셨습니다. <<<\n");
			} else {
				System.out.println(">>> Y 또는 N 만 입력하세요. <<<\n");
			}	// end of if~else(회원탈퇴 유무 확인)---------------
		}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
		return result;
	}	// end of public int Withdrawal(Scanner sc, Company_DTO company)-----------------------

	
	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	


	
	// ◆◆◆ === 기업 추가정보 입력 === ◆◆◆ 
	@Override 
	public int company_detail_input(Map<String, String> paraMap, String company_id) {
		int result = 0;
      
		try {
            String sql = " insert into tbl_company_type ( fk_company_id , employee_num , public_status, begin_day, "
                  + "   capital_money , companylist_num) "
                  + "  values( ? , ? , ? , to_date(?, 'yyyy-mm-dd') , ? , ? ) ";      
            
            pstmt = conn.prepareStatement(sql);         
            
            pstmt.setString(1, company_id );   
            pstmt.setString(2, paraMap.get("employee_num") ); 
            pstmt.setString(3, paraMap.get("public_status") ); 
            pstmt.setString(4, paraMap.get("begin_day"));  
            pstmt.setString(5, paraMap.get("capital_money") );
            pstmt.setString(6, paraMap.get("companylist_num"));
            
            result = pstmt.executeUpdate();      // SQL문 실행
         } catch (SQLException e) {
               e.printStackTrace();
         } finally {
            close();
         }   // end try~catch~finally--------------------
         return result;
   } // end of public int company_detail_input(Map<String, String> paraMap, String company_id)



// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	

	
	// ◆◆◆ === 모든 구직자 조회 === ◆◆◆ //
	public List<User_DTO> All_user() {
	      
		User_DTO userlist = new User_DTO();
		      
		List<User_DTO> memberList = new ArrayList<>(); // 이거 설명
		      
		try {
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
	}	// end of public List<User_DTO> All_user()-------------------------

	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	

	// ◆◆◆ === 모든 기업 조회(관리자 히든 메뉴) === ◆◆◆ //
	@Override
	public List<Company_DTO> All_company() {
		Company_DTO company_list = new Company_DTO();
		
		List<Company_DTO> companyList = new ArrayList<>();
			
		try {
			String sql = " select A.company_id, A.company_name, A.businees_number, A.ceo_name, A.company_address "
					   + " , to_char(B.begin_day,'yyyy-mm-dd') as begin_day "
					   + " , B.employee_num "
					   + " , decode(B.public_status, 0, '비상장', 1, '상장') AS public_status "
					   + " , B.capital_money, B.companylist_num "
					   + " from tbl_company A LEFT JOIN tbl_company_type B "
					   + " on A.company_id = B.fk_company_id "
					   + " order by company_id ";
		         
		    pstmt = conn.prepareStatement(sql); 
		    
		    rs = pstmt.executeQuery(); // SQL문 실행  
		         
		    while (rs.next()) { 
		            
		    	company_list = new Company_DTO();
	            
		    	company_list.setCompany_id(rs.getString("company_id"));
		    	company_list.setCompany_name(rs.getString("company_name"));
		    	company_list.setBusiness_number(rs.getString("businees_number"));
		    	company_list.setCeo_name(rs.getString("ceo_name"));
		    	company_list.setCompany_address(rs.getString("company_address"));
	            
	            Company_type_DTO ctdto = new Company_type_DTO();
	            ctdto.setBegin_day(rs.getString("begin_day"));
	            ctdto.setEmployee_num(rs.getString("employee_num"));
	            ctdto.setPublic_status(rs.getString("public_status"));
	            ctdto.setCapital_money(rs.getString("capital_money"));
	            ctdto.setCompanylist_num(rs.getString("companylist_num"));
		            
	            company_list.setCompany_type_detail(ctdto);
	            
	            companyList.add(company_list);            
		    } // end of while(rs.next())    
		    
	   } catch (SQLException e) {
		        e.printStackTrace();
	   } finally {	 // 성공하든 안하든 무조건! 
		   close();	// 자원반납 하기
	   } // end of finally
			
		return companyList;

	}	// end of public List<Company_DTO> All_company()------


	
	// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	
	// ◆◆◆ == 기업 탈퇴 처리 == ◆◆◆ //
	@Override
	public int remove() {
		int result = 0;
		
		try {
			String sql = " delete from tbl_company "
						+" where status = 0 ";
			
			pstmt = conn.prepareStatement(sql);
	        result = pstmt.executeUpdate();   
		} catch(SQLException e) {
			e.printStackTrace();
	       } finally {
	          close();
	       }   // end try~catch~finally--------------------   
	      return result;
	}	// end of public int remove()-----------





	

	

	





	



	





	
	
	
	
	
	
	
}
