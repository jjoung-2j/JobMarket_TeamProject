package Controller;

import java.util.Scanner;

public class Controller {

	String s_Choice = "";
	
	// == 시작메뉴 == //
	public void menu_Start(Scanner sc) {
		System.out.println();
		System.out.println("\n >>> ---- 시작메뉴 ---- <<<\n"
				+ "1. 회원가입	2. 구직자 로그인		기업 로그인		4. 프로그램종료 \n"
				+ "-------------------------------------------\n");
		
		System.out.print("▷ 메뉴번호 선택 : ");
		s_Choice = sc.nextLine();
	}	// end of public void menu_Start(Scanner sc)--------

}
