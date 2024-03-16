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
	
	
   
	
	
	// ◆◆◆ === 채용지원 === ◆◆◆ //
	@Override
	public void recruit_apply(Scanner sc, User_DTO user, Company_DTO company) {
		// TODO Auto-generated method stub
		
	}	// end of public void recruit_apply(Scanner sc, User_DTO user, Company_DTO company, Paper_DTO paper, Recruit_Apply_DTO rcapply)-----

	
	
	
	
	// ◆◆◆ === 지원현황 === ◆◆◆ //
	@Override
	public void recruit_apply_situation(Scanner sc, Company_DTO company) {
		// TODO Auto-generated method stub
		
	}	// end of public void recruit_apply_situation(Scanner sc, Company_DTO company, Recruit_Apply_DTO rcapply)-------


	


	// ◆◆◆ === 구인회사 조회 === ◆◆◆ //
	   @Override
	    public Company_DTO company_search(Map<String, String> paraMap) {
	      
	      Company_DTO cdto = null;
	      try {
	         String sql = " select A.company_name"
	         		  + "		 , to_char(B.begin_day,'yyyy-mm-dd') as begin_day"
	         		  + "		 ,A.ceo_name, B.company_type, A.company_address "
	                  + "        , B.employee_num "
	                  + "        , decode(B.public_status, 0, '비상장', 1, '상장') AS public_status "
	                  + "        , B.capital_money, B.companylist_num "
	                  + " from tbl_company A RIGHT JOIN tbl_company_type B "
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
	            ctdto.setCompany_type(rs.getString("company_type"));
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





	






	

	

}
