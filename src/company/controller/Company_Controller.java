package company.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.MYDBConnection;
import common.Set_util;
import company.domain.Company_DTO;
import company.domain.Company_type_DTO;
import company.domain.Recruit_INFO_DTO;
import company.model.Company_DAO;
import company.model.Company_DAO_imple;
import company.model.Recruit_DAO;
import company.model.Recruit_DAO_imple;
import user.domain.User_DTO;
import user.model.User_DAO;
import user.model.User_DAO_imple;

public class Company_Controller {

	// == field == 
	User_DAO udao = new User_DAO_imple();
	Company_DAO cdao = new Company_DAO_imple();
	Recruit_DAO rdao = new Recruit_DAO_imple();
	
	private Connection conn = MYDBConnection.getConn();      // 데이터베이스 서버 연결
	private PreparedStatement pstmt;   // 우편배달부
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
      }   // end of try~catch----------
	}   // end of private void close()---------------
	   
	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	
   
   
   // 기업로그인된 메뉴출력하는 메소드
   public void company_menu(Scanner sc, Company_DTO company) {
      
      String c_Choice = "";
      int c = 0;
      
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
				company_info(sc, company);
				break;
			case "2": // 채용 정보 메뉴
				recruit_info(sc, company);
				break;
			case "3": // 구직자 정보 메뉴
				user_info(sc, company);
				break;
			case "4": // 로그아웃
				System.out.println(">>> " + company.getCompany_id() + "님 로그아웃 되었습니다. <<<\n");
				company = null;
				break;             
			case "5": // 회원탈퇴 
				c = cdao.Withdrawal(sc,company);
				if(c==1) {
					company = null;
					System.out.println(">>> 회원탈퇴 성공되었습니다. <<<");
					break; 
				}
				else {
					break;
				}
			default:
				System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				break;
    	  } // end of switch (c_Choice)-------------------------
           ////////////////////////////////////////////////////////////////
      } while (!("4".equals(c_Choice)) && !("5".equals(c_Choice) && c == 1));   
   } // end of public void companyMenu_Start(Scanner sc, Company_DTO company)




  // ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
   
   
   
   
	//◆◆◆ === 기업 정보 메뉴 === ◆◆◆ //
	public void company_info(Scanner sc, Company_DTO company) {
		 
		String c_Choice = "";
	      
	     do {
		     System.out.println("\n>>> ---- 기업 정보 메뉴 ---- <<<\n"
		                   + "1.기업정보 보기 \n"
		                   + "2.추가정보 입력 및 수정 \n"
		                   + "3.이전 메뉴로 돌아가기" );
		      
		     System.out.print("▶ 메뉴번호 선택 : ");
		     c_Choice = sc.nextLine();
		      
	         switch (c_Choice) {
	            
				case "1": // 로그인된 기업정보 보기
					Map<String, String> paraMap = new HashMap<>();
	                paraMap.put("company_id", company.getCompany_id());
	                    
	                Company_DTO company_1 = my_company_info(company , paraMap); //   company = select 조인문장
	                  
	                StringBuilder sb = new StringBuilder();  
	                   
	                sb.append("\n === 기업 정보 ===\n"
	                         + "▶ 기업명 : " + company_1.getCompany_name() + "\n"
	                         + "▶ 기업대표 : " + company_1.getCeo_name() + "\n"
	                         + "▶ 사업자등록번호 : " + company_1.getBusiness_number()+ "\n"
	                         + "▶ 기업주소 : " + company_1.getCompany_address() + "\n" );
	                   
	                if( company_1.getCompany_type_detail().getBegin_day() != null ) 
	                      sb.append("▶ 설립일자 : " + company_1.getCompany_type_detail().getBegin_day() + "\n");
	                if( company_1.getCompany_type_detail().getCapital_money() != null ) 
	                      sb.append("▶ 자본금 : " + company_1.getCompany_type_detail().getCapital_money() + "\n");
	                if (company_1.getCompany_type_detail().getEmployee_num() != null) 
	                      sb.append("▶ 사원수 : " + company_1.getCompany_type_detail().getEmployee_num() + "\n");
	                if (company_1.getCompany_type_detail().getPublic_status() != null ) 
	                      sb.append("▶ 상장여부 : " + company_1.getCompany_type_detail().getPublic_status() + "\n");
	                if (company_1.getCompany_type_detail().getCompanylist_num() != null ) 
	                     sb.append("▶ 계열회사수 : " + company_1.getCompany_type_detail().getCompanylist_num() + "\n");
	                System.out.print(sb.toString());
	                break;
				case "2": // 기업 추가정보 입력
					company_detail_info(sc, company);
					break;
				case "3": // 이전메뉴로 되돌아가기
					break;   
				default:
					System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
					break;
				} // end of switch (c_Choice)-----------------
	      } while (!"3".equalsIgnoreCase(c_Choice));	// end of do~while------------------	
	}	// end of public void company_menu2(Scanner sc, Company_DTO company)--------------


	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	
	
	

	   // ◆◆◆ === 기업 추가정보 입력 === ◆◆◆ // ~~ 아직 작업중
	   public void company_detail_info(Scanner sc, Company_DTO company) {
	      
	      System.out.println("\n--------------------------------------"); 
	      System.out.println(">>> 기업 추가정보 입력하기 <<<");
	        System.out.println("--------------------------------------");
	        
	        boolean chk = cdao.check_info(company);
	           
	        if(!chk ) {
              System.out.println("[경고] 이미 정보를 입력하셨으므로 새로 입력할수 없습니다!!");
              return;
	        }

	        int employee_num = 0;
	        
	        Map<String, String> paraMap = new HashMap<>();
	        
	        
	        do {
	           ///////////////////////////////////////////////////////////
	           System.out.print("(이전메뉴로 돌아가시려면 Q를 눌러주세요)"
	           		+ "▶ 사원수[숫자만 입력] : ");
	           String c_employee_num = sc.nextLine();
	           if("q".equalsIgnoreCase(c_employee_num)) {
	        	   
	        	   return;
	           }
	         
	           try {
	              employee_num = Integer.parseInt(c_employee_num);
	              
	            if(employee_num < 1) {
	               System.out.println(">>[경고] 사원수는 1이상인 정수로만 입력하셔야 합니다. <<\n");
	               continue;
	            } else {   
	               System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
	               
	               paraMap.put("employee_num",  String.valueOf(employee_num) );
	               
	               break;
	            }   // end of if~else--------------
	           } catch (NumberFormatException e) {
	              System.out.println(">>[경고] 사원수는 정수로만 입력하셔야 합니다. <<\n");
	              continue;
	           }   // end of try~catch--------------------
	         //////////////////////////////////////////////////////////
	        } while(true);   // end of do~while-------------------------
	      
	        
	        
	        do {
	         ///////////////////////////////////////////////////////////
	    	  String public_status = "";
	    	  System.out.print("(이전메뉴로 돌아가시려면 Q를 눌러주세요)\n"
	    	  		+ "▶ 상장여부[상장은 1, 비상장은 0을 입력] : ");
	    	  public_status = sc.nextLine();
	    	  if("q".equalsIgnoreCase(public_status)) {
	        	   
	        	   return;
	           }
	         
	         
	    	  if("1".equals(public_status)) {
	    		  System.out.println(">> 상장여부에 '상장'을 선택하셨습니다.");
	    		  System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
	    		  paraMap.put("public_status", public_status);
	    		  break;
	    	  }
	    	  else if("0".equals(public_status)) {
	    		  System.out.println(">> 상장여부에 '비상장'을 선택하셨습니다.");
	    		  System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
	    		  paraMap.put("public_status", public_status);
	    		  break;
	    	  }
	    	  else {
	    		  System.out.println(">>[경고] 상장여부는 0 또는 1만 입력하셔야 합니다. <<\n");
	    		  continue;
	    	  }   // end of if~else------------------   
	         ///////////////////////////////////////////////////////////
	      	} while(true);   // end of do~while----------------------------
	        
  			do {
	         ///////////////////////////////////////////////////////////
  				String begin_day = "";
  				System.out.print("(이전메뉴로 돌아가시려면 Q를 눌러주세요)\n"
  						+ "▶ 설립일자[예 : 2024-03-15 ] : ");
  				begin_day = sc.nextLine();
  			    if("q".equalsIgnoreCase(begin_day)) {
	        	   
	        	   return;
	           }
  				if( begin_day.isBlank() ) {   // 그냥 엔터 또는 공백으로 입력한 경우
 	               System.out.println(">> [경고] 설립일자는 필수로 입력하셔야 합니다. <<\n");
 	               continue;
 	            }
  				else if(Set_util.Check_date_past(begin_day)) {
  					paraMap.put("begin_day", begin_day);
  					break;
  				}
  				 
	         ///////////////////////////////////////////////////////////
  			} while(true);   // end of do~while--------------------------------
	        
  			System.out.println(">> 다음 추가정보입력으로 넘어갑니다. <<\n");
  			long capital_money = 0;
  			do {
	          ///////////////////////////////////////////////////////////
  				System.out.print("(이전메뉴로 돌아가시려면 Q를 눌러주세요)\n"
  						+ "▶ 자본금[숫자만 입력] : ");
  				String c_capital_money = sc.nextLine();
  				if("q".equalsIgnoreCase(c_capital_money)) {
 	        	   
 	        	   return;
 	           }
  				try {
  					capital_money = Long.parseLong(c_capital_money);
	      
  					if(capital_money <= 0) {
  						System.out.println(">>[경고] 자본금은 0이 아닌 정수로만 입력하셔야 합니다. <<\n");
  						continue;
  					}
  					else {   
  						System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
  						paraMap.put("capital_money", c_capital_money);
  						break;
  					}   // end of if~else---------------------
  				} catch (NumberFormatException e) {
  					System.out.println(">>[경고] 자본금은 정수로만 입력하셔야 합니다. <<\n");
  					continue;
  				}   // end of try~catch--------------------------
	          //////////////////////////////////////////////////////////
  			} while(true);   // end of do~while------------
	       
	       
  			int companylist_num = 0;
	        do {
	           ///////////////////////////////////////////////////////////
	        	System.out.print("(이전메뉴로 돌아가시려면 Q를 눌러주세요)\n"
	        			+ "▶ 계열회사수[숫자만 입력] : ");
	        	String c_companylist_num = sc.nextLine();
	        	if("q".equalsIgnoreCase(c_companylist_num)) {
	 	        	   
	 	        	   return;
	 	           }
	        	
	        	try {
	        		companylist_num = Integer.parseInt(c_companylist_num);
	              
	        		if(companylist_num < 1) {
	        			System.out.println(">>[경고] 계열회사수는 1이상인 정수로만 입력하셔야 합니다. <<\n");
	        			continue;
	        		}
	        		else {   
	        			System.out.println("\n>> 다음 추가정보입력으로 넘어갑니다. <<\n");
	        			paraMap.put("companylist_num", String.valueOf(companylist_num) );
	        			break;
	        		}   // end of if~else--------------
	        	} catch (NumberFormatException e) {
	        		System.out.println(">>[경고] 계열회사수는 정수로만 입력하셔야 합니다. <<\n");
	        		continue;
	        	}   // end of try~catch----------------
	         //////////////////////////////////////////////////////////
        	} while(true);   // end of do~while----------------
	        
	        
	        int n = cdao.company_detail_input(paraMap, company.getCompany_id());
	        
	        if(n==1) {
	           System.out.println(">> " + company.getCompany_name() +  " 기업의 상세정보 입력 완료! <<");
	        }
	        else {
	           System.out.println(">> " + company.getCompany_name() +  " 기업의 상세정보 입력 실패! <<");
	        }
	   }   // end of private void company_detail_info(Scanner sc, Company_DTO company)--------

	   
	   
	   
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	   

	   
	   

	// ◆◆◆ === 로그인된기업 정보보기 === ◆◆◆ //
	public Company_DTO my_company_info(Company_DTO company, Map<String, String> paraMap) {
       try {
            // conn = MyDBConnection.getConn(); 데이터베이스 연결하기
           String sql = " select C.company_id , C.company_name , C.businees_number , C.ceo_name "
        		   + ", L.local_name || ' ' || L.city_name || ' ' || C.company_address as company_address, "
                   + " T.employee_num , decode(T.public_status , 1 , '상장기업' , '비상장기업') as public_status , to_char(T.begin_day , 'yyyy-mm-dd') as begin_day , " 
                   + " T.capital_money, to_char (T.companylist_num)  as companylist_num  "
                   + " from tbl_company C join tbl_company_type T " 
                   + " on C.company_id = T.fk_company_id "
                   + " join tbl_local L "
                   + " on C.fk_local_code = L.local_code "
                   + " where C.company_id = ? " ;  
          
           pstmt = conn.prepareStatement(sql); // 우편배달부 
           
           pstmt.setString(1, paraMap.get("company_id") ); 
          
           rs = pstmt.executeQuery(); // SQL문 실행  
           
           if(rs.next()) { // select 결과가 있다면~
              company = new Company_DTO();
              
              company.setCompany_name(rs.getString("company_name"));
              company.setBusiness_number(rs.getString("businees_number"));
              company.setCeo_name(rs.getString("ceo_name"));
              company.setCompany_address(rs.getString("company_address"));
              
              Company_type_DTO ctdto = new Company_type_DTO();
              
              ctdto.setEmployee_num(rs.getString("employee_num"));
              ctdto.setPublic_status(rs.getString("public_status"));
              ctdto.setBegin_day(rs.getString("begin_day"));
              ctdto.setCapital_money(rs.getString("capital_money"));
              ctdto.setCompanylist_num(rs.getString("companylist_num"));
              
              company.setCompany_type_detail(ctdto);
            }   // end of if----------    
       } catch (SQLException e) {
             e.printStackTrace();
       } finally { 
           close();   // 자원 반납
       } // end of try~catch~finally------------------- 
      return company;
   } // end of private void company_info(Company_DTO company)




// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	

	// ◆◆◆ === 채용정보 메뉴 === ◆◆◆ //
	public void recruit_info(Scanner sc, Company_DTO company) {
		String c_Choice = "";
	      
	    do {
		     System.out.println("\n>>> ---- 채용 정보 메뉴 ---- <<<\n"
		                   + "1. 채용공고 등록\n"
		                   + "2. 채용공고 조회\n"
		                   + "3. 채용공고 수정\n"
		                   + "4. 채용공고 삭제\n"
		                   + "5. 이전 메뉴로 돌아가기" );
		     
		     System.out.print("▶ 메뉴번호 선택 : ");
		     c_Choice = sc.nextLine();
		      
	         switch (c_Choice) {
				case "1": 	// 채용공고 등록
					recruit_register(sc, company);
				   	break;
				case "2": 	// 채용공고 조회
					recruit_information(sc, company);
				   	break;
				case "3":	// 채용공고 수정
					recruit_fix(sc, company);
					break;
				case "4":	// 채용공고 삭제
					recruit_delete(sc, company);
					break;
				case "5": 	// 이전메뉴로 되돌아가기
				   break;   
				default:
				   System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
				   break;
	         } // end of switch (c_Choice)-----------------
	     } while (!"5".equalsIgnoreCase(c_Choice));	// end of do~while------------------	
	}	// end of public void recruit_info(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)-----------



// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆	
	
	
	
	// ◆◆◆ === 채용공고 등록 === ◆◆◆ //
	Recruit_INFO_DTO ridto_register = new Recruit_INFO_DTO();
	public void recruit_register(Scanner sc, Company_DTO company) {
		
		Map<String, String> paraMap = new HashMap<>();	// 몇개의 변수이던간에 하나의 변수에 담아서 처리하려면?? MAP
		
		System.out.println("\n >> " + company.getCompany_name() + "채용공고 등록 <<");
		
		paraMap.put("fk_company_id", company.getCompany_id());
		
		// 담당자명
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.print("\n▶ 담당자명 : ");
			String manager_name = sc.nextLine();
			
			if("q".equalsIgnoreCase(manager_name)) {
	            return;
	         }
			if(Set_util.Check_name(manager_name)) {
				paraMap.put("manager_name", manager_name); 
				break;
			}
		}while(true);	// end of do~while-------------
		
		// 담당자 이메일
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.print("\n▶ 담당자 이메일 : ");
			String manager_email = sc.nextLine();
			
			if("q".equalsIgnoreCase(manager_email)) {
	            return;
	         }
			if(Set_util.Check_email(manager_email)) {
				paraMap.put("manager_email", manager_email);
				break;
			}
		}while(true);	// end of do~while---------------
		
		// 채용공고명
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.print("\n▶ 채용공고명 [ 20자 이내 ] : ");
			String recruit_title = sc.nextLine();
			if("q".equalsIgnoreCase(recruit_title)) {
		            return;
			}
			if(!(recruit_title == null || recruit_title.isBlank())) {
				if(recruit_title.length() <= 40) {
					paraMap.put("recruit_title", recruit_title);
					break;
				}
				else {
					System.out.println(">>> [경고] 채용공고명은 20자 이하로 작성해주세요. <<<");
				}
			}
			else {
				System.out.println(">>> [경고] 채용공고명을 반드시 입력해주세요. <<<");
			}
		}while(true);	// end of do~while------------
		
		// 채용인원수
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.println("\n[ 제한이 없으실 경우 엔터를 입력해주세요. ]");
			System.out.print("▶ 채용인원수 : ");
			String recruit_people = sc.nextLine();
			if("q".equalsIgnoreCase(recruit_people)) {
		            return;
		    }
			if(recruit_people == null || recruit_people.isBlank()) {
				break;
			}
			else {
				try {
					if(Integer.parseInt(recruit_people) > 0) {
						if(Set_util.Check_recruit_num(recruit_people)) {
							paraMap.put("recruit_people", "0");
							break;
						}
						else {
							System.out.println(">>> [경고] 숫자 3자리 이내로 입력해주세요. <<<");
						}
					}	// end of if(0명 이상인 경우)
				}catch(NumberFormatException e) {
					System.out.println(">>> [경고] 반드시 숫자를 입력해주세요. <<<");
				}	// end of try~catch------------------------
			}
		}while(true);
		
		// 마감일
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.println("\n[ 제한이 없으실 경우 엔터를 입력해주세요. ]");
			System.out.print("▶ 마감일 : ");
			String deadline = sc.nextLine();
	        if(!(deadline == null || deadline.isBlank())) {
	          if(Set_util.Check_date(deadline)) {
	             paraMap.put("recruit_deadline", deadline);
	             break;
	          }
			}
			else {
				paraMap.put("recruit_deadline", "채용마감시까지");
				break;
			}
		}while(true);
		
        // 신입/경력 여부
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.print("\n▶ 신입/경력 여부[신입/경력/무관] : ");
			String career = sc.nextLine();
			if("q".equalsIgnoreCase(career)) {
	            return;
	        }
			if("신입".equals(career) || "경력".equals(career) || "무관".equals(career)) {
				paraMap.put("career", career);
				break;
			}
			else {
				System.out.println("[경고] 신입 / 경력 / 무관 중 선택해주세요. <<<");
			}
		}while(true);
		
		// 연봉/월급
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.println("\n[ 협의일 경우 엔터를 입력해주세요. ]");
			System.out.print("▶ 연봉/월급을 선택해주세요 : ");
			String choice = sc.nextLine();
			
			if("q".equalsIgnoreCase(choice)) {
	            return;
	         }
			if(!(choice == null || choice.isBlank())) {
				if("연봉".equals(choice)) {
					System.out.println("\n▶ 연봉을 만원단위로 입력해주세요.[예 : 2000] (이전화면 : 엔터)");
					String year_salary = sc.nextLine();
					if(year_salary != null) {
						if(Set_util.Check_year_salary(year_salary)) {
							paraMap.put("year_salary", "연 " + year_salary + "만원");
							break;
						}
					}	// end of if(연봉값을 넣은 경우)----------
				}	// 연봉을 선택한 경우
				else if("월급".equals(choice)) {
					System.out.println("\n▶ 월급을 만원단위로 입력해주세요.[예 : 50] (이전화면 : 엔터)");
					String month_salary = sc.nextLine();
					if(month_salary != null) {
						if(Set_util.Check_year_salary(month_salary)) {
							paraMap.put("year_salary", "월" + month_salary + "만원");
							break;
						}
					}	// end of if(월급값을 넣은 경우)--------------
				}
				else {
					System.out.println(">>> [경고] 연봉이나 월급만 입력해주시고, 협의시 엔터를 입력해주세요. <<<");
				}	// end of if~else(값을 입력한 경우)------------
			}
			else {
				paraMap.put("year_salary", "연봉/월급 협의");
				break;
			}	// end of if~else---------------------
		}while(true);
		
		
		// 채용공고 내용
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.print("\n▶ 채용공고 내용 [ 2000자 이내 ] : ");
			String recruit_content = sc.nextLine();
			
			if("q".equalsIgnoreCase(recruit_content)) {
	            return;
         	}
			if(recruit_content != null) {
				if(recruit_content.length() <= 2000) {
					paraMap.put("recruit_content", recruit_content);
					break;
				}
				else {
					System.out.println(">>> [경고] 채용공고 내용은 2000자 이하로 작성해주세요. <<<");
				}
			}
		}while(true);
		
		// 채용분야(사무직, 영업직, 생산직)
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.print("\n▶ 채용분야 [사무직/영업직/생산직] : ");
			String recruit_field = sc.nextLine();
			
			if("q".equalsIgnoreCase(recruit_field)) {
            	return;
			}
			 
			if(recruit_field != null && "사무직".equals(recruit_field) 
			|| recruit_field != null && "영업직".equals(recruit_field)
			|| recruit_field != null && "생산직".equals(recruit_field)) {
				paraMap.put("recruit_field", recruit_field);
				break;
			}
			else {
				System.out.println(">>>[경고] 사무직, 영업직, 생산직 중에 골라주세요. <<<");
			}
		}while(true);
		
		// 고용형태(Ex.정규직,계약직)
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.println("\n1. 정규직	2. 계약직	3. 인턴	4. 파견직	5. 프리랜서\n"
							+ "6. 아르바이트	7. 연수생	8. 위촉직	9. 개인사업자");
			System.out.print("▶ 채용분야 [1~9 번호를 입력해주세요.] : ");
			String fk_hiretype_code = sc.nextLine();
			 
			if("q".equalsIgnoreCase(fk_hiretype_code)) {
	            return;
	         }

			if(fk_hiretype_code != null) {
				Pattern p = Pattern.compile("^[1-9]$");
				
				Matcher m = p.matcher(fk_hiretype_code);	// 패턴과 일치하는지 확인
				
				if(m.matches()) { 	// 확인 정보가 참이라면 
					paraMap.put("fk_hiretype_code", fk_hiretype_code);
					break;
				}
				else {
					System.out.println(">>> [경고] 1~9에 해당하는 값 하나를 입력해주세요. <<<");
				}
			}
			else {
				System.out.println(">>> [경고] 고용형태를 반드시 입력해주세요. <<<");
			}
		}while(true);
		
		// 근무요일
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.print("\n▶ 근무요일 : ");
			String work_day = sc.nextLine();
			if("q".equalsIgnoreCase(work_day)) {
	            return;
	        }
			if(work_day != null) {
				paraMap.put("work_day", work_day);
				break;
			}
		}while(true);
		
		
		// 근무시간
		do {
			System.out.println(">> 작성을 중단하시려면 Q를 누르세요. <<");
			System.out.print("\n▶ 근무시간 : ");
			String work_time = sc.nextLine();
			 
			if("q".equalsIgnoreCase(work_time)) {
		            return;
			}

			if(work_time != null) {
				paraMap.put("work_time", work_time);
				break;
			}
		}while(true);
		
		int n = rdao.recruit_write(paraMap, company);
        
        if (n==1) {
           
           System.out.println(">> 채용공고 등록 완료!! <<\n");
           System.out.println(company.getCompany_name() + "기업의" + " 채용공고등록일 : " + company.getRecruit().getRecruit_registerday());
        } 
        else {
           System.out.println(">> 채용공고 등록 실패!! <<");
        }
	}	// end of public void recruit_register(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)-------



// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆		
		
		

	
	// ◆◆◆ === 채용공고 조회 === ◆◆◆ //
	public void recruit_information(Scanner sc, Company_DTO company) {
		
		 List<Recruit_INFO_DTO> countlist = rdao.apply_count(company);
	      
	      StringBuilder sb = new StringBuilder();
	         
	      if(countlist.size() > 0) {
	            System.out.println("\n" + "-".repeat(30) + company.getCompany_name() + " 님의 [현재 진행중인 채용공고] " + "-".repeat(30));
	            System.out.println("채용공고번호\t     채용공고명\t\t채용공고등록일\t\t채용마감일");
	            System.out.println("-".repeat(85));
	            
	            int cnt = rdao.get_recruitlist_count(company.getCompany_id());
	            
	            sb = new StringBuilder();
	           
	           
	              
	            for(Recruit_INFO_DTO recruit : countlist) {
	               sb.append(recruit.getRecruit_no() + "\t" +
	                        "   " + recruit.getRecruit_title() + "\t\t" +
	                        recruit.getRecruit_registerday() + "\t\t" +
	                        recruit.getRecruit_deadline() + "\t" + "\n");
	                    
	         } // end of for(Recruit_INFO_DTO recruit_info : recruitList)
	         System.out.println(sb.toString() + "\n");
	         
	         System.out.println(">> 현재 " + company.getCompany_name() + "기업의 채용공고 건수 : " + cnt + "건 입니다");
	         
	         String input_rcno = "";
	         
	         do {
	            
	            System.out.print("[이전메뉴로 돌아가시려면 Q키를 누르세요] \n"
	                  + ">> 상세보기 공고 번호 : ");
	            input_rcno = sc.nextLine();
	            

	              if("Q".equalsIgnoreCase(input_rcno)) {
	                  System.out.println(">> 조회를 취소하고 이전메뉴로 돌아갑니다!"); 
	                  
	                  return;
	              }
	              else if (input_rcno.isBlank()) {
	                  System.out.println(">> 채용공고를 조회하려면 반드시 입력해야 합니다!");
	                  continue;
	              }else {
	                  boolean found = false;
	                  for(Recruit_INFO_DTO rcinfo : countlist) {
	                      if(input_rcno.equals(rcinfo.getRecruit_no())) {
	                       
	                          found = true;
	                         
	                          break; 
	                      }
	                  }
	                  if(!found) {
	                      System.out.println(">> 존재하지 않는 채용공고 번호입니다.");
	                      
	                      continue;
	                  }
	                  
	                  break;
	              }
	            
	         } while (true);   
	         
	         
	         System.out.println("\n" + "-".repeat(10) + company.getCompany_name() + " 님의 [현재 진행중인 채용공고] " + "-".repeat(10));
	          
	         List<Recruit_INFO_DTO> recruit_onelinfo = rdao.one_recruit(input_rcno, company);
	          
	          System.out.println("\n>> 채용공고 상세내역 보기 <<");
	          for(Recruit_INFO_DTO recruit : recruit_onelinfo) {
	              
	                System.out.println("공고명 : " + recruit.getRecruit_title() + "\n"
	                             + "채용분야 : " + recruit.getRecruit_field() + "\n"
	                             + "공고내용 : " + recruit.getRecruit_content() + "\n"
	                             + "연봉 : " + recruit.getYear_salary() + "\n"
	                             + "채용인원 : " + recruit.getRecruit_people() + "\n"
	                             + "근무요일 : " + recruit.getWork_day() + "\n"
	                             + "근무시간 : " + recruit.getWork_time() + "\n"
	                             + "담당자이메일 : " + recruit.getManager_email() + "\n"
	                             + "담당자명: " + recruit.getManager_name() + "\n" ) ;
	                       
	            } // end of for(Recruit_INFO_DTO recruit_info : recruitList)
	            
	         
	            System.out.println("-".repeat(45));
	            
	            List<String> apply_list = rdao.apply_user_search(input_rcno, company);
	            
	             if(apply_list.size() == 0 || apply_list == null) {
	                  
	                System.out.println(">> 현재 채용공고에 지원한 구직자는 없습니다. <<");
	                
	                System.out.println(">> 이전메뉴로 돌아갑니다. <<");
	                
	                return;
	                  
	             } // end of if
	               else {
	                  
	                  System.out.println("-".repeat(45));
	                  System.out.println("이력서번호 구직자성명   \t지원일자 ");
	                  System.out.println("-".repeat(45));
	                  
	                  for (String list : apply_list ) {
	                     
	                     System.out.print(list);
	                     
	                  } // end of for
	                  
	               } // end of else
	         
	         
	      } // end of if (recruitList.size() > 0)
	      else {
	         System.out.println(">>> 현재 진행중인 채용공고가 존재하지 않습니다. <<<\n");
	         
	      }
	      
	      String input_rcno = "";
	         do { 
	            
	            System.out.print("\n[이전메뉴로 돌아가시려면 Q키를 누르세요] \n"
	                         + ">> 지원한 구직자의 이력서를 보려면 이력서번호를 입력하세요 : ");
	              input_rcno = sc.nextLine();
	             
	              if("q".equalsIgnoreCase(input_rcno)) {
	                  System.out.println(">> 조회를 취소하고 이전메뉴로 돌아갑니다!"); 
	                  
	                  return;
	              } 
	              try {
	            
	              int fk_paper_code = Integer.parseInt(input_rcno);

	              Map<String, String> check_papercode = rdao.chk_papercode(fk_paper_code);

	              if(check_papercode == null) {
	                  System.out.println(">> 해당 이력서 번호의 정보를 찾을 수 없습니다.");
	                  continue;
	              }

	              if(input_rcno.isBlank()) {
	                  System.out.println(">> 이력서를 조회하려면 반드시 입력해야 합니다!");
	                  continue;
	              }

	              if(input_rcno.equals(check_papercode.get("fk_paper_code"))) {
	                  break;
	                  
	              } else {
	                 
	                  System.out.println(">> 존재하지 않는 이력서 번호입니다.");
	              }
	             
	            } catch (NumberFormatException e) {
	               
	               System.out.println("[경고] 이력서 번호는 정수로만 입력해야 합니다!");
	            }
	         } while (true);   
	         
	         Map<String,String> paper = rdao.paper_one(input_rcno, company);
	         
	         String yn = "";
	            
	         
	         if(paper!=null) {
	            
	        	 System.out.println ( "\n ===== " + paper.get("user_name") + " 님의 이력서 =====  \n" 
	                     + "▶ 지원한 채용공고 번호 : " + paper.get("recruit_no") + "\n"
	                     + "▶ 이력서 번호 : " + paper.get("paper_code") + "\n"
	                     + "▶ 이력서 제목 : " + paper.get("paper_name") + "\n"
	                     + "▶ 지원자 성명 : " + paper.get("user_name") + "\n"
	                     + "▶ 지원자 나이 : " + paper.get("age") + "\n"
	                     + "▶ 지원자 성별 : " + paper.get("gender") + "\n"
	                     + "▶ 지원자 연락처 : " + paper.get("user_tel") + "\n"
	                     + "▶ 지원자 주소 : " + paper.get("user_address") + "\n"
	                     + "▶ 지원자 이메일 : " + paper.get("user_email") + "\n"
	                     + "▶ 지원자 학력 : " + paper.get("academy_name") + "\n"
	                     + "▶ 취업우대사항 : " + paper.get("priority_name") + "\n"
	                     + "▶ 희망근무지역 : " + paper.get("hope_city") + "\n"
	                     + "▶ 신입/경력여부 : " + paper.get("career") + "\n"
	                     + "▶ 자격증명 : " + paper.get("license_name") + "\n"
	                     + "▶ 자격증 취득일자 : " + paper.get("license_day") + "\n"
	                     + "▶ 자격증 취득기관 : " + paper.get("license_company") + "\n"
	                     + "▶ 희망연봉 : " + paper.get("hope_money") + "\n"
	                     + "▶ 지원동기 : " + paper.get("apply_motive") + "\n");
	            
	            do {
	               boolean chk = rdao.apply_close(paper);
	               String apply_after = rdao.apply_chk(paper);
	               if(!chk) {
	                  
	                  System.out.println(">> 이미 " + paper.get("user_name") + "님의 이력서를 " + apply_after + " 처리 했습니다.");
	                  
	                  return;
	               }
	               else {
	                  
	                  System.out.print("\n (채용을 보류하려면 Q를 누르세요) \n"
	                        + ">> 위 지원자를 (합격)채용하시겠습니까 ? [Y/N] : ");
	                  yn = sc.nextLine();
	                  
	                  if("y".equalsIgnoreCase(yn)) {
	                     
	                     System.out.println(" >> 채용을 축하드립니다 ! << ");
	                     
	                     rdao.apply_yesorno(yn,paper);
	                     
	                     return;
	                     
	                  }
	                  else if("n".equalsIgnoreCase(yn)) {
	                     
	                     System.out.println(" >> 불합격 처리 되었습니다 ! << ");
	                     rdao.apply_yesorno(yn,paper);
	                     return;
	                     
	                  }else if ("q".equalsIgnoreCase(yn)) {
	                     
	                     System.out.println(" >> 채용을 보류하고 이전메뉴로 돌아갑니다 !");
	                     
	                     return;
	                  }
	                  else { 
	                     
	                     System.out.println(" >> Y or N 만 입력 가능합니다! << ");
	                     
	                  }
	               }
	            } while (true);
	            
	         } else {
	            
	            System.out.println("TT");
	            
	         }

	}	// end of public void recruit_information(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------



// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
	
	// ◆◆◆ === 채용공고 수정 === ◆◆◆ //
	public void recruit_fix(Scanner sc, Company_DTO company) {
		List<Recruit_INFO_DTO> rclist = rdao.get_recruitlist(company);
	      
	      StringBuilder sb = new StringBuilder();
	        
	        if(rclist.size() > 0) {
	          
	           System.out.println("-".repeat(80));
	           System.out.println("채용공고번호  \t채용공고명  채용분야 채용등록일 채용마감일 신입/경력여부 채용담당자이름 채용담당자이메일 ");
	           System.out.println("-".repeat(80));
	          
	           sb = new StringBuilder();
	          
	           for(Recruit_INFO_DTO rcinfo : rclist) {
	              sb.append(rcinfo.getRecruit_no() + " " +
	                    rcinfo.getRecruit_title() + " " +
	                    rcinfo.getRecruit_field() + " " +
	                    rcinfo.getRecruit_registerday() + " " +
	                     rcinfo.getRecruit_deadline() + " " +
	                     rcinfo.getCareer() + " " +
	                    rcinfo.getManager_name() + " " +
	                     rcinfo.getManager_email() + "\n");
	           } // end of for-----------------------
	        }	// end of if---------------------
	        
	        String input_rcno = "";
	        do {
	         System.out.println(sb.toString() + "\n");  
	         
	         System.out.print(">> 수정할 채용공고번호를 입력하세요 [수정을 취소하려면 Q키를 누르세요] : ");
	         input_rcno = sc.nextLine();
	         
	         boolean chk = rdao.chk_recruit(company , input_rcno);
	         
	         if("Q".equalsIgnoreCase(input_rcno)) {
	            System.out.println(">> 수정을 취소하고 이전메뉴로 돌아갑니다!"); 
	         }
	         else if (input_rcno.isBlank() || !chk ) {
	            System.out.println(">> 올바른 채용공고번호를 입력하세요!");
	            continue;
	         }
	         else if(chk) {
	            
	              do {
	                 System.out.println("\n>>> ---- " + company.getCompany_name() + " 기업의 채용공고 수정 메뉴 ---- <<<\n"
	                            + "1. 채용공고명 수정\t 2.채용마감일 수정\t 3.채용담당자 수정\t 4.채용담당자 이메일 수정\n"
	                            + "5. 채용공고내용 수정\t 6.채용 신입/경력 수정\t 7.채용공고 연봉 수정\t 8.채용인원 수정\n"
	                            + "9. 채용분야 수정\t 10.채용공고 근무요일 수정\t 11.채용공고 근무시간 수정\n"
	                            + "12. 채용공고 전체수정\t 13.이전메뉴로 돌아가기\n");
	                 
	                 System.out.print("▶ 원하시는 메뉴번호를 입력하세요 : ");
	                 String fixno = sc.nextLine(); // switch 메뉴번호
	                 
	                 String infoview = ""; // select 될 컬럼명 세팅용 변수
	                 String result_value = ""; // sql문 결과물 담을 변수
	                 String fix_info = ""; // 수정할내용이 담기는 변수
	                 
	                 int n; // update 결과가 담기는 변수
	          
	                 switch (fixno) {
	                 
	                 	case "1": // 채용공고명만 수정
	                 		infoview = "recruit_title";
	                  
	                 		result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
	                 		System.out.println("▶ 수정전 채용공고명 : " + result_value);
	                  
	                 		System.out.print("▶ 수정할 채용공고명을 입력하세요 [원치않으면 엔터를 치세요] : ");
	                 		fix_info = sc.nextLine();
	                  
	                 		if ( fix_info.isBlank() ) { 
	                 			System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
	                 		}
	                 		else if ( fix_info != null ){
	                 			n = rdao.recruit_fixone(infoview , input_rcno , fix_info);
	                     
	                 			if (n==1) {
	                 				System.out.println(">> 채용공고명 수정 성공!! << ");
	                 			}
	                 			else {
	                 				System.out.println(">> 채용공고명 수정 실패!! << ");
	                 			}
	                 			break;
	                 		}
	                 		break;
		               case "2": // 채용마감일 수정
		                  infoview = "recruit_deadline";
		                  
		                  result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                  
		                  System.out.println("▶ 수정전 채용마감일 : " + result_value);
		                  
		                  System.out.print("▶ 수정할 채용마감일을 입력하세요 [원치않으면 엔터를 치세요] : ");
		                  fix_info = sc.nextLine();
		               
		                  if ( fix_info.isBlank() ) { 
		                     System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                  }
		                  else if ( fix_info != null ){
		                     n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
		                     
		                     if (n==1) {
		                        System.out.println(">> 채용마감일 수정 성공!! << ");
		                     }
		                     else {
		                        System.out.println(">> 채용마감일 수정 실패!! << ");
		                     }
		                     break;
		                  }                                   
		                  break;
		               case "3": // 채용담당자 수정
		            	   infoview = "manager_name";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 채용담당자 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 채용담당자을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
		            			   System.out.println(">> 채용담당자 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 채용담당자 수정 실패!! << ");
		            		   }
		            		   break;
		            	   }           
		            	   break;
		               case "4": // 채용담당자 이메일 수정
		            	   infoview = "manager_email";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 채용담당자 이메일 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 채용담당자 이메일을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
		            			   System.out.println(">> 채용담당자 이메일 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 채용담당자 이메일 수정 실패!! << ");
		            		   }
		            		   break;
		            	   }                          
		            	   break;
		               case "5": // 채용공고내용 수정
		            	   infoview = "recruit_content";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 채용공고내용 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 채용공고내용 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
		            			   System.out.println(">> 채용공고내용 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 채용공고내용 수정 실패!! << ");
		            		   }
		            		   break;
		            	   }                          
		            	   break;
		               case "6": // 채용 신입/경력 수정
		            	   infoview = "career";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 신입/경력 부분 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 신입/경력 부분을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
		            			   System.out.println(">> 신입/경력 부분 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 신입/경력 부분 수정 실패!! << ");
		            		   }
		            		   break;
		            	   }                        
		            	   break;
		               case "7": // 채용공고 연봉 수정
		            	   infoview = "year_salary";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 채용공고 연봉 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 채용공고연봉을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
		            			   System.out.println(">> 채용공고연봉 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 채용공고연봉 수정 실패!! << ");
		            		   }
		            		   break;
		            	   }                     
		            	   break;
		               case "8": // 채용인원 수정
		            	   infoview = "recruit_people";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 채용인원 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 채용공고인원을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
		            			   System.out.println(">> 채용인원 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 채용인원 수정 실패!! << ");
		            		   }
		            		   break; 
		            	   }                         
		            	   break;
		               case "9": // 채용분야 수정
		            	   infoview = "recruit_field";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 채용분야 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 채용분야를 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
		            			   System.out.println(">> 채용분야 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 채용분야 수정 실패!! << ");
		            		   }
		            		   break;
		            	   }                         
		            	   break;
		               case "10": // 채용공고 근무요일 수정
		            	   infoview = "work_day";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 근무요일 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 근무요일을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone( infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
	                        System.out.println(">> 근무요일 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 근무요일 수정 실패!! << ");
		            		   }
		            		   break;
		            	   }                       
		            	   break;
		               case "11": // 채용공고 근무시간 수정
		            	   infoview = "work_time";
	                  
		            	   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                  
		            	   System.out.println("▶ 수정전 근무시간 : " + result_value);
	                  
		            	   System.out.print("▶ 수정할 근무시간을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            	   fix_info = sc.nextLine();
	                  
		            	   if ( fix_info.isBlank() ) { 
		            		   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            	   }
		            	   else if ( fix_info != null ){
	                     
		            		   n = rdao.recruit_fixone(infoview , input_rcno , fix_info);
	                     
		            		   if (n==1) {
		            			   System.out.println(">> 근무시간 수정 성공!! << ");
		            		   }
		            		   else {
		            			   System.out.println(">> 근무시간 수정 실패!! << ");
		            		   }
		            		   break;
		            	   }                       
		            	   break;
		               case "12":
		            	   Map<String,String> rc_allfixmap = new HashMap<>();
		            	   int ct = 0;
		            	   do {
	                     
		            		   infoview = "recruit_title";
		            		   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                     
		            		   System.out.println("▶ 수정전 채용공고명 : " + result_value);
		            		   System.out.print("▶ 수정할 채용공고명을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            		   String fix_recruit_title = sc.nextLine();
	                     
		            		   if ( fix_recruit_title.isBlank() ) { 
		            			   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            			   rc_allfixmap.put("recruit_title", result_value);
		            			   ct++;
		            			   break;
		            		   }
		            		   else if ( fix_info != null ){
		            			   rc_allfixmap.put("recruit_title", fix_recruit_title);
		            			   break;
		            		   }
		            		   else {
		            			   System.out.println(">> 채용공고명 수정 실패!! << ");
		            		   }
		            	   } while (true);   
	                     ////////////////////////////////////////////////////////////////////////////////////
	                  
		            	   do {
	                  
		            		   infoview = "recruit_deadline";
		            		   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
	                     
		            		   System.out.println("▶ 수정전 채용마감일 : " + result_value);
		            		   System.out.print("▶ 수정할 채용마감일을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            		   String fix_recruit_deadline = sc.nextLine();
		                     
		            		   if ( fix_recruit_deadline.isBlank() ) { 
		            			   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            			   rc_allfixmap.put("recruit_deadline", result_value);
		            			   ct++;
		            			   break;
		            		   }
		            		   else if ( fix_info != null ){
		            			   rc_allfixmap.put("recruit_deadline", fix_recruit_deadline);
		            			   break;
		            		   }
		            		   else {
		                        System.out.println(">> 채용마감일 수정 실패!! << ");
		            		   }
		            	   } while (true);   
		                     ////////////////////////////////////////////////////////////////////////////////////
		            	   do {
		            		   infoview = "manager_name";
		            		   result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		            		   System.out.println("▶ 수정전 채용담당자 : " + result_value);
		            		   System.out.print("▶ 수정할 채용담당자을 입력하세요 [원치않으면 엔터를 치세요] : ");
		            		   String fix_manager_name = sc.nextLine();
		                     
		            		   if ( fix_manager_name.isBlank() ) { 
		            			   System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		            			   rc_allfixmap.put("manager_name", result_value);
		            			   ct++;
		            			   break;
		            		   }
		            		   else if ( fix_info != null ){
		            			   rc_allfixmap.put("manager_name", fix_manager_name);
		            			   break;
		            		   }
		            		   else {
		            			   System.out.println(">> 채용공고담당자 수정 실패!! << ");
		            		   }
		                  } while (true);   
		                     ////////////////////////////////////////////////////////////////////////////////////
		                  do {
		                	  infoview = "manager_email";
		                	  result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		                	  System.out.println("▶ 수정전 채용담당자 이메일 : " + result_value);
		                	  System.out.print("▶ 수정할 채용담당자 이메일을 입력하세요 [원치않으면 엔터를 치세요] : ");
		                	  String fix_manager_email = sc.nextLine();
	
		                	  if ( fix_manager_email.isBlank() ) { 
		                        System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                        rc_allfixmap.put("manager_email", result_value);
		                        ct++;
		                        break;
		                     }
		                     else if ( fix_info != null ){
		                        rc_allfixmap.put("manager_email", fix_manager_email);
		                        break;
		                     }
		                     else {
		                        System.out.println(">> 채용담당자 이메일 수정 실패!! << ");
		                     }
		                  } while (true);   
		                     ////////////////////////////////////////////////////////////////////////////////////
		                  do {
		                     infoview = "recruit_content";
		                     result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		                     System.out.println("▶ 수정전 채용공고내용 : " + result_value);
		                     System.out.print("▶ 수정할 채용공고내용 입력하세요 [원치않으면 엔터를 치세요] : ");
		                     String fix_recruit_content = sc.nextLine();
		                     
		                     if ( fix_recruit_content.isBlank() ) { 
		                        System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                        rc_allfixmap.put("recruit_content", result_value);
		                        ct++;
		                        break;
		                     }
		                     else if ( fix_info != null ){
		                        rc_allfixmap.put("recruit_content", fix_recruit_content);
		                        break;
		                     }
		                     else {
		                        System.out.println(">> 채용공고내용 수정 실패!! << ");
		                     }
		                  } while (true);   
		                     ////////////////////////////////////////////////////////////////////////////////////
		                  do {
		                     infoview = "career";
		                     result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		                     System.out.println("▶ 수정전 신입/경력 부분 : " + result_value);
		                     System.out.print("▶ 수정할 신입/경력 부분을 입력하세요 [원치않으면 엔터를 치세요] : ");
		                     String fix_career = sc.nextLine();
		                     
		                     if ( fix_career.isBlank() ) { 
		                        System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                        rc_allfixmap.put("career", result_value);
		                        ct++;
		                        break;
		                     }
		                     else if ( fix_info != null ){
		                        rc_allfixmap.put("career", fix_career);
		                        break;
		                     }
		                     else {
		                        System.out.println(">> 신입/경력 부분 수정 실패!! << ");
		                     }
		                  } while (true);
		                     ////////////////////////////////////////////////////////////////////////////////////
		                  do {
		                     infoview = "year_salary";
		                     result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		                     System.out.println("▶ 수정전 채용공고 연봉 : " + result_value);
		                     System.out.print("▶ 수정할 채용공고연봉을 입력하세요 [원치않으면 엔터를 치세요] : ");
		                     String fix_year_salary = sc.nextLine();
	
		                     if ( fix_year_salary.isBlank() ) { 
		                        System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                        rc_allfixmap.put("year_salary", result_value);
		                        ct++;
		                        break;
		                     }
		                     else if ( fix_info != null ){
		                        rc_allfixmap.put("year_salary", fix_year_salary);
		                        break;
		                     }
		                     else {
		                        System.out.println(">> 채용공고 연봉 수정 실패!! << ");
		                     }
		                  } while (true);   
		                     ////////////////////////////////////////////////////////////////////////////////////
		                  do {
		                     infoview = "recruit_people";
		                     result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		                     System.out.println("▶ 수정전 채용인원 : " + result_value);
		                     System.out.print("▶ 수정할 채용공고인원을 입력하세요 [원치않으면 엔터를 치세요] : ");
		                     String fix_recruit_people = sc.nextLine();
		                     
		                     if ( fix_recruit_people.isBlank() ) { 
		                        System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                        rc_allfixmap.put("recruit_people", result_value);
		                        ct++;
		                        break;
		                     }
		                     else if ( fix_info != null ){
		                        rc_allfixmap.put("recruit_people", fix_recruit_people);
		                        break;
		                     }
		                     else {
		                        System.out.println(">> 채용인원 수정 실패!! << ");
		                     }
		                  } while (true);                  
		                     ////////////////////////////////////////////////////////////////////////////////////
		                  do {
		                     infoview = "recruit_field";
		                     result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		                     System.out.println("▶ 수정전 채용분야 : " + result_value);
		                     System.out.print("▶ 수정할 채용분야를 입력하세요 [원치않으면 엔터를 치세요] : ");
		                     String fix_recruit_field = sc.nextLine();
		                     
		                     if ( fix_recruit_field.isBlank() ) { 
		                        System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                        rc_allfixmap.put("recruit_field", result_value);
		                        ct++;
		                        break;
		                     }
		                     else if ( fix_info != null ){
		                        rc_allfixmap.put("recruit_field", fix_recruit_field);
		                        break;
		                     }
		                     else {
		                        System.out.println(">> 채용분야 수정 실패!! << ");
		                     }
		                  } while (true);
		                     ////////////////////////////////////////////////////////////////////////////////////
		                  do {
		                     infoview = "work_day";
		                     result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		                     System.out.println("▶ 수정전 근무요일 : " + result_value);
		                     System.out.print("▶ 수정할 근무요일을 입력하세요 [원치않으면 엔터를 치세요] : ");
		                     String fix_work_day = sc.nextLine();
		                     
		                     if ( fix_work_day.isBlank() ) { 
		                        System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                        rc_allfixmap.put("work_day", result_value);
		                        ct++;
		                        break;
		                     }
		                     else if ( fix_info != null ){
		                        rc_allfixmap.put("work_day", fix_work_day);
		                        break;
		                     }
		                     else {
		                        System.out.println(">> 근무요일 수정 실패!! << ");
		                     }
		                  } while (true);
		                     ////////////////////////////////////////////////////////////////////////////////////
		                  do {
		                     infoview = "work_time";
		                     result_value = rdao.recruit_oneview(company.getCompany_id(), infoview , input_rcno);
		                     
		                     System.out.println("▶ 수정전 근무시간 : " + result_value);
		                     System.out.print("▶ 수정할 근무시간을 입력하세요 [원치않으면 엔터를 치세요] : ");
		                     String fix_work_time = sc.nextLine();
		                     
		                     if ( fix_work_time.isBlank() ) { 
		                        System.out.println(">> 기존 내용으로 유지 됩니다. <<\n");
		                        rc_allfixmap.put("work_time", result_value);
		                        ct++;
		                        break;
		                     }
		                     else if ( fix_info != null ){
		                        rc_allfixmap.put("work_time", fix_work_time);
		                        break;
		                     }
		                     else {
		                        System.out.println(">> 근무시간 수정 실패!! << ");
		                     }
		                  } while (true);
		                  ////////////////////////////////////////////////////////////////////////////////////
		                  if(ct==11) {
		                	  
		                	  System.out.println(">> 전부 변경을 하지않으셨기에 자동으로 이전메뉴로 되돌아갑니다 <<");
		                	  
		                	  return;
		                  }
		                  do {
		                     
		                     System.out.print(">> 정말 수정하시겠습니까? [Y/N] :");
		                     
		                     String yn = sc.nextLine();
		                     
		                     if("y".equalsIgnoreCase(yn) ) {
		                           
		                        n =   rdao.fix_recruit_all(rc_allfixmap, input_rcno );
		                        
		                        if(n == 1) {
		                           System.out.println(">> 채용공고 수정 성공!! << \n");
		                           return;
		                        }
		                        else {
		                           System.out.println(">> SQL 구문 오류 발생으로 인해 채용공고 삭제가 실패되었습니다. << \n");
		                           break;
		                        }   
		                     } // end yn if
		                     else if ("n".equalsIgnoreCase(yn) ) {
		                        System.out.println(">> 채용공고 수정을 취소하셨습니다. << \n");
		                        return;
		                     } // else if
		                     else {
		                        System.out.println(">> [경고] Y 또는 N 만 입력하세요.!!");
		                     }
		                  } while (true);
		                  break;
	               case "13":
	                  System.out.println(">> 이전 메뉴로 돌아갑니다 << \n");
	                  return;   
	               default:
	                  System.out.println("[경고] 메뉴에 존재하는 번호만 입력하세요!");
	               }	// end of switch---------------------
	             } while (true);
	         } 
	      } while (!"Q".equalsIgnoreCase(input_rcno));
	}	// end of public void recruit_fix(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------



// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	

	// ◆◆◆ === 채용공고 삭제 === ◆◆◆ //
	public void recruit_delete(Scanner sc, Company_DTO company) {
	      
		List<Recruit_INFO_DTO> rclist = rdao.get_recruitlist(company);
	      
	    StringBuilder sb = new StringBuilder();
	        
	    if(rclist.size() > 0) {
	          
	       System.out.println("-".repeat(50));
	       System.out.println("채용공고번호  채용공고명  채용분야 채용등록일 채용마감일 신입/경력여부 채용담당자이름 채용담당자이메일 ");
	       System.out.println("-".repeat(50));
	          
	       sb = new StringBuilder();
	        
	       for(Recruit_INFO_DTO rcinfo : rclist) {
	           sb.append(rcinfo.getRecruit_no() + " " +
	                     rcinfo.getRecruit_title() + " " +
	                     rcinfo.getRecruit_field() + " " +
	                     rcinfo.getRecruit_registerday() + " " +
	                     rcinfo.getRecruit_deadline() + " " +
	                     rcinfo.getCareer() + " " +
	                     rcinfo.getManager_name() + " " +
	                     rcinfo.getManager_email() + "\n");
	       } // end of for
	           System.out.println(sb.toString() );  
	   }
	   else {  
	       System.out.println(">> 존재하는 채용공고가 없습니다!! <<");   
	   }
	        
	   int cnt = rdao.get_recruitlist_count(company.getCompany_id());
	      
	   System.out.println(">> 현재 " + company.getCompany_name() + "기업의 채용공고 건수 : " + cnt + "건 입니다.");
	      
	   String input_rcno = "";
	   String yn = "";
	   do {
	       System.out.print(">> 삭제할 채용공고번호를 입력하세요 [삭제를 취소하려면 Q키를 누르세요] : ");
	       input_rcno = sc.nextLine();

	       if("q".equalsIgnoreCase(input_rcno)) {
	    	   System.out.println(">> 삭제를 취소하고 이전메뉴로 돌아갑니다!"); 
	           return;
	       } else if (input_rcno.isBlank()) {
	           System.out.println(">> 채용공고번호를 입력해야 합니다!");
	           continue;
	       } else {
	           boolean found = false;
	           for(Recruit_INFO_DTO rcinfo : rclist) {
	               	if(input_rcno.equals(rcinfo.getRecruit_no())) {
	               		found = true;
	                    break; 
	                }
	           }	// end of for--------------------
	           if(!found) {
	                System.out.println(">> 존재하지 않는 채용공고 번호입니다.");
	                continue;
	           }
	           break;
	       }   
	   } while (true);   
	                        
	   do {
           ////////////////////////////////////////////////////////////////////////////////////////
           System.out.print(">> 정말로 해당 채용공고를 삭제 하시겠습니까?[Y/N] : ");
           yn = sc.nextLine();
              
           if("y".equalsIgnoreCase(yn) ) {
                 int n =  rdao.delete_recruitlist(input_rcno);
                 if( n == 1 ) {
                	 System.out.println(">> 채용공고 삭제 성공!! << \n");
                	 return;
                 }
                 else {
                    System.out.println(">> SQL 구문 오류 발생으로 인해 채용공고 삭제가 실패되었습니다. << \n");
                    break; 
                 }
           } // end yn if
           else if ("n".equalsIgnoreCase(yn) ) {
               	System.out.println(">> 채용공고 삭제을 취소하셨습니다. << \n");
                break;
           } // else if
           else {
                System.out.println(">> [경고] Y 또는 N 만 입력하세요.!!");
           }
       } while (true);
	}   // end of public void recruit_delete(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------


	
// ◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆◆
	
	
		
	// ◆◆◆ === 구직자 정보 조회 === ◆◆◆ //
	public void user_info(Scanner sc, Company_DTO company) {
		
		User_DTO user = new User_DTO();
		System.out.println("\n>>> ---- 구직자 정보 ---- <<<\n");
	           
		List<User_DTO> memberList = cdao.All_user();
	           
    	StringBuilder sb = new StringBuilder();
	       
        if(memberList.size() > 0) {
	          
           System.out.println("-".repeat(60));
           System.out.println("성명\t이메일\t\t생년월일");
           System.out.println("-".repeat(60));
	          
           sb = new StringBuilder();
	          
           for(User_DTO member : memberList) {
              sb.append(member.getUser_name() + "\t" 
                    + member.getUser_email() + "\t" 
                    + member.getUser_security_num().substring(0, 6) + "\n");
           } // end of for------------------
           System.out.println(sb.toString() );  
        } else {
            System.out.println(">> 가입된 회원이 1명도 없습니다. ㅜㅜ");  
        }	// end of if~else------------------------------
		
	}	// end of public void user_info(Scanner sc, Company_DTO company)----------------

}
