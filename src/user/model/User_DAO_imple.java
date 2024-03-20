package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import common.MYDBConnection;
import user.domain.Paper_DTO;
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
           String sql = " select user_id, user_name, substr(user_passwd ,1,3) || lpad('*', length(user_passwd)-3, '*') as user_passwd ,"
                 + " user_address, user_tel,"
                 + " substr(user_security_num, 1 ,7) || lpad('*', length(user_security_num)-7, '*') as user_security_num , user_email ,"
                 + " func_age(user_security_num)as age , func_gender(user_security_num) as gender  "
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
              userdto.setAge(String.valueOf(rs.getInt("age")));
              userdto.setGender(rs.getString("gender"));
              userdto.setUser_address(rs.getString("user_address"));
              userdto.setUser_tel(rs.getString("user_tel"));
              userdto.setUser_address(rs.getString("user_address"));
              userdto.setUser_security_num(rs.getString("User_security_num"));
              userdto.setUser_email(rs.getString("User_email"));
           }   // end of while------------
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
	
	
	
	// ◆◆◆ === 나의 정보 수정(기존 정보 불러오기) === ◆◆◆ //
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


	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	
	
	//◆◆◆ === 나의 정보 수정전 정보 가져오기 === ◆◆◆ //
	@Override
	public String check_user_info(String chk, String user_id) {
      
		String result = "";
      
		try {
     		String sql = " select " + chk 
					+ " from tbl_user_info "
					+ " where user_id = ? ";

     		pstmt = conn.prepareStatement(sql); // 우편배달부 = 서버.prepareStatement(전달할sql문)

     		pstmt.setString(1, user_id);
         
     		rs = pstmt.executeQuery(); // SQL문 실행  
         
     		if(rs.next()) {
     			result =  rs.getString(chk);
     		}
               
		} catch (SQLException e) {
            e.printStackTrace();
		} finally { // 성공하든 안하든 무조건! 
			close();
		} // end of finally
		return result;
   } // end of public String check_user_info(String chk, String user_id)
	

// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	

	// ◆◆◆ === 이력서 조회 === ◆◆◆ //
	   @Override
	   public List<Paper_DTO> paper_info(User_DTO user) {
		   
		   List<Paper_DTO> paperlist = new ArrayList<>();
		   int paper_no = 0;
		   paper_no++;
		       
		   try {

	           String sql = " select paper_code, fk_user_id, paper_name, paper_registerday, paper_no "
		                 + " from tbl_paper "
		                 + " where fk_user_id = ? ";
		                 
	           pstmt = conn.prepareStatement(sql);
	           pstmt.setString(1, user.getUser_id()); 
		           
	           rs = pstmt.executeQuery(); // sql문 실행
		           
	           while(rs.next()) {
	        	   Paper_DTO paperdto = new Paper_DTO();
		            
	        	   paperdto.setFk_user_id(user.getUser_id());
	        	   paperdto.setPaper_code(rs.getString("paper_code"));
	        	   paperdto.setPaper_name(rs.getString("paper_name"));
	        	   paperdto.setPaper_registerday(rs.getString("paper_registerday"));
	        	   paperdto.setPaper_no(paper_no++);
		            
		             
		           paperlist.add(paperdto);
		              
	           }   // end of while------------
		           
	           Map<String, String> paraMap = new HashMap<>();
	           String paper_no_str = Integer.toString(paper_no);
	           paraMap.put("paper_no", paper_no_str);
	           paraMap.put("paper_code", user.getPaper().getPaper_code());
	           paraMap.put("user_id", user.getUser_id());
		           
	           update_paper_no(paraMap);
		           
	           user.getPaper().setPaper_no(paper_no);;
		           
	        } catch (SQLException e) {
	           e.printStackTrace();
	        } finally {
	           close();   
	        }    // end of try~catch~fianlly------------------  
	        return paperlist;  
	   }	// end of public List<Paper_DTO> paper_info(User_DTO user)----


	   
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	   
	   
	   
	   // ◆◆◆ ===  이력서 조회(이력서 없는 경우) === ◆◆◆ //
	   @Override
	   public int update_paper_no(Map<String, String> paraMap) {
	   
		   int result = 0;
	   
		   try {
			   String sql = " update tbl_paper set paper_no = ? "
	                 + " where fk_user_id = ? and paper_code = ? ";
	            
	   
			   pstmt = conn.prepareStatement(sql);
			   pstmt.setString(1, paraMap.get("paper_no"));
			   pstmt.setString(2, paraMap.get("user_id"));
			   pstmt.setString(3, paraMap.get("paper_code"));
	        	
			   result = pstmt.executeUpdate();   // sql 문 실행

		   } catch (SQLException e) {
			   e.printStackTrace();
		   } finally {
			   close();   
		   }    // end of try~catch~fianlly------------------  
		   return result;
      }		// end of public int update_paper_no(Map<String, String> paraMap)--------
	

	   
	   
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆


   // ◆◆◆ === 이력서 상세조회 === ◆◆◆ //   //   수정필요
   @Override
   public Map<String, String> paper_info_detail(int u_Choice1, User_DTO user) {
	      
         Map<String,String> papermap = null;
         try {
	            // SQL 문 작성
            String sql =  "   select paper_no, s.paper_name, u.user_name, "
	                  + "                     to_char(paper_registerday, 'yyyy-mm-dd') as register_day , "
	                  + "                      func_gender(u.user_security_num) AS gender "
	                  + "                       , func_age(u.user_security_num) AS age  "
	                  + "                       , substr(u.user_security_num, 1, 7) || lpad('*', length(u.user_security_num) - 7, '*') AS jubun , "
	                  + "                     u.user_name, func_gender(u.user_security_num) AS gender  "
	                  + "                       , func_age(u.user_security_num) AS age , u.user_tel, u.user_address, u.user_email,  "
	                  + "                     nvl(a.academy_name , '미등록') as academy_name , nvl(r.priority_name, '미등록') as priority_name,  "
	                  + "                     y.local_name || y.city_name as hope_city , "
	                  + "                      s.career, nvl(d.license_name , '미등록') as license_name , "
	                  + "                      case when d.license_day is not null then to_char(d.license_day , 'yyyy-mm-dd' ) "
	                  + "                          else '미등록' end as license_day ,  "
	                  + "                     nvl(d.license_company, '미등록') as license_company  , to_char(s.paper_registerday, 'yyyy-mm-dd') as paper_registerday , nvl(s.hope_money, '미등록') as hope_money "
	                  + "                     from tbl_academy a "
	                  + "                     right join tbl_user_info u on a.academy_code = u.fk_academy_code "
	                  + "                     left join tbl_priority r on u.fk_priority_code = r.priority_code "
	                  + "                      join tbl_paper s on u.user_id = s.fk_user_id "
	                  + "                      left join tbl_license_detail d on s.fk_license_code = d.license_code "
	                  + "                      join tbl_local y on s.fk_local_code = y.local_code "
	                  
	                  + "                      where user_id = ? and paper_no = ? ";
	                  
	             pstmt = conn.prepareStatement(sql); // 우편배달부 = 서버.prepareStatement(전달할sql문)
	             
	             
	             pstmt.setString(1, user.getUser_id());
	             pstmt.setInt(2, user.getPaper().getPaper_no());
	             
	             rs = pstmt.executeQuery(); // SQL문 실행  
	                  
	             if(rs.next()) { // select 결과가 있다면~
	                     
	                papermap = new HashMap<String, String>();
	                
	                papermap.put("paper_no", rs.getString("paper_no"));
	                papermap.put("paper_name", rs.getString("paper_name"));
	                papermap.put("user_name", rs.getString("user_name"));
	                papermap.put("register_day", rs.getString("register_day"));
	                papermap.put("gender", rs.getString("gender"));
	                papermap.put("age", rs.getString("age"));
	                papermap.put("user_tel", rs.getString("user_tel"));
	                papermap.put("user_address", rs.getString("user_address"));
	                papermap.put("user_email", rs.getString("user_email"));
	                papermap.put("academy_name", rs.getString("academy_name"));
	                papermap.put("priority_name", rs.getString("priority_name"));
	                papermap.put("hope_city", rs.getString("hope_city"));
	                papermap.put("career", rs.getString("career"));
	                papermap.put("license_name", rs.getString("license_name"));
	                papermap.put("license_day", rs.getString("license_day"));
	                papermap.put("license_company", rs.getString("license_company"));
	                papermap.put("hope_money", rs.getString("hope_money"));
 
	             } // end of while        
	         } catch (SQLException e) {
	                 e.printStackTrace();
	         } finally {    // 성공하든 안하든 무조건! 
	            close();   // 자원반납 하기
	         } // end of finally
	         return papermap;
  } // end of public Map<String, String> paper_one(String input_rcno, Company_DTO company)

	   
	   
	   
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	   
	   
	
	// ◆◆◆ === 희망지역 검색 === ◆◆◆ //
	public Paper_DTO hope_local(Scanner sc, User_DTO user) {
	      
		Paper_DTO paperdto = null;
		
		try {
            String sql = " select local_code, local_name || ' ' || city_name "
	                  + " from tbl_local "
	                  + " where local_name like '%'||?||'%'  and city_name like '%'||?||'%' " ;
	            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getPaper().getHope_local_name()); 
            pstmt.setString(2, user.getPaper().getHope_city_name());
	            
            rs = pstmt.executeQuery(); // sql문 실행

            if(rs.next()) {
                 paperdto = new Paper_DTO();
	                 
                 user.getPaper().setFk_local_code(rs.getString("local_code")); 
            }
            else {
                 System.out.println("값이 없습니다.");
            }
		} catch (SQLException e) {
             e.printStackTrace();
		} finally {
             close();   
		}    // end of try~catch~fianlly------------------  
		return paperdto; 
   }	// end of public Paper_DTO hope_local(Scanner sc, User_DTO user)--------------

	

	// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	
	
	// ◆◆◆ === 신입 === ◆◆◆ //
   @Override
   public int career_detail_new(User_DTO user) {
      int result = 0;
      
         try {
            String sql = " update tbl_paper set career = ? "
                  + " where fk_user_id = ? ";
            
            pstmt = conn.prepareStatement(sql);
             
            pstmt.setString(1, user.getPaper().getCareer());
            pstmt.setString(2, user.getUser_id());
            result = pstmt.executeUpdate();      // SQL문 실행
            
            
         } catch (SQLException e) {
            
               e.printStackTrace();
               // end of if~else------------
         } finally {
            close();
         }   // end of try~catch~finally-------------------
         return result;
   }	// end of public int career_detail_new(User_DTO user)-------
   
   

// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
   
   
   
   
	// ◆◆◆ === 경력 === ◆◆◆ //
   @Override
   public int career_detail(User_DTO user) {
      int result = 0;
      
         try {
            String sql = " update tbl_paper set career = ? "
                  + " where fk_user_id = ? ";
            
            pstmt = conn.prepareStatement(sql);
             
            pstmt.setString(1, user.getPaper().getCareer());
            pstmt.setString(2, user.getUser_id());
            result = pstmt.executeUpdate();      // SQL문 실행
            
            if(result != 0) {
               sql = " insert into tbl_career_detail(career_company_name, career_startday, career_endday) "
                     + "values(?, ?, ?) ";      
               
               pstmt = conn.prepareStatement(sql);         
               pstmt.setString(1, user.getPaper().getCareer_company_name());   
               pstmt.setString(2, user.getPaper().getCareer_startday());   
               pstmt.setString(3, user.getPaper().getCareer_endday());   
               
               result = pstmt.executeUpdate();      // SQL문 실행
            }
         } catch (SQLException e) {
            
               e.printStackTrace();
               // end of if~else------------
         } finally {
            close();
         }   // end of try~catch~finally-------------------
         return result;
   }	// end of public int career_detail(User_DTO user)------------
   
   
 
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
   
   
   
   
   // ◆◆◆ === 이력서 작성 === ◆◆◆ //
   public int write_paper_sql(User_DTO user) {
	      int result = 0;
	        // career_detail(sc, user);
	        
	        try {
	           String sql = " insert into tbl_paper(fk_user_id , fk_license_code, fk_local_code, user_security_num, career, hope_money, paper_name, paper_code) "
	                 + " values(?, ?, ?, ?, ?,?, ?, paper_code.nextval ) ";      
	              
	           pstmt = conn.prepareStatement(sql);         
	           pstmt.setString(1, user.getUser_id());
	           pstmt.setString(2, user.getPaper().getFk_license_code());
	           pstmt.setString(3, user.getPaper().getFk_local_code());
	           pstmt.setString(4, user.getUser_security_num());
	           pstmt.setString(5, user.getPaper().getCareer());
	           pstmt.setString(6, user.getPaper().getHope_money());
	           pstmt.setString(7, user.getPaper().getPaper_name());
	           
	           
	           result = pstmt.executeUpdate();      // SQL문 실행
	        } catch (SQLException e) { 
	              e.printStackTrace();
	        } finally {
	           close();
	        }   // end of try~catch~finally-------------------
	        return result;
	   }
   
   
   
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
   
   
	
   // ◆◆◆ === 이력서 보여주기 === ◆◆◆ //
   public Paper_DTO view_paper(String paper_code, User_DTO user) {
      
      Paper_DTO paperdto = null;
      
      try {
         String sql = " SELECT U.user_id, P.fk_user_id, P.paper_code, P.fk_license_code, P.fk_local_code, P.career, P.hope_money, P.paper_name "
                  + " FROM TBL_USER_INFO U RIGHT JOIN TBL_PAPER P "
                  + " ON U.user_id = P.fk_user_id "
                  + " WHERE U.user_id = ? and P.paper_code = ? ";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, user.getUser_id());
         pstmt.setString(2, paper_code);
         
         rs = pstmt.executeQuery(); // SQL문 실행
         
         if(rs.next()) { // 입력한 숫자에 해당하는 글번호가 존재하는 경우
            paperdto = new Paper_DTO();
            
            paperdto.setFk_user_id(rs.getString("fk_user_id"));
            paperdto.setPaper_code(rs.getString("paper_code"));
            paperdto.setFk_license_code(rs.getString("fk_license_code"));
            paperdto.setFk_local_code(rs.getString("fk_local_code"));
            paperdto.setCareer(rs.getString("career"));
            paperdto.setHope_money(rs.getString("hope_money"));
            paperdto.setPaper_name(rs.getString("paper_name"));
         }
         
      } catch (SQLException e) {
         if(e.getErrorCode() == 1722) {
            System.out.println(">> [경고] 이력서번호는 정수만 가능합니다. <<\n");
         }
         else {
            e.printStackTrace();
         }
         
      } finally {
         close();
      }
      
      return paperdto;

   }   // end of public Paper_DTO view_paper(String paper_code, User_DTO user)----
   
   
   
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
   
   
   
   // ◆◆◆ === 나의 이력서 수정하기 === ◆◆◆ //
   public int update_paper(Map<String, String> paraMap, String paper_code) {
      
      int result = 0;
      
      try {
         String sql = " update TBL_PAPER set fk_license_code = ?, fk_local_code = ?, career = ?, hope_money = ?, paper_name = ? "
                  + " where paper_code = ? ";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, paraMap.get("fk_license_code"));
         pstmt.setString(2, paraMap.get("fk_local_code"));
         pstmt.setString(3, paraMap.get("career"));
         pstmt.setString(4, paraMap.get("hope_money"));
         pstmt.setString(5, paraMap.get("paper_name"));
         pstmt.setString(6, paper_code);
      
         result = pstmt.executeUpdate(); // SQL문 실행
         
      } catch (SQLException e) {
         if(e.getErrorCode() == 1722) {
            System.out.println(">> [경고] 이력서번호는 정수로만 입력하세요. <<\n");
         }
         else {
            e.printStackTrace();
         }
      } finally {
         close(); 
      }
      
      return result;
      
   }   // end of public int update_paper(Map<String, String> paraMap, String paper_code)----
	
	
	
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


	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	// ◆◆◆ === 이력서가 존재하는지 확인하기  === ◆◆◆ //
	@Override
	public boolean check_paper(int paper_code, User_DTO user) {
		boolean is_exist = false; 
	      
		try {
	         String sql = " with "
	         		+ "    P as "
	         		+ "    ( "
	         		+ "        select * "
	         		+ "        from tbl_paper "
	         		+ "    ) "
	         		+ "    , "
	         		+ "    U as "
	         		+ "    ( "
	         		+ "        select * "
	         		+ "        from tbl_user_info "
	         		+ "    ) "
	         		+ "    select U.user_id, P.paper_code "
	         		+ "    from P JOIN U "
	         		+ "    ON P.fk_user_id = U.user_id "
	         		+ "    where U.user_id = ? and paper_code = ? ";
	
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, user.getUser_id());
	         pstmt.setInt(2, paper_code);
	         	         
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	        	 is_exist = true;
	         }
	         
		}catch (SQLException e) {
            e.printStackTrace();
	    }finally {
            close();
	    } // end of finally--------------------
		
		return is_exist;   
	}	// end of public boolean check_paper(String paper_code, User_DTO user)---------------


//◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	// ◆◆◆ === 이력서 삭제전 간단하게 보여주기 === ◆◆◆
	   @Override
	   public List<Paper_DTO> get_paperlist(User_DTO user) {

	       Paper_DTO papinfo = null;
	       
	       List<Paper_DTO> paplist = new ArrayList<>(); // 이거 설명
	               
	       try {
	            String sql = " select paper_code , paper_name , career , hope_money "   
	                  + " from tbl_paper "
	                  + " where fk_user_id = ? ";
	            
	            pstmt = conn.prepareStatement(sql); // 우편배달부 = 서버.prepareStatement(전달할sql문)
	             
	            pstmt.setString(1, user.getUser_id());
	             
	            rs = pstmt.executeQuery(); // SQL문 실행  
	                  
	            while (rs.next()) { // select 결과가 있다면~
	                papinfo = new Paper_DTO();
	                     
	                papinfo.setPaper_code(rs.getString("paper_code"));
	                papinfo.setPaper_name(rs.getString("paper_name"));
	                papinfo.setCareer(rs.getString("career"));
	                papinfo.setHope_money(rs.getString("hope_money"));
	                     
	                paplist.add(papinfo);           
	            } // end of while        
	         } catch (SQLException e) {
	                 e.printStackTrace();
	         } finally {    // 성공하든 안하든 무조건! 
	            close();   // 자원반납 하기
	         } // end of finally  
	         return paplist;
	   } // end of public List<Paper_DTO> get_paperlist(User_DTO user)

	
	   
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	   
	   
   // ◆◆◆ === 이력서 삭제 === ◆◆◆ //
   @Override
   public int delete_paper(String input_rcno) {

         int result = 0;
         
         try {
               String sql = " delete from tbl_paper where paper_code = ? ";
                         
               pstmt = conn.prepareStatement(sql);   
               
               pstmt.setString(1, input_rcno);   
               
               result = pstmt.executeUpdate();      // SQL문 실행
               
         } catch (SQLException e) {
               e.printStackTrace(); 
         } finally {
            close();
         }   // end try~catch~finally--------------------
          
      return result;
      
   } // end of public int delete_paper(String input_rcno)
	   
	   
	   
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	

	// ◆◆◆ ===  관리자 탈퇴 처리 === ◆◆◆ //
	@Override
	public int remove() {
		int result = 0;
		
		try {
			String sql = " delete from tbl_user_info "
						+" where status = 0 ";
			
			pstmt = conn.prepareStatement(sql);
	          
	        result = pstmt.executeUpdate();   
		} catch(SQLException e) {
			e.printStackTrace();
	       } finally {
	          close();
	       }   // end try~catch~finally--------------------   
	      return result;
	}	// end of public int remove()------------
	
	

// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆

	
	
	// ◆◆◆ ===  아이디 찾기 === ◆◆◆ //
	@Override
	public String findid(User_DTO user) {
		User_DTO userdto = null;
     
		try {
			String sql = " select user_id  "
					+ " from tbl_user_info "
					+ " where user_name = ? and user_email = ? and user_security_num = ? ";
        
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUser_name()); 
			pstmt.setString(2, user.getUser_email());
			pstmt.setString(3, user.getUser_security_num());
        
			rs = pstmt.executeQuery(); // sql문 실행
          
			if(rs.next()) {
				userdto = new User_DTO();
				userdto.setUser_id(rs.getString("user_id"));
			}
			else if (user.getUser_id() == null) {
				return "값이 없습니다.";
			}
			else {
				System.out.println("값이 없습니다."); 
			}    
		} catch (SQLException e) {
     		e.printStackTrace();
		} finally {
			close();   
		}    // end of try~catch~fianlly------------------  
		return "야이디 : " + userdto.getUser_id();
  }		// end of public String findid(User_DTO user)-----------
	

// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
   
   
	// ◆◆◆ ===  비밀번호 찾기 === ◆◆◆ //
   @Override
   public String findpasswd(User_DTO user) {
      User_DTO userdto = null;
      
      try {
         String sql = " select user_passwd "
               + " from tbl_user_info "
               + " where user_name = ? and user_email = ? and user_security_num = ? and user_id = ? ";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, user.getUser_name()); 
         pstmt.setString(2, user.getUser_email());
         pstmt.setString(3, user.getUser_security_num());
         pstmt.setString(4, user.getUser_id());
         
         rs = pstmt.executeQuery(); // sql문 실행
           
         if(rs.next()) {
        	 userdto = new User_DTO();
        	 userdto.setUser_passwd(rs.getString("user_passwd"));
         }
         else if (user.getUser_passwd() == null) {
        	 return "값이 없습니다.";
         }
         else {
        	 System.out.println("값이 없습니다."); 
         }  
      } catch (SQLException e) {
              e.printStackTrace();
      } finally {
          close();   
      }    // end of try~catch~fianlly------------------  
      return "비밀번호 : " + userdto.getUser_passwd();
   }	// end of public String findpasswd(User_DTO user)--------------




}
