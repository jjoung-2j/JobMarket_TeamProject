package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import common.MYDBConnection;
import common.Set_util;
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
					if(user != null) {	// 로그인 성공
						user_menu.user_menu(sc, user);
					}
					break;
				case "3":	// 기업 로그인
					Company_DTO company = company_login(sc);
					if(company != null)	{ // 로그인 성공
						company_menu.company_menu(sc, company);
					}
					break;
				case "4":	// 프로그램 종료
					MYDBConnection.closeConnection(); 	// Connection 객체 자원 반납
					return;
				case "ssang":	// 관리자 히든 메뉴
					User_DTO udto = new User_DTO();
					Company_DTO cdto = new Company_DTO();
					admin(sc, udto, cdto);
					break;
				default:
					System.out.println(">>> 메뉴에 없는 번호입니다. 다시 선택해 주세요. <<<");
					break;
			}	// end of switch (s_Choice)----------------
			//////////////////////////////////////////////////////////
		} while(!("4".equals(s_Choice)));
			
	}	// end of public void menu_Start(Scanner sc)---------------







// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆



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

	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	
	// ◆◆◆ == 구직자 회원가입 == ◆◆◆ //
	private void Join_Membership_User(Scanner sc) {
		User_DTO user = new User_DTO();
		
		System.out.println("\n >>> 구직자 회원가입 입력 <<<");
		
		// 아이디 유효성 검사
		String user_id = null;
		do {
			System.out.println("\n[ 4~20 자리 / 영문, 숫자, 특수문자 '_','-' 사용 ]");
	        System.out.print("▶ 개인아이디 : ");
	        user_id = sc.nextLine();
	        if( udao.is_exist_user_id(user_id) ) {
				System.out.println(">>> "+ user_id +"는 이미 존재하는 아이디 입니다. 새로운 아이디를 입력해주세요 <<<"); 
				continue;
			}
			else { 
				if(Set_util.Check_id(user_id)) { 
					break;
				}
			}
		} while(true);		// do~while----------------------
		user.setUser_id(user_id);
        
		// 비밀번호 유효성 검사
		String user_passwd = null;
		do {
			System.out.println("\n[ 8~16자리 / 영문 대소문자, 숫자, 특수문자 조합 ]");
	        System.out.print("▶ 비밀번호 : ");
	        user_passwd = sc.nextLine();
	        if(Set_util.Check_passwd(user_passwd)) {
	        	break;
	        }
	        else {
	        	System.out.println(">>> [경고] 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요. <<<");
	        }
		} while(true);	// do~while---------------------------
		user.setUser_passwd(user_passwd);
		
		// 성명 유효성 검사
		String user_name = null;
        do {
        	System.out.println("\n[ 2~37자리 / 영문, 한글 사용 ]");
	        System.out.print("▶ 성명 : ");
	        user_name = sc.nextLine();
	        if(Set_util.Check_name(user_name)) {
	        	break;
	        }
        } while(true);	// do~while---------------------------
        user.setUser_name(user_name);
        
        // 주소
        System.out.print("\n▶ 주소 : ");
        String user_address = sc.nextLine();
        user.setUser_address(user_address);
        
        // 연락처 유효성 검사
        String user_tel = null;
        do {
	        System.out.print("\n▶ 연락처 : ");
	        user_tel = sc.nextLine();
	        if(Set_util.Check_tel(user_tel)) {
	        	break;
	        }
        } while(true);	// do~while---------------------------
        user.setUser_tel(user_tel);
        
        // 주민번호 유효성 검사
        String user_security_num = null;
        do {
	        System.out.print("\n▶ 주민번호 :  ");
	        user_security_num = sc.nextLine();
	        if(Set_util.Check_security_num(user_security_num)) {
	        	break;
	        }
		} while(true);	// do~while---------------------------
        user.setUser_security_num(user_security_num);
        
        // 이메일 유효성 검사
        String user_email = null;
        do {
	        System.out.print("\n▶ 이메일 : ");
	        user_email = sc.nextLine();
	        if(Set_util.Check_email(user_email)) {
	        	break;
	        }
        } while(true);	// do~while---------------------------
        user.setUser_email(user_email);
        
        int n = udao.userRegister(user);
        
        if(n == 1) {
           System.out.println("\n>>> 회원가입을 축하드립니다. <<<");
        }
        else { 
           System.out.println("\n>>> 회원가입이 실패되었습니다. <<<");
        }	// end of if~else--------
	}	// end of private void Join_Membership_User(Scanner sc)-------------------
		
		
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆		
		
		
	
	// ◆◆◆ == 기업 회원가입 == ◆◆◆ //
	private void Join_Membership_Company(Scanner sc) {
		Company_DTO company = new Company_DTO(); 
		
		System.out.println("\n >>> 기업 회원가입 입력 <<<");
         
		
		// 아이디 유효성 검사
		String company_id = null;
		
		do {
			System.out.println("\n[ 4~20 자리 / 영문, 숫자, 특수문자 '_','-' 사용 ]");
			System.out.print("▶ 기업아이디 : ");
			company_id = sc.nextLine();
			
			if( cdao.is_exist_company_id(company_id) ) {
				System.out.println(">> "+ company_id +"는 이미 존재하는 아이디 입니다. 새로운 아이디를 입력하세요!!\n"); 
				continue;
			}
			else { 
				if(Set_util.Check_id(company_id)) { 
					break;
				}
			}
		} while(true);
		// end of do_while
		company.setCompany_id(company_id);
		
		
		// 비밀번호 유효성 검사
		String company_passwd = null;
		
		do {
			System.out.println("\n[ 8~16자리 / 영문 대소문자, 숫자, 특수문자 조합 ]");
			System.out.print("▶ 비밀번호 : ");
			company_passwd = sc.nextLine();
			if(Set_util.Check_passwd(company_passwd)) {
				break;
			}
			else {
				System.out.println(">>> [경고] 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요. <<<");
			}
		} while(true);
		// end of do_while
		company.setCompany_passwd(company_passwd);
		
		
		// 기업명 유효성 검사
		String company_name = null;
		
		do {
			System.out.println("\n[ 1~30자리 / 영문, 한글, 숫자 사용 ]");
			System.out.print("▶ 기업명 : ");
			company_name = sc.nextLine();
			if(Set_util.Check_company_name(company_name)) {
				break;
			}
		} while(true);
		// end of do_while
		company.setCompany_name(company_name);
		
		// 지역
		Map<String, String> paraMap = new HashMap<>();
		do {
			System.out.println("\n" + "-".repeat(50));
			System.out.println("서울 | 경기 | 인천 | 부산 | 대구\n"
							+ "광주 | 대전 | 울산 | 세종 | 강원\n"
							+ "경남 | 경북 | 전남 | 전북 | 충남\n"
							+ "충북 | 제주");
			System.out.println("-".repeat(50));
			System.out.print("▶ 지역명 : ");
			String local = sc.nextLine();

			if("서울".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("강남구 | 강북구 | 강서구 | 광진구 | 구로구\n"
									+ "노원구 | 도봉구 | 동작구 | 마포구 | 서초구\n"
									+ "성동구 | 송파구 | 양천구 | 용산구 | 은평구\n"
									+ "중구 | 중랑구");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();
					
					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("경기".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("고양시 | 과천시 | 광명시 | 구리시 | 군포시\n"
									+ "남양주시 | 동두천시 | 부천시 | 성남시 | 수원시\n"
									+ "시흥시 | 안산시 | 안성시 | 안양시 | 여주시\n"
									+ "오산시 | 용인시 | 의정부시 | 이천시 | 평택시\n"
									+ "포천시 | 화성시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();
					
					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("인천".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("남동구 | 동구 | 부평구 | 서구 | 중구");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();
					
					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("부산".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("강서구 | 남구 | 동래구 | 부산진구 | 사상구\n"
									+ "사하구 | 수영구 | 연제구 | 중구 | 해운대구");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();
					
					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("대구".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("달서구 | 북구 | 서구 | 중구 |");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("광주".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("대덕구 | 서구 | 유성구");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("대전".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("광서구 | 동구 | 북구");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("울산".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("남구 | 북구 | 울주군");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("세종".equals(local)) {
				paraMap.put("local", local);
				
				String city = "세종특별자치시";
				paraMap.put("city", city);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("강원".equals(local)) {
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("강릉시 | 동해시 | 삼척시 | 원주시 | 춘천시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("경남".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("거제시 | 김해시 | 밀양시 | 사천시 | 양산시\n"
									+ "창원시 | 통영시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("경북".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("경산시 | 구미시 | 김천시 | 문경시 | 상주시\n"
									+ "영천시 | 포항시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("전남".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("광양시 | 나주시 | 여수시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("전북".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("김제시 | 남원시 | 익산시 | 전주시 | 정읍시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("충남".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("계룡시 | 논산시 | 보령시 | 아산시 | 천안시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("충북".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("제천시 | 청주시 | 충주시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					boolean check = false;
		            
		            check =  cdao.list_cityname_check(local, city);
					
					if(check) {
						paraMap.put("city", city);
						break;
					}
					else {
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else if("제주".equals(local)) {
				paraMap.put("local", local);
				do {
					System.out.println("\n" + "-".repeat(50));
					System.out.println("제주시 | 서귀포시");
					System.out.println("-".repeat(50));
					
					System.out.println("▶ 도시명 : ");
					String city = sc.nextLine();

					if(cdao.list_cityname_check(local, city)) {
						paraMap.put("city", city);
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
						continue;
					}
					
				}while(true);
				
				// 주소
				System.out.print("\n▶ 상세 주소 : ");
				String company_address = sc.nextLine();
				paraMap.put("company_address", company_address);
				company.setCompany_address(company_address);
				break;
			}
			else {
				System.out.println(">>> [경고] 보기에 있는 값을 입력해주세요. <<<");
			}
		}while(true);
		
		
		// 사업자등록번호 유효성 검사
		String business_number = null;
		
		do {
			System.out.println("\n[ 10자리 / 숫자 사용 ] ");
			System.out.print("▶ 사업자등록번호 : ");
			business_number = sc.nextLine();
			if(Set_util.Check_business_number(business_number)) {
				break;
			}
		} while(true);
		// end of do_while
		company.setBusiness_number(business_number);
		
		
		// 업종
		do {
			System.out.println("-".repeat(100));
			System.out.println("1. 서비스업  2. IT, 정보통신업  3. 판매, 유통업  4. 제조업, 생산업  5. 교육업\n"
					+ "6. 건설업  7. 의료, 제약업  8. 미디어, 광고업  9. 문화, 예술, 디자인업  10. 기관, 협회");
			System.out.println("-".repeat(100));
			System.out.print("▶ 업종 : ");
			try {
				String jobtype = sc.nextLine();
				if(!(jobtype.isBlank())) {
					if(Integer.valueOf(jobtype) >= 1 && Integer.valueOf(jobtype) <= 10) {
						company.setFk_jobtype_code(Integer.valueOf(jobtype));
						break;
					}
					else {
						System.out.println(">>> [경고] 보기에 있는 숫자만 입력해주세요. <<<");
					}
				}
				else {
					System.out.println(">>> [경고] 업종을 반드시 입력해주세요. <<<");
				}
			}catch(NumberFormatException e) {
				System.out.println(">>> [경고] 반드시 숫자를 입력하셔야 합니다.");
			}
		}while(true);
		
		
		// 대표자명 유효성 검사
		String ceo_name = null;
		
		do {
			System.out.println("\n[ 2~37자리 / 영문, 한글 사용 ]");
			System.out.print("▶ 대표자명 : ");
			ceo_name = sc.nextLine();
			if(Set_util.Check_name(ceo_name)) {
				break;
			}
		} while(true);
		// end of do_while
		company.setCeo_name(ceo_name);
		
		int n = cdao.companyRegister(company,paraMap);
		 
		if(n == 1) {
		    System.out.println("\n>>> 회원가입을 축하드립니다. <<<");
		}
		else { 
		    System.out.println(">>> 회원가입이 실패되었습니다. <<<");
		}
	}	// end of private void Join_Membership_Company(Scanner sc)------------




// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆



	
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
		  
		if(user != null) {
		     System.out.println("\n >>> 로그인 성공!! <<<");
		}
		else {
		     System.out.println("\n >>> 로그인 실패!! <<<");
		}
		return user;
	}	// end of private User_DTO user_login(Scanner sc)-----------

	
	 
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	
	
	
	
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
	      
	      if(company != null) {
	         System.out.println("\n >>> 로그인 성공!! <<< \n");
	      }
	      else { 
	         System.out.println("\n >>> 로그인 실패ㅜㅜ <<< \n");
	      }
	      return company;
	}	// end of private Company_DTO company_login(Scanner sc)---------


	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	
	
	
	
	
	// ◆◆◆ == 관리자 히든 메뉴 == ◆◆◆ //
	private void admin(Scanner sc, User_DTO udto, Company_DTO cdto) {

		String a_choice = "";
		System.out.print(">> 암호를 입력해주세요 : ");
		String passwd = sc.nextLine();
		if("1004".equals(passwd)) {
		
			do {
				System.out.println("\n >>> ---- 관리자히든메뉴 ---- <<<\n"
						+ "1. 모든 구직자 조회\n"
						+ "2. 모든 기업 조회\n"
						+ "3. 구직자 탈퇴 처리\n"
						+ "4. 기업 탈퇴 처리\n"
						+ "5. 시작메뉴로 돌아가기\n"
						+ "-------------------------------------------\n");
				
				System.out.print("▶ 메뉴번호 선택 : ");
				a_choice = sc.nextLine();
				
				switch (a_choice) {
				case "1":	// 모든 구직자 조회
					List<User_DTO> memberList = cdao.All_user();
		               
	                StringBuilder sb = new StringBuilder();
	               
	                if(memberList.size() > 0) {
	                  
	                	System.out.println("-".repeat(70));
	                	System.out.println("성명\t주소\t\t연락처\t\t이메일\t\t생년월일");
	                	System.out.println("-".repeat(70));
	                  
	                	sb = new StringBuilder();
	                  
	                	for(User_DTO member : memberList) {
	                		sb.append(member.getUser_name() + "\t" 
	                				+ member.getUser_address() + "\t"
	                				+ member.getUser_tel() + "\t"
	                				+ member.getUser_email() + "\t"
	                				+ member.getUser_security_num().substring(0, 6) + "\n");
	                	} // end of for
	                	System.out.println(sb.toString() );  
		            } else 
		                System.out.println(">> 가입된 회원이 존재하지 않습니다. <<");  
					break;
					
					
				case "2":	// 모든 기업 조회
					List<Company_DTO> companyList = cdao.All_company();
					
	                if(companyList.size() > 0) {
	                  
	                	System.out.println("-".repeat(100));
	                	System.out.println("회사아이디\t회사명\t사업자등록번호\t대표자명\t기업주소\t\t설립일자\t사원수\t상장여부\t자본금\t계열회사수");
	                	System.out.println("-".repeat(100));
	                  
	                	sb = new StringBuilder();
	                  
	                	for(Company_DTO company_list : companyList) {
	                		sb.append(company_list.getCompany_id()+ "\t"
	                				+ company_list.getCompany_name() + "\t" 
	                				+ company_list.getBusiness_number() + "\t"
	                				+ company_list.getCeo_name() + "\t"
	                				+ company_list.getCompany_address() + "\t\t"
	                				+ company_list.getCompany_type_detail().getBegin_day() + "\t\t"
	                				+ company_list.getCompany_type_detail().getEmployee_num() + "\t"
	                				+ company_list.getCompany_type_detail().getPublic_status() + "\t"
	                				+ company_list.getCompany_type_detail().getCapital_money() + "\t"
	                				+ company_list.getCompany_type_detail().getCompanylist_num() + "\n");
	                				
	                	} // end of for(Company_DTO company_list : companyList)
	                	System.out.println(sb.toString() );  
		            } else 
		                System.out.println(">> 가입된 기업이 존재하지 않습니다. <<");  
					
					break;
					
				case "3":	// 구직자 탈퇴처리
					remove_user(sc, udto);
					
					break;
					
				case "4":	// 기업 탈퇴처리
					remove_company(sc, cdto);
					break;
					
				case "5":	// 시작메뉴로 돌아가기
					
					break;
					
				default:
					System.out.println(">> 올바른 번호를 선택해주세요. <<");
					break;
				} // end of switch (a_choice)
				
			} while(!("5".equalsIgnoreCase(a_choice)));
		}
		else {
			System.out.println(">> 올바른 관리자 암호를 입력해주세요. <<");
			return;
		}
		
	} // end of private void admin(Scanner sc, User_DTO user, Company_DTO company)




// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆

	

	// ◆◆◆ == 구직자 탈퇴 처리 == ◆◆◆ //
	private void remove_user(Scanner sc, User_DTO udto) {
		int n = udao.remove();
		
		if(n==1) {
			System.out.println(">> status가 0인 구직자 회원탈퇴가 처리되었습니다.<<");
		}
	}	// end of private void remove_user(Scanner sc, User_DTO udto)---------
		
	

// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	// ◆◆◆ == 기업 탈퇴 처리 == ◆◆◆ //
	private void remove_company(Scanner sc, Company_DTO cdto) {
		int n = cdao.remove();
		
		if(n==1) {
			System.out.println(">> status가 0인 기업 회원탈퇴가 처리되었습니다.<<");
		}

	}	// end of private void remove_company(Scanner sc, Company_DTO cdto)-----

	
}

