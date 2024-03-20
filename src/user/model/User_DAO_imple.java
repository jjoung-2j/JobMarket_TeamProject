package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	   }	// end of public int write_paper_sql(User_DTO user)-----
   
   
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
   
	
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
 	       Paper_DTO paperdto = null;
 	      
 	      List<Paper_DTO> paperlist = new ArrayList<>();
 	         int paper_no = 0;
 	         paper_no++;
 	          
 	           try {
 	              String sql = " select paper_code, fk_user_id"
 	                          + "      , CASE WHEN length(paper_name) > 10 THEN substr(paper_name,1,7) || '...' ELSE paper_name END AS paper_name"
 	                          + "      , paper_registerday, paper_no "
 	                         + " from tbl_paper p, tbl_license_detail l "
 	                         + " where p.fk_license_code = l.license_code and fk_user_id = ? ";
 	                    
 	              pstmt = conn.prepareStatement(sql);
 	              pstmt.setString(1, user.getUser_id()); 
 	              
 	              rs = pstmt.executeQuery(); // sql문 실행
 	              
 	              while(rs.next()) {
 	                 paperdto = new Paper_DTO();
 	                 
 	                 //paperdto.setUser_id(user.getUser_id());
 	                 paperdto.setPaper_code(rs.getInt("paper_code"));
 	                 paperdto.setPaper_name(rs.getString("paper_name"));
 	                 paperdto.setPaper_registerday(rs.getString("paper_registerday"));
 	                 paperdto.setPaper_no(paper_no++);
 	                 
 	                 paperlist.add(paperdto);
 	              }   // end of while------------
 	           } catch (SQLException e) {
 	              e.printStackTrace();
 	           } finally {
 	              close();   
 	           }    // end of try~catch~fianlly------------------  
 	           
 	           return paperlist;
 	         
 	      }

	
	
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

	//  아이디찾기
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
   
   
   //   비밀번호 찾기
   @Override
   public User_DTO findpasswd(User_DTO user) {
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
           else {
              System.out.println("값이 없습니다.");
           }
      } catch (SQLException e) {
              e.printStackTrace();
           } finally {
              close();   
           }    // end of try~catch~fianlly------------------  
      return userdto;
   }	// end of public User_DTO findpasswd(User_DTO user)--------------



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
            paperdto.setPaper_code(rs.getInt("paper_code"));
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
	
	

}
