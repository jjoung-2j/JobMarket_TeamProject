package user.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import common.MYDBConnection;
import common.Set_util;
import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import user.domain.Paper_DTO;
import user.domain.User_DTO;
import company.model.Recruit_DAO;
import company.model.Recruit_DAO_imple;
import user.model.Recruit_apply_DAO;
import user.model.Recruit_apply_DAO_imple;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class User_Controller {

	// == field == 
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	Recruit_DAO rdao = new Recruit_DAO_imple();
	Recruit_apply_DAO radao = new Recruit_apply_DAO_imple();
	
	
	private Connection conn = MYDBConnection.getConn();		// 데이터베이스 서버 연결
	private PreparedStatement pstmt;	// 우편배달부
	private ResultSet rs;
	
	// ◆◆◆ === 자원반납을 해주는 메소드 === ◆◆◆ //
	private void close() {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if(pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}	// end of try~catch----------
	}	// end of private void close()---------------
	
	
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	public void user_menu(Scanner sc, User_DTO user) {
		
		String u_Choice = "";
		int u = 0;
		
		do {
			System.out.println("\n >>> ---- 회원 메뉴 [ " + user.getUser_name()+ " 님 로그인 중.. ]---- <<<\n"
					+ "1. 나의정보\n"
					+ "2. 이력서 관리\n"
					+ "3. 채용지원\n"
					+ "4. 로그아웃\n"
					+ "5. 회원탈퇴\n"
					+ "-------------------------------------------\n");
			System.out.print("▶ 메뉴번호 선택 : ");
			u_Choice = sc.nextLine();
			
			switch (u_Choice) {
			case "1":	// 나의 정보 메뉴
				user_info_menu(sc, user);
				break;
			case "2":	// 이력서 관리 메뉴
				Paper_menu(sc, user);
				break;
			case "3": 	// 채용지원 메뉴
				Company_DTO company = new Company_DTO();
				Recruit_apply_menu(sc, user, company);
				break;
			case "4":	// 로그아웃
				System.out.println(">>> " + user.getUser_name() + "님 로그아웃 되었습니다. <<<\n");
				user = null;
				break;
			case "5":	// 회원탈퇴
				u = udao.Withdrawal(sc,user);
				if(u==1) {
					user = null;
					System.out.println(">>> 회원탈퇴 성공되었습니다. <<<");
					break;
				}
				else {
					break;
				}
			default:
				break;
			} 	// end of switch-----------
		}while(!("4".equals(u_Choice)) && !("5".equals(u_Choice) && u == 1));
	}	// end of public void user_menu(Scanner sc, User_DTO user, Company_DTO company)---------



	
	
	
	// ◆◆◆ === 나의 정보 메뉴 === ◆◆◆ //
		public void user_info_menu(Scanner sc, User_DTO user) {
			String u_Choice = "";
		      
			do {
				System.out.println("\n>>> ---- " + user.getUser_name() + "님의 정보 메뉴 ---- <<<\n"
			                   + "1. 나의 정보 보기\n"
			                   + "2. 나의 정보 수정\n"
			                   + "3. 학력/취업우대 정보 입력 및 수정\n"
			                   + "4. 이전 메뉴로 돌아가기" );
			      
			    System.out.print("▶ 메뉴번호 선택 : ");
			    u_Choice = sc.nextLine();
			      
			    switch (u_Choice) {
			        
					case "1": // 나의 정보 보기
						User_DTO result = udao.view_userinfo(user);
						
						System.out.println("\n" + "-".repeat(50));
						System.out.println("[ " + result.getUser_name()+ " 님의 정보 보기]");
	                    System.out.println("▶ 아이디 : " + result.getUser_id() );
	                    System.out.println("▶ 패스워드 : " + result.getUser_passwd() );
	                    System.out.println("▶ 성명 : " + result.getUser_name() );
	                    System.out.println("▶ 주소 : " + result.getUser_address() );
	                    System.out.println("▶ 연락처 : " + result.getUser_tel() );
	                    System.out.println("▶ 주민번호 : " + result.getUser_security_num() );
	                    System.out.println("▶ 이메일 : " + result.getUser_email() );
						System.out.println("-".repeat(50));
						
						break;
					case "2": // 나의 정보 수정
						change_information(sc, user);
						break;
					case "3":	// 나의 추가정보 입력 및 추가정보에 대한 수정
						insert_info(sc,user);
						break;
					case "4": // 이전 메뉴로 돌아가기
						break;   
					default:
						System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
						break;
			    } // end of switch (u_Choice)-----------------
			} while (!"4".equalsIgnoreCase(u_Choice));	// end of do~while------------------	
		}	// end of user_info(Scanner sc, User_DTO user)-----------------

	
		
		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆

		// ◆◆◆ === 나의 정보 수정 === ◆◆◆ //
		public void change_information(Scanner sc, User_DTO user) {
			
			User_DTO udto = new User_DTO();		// 전체수정 바꿀값 담을 용도
	        udto.setUser_id(user.getUser_id());
			
			System.out.println("\n>>> 나의 정보 수정하기 <<<");
			System.out.println("\n ※ 이전으로 돌아가시려면 엔터하세요.");
	         
			System.out.print("▷ [본인 확인용] 패스워드 : ");
	        String user_passwd = sc.nextLine();
	        
	        if( !(user_passwd.equals(user.getUser_passwd()))) {	// 패스워드가 일치하지 않는 경우
	            System.out.println(">>> [경고] 패스워드가 일치하지 않습니다. <<<");
	        }
	        else {
	        	String fixno = "";
	        	outer:
	        	do {
	        		System.out.println("\n>>> 수정할 내용을 선택해주세요. <<<");
	        		System.out.println("-".repeat(70));
	        		System.out.println("1. 비밀번호		2. 성명		3. 주소			4. 연락처\n"
	        				+ "5. 주민번호		6. 이메일		7. 전체 수정		8. 돌아가기");
	        		System.out.println("-".repeat(70));
	        		System.out.print("▶ 메뉴번호 선택 : ");
	        		
	        		fixno = sc.nextLine(); // switch 메뉴번호
	        		String infoview = "";		// select 될 컬럼명 세팅용 변수
	        		String fix_info = "";		// 수정할 내용이 담기는 변수
	        	
	        		int n;						// update 결과가 담기는 변수
	        		String yn = "";
	        		
	        		switch(fixno) {
	        		case "1":	// 비밀번호
	        			infoview = "user_passwd";
	        			System.out.println("\n▶ 수정 전 비밀번호 : " + user.getUser_passwd());
	        			
	        			System.out.println("※ 변경하지 않으려면 엔터하세요.");
	        			fix_info = sc.nextLine();
	        			
	        			if(!(fix_info.isBlank() || user.getUser_passwd().equals(fix_info))) {
	        				if(Set_util.Check_passwd(fix_info)) {
	        					do {
		        					System.out.println(fix_info + "로 수정하시겠습니까? [Y/N]");
		        					yn = sc.nextLine();
		        					if("y".equalsIgnoreCase(yn)) {
		        						udao.fix_info(user, infoview, fix_info);
			        					System.out.println(">>> 비밀번호 수정 성공!! <<<");
		        					}
		        					else if("n".equalsIgnoreCase(yn)){
		        						System.out.println(">>> 비밀번호 수정 취소!! <<<");
		        					}
		        					else {
		        						System.out.println(">>> [경고] Y 또는 N 만 입력해주세요. <<<");
		        					}
	        					}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
	        				}
	        				else {	// 비밀번호 규격에 맞지 않은 경우
	        					System.out.println(">>> [경고] 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요. <<<");
	        					System.out.println(">>> 비밀번호 수정 실패!! <<<");
	        					continue outer;
	        				}
	        			}	// end of if(공백이 아닌 경우)--------------
	        			break;
	        		case "2":	// 성명
	        			infoview = "user_name";
	        			System.out.println("\n▶ 수정 전 성명 : " + user.getUser_name());
	        			
	        			System.out.println("※ 변경하지 않으려면 엔터하세요.");
	        			fix_info = sc.nextLine();
	        			
	        			if(!(fix_info.isBlank() || user.getUser_name().equals(fix_info))) {
	        				if(Set_util.Check_name(fix_info)) {
	        					do {
		        					System.out.println(fix_info + "로 수정하시겠습니까? [Y/N]");
		        					yn = sc.nextLine();
		        					if("y".equalsIgnoreCase(yn)) {
		        						udao.fix_info(user, infoview, fix_info);
			        					System.out.println(">>> 성명 수정 성공!! <<<");
		        					}
		        					else if("n".equalsIgnoreCase(yn)){
		        						System.out.println(">>> 성명 수정 취소!! <<<");
		        					}
		        					else {
		        						System.out.println(">>> [경고] Y 또는 N 만 입력해주세요. <<<");
		        					}
	        					}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
	        				}
	        				else {	// 성명 규격이 맞지 않은 경우
	        					System.out.println(">>> 성명 수정 실패!! <<<");
	        					continue outer;
	        				}
	        			}	// end of if(공백이 아닌 경우)--------------
	        			break;
	        		case "3":	// 주소
	        			infoview = "user_address";
	        			System.out.println("\n▶ 수정 전 주소 : " + user.getUser_address());
	        			
	        			System.out.println("※ 변경하지 않으려면 엔터하세요.");
	        			fix_info = sc.nextLine();
	        		       
	        			 if(!(fix_info.isBlank() || user.getUser_address().equals(fix_info))) {
	        				 do {
		        				 System.out.println(fix_info + "로 수정하시겠습니까? [Y/N]");
		        				 yn = sc.nextLine();
		        				 n = udao.fix_info(user, infoview, fix_info);
		        				 if("y".equalsIgnoreCase(yn)) {
		        					 udao.fix_info(user, infoview, fix_info);
		        					 System.out.println(">>> 주소 수정 성공!! <<<");
		        				 }
		        				 else if("n".equalsIgnoreCase(yn)){
		        					 System.out.println(">>> 주소 수정 취소!! <<<");
		        				 }
		        				 else {
		        					 System.out.println(">>> [경고] Y 또는 N 만 입력해주세요. <<<");
		        				 }
	        				 }while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
	        			 }	// end of if(공백이 아닌 경우)--------------
	        			break;
	        		case "4":	// 연락처
	        			infoview = "user_tel";
	        			System.out.println("\n▶ 수정 전 연락처 : " + user.getUser_tel());
	        			
	        			System.out.println("※ 변경하지 않으려면 엔터하세요.");
	        			fix_info = sc.nextLine();
	        			
	        			if(!(fix_info.isBlank() || user.getUser_tel().equals(fix_info))) {
	        				if(Set_util.Check_tel(fix_info)) {
	        					do {
		        					System.out.println(fix_info + "로 수정하시겠습니까? [Y/N]");
		        					yn = sc.nextLine();
		        					if("y".equalsIgnoreCase(yn)) {
		        						udao.fix_info(user, infoview, fix_info);
			        					System.out.println(">>> 연락처 수정 성공!! <<<");
		        					}
		        					else if("n".equalsIgnoreCase(yn)){
		        						System.out.println(">>> 연락처 수정 취소!! <<<");
		        					}
		        					else {
		        						System.out.println(">>> [경고] Y 또는 N 만 입력해주세요. <<<");
		        					}
	        					}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
	        				}
	        				else {	// 연락처 규격이 맞지 않은 경우
	        					System.out.println(">>> 연락처 수정 실패!! <<<");
	        					continue outer;
	        				}
	        			}	// end of if(공백이 아닌 경우)--------------
	        			break;
	        		case "5":	// 주민번호
	        			infoview = "user_security_num";
	        			System.out.println("\n▶ 수정 전 주민번호 : " + user.getUser_security_num());
	        			
	        			System.out.println("※ 변경하지 않으려면 엔터하세요.");
	        			fix_info = sc.nextLine();
	        			
	        			if(!(fix_info.isBlank() || user.getUser_security_num().equals(fix_info))) {
	        				if(Set_util.Check_security_num(fix_info)) {
	        					System.out.println(fix_info + "로 수정하시겠습니까? [Y/N]");
	        					n = udao.fix_info(user, infoview, fix_info);
	        					System.out.println(">>> 주민번호 수정 성공!! <<<");
	        				}
	        				else {
	        					System.out.println(">>> 주민번호 수정 실패!! <<<");
	        					continue outer;
	        				}
	        			}	// end of if(공백이 아닌 경우)--------------
	        			break;
	        		case "6":	// 이메일
	        			infoview = "user_email";
	        			System.out.println("\n▶ 수정 전 이메일 : " + user.getUser_email());
	        			
	        			System.out.println("※ 변경하지 않으려면 엔터하세요.");
	        			fix_info = sc.nextLine();
	        			
	        			if(!(fix_info.isBlank() || user.getUser_email().equals(fix_info))) {
	        				if(Set_util.Check_email(fix_info)) {
	        					do {
		        					System.out.println(fix_info + "로 수정하시겠습니까? [Y/N]");
		        					yn = sc.nextLine();
		        					if("y".equalsIgnoreCase(yn)) {
		        						udao.fix_info(user, infoview, fix_info);
			        					System.out.println(">>> 이메일 수정 성공!! <<<");
		        					}
		        					else if("n".equalsIgnoreCase(yn)){
		        						System.out.println(">>> 이메일 수정 취소!! <<<");
		        					}
		        					else {
		        						System.out.println(">>> [경고] Y 또는 N 만 입력해주세요. <<<");
		        					}
	        					}while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
	        				}
	        				else {
	        					System.out.println(">>> 이메일 수정 실패!! <<<");
	        					continue outer;
	        				}
	        			}	// end of if(공백이 아닌 경우)--------------
	        			break;
	        		case "7":	// 전체 수정
	        			System.out.println("\n ※ 변경하지 않으려면 엔터하세요.");
	        			do {
	     	           		System.out.print("\n▶ 비밀번호 : ");
	     	           		user_passwd = sc.nextLine();
		     	           	if(!(user_passwd.isBlank() || user.getUser_passwd().equals(user_passwd))) {
		     	           		if(Set_util.Check_passwd(user_passwd)) {
		     	           			udto.setUser_passwd(user_passwd);
		     	           			break;
		     	           		}
		     	           		else {
		     	           			System.out.println(">>> [경고] 8~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요. <<<");
		     	           		}
		     	           	}	
		     	           	else {
		     	           		break;
		     	           	}
	        			}while(true);
	     	           
	     	           	do {
	     	           		System.out.print("\n▶ 성명 : ");
	     	           		String user_name = sc.nextLine();
	     	           		if(!(user_name.isBlank() || user.getUser_name().equals(user_name))) {
	     	           			if(Set_util.Check_name(user_name)) {
	     	           				udto.setUser_name(user_name);
	     	           				break;
	     	           			}
	     	           			else {
	     	           				System.out.println(">>> [경고] 2~37자리 / 영문, 한글만 사용 가능합니다. <<<");
	     	           			}
	     	           		}	
	     	           		else {
	     	           			break;
	     	           		}
	     	           	}while(true);
	     	           
	     	           	System.out.print("\n▶ 주소 : ");
	     	           	String user_address = sc.nextLine();
	     	       
	     	           	if(!(user_address.isBlank() || user.getUser_address().equals(user_address))) {
	     	        	  udto.setUser_address(user_address);
	     	           	}
	     	           
	     	           	do {
	     	           		System.out.print("\n▶ 연락처 : ");
	     	           		String user_tel = sc.nextLine();
	     	       
	     	           		if(!(user_tel.isBlank() || user.getUser_tel().equals(user_tel))) {
	     	           			if(Set_util.Check_tel(user_tel)) {
	     	           				udto.setUser_tel(user_tel);
	     	           				break;
	     	           			}
	     	           			else {
	     	           				System.out.println(">>> [경고] 공백이 없으며, 숫자만 사용 가능합니다. <<<");
	     	           			}
	     	           		}	
	     	           		else {
	     	           			break;
	     	           		}
     	           		}while(true);
	     	           
	     	           	do {
	     	        	   System.out.print("\n▶ 주민번호 : ");
	     	        	   String user_security_num = sc.nextLine();
	     	       
	     	        	   if(!(user_security_num.isBlank() || user.getUser_security_num().equals(user_security_num))) {
	     	           			if(Set_util.Check_security_num(user_security_num)) {
	     	           				udto.setUser_security_num(user_security_num);
	     	           				break;
	     	           			}
	     	           		}	
	     	           		else {
	     	           			break;
	     	           		}
	     	           	}while(true);
	     	           
	     	           do {
	     	        	   System.out.print("\n▶ 이메일 : ");
	     	        	   String user_email = sc.nextLine();
	     	       
	     	        	  if(!(user_email.isBlank() || user.getUser_email().equals(user_email))) {
	     	           			if(Set_util.Check_email(user_email)) {
		     	           			udto.setUser_email(user_email);
	     	           				break;
	     	           			}
	     	           		}	
	     	           		else {
	     	           			break;
	     	           		}
	     	           	}while(true);	        	
	           
			           yn = "";
			           n = 0;
			           do {
			        	   ///////////////////////////////////////////////////////////////
			        	   System.out.print("\n▶ 정보를 수정하시겠습니까?[Y/N] : ");
			        	   yn = sc.nextLine();
			             
			        	   if("y".equalsIgnoreCase(yn)) {
			               
			        		   Map<String, String> paraMap = new HashMap<>();
			                   
			                   paraMap.put("user_id", udto.getUser_id());
			                   paraMap.put("user_passwd", udto.getUser_passwd());
			                   paraMap.put("user_name", udto.getUser_name());
			                   paraMap.put("user_address", udto.getUser_address());
			                   paraMap.put("user_tel", udto.getUser_tel());
			                   paraMap.put("user_security_num", udto.getUser_security_num());
			                   paraMap.put("user_email", udto.getUser_email());
			                   
			                   n = udao.updateBoard(paraMap);   // 나의 정보 수정하기
			                   
			                   if(n==1) {
			                      System.out.println(">>> 수정 성공!! <<<\n");
			                   }
			                   else {
			                      System.out.println(">>> SQL 구문 오류 발생으로 인해 글수정이 실패되었습니다. <<<\n");   
			                   }
			               } 
			        	   else if("n".equalsIgnoreCase(yn)) {
			        		   System.out.println(">>> 수정을 취소하셨습니다. <<<\n");
			        	   } 
			        	   else {
			        		   System.out.println(">>> Y 또는 N만 입력하세요.!! <<<\n");
			        	   }	// end of if~else------------
			            ///////////////////////////////////////////////////////////////
			           } while(!("y".equalsIgnoreCase(yn) || "n".equalsIgnoreCase(yn)));
			           
		        	case "8":
		        		break;
	        		default:
	        			System.out.println(">>> [경고] 보기에 있는 값만 입력해주세요. <<<");
	        			break;
	        		}	// end of switch(fixno)--------
        	}while(!("8".equals(fixno)));	// end of do~while----------------
        }
	        
		}	// end of private void change_information(Scanner sc, User_DTO user)------
		
		

		
		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
		
		
		// ◆◆◆ === 나의 추가정보 입력 및 추가정보에 대한 수정 === ◆◆◆ //   
	      private void insert_info(Scanner sc, User_DTO user) {
	    	  
	    	  System.out.println("-".repeat(80));
	    	  if(user.getAcademy_name() == null) {
	    		  user.setAcademy_name("미입력");
	    	  }
	    	  if(user.getPriority_name() == null) {
	    		  user.setPriority_name("미입력");
	    	  }
	    	  
	    	  System.out.println("\n현재 학력 : " + user.getAcademy_name());
	    	  System.out.println("현재 취업우대 : " + user.getPriority_name());
	    	  
	         System.out.println("\n--------------------------------------"); 
	         System.out.println("\t>>> 나의 추가정보 입력하기 <<<");
	         System.out.println("--------------------------------------");
	           
	         System.out.println("1. 학력 2. 취업우대 3. 돌아가기");
	         System.out.print("▶ 메뉴번호 선택(번호로 입력해주세요.) : ");
	         String choice = sc.nextLine();
	           
	         String academy_code = "";
	         String priority_code = "";
	         switch(choice) {
	         case "1":
	        	 do {
	              ///////////////////////////////////////////////////////////
	        	   System.out.println("\n" + "-".repeat(80));
	        	   System.out.print(">> 학력에 해당하는 번호를 입력하세요 << \n"
	                    + "1.고등학교졸업미만 \t2.고등학교졸업 \t3.대학교 재학중 \t4.대학교 졸업2년제 \n"
	                    + "5.대학교 졸업3년제 \t 6.대학교 졸업4년제 \t\t7.대학원 석사 졸업 \t8.대학원 박사 졸업\n"
	                    + "[입력을 취소하시려면 Z키를 눌러주세요] \n");
	        	   System.out.println("-".repeat(80));
	        	   System.out.print("▶ 학력 입력 [숫자만 입력] : ");
	        	   academy_code = sc.nextLine();
	             
	        	   if("z".equalsIgnoreCase(academy_code)) {
	        		   return;
	        	   }
	        	   try {
	                   int num = Integer.parseInt(academy_code);
	                   
	                   if(num >= 1 && num <= 8) {
	                      break;
	                   }
	                   else {
	                      System.out.println(">> 메뉴에 있는 숫자만 입력하세요!");
	                   }
	               } catch (NumberFormatException e) {
	                  System.out.println(">> 메뉴에 있는 숫자를 반드시 입력하세요!");
	               }	// end of try~catch-------------------

	            //////////////////////////////////////////////////////////
	           } while(true);   // end of do~while-------------------------
           case "2":
	        	do {
	            ///////////////////////////////////////////////////////////
	        	 System.out.println("\n" + "-".repeat(80));
	        	 System.out.print(">> 취업우대에 해당하는 번호를 입력하세요 << \n"
	                    + "1.컴퓨터활용능력 우수 \t2.국가유공자 \t3.보훈대상자 \t4.고용촉진지원금 대상\n"
	                    + "5.취업보호대상자 \t6.병역특례 \t7.공모전입상자 \t8.외국어가능자\n"
	                    + "9.인근거주자 [입력을 취소하시려면 Z키를 눌러주세요]\n");
	        	 System.out.println("-".repeat(80));
	        	 System.out.print("▶ 번호 입력 [숫자만 입력] (해당하지않는다면 엔터를 입력하세요) : ");
	        	 
	        	 priority_code = sc.nextLine();
	        	 if(priority_code.isBlank()) {
	        		 System.out.println(" >> 해당없음으로 입력됩니다! << ");
	        		 break;
	        	 }
	        	 if("z".equalsIgnoreCase(priority_code)) {
	        		   return;
	        	   }
	        	 try {
	                  int num = Integer.parseInt(priority_code);
	                  
	                  if(num >= 1 && num <= 9) {
	                     break;
	                  }
	                  else {
	                     System.out.println(">> 메뉴에 있는 숫자만 입력하세요!");
	                  }
	              } catch (NumberFormatException e) {

	              }		// end of try~catch----------------
	        	}while(true);   // end of do~while-------------------------
           case "3":
        	   return;
    	   default:
    		   System.out.println(">>>[경고] 보기에 있는 번호를 입력해주세요. <<<");
           }	// end of switch---------------
	           
	         String yn = "";
	         
	         do {
	        	 System.out.print("\n>> 정말(수정)입력하시겠습니까 ? [Y/N] : ");
	        	 yn = sc.nextLine();
	        	 if("y".equalsIgnoreCase(yn)) {
	            
	        		 int n = udao.insert_anotherinfo(academy_code, priority_code, user.getUser_id());
	              
	        		 if(n==1) {
	        			 System.out.println(">> " + user.getUser_name() +  " 회원의 상세정보 입력 완료! <<");
	        			 break;
	        		 }
	        		 else {
	        			 System.out.println(">> " + user.getUser_name() +  " 회원의 상세정보 입력 실패! <<");
	        			 break;
	        		 }
 				 }
	        	 else if("n".equalsIgnoreCase(yn)) {
	        		 System.out.println(">> 입력 취소 ! 이전메뉴로 돌아갑니다. << ");
	        	 }
	         } while (!"n".equalsIgnoreCase(yn));
	      } // end of private void insert_info(Scanner sc, User_DTO user)

	      
	      
	      
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	      
	      
	      
		// ◆◆◆ === 이력서 관리 메뉴 === ◆◆◆ //
		public void Paper_menu(Scanner sc, User_DTO user) {
			String u_Choice = "";
		      
			do {
			    
				System.out.println("\n>>> ---- " + user.getUser_name() + "님의 이력서 관리 메뉴 ---- <<<\n"
			                   + "1. 이력서 조회\n"
			                   + "2. 이력서 작성\n"
			                   + "3. 이력서 수정\n"
			                   + "4. 이력서 삭제\n"
			                   + "5. 이전 메뉴로 돌아가기" );
			      
			    System.out.print("▶ 메뉴번호 선택 : ");
			    u_Choice = sc.nextLine();
			      
			    switch (u_Choice) {
			        
					case "1": 	// 이력서 조회
						paper_info(sc, user);
					   	break;
					case "2": 	// 이력서 작성
						write_paper(sc, user);
					   	break;
					case "3":	// 이력서 수정
						change_paper(sc, user);
						break;
					case "4": 	// 이력서 삭제
						delete_paper(sc, user);
						break;   
					case "5":	// 돌아가기
						break;
					default:
					   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
					   break;
			    } // end of switch (u_Choice)-----------------
			} while (!"5".equalsIgnoreCase(u_Choice));	// end of do~while------------------	
		}	// end of public void Paper_menu(Scanner sc, User_DTO user, Paper_DTO paper, License_DTO license)---------

		
		



		// ◆◆◆ === 이력서 조회 === ◆◆◆ //
		private void paper_info(Scanner sc, User_DTO user) {
			// TODO Auto-generated method stub
			
		}
		
		





		// ◆◆◆ === 이력서 작성 === ◆◆◆ //
		private void write_paper(Scanner sc, User_DTO user) {
			System.out.println("\n 이력서 작성");
		      System.out.println("작성을 취소하시려면 'Q'를 입력하세요.");
		      
		      String paper_name = "";
		      String u_Choice = "";
		      
		      SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM", Locale.KOREA );
		      
		      
		      int n =0;
		      
		      outer:
		      do {
		    	  do {
		    		  System.out.print("> 지원서 제목 : ");
		    		  paper_name = sc.nextLine();
		         
		    		  if(paper_name.isEmpty() || paper_name == null) {
		    			  System.out.println("정확히 입력하세요."); 
		    		  }
		    		  else if ("q".equalsIgnoreCase(paper_name)) {
		    			  break outer;
		    		  }
		    		  else {	// 제목을 입력한 경우
		    			  n = 1;
		    			  user.getPaper().setPaper_name(paper_name);
		    		  }
		    	  }while(!(n==1) || "q".equalsIgnoreCase(paper_name));
		      
		    	  System.out.println("> 성명 : " + user.getUser_id());
		    	  System.out.println("> 전화번호 : " + user.getUser_tel());
		    	  System.out.println("> 주소 : " + user.getUser_address());
		    	  System.out.println("> 이메일 : " + user.getUser_email());
		    	  System.out.print("> 학력 : ");
		    	  if(user.getAcademy_name() == null){
		    		  System.out.println("입력없음");
		    	  }
		    	  else {
		    		  System.out.println(user.getAcademy_name());
		    	  }
		         
		    	  do {
		    		  n = 0;
		    		  try {
		    			  System.out.println("\n> [희망연봉 (단위: 만원)]");
		    			  System.out.println("해당사항 없을 시 엔터");
		    			  System.out.println("-".repeat(50));
		    			  System.out.println("1. 면접 후 결정");
		    			  System.out.println("2. 2500 미만");
		    			  System.out.println("3. 2500 이상 ~ 3000 미만");
		    			  System.out.println("4. 3000 이상 ~ 3500 미만");
		    			  System.out.println("5. 3500 이상 ~ 4000 미만");
		    			  System.out.println("6. 4000 이상 ~ 4500 미만");
		    			  System.out.println("7. 4500 이상 ~ 5000 미만");
		    			  System.out.println("8. 5000 이상");
		    			  System.out.println("-".repeat(50));
		            
		    			  System.out.print("> 희망연봉 선택 : ");
		    			  u_Choice = sc.nextLine();
		            
		    			  if("q".equalsIgnoreCase(u_Choice)) {
		    				  break outer;
		    			  }
		            
		    			  else if(Integer.parseInt(u_Choice) < 1 || Integer.parseInt(u_Choice) > 8) {
		    				  System.out.println("목록에 있는 사항만 선택 가능합니다.");
		    				  continue;
		    			  }
		    			  else if (u_Choice.isEmpty() || u_Choice == null){
		    				  n = 1;
		    			  }
		    			  else
		    				  n = 1;
		    			  if("1".equals(u_Choice)) {
		    				  user.getPaper().setHope_money("면접 후 결정");
		    			  }
		    			  else if("2".equals(u_Choice)) {
		    				  user.getPaper().setHope_money("2500만원 미만");   
		    			  }
			               else if("3".equals(u_Choice)) {
			                  user.getPaper().setHope_money("2500 이상 ~ 3000 미만");
			               }
			               else if("4".equals(u_Choice)) {
			                  user.getPaper().setHope_money("3000 이상 ~ 3500 미만");
			               }
			               else if("5".equals(u_Choice)) {
			                  user.getPaper().setHope_money("3500 이상 ~ 4000 미만");
			               }
			               else if("6".equals(u_Choice)) {
			                  user.getPaper().setHope_money("4000 이상 ~ 4500 미만");
			               }
			               else if("7".equals(u_Choice)) {
			                  user.getPaper().setHope_money("4500 이상 ~ 5000 미만");
			               }
			               else if("8".equals(u_Choice)) {
			                  user.getPaper().setHope_money("5000 이상");
			               }
			               else {
			                  System.out.println("정확히 입력하세요.");
			               }
		               
		    			  System.out.println(user.getPaper().getHope_money());
		    		  }catch(NumberFormatException e) {
		    			  System.out.println("정확히 입력하세요."); 
		    		  }
		    	  }while (!(n==1) || "q".equalsIgnoreCase(u_Choice));
		         
		    	  if(n==1) {
		    		  do {
		    			  n = 0;
		    			  System.out.println("\n[희망지역]\n");
		    			  String hope_local_name = "";
		    			  String hope_city_name = "";
		            
		    			  System.out.print("희망하는 지역을 입력하세요[예 : 서울] : ");
		    			  hope_local_name = sc.nextLine();
		               
		    			  if(hope_local_name != null && hope_local_name.length() == 0 ||hope_local_name.isEmpty()) {
		    				  System.out.println("정확히 입력하세요.");
		    			  }
		    			  else if("q".equalsIgnoreCase(hope_local_name)) {
		    				  break outer;
		    			  }
		    			  else {
		                    user.getPaper().setHope_local_name(hope_local_name);
		                 
		                    System.out.print("희망하는 도시명을 입력하세요[예 : 마포구] : ");
		                    hope_city_name = sc.nextLine();
		                    
		                    if(hope_city_name != null && hope_city_name.length() == 0 ||hope_city_name.isEmpty()) {
		                       System.out.println("정확히 입력하세요.");
		                    }
		                    else if ("q".equalsIgnoreCase(hope_city_name)) {
		                       break outer;
		                    }
		                    else {
		                       user.getPaper().setHope_city_name(hope_city_name);
		                    }
		                 }
		                 udao.hope_local(sc, user);	// 희망지역 메소드
		            }while(!(user.getPaper().getLocal_code() != null));
		            
	    		  	n = 1;
		         }	// end of if----------------
		         
		         if(n == 1) {
		            String career_choice = "";
		            n = 0;	// 다시 초기화
		            
		            do {
		               System.out.println("\n> [경력사항]");
		               System.out.println("-".repeat(50));
		               System.out.println("1. 신입");
		               System.out.println("2. 경력");
		               System.out.println("-".repeat(50));
		               System.out.print("> 경력사항 선택 : ");
		               career_choice = sc.nextLine();
		               
		               if("q".equalsIgnoreCase(career_choice)) {
		                  break outer;
		               }
		               else if("1".equals(career_choice)) {
		                  n = 1;
		                  user.getPaper().setCareer("신입");
		                  udao.career_detail_new(user);	
		                  break;
		               }
		               else if("2".equals(career_choice)) {
		                  user.getPaper().setCareer("경력");
		                  n = 0;
		                  formatter = new SimpleDateFormat("yyyyMM");
		                  Date currentTime = new Date();
		                  String dTime = formatter.format ( currentTime );
		                  String user_answer = "";
		                  
		                  try {
		                     currentTime = formatter.parse(dTime);
		                  
		                     System.out.println("이전메뉴로 돌아가시려면 'Q'를 입력하세요.");
		                     
		                     outer1:
	                    	 do {
	                    		 do {
	                    			 System.out.print("회사명 : ");
	                    			 user_answer = sc.nextLine();
	                    			 if(user_answer.isEmpty() || user_answer == null) {
	                    				 System.out.println("정확히 입력하세요.");
	                    			 }
	                    			 else if("q".equalsIgnoreCase(user_answer)) {
	                    				 break outer1;
	                    			 }
	                    			 else {
	                    				 n = 1;
	                    				 user.getPaper().setCareer_company_name(user_answer);
	                    			 }
	                    		 }while(!(n==1) );
		                        
	                    		 do {
	                    			 n = 0;
		                     
	                    			 try{
	                    				 System.out.print("입사년월 [" + dTime + "] : ");
	                    				 user_answer = sc.nextLine();
	                    				 user_answer = user_answer.replace(".", "").replace(" ", "");
		                           
	                    				 if(user_answer.isEmpty() || user_answer == null) {
	                    					 System.out.println("정확히 입력하세요.");
	                    				 }
	                    				 else if("q".equalsIgnoreCase(user_answer)) {
	                    					 break outer1;
	                    				 }
	                    				 else {
	                    					 if(user_answer.length() != 6) {
	                    						 System.out.println("[" + dTime + "] 처럼 입력해주세요");
	                    					 }
	                    					 else {
	                    						 Date career_startday = formatter.parse(user_answer);
	                    						 formatter.setLenient(false); //false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
	                    						 formatter.parse(user_answer); //대상 값 포맷에 적용되는지 확인
		                                    
	                    						 if(currentTime.before(career_startday)) { 
	                    							 System.out.println(">>> [경고] 입사년월은 오늘보다 과거여야 합니다. <<<");
	                    						 }
		                                    
	                    						 else {
	                    							 user.getPaper().setCareer_startday(user_answer);
	                    							 do {
	                    								 if("q".equalsIgnoreCase(user_answer)){
	                    									 break;
	                    								 }
	                    								 else if(!(currentTime.before(career_startday))) {
	                    									 n = 1;
	                    									 user.getPaper().setCareer_startday(user_answer);
	                    								 }
	                    								 else { 
	                    									 System.out.println("정확히 입력하세요.");   
	                    									 break;
	                    								 } 
	                    							 } while(!(n==1) || "q".equalsIgnoreCase(user_answer));
		                                  
	                    							 do {
	                    								 try {
	                    									 n = 0;
	                    									 System.out.print("퇴사년월 [" + dTime + "] : ");
	                    									 user_answer = sc.nextLine().replace(".", "");
		                                     
	                    									 if(user_answer.isEmpty() || user_answer == null) {
	                    										 System.out.println("정확히 입력하세요.");
		                                         
	                    									 }
	                    									 else if("q".equalsIgnoreCase(user_answer)) {
	                    										 break outer1;
	                    									 }
	                    									 else {
	                    										 if(user_answer.length() != 6) {
	                    											 System.out.println("[" + dTime + "] 처럼 입력해주세요");
	                    										 }
	                    										 else {
	                    											 Date career_endday = formatter.parse(user_answer);
	                    											 formatter.setLenient(false); //false일경우 처리시 입력한 값이 잘못된 형식일 시 오류가 발생
	                    											 formatter.parse(user_answer); //대상 값 포맷에 적용되는지 확인
		                                             
	                    											 if(career_endday.before(career_startday)) {
	                    												 System.out.println("퇴사년월이 입사년월보다 미래여야 합니다.");
	                    											 }
	                    											 else {
	                    												 user.getPaper().setCareer_endday(user_answer);
		                                                  
	                    												 do {
	                    													 if("q".equalsIgnoreCase(user_answer)){
	                    														 break;
	                    													 }
	                    													 else if(!(currentTime.before(career_endday))) {
	                    														 n = 1;
	                    														 user.getPaper().setCareer_startday(user_answer);
	                    													 }
	                    													 else { 
	                    														 System.out.println("정확히 입력하세요.");   
	                    														 break;
	                    													 }
	                    												 } while(!(n==1) || "q".equalsIgnoreCase(user_answer));
	                    											 }	
	                    										 }	
	                    									 }
	                    								 } catch (ParseException e) {
	                    									 System.out.println(">>> [경고] 존재하지 않은 날짜입니다. <<<");
	                    								 }	// end of try~catch--------------
	                    							 	} while(!(n==1) || "q".equalsIgnoreCase(user_answer));
	                    						 }		
	                    					 }
	                    				 } 
	                    			 }catch (ParseException e) {
                                      		System.out.println(">>> [경고] 존재하지 않은 날짜입니다. <<<");
	                    			 } // end of try~catch------------------                      
	                    		 }while(!(n==1));
	                    		 udao.career_detail(user);   
	                    	 } while("q".equalsIgnoreCase(user_answer));
		                  } catch (ParseException e) {
		                	  e.printStackTrace();
		                  }		// end of try~catch-------------- 
		               }	// end of (경력을 입력한 경우)
		               else {
		                  System.out.println("목록에 있는 사항만 선택 가능합니다.");
		                  continue;
		               }
		            }while(!(n==1) || "q".equalsIgnoreCase(career_choice));
		            
		            udao.write_paper_sql(user); 
		         
		         }
		      }while(!(n==1));
		}	// end of private void write_paper(Scanner sc, User_DTO user)


		





		// ◆◆◆ === 이력서 수정 === ◆◆◆ //
		private void change_paper(Scanner sc, User_DTO user) {
			// TODO Auto-generated method stub
			
		}

		


		// ◆◆◆ === 이력서 삭제 === ◆◆◆ //
		private void delete_paper(Scanner sc, User_DTO user) {
			// TODO Auto-generated method stub
			
		}
		
		

// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆


		// ◆◆◆ === 채용지원 메뉴 === ◆◆◆ //
	      public void Recruit_apply_menu(Scanner sc, User_DTO user, Company_DTO company) {
	         String u_Choice = "";
	            
	         do {
	            System.out.println("\n>>> ---- " + user.getUser_name() + "님의 구직 메뉴 ---- <<<\n"
	                            + "1. 구인회사 조회\n"
	                            + "2. 채용공고 조회\n"
	                            + "3. 공고분야별 검색\n"
	                            + "4. 지원현황\n"
	                            + "5. 이전 메뉴로 돌아가기" );
	               
	             System.out.print("▶ 메뉴번호 선택 : ");
	             u_Choice = sc.nextLine();
	               
	             switch (u_Choice) {
	               case "1": // 구인회사 조회
	                  company_search(sc, user, company);
	                     break;
	               case "2": // 채용공고 조회
	            	   List<Recruit_INFO_DTO> recruit_info_list = rdao.recruit_info_list();
	                   
	                   if(recruit_info_list.size() > 0) {
	                      
	                      System.out.println("\n------------------------------ [채용공고 목록] ------------------------------");
	                      System.out.println("채용공고번호\t\t채용공고명\t\t기업명\t주소\t신입,경력여부\t연봉\t채용마감일");
	                      System.out.println("-------------------------------------------------------------------------"); 
	                        
	                      StringBuilder sb = new StringBuilder();
	                        
	                      for(Recruit_INFO_DTO recruit_info_li : recruit_info_list) {
	                           sb.append(recruit_info_li.getRecruit_no() + "\t" +
	                                   recruit_info_li.getRecruit_title() + "\t" +
	                                   recruit_info_li.getCdto().getCompany_name() + "\t" +
	                                   recruit_info_li.getCdto().getCompany_address() + "\t" +
	                                   recruit_info_li.getCareer() + "\t" + 
	                                   recruit_info_li.getYear_salary() + "\t" + 
	                                   recruit_info_li.getRecruit_deadline() + "\n"); 
	                      } // end of for ----------
	                      System.out.println(sb.toString());
	                      
	                      Map<String, String> paraMap = new HashMap<>();
	                      System.out.println(">>> 채용공고 지원하기(지원하지 않으실 경우 엔터를 입력해주세요. <<<");
	                      System.out.print("▶ 채용공고번호 : ");
	                      String search_recruit_no = sc.nextLine();

		                      if(search_recruit_no.isBlank()) {
		                    	  break;
		                      }
	                      
	                      paraMap.put("recruit_no", search_recruit_no);
                           
	                      Recruit_INFO_DTO rdto = rdao.recruit_info(paraMap);	// 채용공고 존재여부 조회
	                        
	                      if(rdto != null) {
	                            
	                    	  System.out.println("\n" + "-".repeat(50));
	                    	  System.out.println("▶ 채용공고번호 : " + rdto.getRecruit_no() + "\n"
	                                             + "▶ 기업명 : " + rdto.getCdto().getCompany_name() + "\n"
	                                             + "▶ 채용공고명 : " + rdto.getRecruit_title() + "\n" 
	                                             + "▶ 신입/경력 여부 : " + rdto.getCareer() + "\n" 
	                                             + "▶ 채용분야 : " + rdto.getRecruit_field() + "\n"
	                                             + "▶ 연봉 : " + rdto.getYear_salary() + "\n" 
	                                             + "▶ 근무요일 : " + rdto.getWork_day() + "\n"
	                                             + "▶ 근무시간 : " + rdto.getWork_time() + "\n"
	                                             + "▶ 채용공고내용 : " + rdto.getRecruit_content() + "\n"
	                                             + "▶ 채용인원 : " + rdto.getRecruit_people() + "\n"
	                                             + "▶ 채용담당자명 : " + rdto.getManager_name() + "\n" 
	                                             + "▶ 채용담당자이메일 : " + rdto.getManager_email() + "\n" 
	                                             + "▶ 채용등록일 : " + rdto.getRecruit_registerday() + "\n" 
	                                             + "▶ 채용마감일 : " + rdto.getRecruit_deadline());
	                    	  System.out.println("-".repeat(50));
 // ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆                              
	                    	  String yn = "";
	                               
	                    	  do { // 채용지원
                                  ////////////////////////////////////////////////////////////
                                  System.out.print("\n>>> 채용공고명 < " + rdto.getRecruit_title() + " > 에 지원하시겠습니까? [Y/N] => ");
	                                  
                                  yn = sc.nextLine();
	                                  
                                  if("y".equalsIgnoreCase(yn)) {
                                      int n = recruit_apply(sc, search_recruit_no, user, rdto); 

                                      if(n == 1) { 
                                    	  System.out.println(">> 입사지원에 성공하셨습니다. <<");
                                      }
                                      else if(n == 0) {
                                    	  System.out.println(">> 입사지원을 취소하셨습니다. <<");
                                      }
                                      else if(n == -1) { 
                                    	  System.out.println(">> 입사지원에 실패하셨습니다. <<");
                                      }
                                      break;
                                  }
                                  else if("n".equalsIgnoreCase(yn)) {
                                	  break;
                                  }
                                  else {
                                	  System.out.println(">> Y 또는 N 만 입력하세요!! <<\n");
                                  }
                                  ////////////////////////////////////////////////////////////
	                    	  } while(!"n".equalsIgnoreCase(yn));
                            }
	                        else {
	                        	System.out.println("\n>> 채용공고번호가 존재하지 않습니다. <<");
	                        }
	                   }
	                   else {
	                      System.out.println("\n>> 채용공고 목록이 존재하지 않습니다. <<");
	                   }	// end of if~else(채용공고 존재)----------------------------
	                   break;
	               case "3":   // 공고분야별 검색
	            	  search_recruit(sc, user, company);
	                  break;
	               case "4":   // 지원한 공고 조회
	                  recruit_apply_situation(sc, user, company);
	                  break;
	               case "5": // 이전 메뉴로 돌아가기
	                  break;   
	               default:
	                  System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
	                  break;
	             } // end of switch (u_Choice)-----------------
	         } while (!"5".equalsIgnoreCase(u_Choice));   // end of do~while------------------   
	      }   // end of public void Recruit_apply(Scanner sc, User_DTO user, Company_DTO company)------


		
		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆		
		
		


		// ◆◆◆ === 구인회사 조회 === ◆◆◆ //
		public void company_search(Scanner sc, User_DTO user, Company_DTO company) {
			System.out.println("\n >>> --- 구인회사 조회 --- <<<");
		      
		      System.out.print("▶ 기업명 : ");
		      String search_company_name = sc.nextLine().trim().toLowerCase();;
		      
		      Map<String, String> paraMap = new HashMap<>();
		      paraMap.put("company_name", search_company_name);
		      
		      Company_DTO cdto = radao.company_search(paraMap);
		      
		      StringBuilder sb = new StringBuilder();  
              if(cdto != null) {
	              sb.append("\n === 기업 정보 ===\n"
	                       + "▶ 기업명 : " + cdto.getCompany_name() + "\n"
	                       + "▶ 기업대표 : " + cdto.getCeo_name() + "\n"
	                       + "▶ 기업주소 : " + cdto.getCompany_address() + "\n" );
	                 
	              if( cdto.getCompany_type_detail().getBegin_day() != null ) 
	                    sb.append("▶ 설립일자 : " + cdto.getCompany_type_detail().getBegin_day() + "\n");
	              if( cdto.getCompany_type_detail().getCapital_money() != null ) 
	                    sb.append("▶ 자본금 : " + cdto.getCompany_type_detail().getCapital_money() + "\n");
	              if (cdto.getCompany_type_detail().getEmployee_num() != null) 
	                    sb.append("▶ 사원수 : " + cdto.getCompany_type_detail().getEmployee_num() + "\n");
	              if (cdto.getCompany_type_detail().getPublic_status() != null ) 
	                    sb.append("▶ 상장여부 : " + cdto.getCompany_type_detail().getPublic_status() + "\n");
	              if (cdto.getCompany_type_detail().getCompanylist_num() != null ) 
	                   sb.append("▶ 계열회사수 : " + cdto.getCompany_type_detail().getCompanylist_num() + "\n");
	              System.out.print(sb.toString());
              }
              else {
 		         System.out.println(">> 기업명 " + search_company_name + " 은(는) 존재하지 않습니다. <<\n");
 		      }		// end of if~else---------------
              
		}	// end of public void company_search(Scanner sc, User_DTO user, Company_DTO company)-------

		
		
		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆		
		
		
		
		// ◆◆◆ === 채용지원 === ◆◆◆ //
		private int recruit_apply(Scanner sc, String search_recruint_no, User_DTO user, Recruit_INFO_DTO rdto) {
			int result = 0;
		      
			Map<String, String> paraMap = new HashMap<>();
			
			System.out.println("\n>>> 입사지원하기 <<<");
			System.out.println("1. 채용공고일련번호 : " + search_recruint_no);
			paraMap.put("recruit_no", search_recruint_no);
		      
			System.out.println("-".repeat(20) + " [" + user.getUser_name() + " 님의 이력서 목록] " + "-".repeat(20));
        	System.out.print("번호\t제목\t\t작성일\n");
        	System.out.println("-".repeat(50));
		      
        	String paper_code_str = "";
        	int paper_code = 0;
        	do {
        		System.out.println(">> 돌아가시려면 엔터를 입력하세요. <<");
        		System.out.print("2. 이력서번호 : ");
        		paper_code_str = sc.nextLine();
        		
        		if(paper_code_str.isBlank()) {
        			return 0;
        		}
        		try {
	        		paper_code = Integer.parseInt(paper_code_str);
	        		
	        		User_DAO udao = new User_DAO_imple();
	        		if(udao.check_paper(paper_code, user)) {	// 이력서가 존재한다면
	        			break;
	        		}
	        		else {
	        			System.out.println(">>>[경고] 선택하신 이력서는 존재하지 않습니다. <<<");
	        		}
        		}catch(NumberFormatException e) {
        			System.out.println(">>> [경고] 반드시 숫자를 입력해주세요.");
        		}
        	}while(true);
        		
        	do {
        		System.out.print("3. 지원동기[최대 300글자] : ");
        		String apply_motive = sc.nextLine();
        		
        		if(1 <= apply_motive.length() && apply_motive.length() <= 300) {  
        			paraMap.put("apply_motive", apply_motive);
					break;
				}	
        		else {
        			System.out.println(">>> 입력한 데이터가 너무 크므로 입력이 불가합니다.!! <<<");	
        		}
        	} while(true); 
        		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
        	
    		do {
		         ////////////////////////////////////////////////////////////
        			System.out.print(">> 정말로 입사지원을 하시겠습니까? [Y/N] => ");
        			String yn = sc.nextLine();
		         
        			if("y".equalsIgnoreCase(yn)) {
        				result = radao.my_recruit_apply(paraMap, paper_code);
		            break;
		         }
		         else if("n".equalsIgnoreCase(yn)) {
		            break;
		         }
		         else {
		            System.out.println(">>> Y 또는 N 만 입력하세요!! <<<\n");
		         }
		         ////////////////////////////////////////////////////////////
		      } while(true);
        	
		      return result;
        	
		}	// end of private int recruit_apply(Scanner sc, String search_recruint_no, User_DTO user, Recruit_INFO_DTO rdto)-----
		
		
		
		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
		
		
		
		
		// ◆◆◆ === 공고분야별 검색 === ◆◆◆ //
		private void search_recruit(Scanner sc, User_DTO user, Company_DTO company) {
			String s_Choice = "";
			
			do {
				System.out.println("\n >>> --- 채용지원  --- <<<");
				System.out.println("1. 업종별 검색	2. 지역별 검색	3. 경력별 검색	4. 고용형태별 검색	5. 이전 메뉴 돌아가기");
				System.out.print("▶ 메뉴번호 선택 : ");
				s_Choice = sc.nextLine();
	            
				switch (s_Choice) {
					case "1":	// 업종별 검색(제조,의약)
						search_jobtype(sc, company);
						break;
		
					case "2":	// 지역별 검색
						search_local(sc, company);
						break;
					
					case "3":	// 경력별 검색
						search_career(sc,company);
						break;
					
					case "4":	// 고용형태별 검색(인턴,정규직,계약직) - 공고에 채용분야
						search_recruit_field(sc, company);
						break;
					
					case "5":	// 이전 메뉴 돌아가기
						break;
				default:
					System.out.println(">> 메뉴에 존재하지 않는 번호입니다. <<");
					break;
				} // end of switch (s_Choice)
			} while(!("5".equals(s_Choice)));
			
			
			
			
		} // end of private void recruit_apply(Scanner sc, User_DTO user, Company_DTO company)
	


// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆


		// 1. 업종별 검색(서비스업, 제조업) - (job_type)
		private void search_jobtype(Scanner sc, Company_DTO company) {
			System.out.println("-".repeat(80));
			System.out.println("1. 서비스업   2. IT, 정보통신업      3. 판매, 유통업      4. 제조업, 생산업 \n"
	                      + "5. 교육업      6. 건설업         7. 의료, 제약업      8. 미디어, 광고업 \n"
	                      + "9. 문화, 예술, 디자인업         10. 기관, 협회");
			System.out.println("-".repeat(80));
	   
			do {
	            System.out.print("▶ 검색하고자 하는 업종 입력[예: 서비스업, 제조업] : ");             
	            String job_type_select = sc.nextLine();         
	            
	            if(job_type_select.isBlank()) {
	               System.out.println(">>[경고] 업종명을 입력해주세요. << ");
	            }
	            else {
	               List<Recruit_INFO_DTO> recruitList = rdao.search_job_type(job_type_select, company);
	               
	               if(recruitList.size() > 0) {
	                  System.out.println("-".repeat(200));
	                  System.out.println("공고번호\t\t고용형태\t\t공고명\t\t공고내용\t\t등록일\t\t마감일\t\t신입경력여부\t연봉\t채용인원수\t\t근무요일\t\t근무시간\t\t담당자이메일\t\t담당자명");
	                  System.out.println("-".repeat(200));
	                  
	                  StringBuilder sb = new StringBuilder();
	                  
	                  for(Recruit_INFO_DTO ridto : recruitList) {
	                  
	                     sb.append(ridto.getRecruit_no() + "\t"
	                           + ridto.getRecruit_field() + "\t"
	                           + ridto.getRecruit_title() + "\t"
	                           + ridto.getRecruit_content() + "\t"
	                           + ridto.getRecruit_registerday() + "\t"
	                           + ridto.getRecruit_deadline() + "\t"
	                           + ridto.getCareer() + "\t"
	                           + ridto.getYear_salary() + "\t"
	                           + ridto.getRecruit_people() + "\t"
	                           + ridto.getWork_day() + "\t"
	                           + ridto.getWork_time() + "\t"
	                           + ridto.getManager_email() + "\t"
	                           + ridto.getManager_name() + "\n");
	                     
	                  } // end of for(Recruit_INFO_DTO ridto : recruitList)
	                  
	                  System.out.println(sb.toString());
	                  break;
	               
	               }
	               else {
	                  System.out.println(">> 입력하신 업종과 일치하는 공고가 없습니다. << \n");
	                  break;
	               }
	            }    
	         } while(true);
	      } // end of private void search_jobtype(Scanner sc, Company_DTO company)
	      

	      
	      
	      
	      
	      // 2. 지역별 검색
	      private void search_local(Scanner sc, Company_DTO company) {
	         
	         System.out.println("-".repeat(80));
	         System.out.println("1. 서울       2. 경기       3. 인천       4. 부산       5. 대구 \n"
	                      + "6. 광주       7. 대전       8. 울산       9. 세종       10. 강원 \n"
	                      + "11. 경남       12. 경북       13. 전남       14. 전북       15. 충남 \n"
	                      + "16. 충북       17. 제주");
	         System.out.println("-".repeat(80));
	         
	         do {
	            System.out.print("▶ 검색하고자 하는 지역 입력[예: 서울, 경기] : "); 
	            String local_select = sc.nextLine();
	            
	            if(local_select.isBlank()) {
	               System.out.println(">>[경고] 지역명을 입력해주세요. << ");
	            }
	            else {
	               List<Recruit_INFO_DTO> recruitList = rdao.search_local(local_select, company);
	               
	               if(recruitList.size() > 0) {
	                  System.out.println("-".repeat(200));
	                  System.out.println("공고번호\t\t고용형태\t\t공고명\t\t공고내용\t\t등록일\t\t마감일\t\t신입경력여부\t연봉\t채용인원수\t\t근무요일\t\t근무시간\t\t담당자이메일\t\t담당자명");
	                  System.out.println("-".repeat(200));
	                  
	                  StringBuilder sb = new StringBuilder();
	                  
	                  for(Recruit_INFO_DTO ridto : recruitList) {
	                  
	                     sb.append(ridto.getRecruit_no() + "\t"
	                           + ridto.getRecruit_field() + "\t"
	                           + ridto.getRecruit_title() + "\t"
	                           + ridto.getRecruit_content() + "\t"
	                           + ridto.getRecruit_registerday() + "\t"
	                           + ridto.getRecruit_deadline() + "\t"
	                           + ridto.getCareer() + "\t"
	                           + ridto.getYear_salary() + "\t"
	                           + ridto.getRecruit_people() + "\t"
	                           + ridto.getWork_day() + "\t"
	                           + ridto.getWork_time() + "\t"
	                           + ridto.getManager_email() + "\t"
	                           + ridto.getManager_name() + "\n");
	                     
	                  } // end of for(Recruit_INFO_DTO ridto : recruitList)
	                  
	                  System.out.println(sb.toString());
	                  break;
	               }
	               else {
	                  System.out.println(">> 입력하신 지역과 일치하는 정보가 없습니다. << \n");
	                  break;
	               }
	            }
	         } while(true);
	      } // end of private void search_local(Scanner sc, Company_DTO company)




	      
	      // 3. 경력별 검색(신입,경력,무관)
	      private void search_career(Scanner sc, Company_DTO company) {
	         System.out.println("-".repeat(30));
	         System.out.println("1. 신입    2. 경력    3. 무관");
	         System.out.println("-".repeat(30));
	         
	         do {
	            System.out.print("▶ 검색하고자 하는 경력 형태 입력[예: 신입, 경력] : ");
	            
	            String career_select = sc.nextLine();
	            if(career_select.isBlank()) {
	               System.out.println(">>[경고] 경력명을 입력해주세요. <<");
	            }
	            else {
	               List<Recruit_INFO_DTO> recruitList = rdao.search_career(career_select, company);
	               
	               
	               if(recruitList.size() > 0) {
	                  System.out.println("-".repeat(200));
	                  System.out.println("공고번호\t\t고용형태\t\t공고명\t\t공고내용\t\t등록일\t\t마감일\t\t신입경력여부\t연봉\t채용인원수\t\t근무요일\t\t근무시간\t\t담당자이메일\t\t담당자명");
	                  System.out.println("-".repeat(200));
	                  
	                  StringBuilder sb = new StringBuilder();
	                  
	                  for(Recruit_INFO_DTO ridto : recruitList) {
	                  
	                     sb.append(ridto.getRecruit_no() + "\t"
	                           + ridto.getRecruit_field() + "\t"
	                           + ridto.getRecruit_title() + "\t"
	                           + ridto.getRecruit_content() + "\t"
	                           + ridto.getRecruit_registerday() + "\t"
	                           + ridto.getRecruit_deadline() + "\t"
	                           + ridto.getCareer() + "\t"
	                           + ridto.getYear_salary() + "\t"
	                           + ridto.getRecruit_people() + "\t"
	                           + ridto.getWork_day() + "\t"
	                           + ridto.getWork_time() + "\t"
	                           + ridto.getManager_email() + "\t"
	                           + ridto.getManager_name() + "\n");
	                     
	                  } // end of for(Recruit_INFO_DTO ridto : recruitList)
	                  
	                  System.out.println(sb.toString());
	                  break;
	               }
	               else {
	                  System.out.println(">> 입력하신 고용형태와 일치하는 공고가 없습니다. << \n");
	                  break;
	               }
	            }
	         } while(true);
	      } // end of private void search_career(Scanner sc, Company_DTO company)




	      // 4. 고용형태별 검색(인턴,정규직,계약직) - 공고에 채용분야(recruit_type)
	      private void search_recruit_field(Scanner sc, Company_DTO company) {
	         System.out.println("-".repeat(70));
	         System.out.println("1. 정규직    2. 계약직    3. 인턴         4. 파견직     5.프리랜서 \n"
	                      + "6. 아르바이트    7. 연수생    8. 위촉직      9. 개인사업자");
	         System.out.println("-".repeat(70));
	         
	         do {
	            System.out.print("▶ 고용형태 입력[예: 정규직, 계약직] : ");
	            
	            String recruit_type_select = sc.nextLine();
	            
	            if(recruit_type_select.isBlank()) {
	               System.out.println(">>[경고] 고용형태명을 입력해주세요. <<");
	            }
	            else {
	               List<Recruit_INFO_DTO> recruitList = rdao.search_recruit_field(recruit_type_select, company);
	               
	               
	               if(recruitList.size() > 0) {
	                  System.out.println("-".repeat(200));
	                  System.out.println("공고번호\t\t고용형태\t\t공고명\t\t공고내용\t\t등록일\t\t마감일\t\t신입경력여부\t연봉\t채용인원수\t\t근무요일\t\t근무시간\t\t담당자이메일\t\t담당자명");
	                  System.out.println("-".repeat(200));
	                  
	                  StringBuilder sb = new StringBuilder();
	                  
	                  for(Recruit_INFO_DTO ridto : recruitList) {
	                  
	                     sb.append(ridto.getRecruit_no() + "\t"
	                           + ridto.getRecruit_field() + "\t"
	                           + ridto.getRecruit_title() + "\t"
	                           + ridto.getRecruit_content() + "\t"
	                           + ridto.getRecruit_registerday() + "\t"
	                           + ridto.getRecruit_deadline() + "\t"
	                           + ridto.getCareer() + "\t"
	                           + ridto.getYear_salary() + "\t"
	                           + ridto.getRecruit_people() + "\t"
	                           + ridto.getWork_day() + "\t"
	                           + ridto.getWork_time() + "\t"
	                           + ridto.getManager_email() + "\t"
	                           + ridto.getManager_name() + "\n");
	                     
	                  } // end of for(Recruit_INFO_DTO ridto : recruitList)
	                  
	                  System.out.println(sb.toString());
	                  break;
	               }
	               else {
	                  System.out.println(">> 입력하신 고용형태와 일치하는 공고가 없습니다. << \n");
	                  break;
	               }
	            }
	         } while(true);
	      }


		
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆		
		
		

		// ◆◆◆ === 지원한 공고 조회 === ◆◆◆ //
		private void recruit_apply_situation(Scanner sc, User_DTO user, Company_DTO company) {
			
			// 지원한 공고 조회
			List<User_DTO> applyList = radao.applylist(radao, user);
			
			StringBuilder sb = new StringBuilder();
			
			if(applyList.size() > 0) {
				System.out.println("\n" + "-".repeat(10) + " " + user.getUser_name() + " 님이 지원한 공고 "
						+ "-".repeat(10));
				System.out.println("공고번호\t\t경력\t지원동기\t이력서번호\t이력서명\t학력\t취업우대");
				System.out.println("-".repeat(40));
				
				sb = new StringBuilder();
				
				for(User_DTO recruit_apply : applyList) {
					sb.append(recruit_apply.getRcapply().getRecruit_no() + " ");
					sb.append(recruit_apply.getPaper().getCareer() + "\t");
					sb.append(recruit_apply.getRcapply().getApply_motive() + "\t");
					sb.append(recruit_apply.getPaper().getPaper_code() + "\t");
					sb.append(recruit_apply.getPaper().getPaper_name() + "\t");
					sb.append(recruit_apply.getAcademy_name() + "\t");
					sb.append(recruit_apply.getPriority_name() + "\t");
				}	// end of for----------
				System.out.println(sb.toString());
			}
			else {
				System.out.println(">>> 지원한 공고가 없습니다. <<<");
				return;
			}	// end of if~else---------------------------
			
			System.out.println("\n------- <<< 합격여부 조회 >>> ---------");
			System.out.print("공고번호 : ");
			String num = sc.nextLine();		// 합격여부 확인
			
			if(!num.isBlank()) {
				Recruit_apply_DAO radao = new Recruit_apply_DAO_imple();
				
				if( radao.check_success(sc, user, num) == 0)	{ // 미입력
					System.out.println(">>> 기업에서 확인하지 않았습니다. <<<");
				}
				else if(radao.check_success(sc, user, num) == 1) {
					System.out.println(">>> 서류심사 합격되었습니다. <<<");
				}
				else if(radao.check_success(sc, user, num) == 2) {
					System.out.println(">>> 서류심사 불합격되었습니다. <<<");
				}
			}
			return;
				
		}	// end of private void recruit_apply_situation(Scanner sc, User_DTO user, Company_DTO company)-----

	






	
		
		
		
}
