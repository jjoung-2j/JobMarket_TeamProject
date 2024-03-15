package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import common.MYDBConnection;
import company.controller.Company_Controller;
import company.domain.Company_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.controller.User_Controller;
import user.domain.User_DTO;
import user.model.Recruit_apply_DAO;
import user.model.Recruit_apply_DAO_imple;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class Controller {

	// field
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	Recruit_apply_DAO rdao = new Recruit_apply_DAO_imple();
	
	User_Controller user_menu = new User_Controller();
	Company_Controller company_menu = new Company_Controller();
	
	//User_DTO user = null;
	//Company_DTO company = null;
	
	// == 시작메뉴 == //
	public void menu_Start(Scanner sc) {
		
		String s_Choice = "";
		
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
					User_DTO user = user_login(sc);	
					if(user != null) 	// 로그인 성공
						user_menu.user_menu(sc, user);
					break;
				case "3":	// 기업 로그인
					Company_DTO company = company_login(sc);
					if(company != null)	// 로그인 성공
						company_menu.company_menu(sc, company);
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
		String Join_Membership = "";
	      
		do {
	        //////////////////////////////////////////////////////////
	        System.out.println("\n[회원가입 메뉴]\n"
	             + "1. 구직자 회원가입" + "\n" 
	             + "2. 기업 회원가입" + "\n"
	             + "3. 이전 메뉴로 돌아가기" + "\n");
	        System.out.print("▷ 메뉴번호 선택 : ");
	           Join_Membership = sc.nextLine();
		           
			switch (Join_Membership) {
				case "1":   //   1. 구직자 회원가입
				     Join_Membership_User(sc);   
				     break;
				case "2":   //   2. 기업 회원가입
				     Join_Membership_Company(sc);
				     break;
				case "3":   //   3. 이전 메뉴로 돌아가기
				     break;
				default:
				     System.out.println(">>> 메뉴에 없는 번호입니다. 다시 선택하세요!! <<<");
				     continue;
			}	// end of switch (Join_Membership)-------------
		} while(false);

	}	// end of private void Regiser(Scanner sc)---------------

	
	
	
	
	
	// ◆◆◆ == 구직자 회원가입 == ◆◆◆ //
	private void Join_Membership_User(Scanner sc) {
		User_DTO user = new User_DTO();
		
		System.out.println("\n >>> 구직자 회원가입 입력 <<<");
		
		// 아이디 유효성 검사
		do {
	        System.out.print("▶ 개인아이디 : ");
	        String user_id = sc.nextLine();
	        user.setUser_id(user_id);
	        if(user.getUser_id() != null)
	        	break;
		} while(true);		// do~while----------------------
		
        // 비밀번호 유효성 검사
		do {
	        System.out.print("▶ 비밀번호 : ");
	        String user_passwd = sc.nextLine();
	        user.setUser_passwd(user_passwd);
	        if(user.getUser_passwd() != null)
	        	break;
		} while(true);	// do~while---------------------------
		
		// 성명 유효성 검사
        do {
	        System.out.print("▶ 성명 : ");
	        String user_name = sc.nextLine();
	        user.setUser_name(user_name);
	        if(user.getUser_name() != null)
	        	break;
        } while(true);	// do~while---------------------------
        
        System.out.print("▶ 주소 : ");
        String user_address = sc.nextLine();
        user.setUser_address(user_address);
        
        // 연락처 유효성 검사
        do {
	        System.out.print("▶ 연락처 : ");
	        String user_tel = sc.nextLine();
	        user.setUser_tel(user_tel);
	        if(user.getUser_tel() != null)
	        	break;
        } while(true);	// do~while---------------------------
        
        // 주민번호 유효성 검사
        do {
	        System.out.print("▶ 주민번호 :  ");
	        String user_security_num = sc.nextLine();
	        user.setUser_security_num(user_security_num);
	        if(user.getUser_security_num() != null)
	        	break;
		} while(true);	// do~while---------------------------
        
        // 이메일 유효성 검사
        do {
	        System.out.print("▶ 이메일 : ");
	        String user_email = sc.nextLine();
	        user.setUser_email(user_email);
	        if(user.getUser_email() != null)
	        	break;
        } while(true);	// do~while---------------------------

        int n = udao.userRegister(user);
        
        if(n == 1) 
           System.out.println("\n>>> 회원가입을 축하드립니다. <<<");
        else 
           System.out.println("\n>>> 회원가입이 실패되었습니다. <<<");
	}	// end of private void Join_Membership_User(Scanner sc)-------------------
		
		
		
		
		
	
	// ◆◆◆ == 기업 회원가입 == ◆◆◆ //
	private void Join_Membership_Company(Scanner sc) {
		System.out.println("\n >>> 기업 회원가입 입력");
         
		System.out.print("▶ 기업아이디 : ");
		String company_id = sc.nextLine();
		
		System.out.print("▶ 비밀번호 : ");
		String company_passwd = sc.nextLine();
		 
		System.out.print("▶ 기업명 : ");
		String company_name = sc.nextLine();
		 
		System.out.print("▶ 주소 : ");
		String company_address = sc.nextLine();
		 
		System.out.print("▶ 사업자등록번호 : ");
		String business_number = sc.nextLine();
		 
		System.out.print("▶ 대표자명 : ");
		String ceo_name = sc.nextLine();
		 
		Company_DTO company = new Company_DTO();
		company.setCompany_id(company_id);
		company.setCompany_passwd(company_passwd);
		company.setCompany_name(company_name);
		company.setCompany_address(company_address);
		company.setBusiness_number(business_number);
		company.setCeo_name(ceo_name);
		 
		int n = cdao.companyRegister(company);
		 
		if(n == 1) 
		    System.out.println("\n>>> 회원가입을 축하드립니다. <<<");
		else 
		    System.out.println(">>> 회원가입이 실패되었습니다. <<<");
	}	// end of private void Join_Membership_Company(Scanner sc)------------







	







	// ◆◆◆ == 구직자 로그인 == ◆◆◆ //
	private User_DTO user_login(Scanner sc) {
		User_DTO user = null;
		  
		System.out.println("\n >>> --- 로그인 --- <<<");
		  
		System.out.print("▶ 아이디 : ");
		String user_id = sc.nextLine();
		  
		System.out.print("▶ 비밀번호 : ");
		String user_passwd = sc.nextLine();
		  
		Map<String, String> paraMap = new HashMap<>(); 
		paraMap.put("user_id", user_id);
		paraMap.put("user_passwd", user_passwd);
		
		user = udao.user_login(paraMap);
		  
		if(user != null) 
		     System.out.println("\n >>> 로그인 성공!! <<<");
		else 
		     System.out.println("\n >>> 로그인 실패!! <<<");
		
		return user;
	}	// end of private User_DTO user_login(Scanner sc)-----------

	
	 
	
	
	
	
	// ◆◆◆ == 기업 로그인 == ◆◆◆ //
	private Company_DTO company_login(Scanner sc) {
		Company_DTO company  = null;
	      
	      System.out.println("\n >>> --- 로그인 --- <<<");
	      
	      System.out.print("▷ 아이디 : ");
	      String company_id = sc.nextLine();
	      
	      System.out.print("▷ 비밀번호 : ");
	      String passwd = sc.nextLine();
	   
	      Map<String, String> paraMap = new HashMap<>();
	      // 몇개의 변수이던간에 하나의 변수에 담아서 처리하려면?? MAP
	      paraMap.put("company_id", company_id); // 문법 복습하자
	      paraMap.put("company_passwd", passwd);
	      
	      company = cdao.login(paraMap);
	      
	      if(company != null) 
	         System.out.println("\n >>> 로그인 성공!! <<< \n");
	      else 
	         System.out.println("\n >>> 로그인 실패ㅜㅜ <<< \n");
	      
	      return company;
	}	// end of private Company_DTO company_login(Scanner sc)---------


	
	
	

	
	
	
}

