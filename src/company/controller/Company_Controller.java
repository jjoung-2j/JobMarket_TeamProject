package company.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import common.MYDBConnection;
import common.Set_util;
import company.domain.Company_DTO;
import company.domain.Company_type_DTO;
import company.domain.Recruit_INFO_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import company.model.Recruit_DAO;
import company.model.Recruit_DAO_imple;
import user.domain.User_DTO;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class Company_Controller {

	// == field == 
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	Recruit_DAO rdao = new Recruit_DAO_imple();
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
	   
	
	
   
   
   // 기업로그인된 메뉴출력하는 메소드
   public void company_menu(Scanner sc, Company_DTO company) {
      
      String c_Choice = "";
      int c = 0;
      
      do { 
    	 ////////////////////////////////////////////////////////////////   
    	  System.out.println("\n>>> ---- 기업메뉴 ---- <<<\n"
		             + "1.기업정보 \n"
		             + "2.채용정보 \n"
		             + "3.구직자정보 \n"
		             + "4.로그아웃 \n"
		             + "5.회원탈퇴" );
		             
		
    	  System.out.print("▷ 메뉴번호 선택 : ");
    	  c_Choice = sc.nextLine();
		
    	  switch (c_Choice) {
    	  	case "1": // 기업 정보 메뉴
				company_info(sc, company);
				break;
			case "2": // 채용 정보 메뉴
				recruit_info(sc, company);
				break;
			case "3": // 구직자 정보 메뉴
				user_info(sc, company);
				break;
			case "4": // 로그아웃
				System.out.println(">>> " + company.getCompany_id() + "님 로그아웃 되었습니다. <<<\n");
				company = null;
				break;             
			case "5": // 회원탈퇴 
				c = cdao.Withdrawal(sc,company);
				if(c==1) {
					company = null;
					System.out.println(">>> 회원탈퇴 성공되었습니다. <<<");
					break; 
				}
				else {
					break;
				}
			default:
				System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				break;
    	  } // end of switch (c_Choice)-------------------------
           ////////////////////////////////////////////////////////////////
      } while (!("4".equals(c_Choice)) && !("5".equals(c_Choice) && c == 1));   
   } // end of public void companyMenu_Start(Scanner sc, Company_DTO company)




   
   
   
   
   
	//◆◆◆ === 기업 정보 메뉴 === ◆◆◆ //
	public void company_info(Scanner sc, Company_DTO company) {
		 
		String c_Choice = "";
	      
	     do {
		     System.out.println("\n>>> ---- 기업 정보 메뉴 ---- <<<\n"
		                   + "1.기업정보 보기 \n"
		                   + "2.추가정보 입력 \n"
		                   + "3.이전 메뉴로 돌아가기" );
		      
		     System.out.print("▶ 메뉴번호 선택 : ");
		     c_Choice = sc.nextLine();
		      
	         switch (c_Choice) {
	            
				case "1": // 로그인된 기업정보 보기
					Map<String, String> paraMap = new HashMap<>();
					paraMap.put("company_id", company.getCompany_id());
	               
					company = my_company_info(company , paraMap); //   company = select 조인문장
	              
					StringBuilder sb = new StringBuilder();  
	               
					sb.append("\n=== 기업 정보 ===\n"
	                     + "▶ 기업명 : " + company.getCompany_name() + "\n"
	                     + "▶ 기업대표 : " + company.getCeo_name() + "\n"
	                     + "▶ 사업자등록번호 : " + company.getBusiness_number()+ "\n"
	                     + "▶ 기업소재지 : " + company.getCompany_address() + "\n" );
	               
					if( company.getCompany_type_detail().getBegin_day() != null ) 
	                  sb.append("▶ 설립일자 : " + company.getCompany_type_detail().getBegin_day() + "\n");
					if( company.getCompany_type_detail().getCompany_type() != null ) 
	                  sb.append("▶ 업종 : " + company.getCompany_type_detail().getCompany_type() + "\n");
					if(   company.getCompany_type_detail().getCapital_money() != null ) 
	                  sb.append("▶ 자본금 : " + company.getCompany_type_detail().getCapital_money() + "\n");
					if (company.getCompany_type_detail().getEmployee_num() != null) 
	                  sb.append("▶ 사원수 : " + company.getCompany_type_detail().getEmployee_num() + "\n");
					if (company.getCompany_type_detail().getPublic_status() != null ) 
	                  sb.append("▶ 상장여부 : " + company.getCompany_type_detail().getPublic_status() + "\n");
					System.out.print(sb.toString());
					break;
				case "2": // 기업 추가정보 입력
					// == ●●●●●●●●●●●●● 작업중 ●●●●●●●●●●●●● == //
					//Company_type_DTO ctdto = new Company_type_DTO(); // 
					// ctdto = cdao.company_detail_info(sc, company);// 정수가 작업하려던중
					break;
				case "3": // 이전메뉴로 되돌아가기
					break;   
				default:
					System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
					break;
				} // end of switch (c_Choice)-----------------
	      } while (!"3".equalsIgnoreCase(c_Choice));	// end of do~while------------------	
	}	// end of public void company_menu2(Scanner sc, Company_DTO company)--------------


	
	
	
	
	
	
	// ◆◆◆ === 로그인된기업 정보보기 === ◆◆◆ //
	public Company_DTO my_company_info(Company_DTO company, Map<String, String> paraMap) {
       try {
            // conn = MyDBConnection.getConn(); 데이터베이스 연결하기
           String sql = " select C.company_id , C.company_name , C.businees_number , C.ceo_name , C.company_address, "
                   + " T.employee_num , decode(T.public_status , 1 , '상장기업' , '비상장기업') as public_status , to_char(T.begin_day , 'yyyy-mm-dd') as begin_day , " 
                   + " T.capital_money,  T.company_type   , to_char (T.companylist_num)  as companylist_num  "
                   + " from tbl_company C join tbl_company_type T " 
                   + " on C.company_id = T.fk_company_id "
                   + " where C.company_id = ? " ;  
          
           pstmt = conn.prepareStatement(sql); // 우편배달부 
           
           pstmt.setString(1, paraMap.get("company_id") ); 
          
           rs = pstmt.executeQuery(); // SQL문 실행  
           
           if(rs.next()) { // select 결과가 있다면~
              company = new Company_DTO();
              
              company.setCompany_name(rs.getString("company_name"));
              company.setBusiness_number(rs.getString("businees_number"));
              company.setCeo_name(rs.getString("ceo_name"));
              company.setCompany_address(rs.getString("company_address"));
              
              Company_type_DTO ctdto = new Company_type_DTO();
              
              ctdto.setEmployee_num(rs.getString("employee_num"));
              ctdto.setPublic_status(rs.getString("public_status"));
              ctdto.setBegin_day(rs.getString("begin_day"));
              ctdto.setCapital_money(rs.getString("capital_money"));
              ctdto.setCompany_type(rs.getString("company_type"));
              ctdto.setCompanylist_num(rs.getString("companylist_num"));
              
              company.setCompany_type_detail(ctdto);
            }   // end of if----------    
       } catch (SQLException e) {
             e.printStackTrace();
       } finally { 
           close();   // 자원 반납
       } // end of try~catch~finally------------------- 
      return company;
   } // end of private void company_info(Company_DTO company)




	
	
	
	

	// ◆◆◆ === 채용정보 메뉴 === ◆◆◆ //
	public void recruit_info(Scanner sc, Company_DTO company) {
		String c_Choice = "";
	      
	    do {
		     System.out.println("\n>>> ---- 채용 정보 메뉴 ---- <<<\n"
		                   + "1. 채용공고 등록\n"
		                   + "2. 채용공고 조회\n"
		                   + "3. 채용공고 수정\n"
		                   + "4. 채용공고 삭제\n"
		                   + "5.이전 메뉴로 돌아가기" );
		     
		     System.out.print("▶ 메뉴번호 선택 : ");
		     c_Choice = sc.nextLine();
		      
	         switch (c_Choice) {
				case "1": 	// 채용공고 등록
					recruit_register(sc, company);
				   	break;
				case "2": 	// 채용공고 조회
					recruit_information(sc, company);
				   	break;
				case "3":	// 채용공고 수정
					recruit_fix(sc, company);
					break;
				case "4":	// 채용공고 삭제
					recruit_delete(sc, company);
					break;
				case "5": 	// 이전메뉴로 되돌아가기
				   break;   
				default:
				   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				   break;
	         } // end of switch (c_Choice)-----------------
	     } while (!"5".equalsIgnoreCase(c_Choice));	// end of do~while------------------	
	}	// end of public void recruit_info(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)-----------



	
	
	
	
	// ◆◆◆ === 채용공고 등록 === ◆◆◆ //
	Recruit_INFO_DTO ridto_register = new Recruit_INFO_DTO();
	public void recruit_register(Scanner sc, Company_DTO company) {
		
		Map<String, String> paraMap = new HashMap<>();	// 몇개의 변수이던간에 하나의 변수에 담아서 처리하려면?? MAP
		
		System.out.println("\n >> " + company.getCompany_name() + "채용공고 등록 <<");
		
		paraMap.put("fk_company_id", company.getCompany_id());
		
		// 담당자명
		do {
			System.out.print("\n▶ 담당자명 : ");
			String manager_name = sc.nextLine();
			
			if(Set_util.Check_name(manager_name)) {
				paraMap.put("manager_name", manager_name); 
				break;
			}
		}while(true);	// end of do~while-------------
		
		// 담당자 이메일
		do {
			System.out.print("\n▶ 담당자 이메일 : ");
			String manager_email = sc.nextLine();
			if(Set_util.Check_email(manager_email)) {
				paraMap.put("manager_email", manager_email);
				break;
			}
		}while(true);	// end of do~while---------------
		
		// 채용공고명
		do {
			System.out.print("\n▶ 채용공고명 : ");
			String recruit_title = sc.nextLine();
			if(!(recruit_title == null || recruit_title.isBlank())) {
				if(recruit_title.length() <= 40) {
					paraMap.put("recruit_title", recruit_title);
					break;
				}
				else {
					System.out.println(">>> [경고] 채용공고명은 40자 미만으로 작성해주세요. <<<");
				}
			}
			else {
				System.out.println(">>> [경고] 채용공고명을 반드시 입력해주세요. <<<");
			}
		}while(true);	// end of do~while------------
		
		// 채용인원수
		do {
			System.out.println("\n[ 제한이 없으실 경우 엔터를 입력해주세요. ]");
			System.out.print("▶ 채용인원수 : ");
			String recruit_people = sc.nextLine();
			if(recruit_people == null || recruit_people.isBlank()) {
				paraMap.put("recruit_people", "0");
				break;
			}
			else {
				if(Set_util.Check_recruit_num(recruit_people)) {
					paraMap.put("recruit_people", recruit_people);
					break;
				}
				else {
					System.out.println(">>> [경고] 숫자 3자리 이내로 입력해주세요. <<<");
				}
			}
		}while(true);
		
		 
		System.out.println("\n[ 제한이 없으실 경우 엔터를 입력해주세요. ]");
		System.out.print("▶ 마감일 : ");
		String deadline = sc.nextLine();
        if(!(deadline == null || deadline.isBlank())) {
          if(Set_util.Check_date(deadline)) {
             paraMap.put("recruit_deadline", deadline);
          }
		}
		else {
			paraMap.put("recruit_deadline", "채용마감시까지");
		}
		
		do {
			System.out.print("\n▶ 신입/경력 여부[신입/경력/무관] : ");
			String career = sc.nextLine();
			if("신입".equals(career) || "경력".equals(career) || "무관".equals(career)) {
				paraMap.put("career", career);
				break;
			}
			else {
				System.out.println("[경고] 신입 / 경력 / 무관 중 선택해주세요. <<<");
			}
		}while(true);
		
		do {
			System.out.print("\n▶ 연봉 : ");
			String year_salary = sc.nextLine();
			if(year_salary != null) {
				paraMap.put("year_salary", year_salary);
				break;
			}
		}while(true);
		
		do {
			System.out.print("\n▶ 채용공고내용 : ");
			String recruit_content = sc.nextLine();
			if(recruit_content != null) {
				paraMap.put("recruit_content", recruit_content);
				break;
			}
		}while(true);
		
		do {
			System.out.print("\n▶ 채용분야 (1~9) : ");
			String recruit_field = sc.nextLine();
			if(recruit_field != null) {
				paraMap.put("recruit_field", recruit_field);
				paraMap.put("fk_hiretype_code", recruit_field);
				break;
			}
		}while(true);
		
		do {
			System.out.print("\n▶ 근무요일 : ");
			String work_day = sc.nextLine();
			if(work_day != null) {
				paraMap.put("work_day", work_day);
				break;
			}
		}while(true);
		
		do {
			System.out.print("\n▶ 근무시간 : ");
			String work_time = sc.nextLine();
			if(work_time != null) {
				paraMap.put("work_time", work_time);
				break;
			}
		}while(true);
		
		int n = rdao.recruit_write(paraMap, company);
        

        if ( n == 1 ) {
           
           System.out.println(">> 채용공고 등록 완료!! <<");
           System.out.println(company.getCompany_name() + "기업의" + " 채용공고등록일 : " + company.getRecruit().getRecruit_registerday());
        } 
        else {
           
           System.out.println(">> 채용공고 등록 실패!! <<");
        }
	}	// end of public void recruit_register(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)-------



		
		
		

	
	
	// ◆◆◆ === 채용공고 조회 === ◆◆◆ //
	public void recruit_information(Scanner sc, Company_DTO company) {
		
		List<Recruit_INFO_DTO> recruitList = rdao.All_recruit(company, ridto_register);
		
		StringBuilder sb = new StringBuilder();
			
		if(recruitList.size() > 0) {
			System.out.println("\n" + "-".repeat(40) + company.getCompany_name() + "님의 [현재 진행중인 채용공고] " + "-".repeat(40));
			System.out.println("공고번호 채용분야 공고명 공고내용 등록일 마감일 신입/경력여부 연봉 채용인원 근무요일 근무시간 담당자이메일 담당자명");
			System.out.println("-".repeat(100));
			
			sb = new StringBuilder();
			
			for(Recruit_INFO_DTO recruit : recruitList) {
				sb.append(recruit.getRecruit_no() + "" +
					      recruit.getRecruit_field() + "" +
					      recruit.getRecruit_title() + "" +
					      recruit.getRecruit_content() + "" +
					      recruit.getRecruit_registerday() + "" +
					      recruit.getRecruit_deadline() + "" +
					      recruit.getCareer() + "" +
					      recruit.getYear_salary() + "" +
					      recruit.getRecruit_people() + "" +
					      recruit.getWork_day() + "" +
					      recruit.getWork_time() + "" +
					      recruit.getManager_email() + "" +
					      recruit.getManager_name() + "\n");
						  
			} // end of for(Recruit_INFO_DTO recruit_info : recruitList)
			
			System.out.println(sb.toString());
			
		} // end of if (recruitList.size() > 0)
		else
			System.out.println(">> 현재 진행중인 채용공고가 존재하지 않습니다. <<\n");
		
		
	}	// end of public void recruit_information(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------



	
	
	
	// ◆◆◆ === 채용공고 수정 === ◆◆◆ //
	public void recruit_fix(Scanner sc, Company_DTO company) {
		// TODO Auto-generated method stub
		
	}	// end of public void recruit_fix(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------




	
	

	// ◆◆◆ === 채용공고 삭제 === ◆◆◆ //
	public void recruit_delete(Scanner sc, Company_DTO company) {
		// TODO Auto-generated method stub
		
	}	// end of public void recruit_delete(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------


		
		
		
	// ◆◆◆ === 구직자 정보 메뉴 === ◆◆◆ //
	public void user_info(Scanner sc, Company_DTO company) {
		String c_Choice = "";
	      
	     do {
	    	 User_DTO user = new User_DTO();
		     System.out.println("\n>>> ---- 구직자 정보 메뉴 ---- <<<\n"
		                   + "1. 모든 구직자 조회\n"
		                   + "2. 지원한 구직자 조회\n"
		                   + "3.이전 메뉴로 돌아가기" );
		      
		     System.out.print("▶ 메뉴번호 선택 : ");
		     c_Choice = sc.nextLine();
		      
	         switch (c_Choice) {
	            
				case "1": // 모든 구직자 조회
					List<User_DTO> memberList = cdao.All_user();
		               
	                StringBuilder sb = new StringBuilder();
	               
	                if(memberList.size() > 0) {
	                  
	                	System.out.println("-".repeat(80));
	                	System.out.println("성명\t주소\t\t연락처\t\t이메일\t\t생년월일");
	                	System.out.println("-".repeat(80));
	                  
	                	sb = new StringBuilder();
	                  
	                	for(User_DTO member : memberList) {
	                		sb.append(member.getUser_name() + " " +
	                				member.getUser_address() + " " +
	                				member.getUser_tel() + " " +
	                				member.getUser_email() + " " +
	                				member.getUser_security_num().substring(0, 6) + "\n");
	                	} // end of for
	                	System.out.println(sb.toString() );  
		            } else 
		                System.out.println(">> 가입된 회원이 1명도 없습니다. ㅜㅜ");  
				   break;
				case "2": // 지원한 구직자 조회
				   rdao.apply_user_search(sc, user, company);
				   break;
				case "3": // 이전메뉴로 되돌아가기
				   break;   
				default:
				   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				   break;
				} // end of switch (c_Choice)-----------------
	      } while (!"3".equalsIgnoreCase(c_Choice));	// end of do~while------------------	
	}	// end of public void user_info(Scanner sc, User_DTO user)-------

}
