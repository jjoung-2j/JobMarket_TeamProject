package Controller;

import java.util.Scanner;

import Comment.MYDBConnection;
import company.domain.Company_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.domain.User_DTO;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class Controller {

	// field
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	
	boolean isLogin_user = false;
	boolean isLogin_company = false;
	
	// == 시작메뉴 == //
	public void menu_Start(Scanner sc) {
		
		String s_Choice = "";
		User_DTO user = null;
		Company_DTO company = null;
		
		
		do {
			if(isLogin_user == false || isLogin_company == false) {
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
						if(user != null)	// 로그인 성공
							isLogin_user = true;
						break;
					case "3":	// 기업 로그인
						company = company_login(sc);
						if(company != null)
							isLogin_company = true;
					case "4":	// 프로그램 종료
						MYDBConnection.closeConnection(); 	// Connection 객체 자원 반납
						return;
					default:
						System.out.println(">>> 메뉴에 없는 번호입니다. 다시 선택해 주세요. <<<");
						break;
				}	// end of switch (s_Choice)----------------
				//////////////////////////////////////////////////////////
			} while(!("2".equals(s_Choice) && isLogin_user == true)
					|| !("3".equals(s_Choice)) && isLogin_company == true);
			}	// end of if~~(로그인 처음, 또는 로그인 실패한 경우)
			
			
			if(isLogin_user == true) {	// 로그인 성공한 경우 중 user화면
				
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
					user = null;
					isLogin_user = false;
					System.out.println(">>>" + user.getUser_name() + "님 로그아웃 되었습니다. <<<\n");
					break;
				case "5":	// 회원탈퇴
					Withdrawal(sc,user, company);
				default:
					break;
				}	// end of switch (s_Choice)-----------
				
			}	// end of if(isLogin_user == true)------------
		}while(true);	// end of do~while(return 프로그램 종료일때만 탈출가능)----------
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


	
	
	
	// ◆◆◆ ==  이력서 관리  == ◆◆◆ //
	private void Paper(Scanner sc, User_DTO user) {
		// TODO Auto-generated method stub
		
	}	// end of private void Paper(Scanner sc, User_DTO user2)-------


	
	
	// ◆◆◆ ==  회원탈퇴  == ◆◆◆ //
	private void Withdrawal(Scanner sc, User_DTO user, Company_DTO company) {
		
		String yn = "";
		do {
			System.out.print(">>> 정말로 탈퇴하시겠습니까? [Y/N] : ");
			yn = sc.nextLine();
		
			if("y".equalsIgnoreCase(yn)) {
				// ◆◆◆ === user 탈퇴 === ◆◆◆ //
				int u = udao.memberDelete(sc, user.getUser_id());
				//◆◆◆ === company 탈퇴 === ◆◆◆ //
				int c = cdao.companyDelete(sc, company.getCompany_id());
				
				if(u==1) {	// user -> delete 일 경우 1행 이니까 1
					isLogin_user = false;	// 로그아웃 처리
					user = null;			// 회원탈퇴
					System.out.println(">>> 회원탈퇴가 성공했습니다. <<<\n");
				}
			} else if("n".equalsIgnoreCase(yn)) {
				System.out.println(">>> 회원탈퇴를 취소하셨습니다. <<<\n");
			} else {
				System.out.println(">>> Y 또는 N 만 입력하세요. <<<\n");
			}	// end of if~else(회원탈퇴 유무 확인)---------------
		}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
		// Y 또는 N 을 누르면 멈춘다.
		
	}	// end of private void Withdrawal(Scanner sc, User_DTO user2)---------

	
	
	
}

