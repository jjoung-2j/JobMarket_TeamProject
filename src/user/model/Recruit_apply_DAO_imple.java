package user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import common.MYDBConnection;
import company.domain.Company_DTO;
import company.domain.Company_type_DTO;
import company.domain.Recruit_INFO_DTO;
import user.domain.Paper_DTO;
import user.domain.Recruit_Apply_DTO;
import user.domain.User_DTO;

public class Recruit_apply_DAO_imple implements Recruit_apply_DAO {
	
	private Connection conn = MYDBConnection.getConn();      // 데이터베이스 서버 연결
   private PreparedStatement pstmt;   // 우편배달부
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
      }   // end of try~catch----------
   }   // end of private void close()---------------
	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆  
	
	

   
   // ◆◆◆ === 구인회사 조회 === ◆◆◆ //
   @Override
    public Company_DTO company_search(Map<String, String> paraMap) {
      
      Company_DTO cdto = null;
      try {
         String sql = " select A.company_name"
         		  + "		 , to_char(B.begin_day,'yyyy-mm-dd') as begin_day"
         		  + "		 , A.ceo_name, A.company_address "
                  + "        , B.employee_num "
                  + "        , decode(B.public_status, 0, '비상장', 1, '상장') AS public_status "
                  + "        , B.capital_money, B.companylist_num "
                  + " from tbl_company A LEFT JOIN tbl_company_type B "
                  + " ON A.company_id = B.fk_company_id "
                  + " where lower(A.company_name) = lower(?) ";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, paraMap.get("company_name"));
         
         rs = pstmt.executeQuery(); // SQL문 실행
         
         if(rs.next()) {
            cdto = new Company_DTO();
            
            cdto.setCompany_name(rs.getString("company_name"));
            cdto.setCeo_name(rs.getString("ceo_name"));
            cdto.setCompany_address(rs.getString("company_address"));
            
            Company_type_DTO ctdto = new Company_type_DTO();
            ctdto.setBegin_day(rs.getString("begin_day"));
            ctdto.setEmployee_num(rs.getString("employee_num"));
            ctdto.setPublic_status(rs.getString("public_status"));
            ctdto.setCapital_money(rs.getString("capital_money"));
            ctdto.setCompanylist_num(rs.getString("companylist_num"));
            
            cdto.setCompany_type_detail(ctdto);
         }   // end of if--------------------  
      } catch (SQLException e) {
            e.printStackTrace(); 
      } finally {
         close();
      }      // end of try~catch~finally------------------
      return cdto;
   }   // end of public Company_DTO company_search(Map<String, String> paraMap)-----



// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
   
   
   
   // ◆◆◆ === 채용공고에 채용지원서 넣기 === ◆◆◆ //
   @Override
   public int my_recruit_apply(Recruit_Apply_DTO radto) {
	   int result = 0;
	      
	      try {
	         conn.setAutoCommit(false);
	         
	         String sql = " insert into tbl_recruit_apply(fk_recruit_no, fk_paper_code, apply_motive) "
	                  + " values(?, ?, ?) ";
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, radto.getRecruit_no());
	         pstmt.setInt(2, radto.getPaper_code());
	         pstmt.setString(3, radto.getApply_motive());
	         
	         int n = pstmt.executeUpdate();
	         if(n == 1) { 
	            conn.commit(); 
	            result = 1;
	         }
	      } catch (SQLException e) {
	    	  
	    	  e.printStackTrace();
	    	  try {
	    		  conn.rollback(); 
	    		  result = -1; 
	    	  } catch(SQLException e2) { }
	    	  
	      } finally {
	    	  
	         try {
	        	 conn.setAutoCommit(true); 
	         } catch(SQLException e) { }
	         close(); 
	      }
	      return result;
   }	// end of public int my_recruit_apply(Recruit_Apply_DTO radto)-----------
   
   
   
   
   
   
   
   
   
   
   
   
   


   
   





	






	

	

}
