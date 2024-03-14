package company.controller;

import java.util.Scanner;

import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import company.model.Recruit_DAO;
import company.model.Recruit_DAO_imple;
import user.domain.Recruit_Apply_DTO;
import user.domain.User_DTO;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class Company_Controller {

	// == field == 
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	Recruit_DAO rdao = new Recruit_DAO_imple();
	
	
	// 기업로그인된 메뉴출력하는 메소드
   public void company_menu(Scanner sc, Company_DTO company) {
      
      String c_Choice = "";
      
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
				cdao.company_info(sc, company);
				break;
			   
			case "2": // 채용 정보 메뉴
				cdao.recruit_info(sc, company);
				break;
			   
			case "3": // 구직자 정보 메뉴
				cdao.user_info(sc, company);
				break;
			
			case "4": // 로그아웃
				System.out.println(">>>" + company.getCompany_id() + "님 로그아웃 되었습니다. <<<\n");
				company = null;
				break;             
			case "5": // 회원탈퇴 
				int c = cdao.Withdrawal(sc,company);
				if(c==1) {
					company = null;
					System.out.println(">>> 회원탈퇴 성공되었습니다. <<<");
						}
			        	break;   
			  
			        default:
			           
			           System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
			   
			   break;
		} // end of switch
           ////////////////////////////////////////////////////////////////
         } while (!("4".equals(c_Choice)|| "5".equals(c_Choice)));   
      
   } // end of public void companyMenu_Start(Scanner sc, Company_DTO company)






	
}
