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
import company.domain.Recruit_INFO_DTO;
import user.domain.User_DTO;

public class Recruit_DAO_imple implements Recruit_DAO {

	// field

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
	
	
	
	
	
   // ◆◆◆ === 채용공고 등록 === ◆◆◆ //
	 @Override
	   public int recruit_write(Map<String, String> paraMap, Company_DTO company) {
	      

	      int result = 0;
	      
	      try {
	            String sql = " insert into tbl_recruit_info (recruit_no, fk_company_id , manager_name , manager_email , "            
	                  + "  recruit_title, recruit_people , recruit_deadline, year_salary, recruit_content , "
	                  + "  work_day , work_time, career , fk_hiretype_code, recruit_field ) "
	                  + "  values(to_char(sysdate, 'yyyymmdd') || '-' || SEQ_RECRUIT_NO.nextval , ? , ? , ? , "
	                  + "   ?, ? , ?  ,? ,? , ? , ? , ? , ? , ?)  ";      
	            
	            pstmt = conn.prepareStatement(sql);         
	            
	            pstmt.setString(1, paraMap.get("fk_company_id") );   
	            pstmt.setString(2, paraMap.get("manager_name") ); 
	            pstmt.setString(3, paraMap.get("manager_email") );   
	            pstmt.setString(4, paraMap.get("recruit_title") );
	            pstmt.setString(5, paraMap.get("recruit_people") );
	            pstmt.setString(6, paraMap.get("recruit_deadline"));
	            pstmt.setString(7, paraMap.get("year_salary") );
	            pstmt.setString(8, paraMap.get("recruit_content") );
	            pstmt.setString(9, paraMap.get("work_day") );
	            pstmt.setString(10, paraMap.get("work_time") );
	            pstmt.setString(11, paraMap.get("career") );
	            pstmt.setString(12, paraMap.get("fk_hiretype_code") );
	            
	            if("1".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "정규직" );
	               
	            }
	            else if("2".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "계약직" );
	               
	            }else if("3".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "인턴" );
	               
	            }else if("4".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "파견직" );
	               
	            }else if("5".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "프리랜서" );
	               
	            }else if("6".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "아르바이트" );
	               
	            }else if("7".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "연수생" );
	               
	            }else if("8".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "위촉직" );
	               
	            }else if("9".equals(paraMap.get("recruit_field") ) ) {
	               
	               pstmt.setString(13, "개인사업자" );
	               
	            }
	            
	            
	            result = pstmt.executeUpdate();      // SQL문 실행
	            
	         } catch (SQLException e) {
	            if(e.getErrorCode() == 1) {   // 유니크 제약(userid)에 중복되어지면,
	               System.out.println(">>  <<");
	            }
	            else {
	               e.printStackTrace();
	            }   // end of if~else------------
	         } finally {
	            close();
	         }   // end try~catch~finally--------------------
	      
	         return result;
	      
	   } // end of public int recruit_write(Map<String, String> paraMap, Company_DTO company)
	
	
	// ◆◆◆ === 지원한 구직자 조회 === ◆◆◆ //
	@Override
	public void apply_user_search(Scanner sc,  User_DTO user, Company_DTO company) {
		// TODO Auto-generated method stub
		
	}	// end of public void apply_user_search(Scanner sc, User_DTO user, Company_DTO company, Recruit_INFO_DTO recruit)------




	// ◆◆◆ === 채용공고 조회(전체) === ◆◆◆ //
	@Override
	public List<Recruit_INFO_DTO> recruit_info_list() {
		List<Recruit_INFO_DTO> recruit_info_list = new ArrayList<>();
	      
	      try {
	            String sql = " select B.recruit_no, B.recruit_title, A.company_name, A.company_address, B.career, B.year_salary "
	                      + "      , '~ ' || B.recruit_deadline AS recruit_deadline "
	                      + " from TBL_COMPANY A RIGHT JOIN TBL_RECRUIT_INFO B "
	                      + " ON A.company_id = B.fk_company_id ";
	            
	            pstmt = conn.prepareStatement(sql);
	            
	            rs = pstmt.executeQuery(); // SQL문 실행
	            
	            while(rs.next()) {
	               
	               Recruit_INFO_DTO rdto = new Recruit_INFO_DTO();
	               
	               rdto.setRecruit_no(rs.getString("recruit_no"));
	               rdto.setRecruit_title(rs.getString("recruit_title"));
	               rdto.setCareer(rs.getString("career"));
	               rdto.setYear_salary(rs.getString("year_salary"));
	               rdto.setRecruit_deadline(rs.getString("recruit_deadline"));
	              
	               Company_DTO cdto = new Company_DTO();
	               cdto.setCompany_name(rs.getString("company_name"));
	               cdto.setCompany_address(rs.getString("company_address"));
	           
	               rdto.setCdto(cdto);
	               
	               recruit_info_list.add(rdto);
	               
	            }   // end of while--------------------  
	         } catch (SQLException e) {
	               e.printStackTrace(); 
	         } finally {
	            close();
	         }      // end of try~catch~finally------------------

	      return recruit_info_list;
	}	// end of public List<Recruit_INFO_DTO> recruit_info_list()------




	// ◆◆◆ === 채용공고 조회(로그인한 기업의 공고만) === ◆◆◆ //
	@Override
	public List<Recruit_INFO_DTO> All_recruit(Company_DTO company, Recruit_INFO_DTO ridto_register) {
		Recruit_INFO_DTO recruit_view = new Recruit_INFO_DTO();
		
		List<Recruit_INFO_DTO> recruitList = new ArrayList<>();
		
		try {
			String sql = " select recruit_no AS 공고번호 "
				         + " , recruit_field AS 채용분야 "
				         + " , recruit_title AS 공고명 "
				         + " , recruit_content AS 공고내용 "
				         + " , recruit_registerday AS 등록일 "
				         + " , recruit_deadline AS 마감일 "
				         + " , career AS 신입경력여부 "
				         + " , year_salary AS 연봉 "
				         + " , recruit_people AS 채용인원 "
				         + " , work_day AS 근무요일 "
				         + " , work_time AS 근무시간 "
				         + " , manager_email AS 담당자이메일 "
				         + " , manager_name AS 담당자명 "
				         + " from tbl_recruit_info "
				         + " where fk_company_id = ? ";
							
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, company.getCompany_id());  
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	
				recruit_view = new Recruit_INFO_DTO();
				
				recruit_view.setRecruit_no(rs.getString("공고번호"));
				recruit_view.setRecruit_field(rs.getString("채용분야"));
				recruit_view.setRecruit_title(rs.getString("공고명"));
				recruit_view.setRecruit_content(rs.getString("공고내용"));
				recruit_view.setRecruit_registerday(rs.getString("등록일"));
				recruit_view.setRecruit_deadline(rs.getString("마감일"));
				recruit_view.setCareer(rs.getString("신입경력여부"));
				recruit_view.setYear_salary(rs.getString("연봉"));
				recruit_view.setRecruit_people(rs.getString("채용인원"));
				recruit_view.setWork_day(rs.getString("근무요일"));
				recruit_view.setWork_time(rs.getString("근무시간"));
				recruit_view.setManager_email(rs.getString("담당자이메일"));
				recruit_view.setManager_name(rs.getString("담당자명"));
				
				recruitList.add(recruit_view);
			} // end of while(rs.next())
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		} // end of finally
		
		return recruitList;

	}	// end of public List<Recruit_INFO_DTO> All_recruit(Recruit_INFO_DTO ridto_register)--------




	
	
	
	
	
	

	
	

}
