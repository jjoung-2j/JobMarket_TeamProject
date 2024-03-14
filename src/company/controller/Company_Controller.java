package company.controller;

import java.util.Scanner;

import company.domain.Company_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.domain.User_DTO;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class Company_Controller {

	// == field == 
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	
	// 기업로그인된 메뉴출력하는 메소드
   public void companyMenu_Start(Scanner sc, Company_DTO company) {
      
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
            case "1": // 기업정보 보기
               
               viewMyCompanyInfo(sc, company);
               
               break;
               
            case "2": // 채용정보 보기
               
               break;
               
            case "3": // 구직자정보 보기
               
               break;
            
            case "4": // 로그아웃
               
               return;
                              
            case "5": // 회원탈퇴 
            	Withdrawal(sc,company);
            	break;   
      
            default:
               
               System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
               
               break;
            } // end of switch
           ////////////////////////////////////////////////////////////////
         } while (!("4".equals(c_Choice)|| "5".equals(c_Choice)));   
      
   } // end of public void companyMenu_Start(Scanner sc, Company_DTO company)

	
   // ◆◆◆ ==  회원탈퇴  == ◆◆◆ //
	private void Withdrawal(Scanner sc, Company_DTO company) {
		
		String yn = "";
		do {
			System.out.print(">>> 정말로 탈퇴하시겠습니까? [Y/N] : ");
			yn = sc.nextLine();
		
			if("y".equalsIgnoreCase(yn)) {
				//◆◆◆ === company 탈퇴 === ◆◆◆ //
				int c = cdao.companyDelete(sc, company.getCompany_id());
				
				if(c==1) {	// user -> delete 일 경우 1행 이니까 1
					company = null;			// 회원탈퇴
					System.out.println(">>> 회원탈퇴가 성공했습니다. <<<\n");
				}
			} else if("n".equalsIgnoreCase(yn)) {
				System.out.println(">>> 회원탈퇴를 취소하셨습니다. <<<\n");
			} else {
				System.out.println(">>> Y 또는 N 만 입력하세요. <<<\n");
			}	// end of if~else(회원탈퇴 유무 확인)---------------
		}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
		// Y 또는 N 을 누르면 멈춘다.
		
	}	// end of private void Withdrawal(Scanner sc, Company_DTO company)---------
   
}
