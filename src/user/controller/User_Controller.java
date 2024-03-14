package user.controller;

import java.util.Scanner;

import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.domain.User_DTO;
import user.domain.Paper_DTO;
import user.domain.Recruit_Apply_DTO;
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
				udao.user_info_menu(sc, user);
				break;
			case "2":	// 이력서 관리 메뉴
				udao.Paper_menu(sc, user);
				break;
			case "3": 	// 입사지원 제안 관리 메뉴
				Company_DTO company = new Company_DTO();
				udao.Recruit_apply_menu(sc, user, company);
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

	
}
