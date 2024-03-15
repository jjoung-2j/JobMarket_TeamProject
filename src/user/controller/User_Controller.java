package user.controller;

import java.util.Scanner;

import company.domain.Company_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.domain.User_DTO;
import user.model.Recruit_apply_DAO;
import user.model.Recruit_apply_DAO_imple;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class User_Controller {

	// == field == 
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	Recruit_apply_DAO rdao = new Recruit_apply_DAO_imple();
	
	public void user_menu(Scanner sc, User_DTO user) {
		
		String u_Choice = "";
		do {
			System.out.println("\n >>> ---- 회원 메뉴 [" + user.getUser_name()+ " 님 로그인 중.. ]---- <<<\n"
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
				System.out.println(">>>" + user.getUser_name() + "님 로그아웃 되었습니다. <<<\n");
				user = null;
				break;
			case "5":	// 회원탈퇴
				int u = udao.Withdrawal(sc,user);
				if(u==1) {
					user = null;
					System.out.println(">>> 회원탈퇴 성공되었습니다. <<<");
				}
				break;
			default:
				break;
			} 	// end of switch-----------
		}while(!("4".equals(u_Choice) || "5".equals(u_Choice)));
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
						udao.company_search(sc, user, company);
					   	break;
					case "2": // 채용공고 조회
						rdao.recruit_search(sc, user, company);
					   	break;
					case "3":	// 채용지원
						rdao.recruit_apply(sc, user, company);
						break;
					case "4":	// 지원현황
						rdao.recruit_apply_situation(sc, company);
						break;
					case "5": // 이전 메뉴로 돌아가기
					   break;   
					default:
					   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
					   break;
			    } // end of switch (u_Choice)-----------------
			} while (!"5".equalsIgnoreCase(u_Choice));	// end of do~while------------------	
		}	// end of public void Recruit_apply(Scanner sc, User_DTO user, Company_DTO company)------
		
		
		
		
}
