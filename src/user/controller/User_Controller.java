package user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.domain.User_DTO;
import company.model.Recruit_DAO;
import company.model.Recruit_DAO_imple;
import user.model.Recruit_apply_DAO;
import user.model.Recruit_apply_DAO_imple;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class User_Controller {

	// == field == 
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	Recruit_DAO rdao = new Recruit_DAO_imple();
	Recruit_apply_DAO radao = new Recruit_apply_DAO_imple();
	
	public void user_menu(Scanner sc, User_DTO user) {
		
		String u_Choice = "";
		int u = 0;
		
		do {
			System.out.println("\n >>> ---- 회원 메뉴 [ " + user.getUser_name()+ " 님 로그인 중.. ]---- <<<\n"
					+ "1. 나의정보\n"
					+ "2. 이력서관리\n"
					+ "3. 입사지원 제안 관리\n"
					+ "4. 로그아웃\n"
					+ "5. 회원탈퇴\n"
					+ "-------------------------------------------\n");
			System.out.print("▶ 메뉴번호 선택 : ");
			u_Choice = sc.nextLine();
			
			switch (u_Choice) {
			case "1":	// 나의 정보 메뉴
				user_info_menu(sc, user);
				break;
			case "2":	// 이력서 관리 메뉴
				Paper_menu(sc, user);
				break;
			case "3": 	// 입사지원 제안 관리 메뉴
				Company_DTO company = new Company_DTO();
				Recruit_apply_menu(sc, user,company);
				break;
			case "4":	// 로그아웃
				System.out.println(">>> " + user.getUser_name() + "님 로그아웃 되었습니다. <<<\n");
				user = null;
				break;
			case "5":	// 회원탈퇴
				u = udao.Withdrawal(sc,user);
				if(u==1) {
					user = null;
					System.out.println(">>> 회원탈퇴 성공되었습니다. <<<");
					break;
				}
				else {
					break;
				}
			default:
				break;
			} 	// end of switch-----------
		}while(!("4".equals(u_Choice)) && !("5".equals(u_Choice) && u == 1));
	}	// end of public void user_menu(Scanner sc, User_DTO user, Company_DTO company)---------



	
	
	
	// ◆◆◆ === 나의 정보 메뉴 === ◆◆◆ //
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
			        
					case "1": // 나의 정보 보기
						udao.view_userinfo(user);
						break;
					case "2": // 나의 정보 수정
						udao.change_information(sc, user);
						break;
					case "3": // 이전 메뉴로 돌아가기
						break;   
					default:
						System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
						break;
			    } // end of switch (u_Choice)-----------------
			} while (!"3".equalsIgnoreCase(u_Choice));	// end of do~while------------------	
		}	// end of user_info(Scanner sc, User_DTO user)-----------------

	
		
		
		
		
		// ◆◆◆ === 이력서 관리 메뉴 === ◆◆◆ //
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
						udao.paper_info(sc, user);
					   	break;
					case "2": // 이력서 작성
						udao.write_paper(sc, user);
					   	break;
					case "3":	// 이력서 수정
						udao.change_paper(sc, user);
						break;
					case "4": // 이전 메뉴로 돌아가기
					   break;   
					default:
					   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
					   break;
			    } // end of switch (u_Choice)-----------------
			} while (!"4".equalsIgnoreCase(u_Choice));	// end of do~while------------------	
		}	// end of public void Paper_menu(Scanner sc, User_DTO user, Paper_DTO paper, License_DTO license)---------

		
		
		
		
		
		// ◆◆◆ === 입사지원 제안 관리 메뉴 === ◆◆◆ //
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
	                  company_search(sc, user, company);
	                     break;
	               case "2": // 채용공고 조회
	            	   List<Recruit_INFO_DTO> recruit_info_list = rdao.recruit_info_list();
	                   
	                   if(recruit_info_list.size() > 0) {
	                      
	                      System.out.println("\n------------------------------ [채용공고 목록] ------------------------------");
	                        System.out.println("채용공고번호\t\t채용공고명\t\t기업명\t주소\t신입,경력여부\t연봉\t채용마감일");
	                        System.out.println("-------------------------------------------------------------------------"); 
	                        
	                        StringBuilder sb = new StringBuilder();
	                        
	                        for(Recruit_INFO_DTO recruit_info_li : recruit_info_list) {
	                           sb.append(recruit_info_li.getRecruint_no() + "\t" +
	                                   recruit_info_li.getRecruit_title() + "\t" +
	                                   recruit_info_li.getCdto().getCompany_name() + "\t" +
	                                   recruit_info_li.getCdto().getCompany_address() + "\t" +
	                                   recruit_info_li.getCareer() + "\t" + 
	                                   recruit_info_li.getYear_salary() + "\t" + 
	                                   recruit_info_li.getRecruit_deadline() + "\n"); 
	                        } // end of for ----------
	                        
	                        System.out.println(sb.toString());
	                      
	                   }
	                   else {
	                      System.out.println(">> 채용공고 목록이 존재하지 않습니다. <<\n");
	                   }
	                      break;

	               case "3":   // 채용지원
	                  recruit_apply(sc, user, company);
	                  break;
	               case "4":   // 지원현황
	                  recruit_apply_situation(sc, company);
	                  break;
	               case "5": // 이전 메뉴로 돌아가기
	                  break;   
	               default:
	                  System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
	                  break;
	             } // end of switch (u_Choice)-----------------
	         } while (!"5".equalsIgnoreCase(u_Choice));   // end of do~while------------------   
	      }   // end of public void Recruit_apply(Scanner sc, User_DTO user, Company_DTO company)------


		
		
		
		
		
		// ◆◆◆ === 구인회사 조회 === ◆◆◆ //
		public void company_search(Scanner sc, User_DTO user, Company_DTO company) {
			System.out.println("\n >>> --- 구인회사 조회 --- <<<");
		      
		      System.out.print("▶ 기업명 : ");
		      String search_company_name = sc.nextLine().trim().toLowerCase();;
		      
		      Map<String, String> paraMap = new HashMap<>();
		      paraMap.put("company_name", search_company_name);
		      
		      Company_DTO cdto = radao.company_search(paraMap);
		      
		      if(cdto != null) {
		         
		         System.out.println("\n" + "-".repeat(30));
		         System.out.println("▶ 기업명 : " + cdto.getCompany_name() + "\n"
		                      + "▶ 설립일자 : " +cdto.getCompany_type_detail().getBegin_day() + "\n"
		                      + "▶ 대표자명 : " + cdto.getCeo_name() + "\n"
		                      + "▶ 기업형태 : " + cdto.getCompany_type_detail().getCompanylist_num() + "\n"
		                      + "▶ 주소 : " + cdto.getCompany_address() + "\n"
		                      + "▶ 사원수 : " + cdto.getCompany_type_detail().getEmployee_num() + "\n"
		                      + "▶ 상장여부 : " + cdto.getCompany_type_detail().getPublic_status() + "\n"
		                      + "▶ 자본금 : " + cdto.getCompany_type_detail().getCapital_money() + "\n"
		                      + "▶ 계열회사수 : " + cdto.getCompany_type_detail().getCompanylist_num());
		         System.out.println("-".repeat(30));
		      }
		      else {
		         System.out.println(">> 기업명 " + search_company_name + "은(는) 존재하지 않습니다. <<\n");
		      }		// end of if~else---------------
		}	// end of public void company_search(Scanner sc, User_DTO user, Company_DTO company)-------

		
		
		
		
		
		


		// ◆◆◆ === 채용지원 === ◆◆◆ //
		private void recruit_apply(Scanner sc, User_DTO user, Company_DTO company) {
			// TODO Auto-generated method stub
			
		}



		
		
		
		

		// ◆◆◆ === 지원현황 === ◆◆◆ //
		private void recruit_apply_situation(Scanner sc, Company_DTO company) {
			// TODO Auto-generated method stub
			
		}

	






	
		
		
		
}
