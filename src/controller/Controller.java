package controller;

import java.util.Scanner;

import common.MYDBConnection;
import company.controller.Company_Controller;
import company.domain.Company_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.controller.User_Controller;
import user.domain.User_DTO;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class Controller {

	// field
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	
	User_Controller user_menu = null;
	Company_Controller company_menu = null;
	
	// == 시작메뉴 == //
	public void menu_Start(Scanner sc) {
		
		String s_Choice = "";
		User_DTO user = null;
		Company_DTO company = null;
		
		do {
			/////////////////////////////////////////////////////////
			System.out.println("\n >>> ---- 시작메뉴 ---- <<<\n"
					+ "1. 회원가입\n"
					+ "2. 구직자 로그인\n"
					+ "3. 기업 로그인\n"
					+ "4. 프로그램종료\n"
					+ "-------------------------------------------\n");
			
			System.out.print("▶ 메뉴번호 선택 : ");
			s_Choice = sc.nextLine();
			
			switch (s_Choice) {
				case "1":	// 회원가입
					Regiser(sc);
					break;
				case "2": 	// 구직자 로그인
					user = user_login(sc);	
					if(user != null) {	// 로그인 성공
						System.out.println(">> 로그인 성공!! <<");
						user_menu.user_menu(sc,user,company);
					}
					break;
				case "3":	// 기업 로그인
					company = company_login(sc);
					if(company != null)
					break;
				case "4":	// 프로그램 종료
					MYDBConnection.closeConnection(); 	// Connection 객체 자원 반납
					return;
				default:
					System.out.println(">>> 메뉴에 없는 번호입니다. 다시 선택해 주세요. <<<");
					break;
			}	// end of switch (s_Choice)----------------
			//////////////////////////////////////////////////////////
		} while(!("4".equals(s_Choice)));
			
	}	// end of public void menu_Start(Scanner sc)---------------







	// ◆◆◆ ==  회원가입  == ◆◆◆ //
	private void Regiser(Scanner sc) {
		// TODO Auto-generated method stub
		
	}	// end of private void Regiser(Scanner sc)---------------

	
	
	
	
	// ◆◆◆ == 구직자 로그인 == ◆◆◆ //
	private User_DTO user_login(Scanner sc) {
		// TODO Auto-generated method stub
		return null;
	}	// end of private User_DTO user_login(Scanner sc)-----------

	
	
	
	
	
	
	// ◆◆◆ == 기업 로그인 == ◆◆◆ //
	private Company_DTO company_login(Scanner sc) {
		// TODO Auto-generated method stub
		return null;
	}	// end of private Company_DTO company_login(Scanner sc)---------


	
	
	

	
	
	
}

