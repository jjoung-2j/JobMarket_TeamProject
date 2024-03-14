package Controller;

import java.util.Scanner;

import Comment.MYDBConnection;
import company.domain.Company_DTO;
import user.domain.User_DTO;

public class Controller {

	String s_Choice = "";
	boolean isLogin = false;
	User_DTO user = null;
	Company_DTO company = null;
	
	// == 시작메뉴 == //
	public void menu_Start(Scanner sc) {
		do {
		System.out.println("\n >>> ---- 시작메뉴 ---- <<<\n"
				+ "1. 회원가입\n"
				+ "2. 구직자 로그인\n"
				+ "3. 기업 로그인\n"
				+ "4. 프로그램종료\n"
				+ "-------------------------------------------\n");
		
		System.out.print("▷ 메뉴번호 선택 : ");
		s_Choice = sc.nextLine();
		
		switch (s_Choice) {
		case "1":	// 회원가입
			Regiser(sc);
			break;
		case "2": 	// 구직자 로그인
			user = user_login(sc);	
			if(user != null)	// 로그인 성공
				isLogin = true;
			break;
		case "3":	// 기업 로그인
			company = company_login(sc);
			if(company != null)
				isLogin = true;
		case "4":	// 프로그램 종료
			MYDBConnection.closeConnection(); 	// Connection 객체 자원 반납
			return;
		default:
			System.out.println(">>> 메뉴에 없는 번호입니다. 다시 선택해 주세요. <<<");
			break;
		}	// end of switch (s_Choice)----------------
		} while(!("2".equals(s_Choice) && isLogin == true)
				|| !("3".equals(s_Choice)) && isLogin == true);
	}	// end of public void menu_Start(Scanner sc)--------

	



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
