package user.controller;

import java.util.Scanner;

import company.domain.Company_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.domain.User_DTO;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class User_Controller {

	// == field == 
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	
	public void user_menu(Scanner sc, User_DTO user, Company_DTO company) {
		
		String s_Choice = "";
		do {
			System.out.println("\n >>> ---- 회원 메뉴 [" + user.getUser_name()+ " 님 로그인 중.. ]---- <<<\n"
					+ "1. 나의정보\n"
					+ "2. 이력서관리\n"
					+ "3. 입사지원 제안 관리\n"
					+ "4. 로그아웃\n"
					+ "5. 회원탈퇴\n"
					+ "-------------------------------------------\n");
			System.out.print("▶ 메뉴번호 선택 : ");
			s_Choice = sc.nextLine();
			
			switch (s_Choice) {
			case "1":	// 나의 정보
				System.out.println(user);
				break;
			case "2":	// 이력서 관리
				Paper(sc,user);
				break;
			case "3": 	// 입사지원 제안 관리
				
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
		
		}while(!("4".equals(s_Choice) || "5".equals(s_Choice)));
	}	// end of public void user_menu(Scanner sc, User_DTO user, Company_DTO company)---------

	
	
	// ◆◆◆ ==  이력서 관리  == ◆◆◆ //
	private void Paper(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void Paper(Scanner sc, User_DTO user2)-------








	
	
}
