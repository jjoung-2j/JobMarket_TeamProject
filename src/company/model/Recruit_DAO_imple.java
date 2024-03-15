package company.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import user.domain.User_DTO;

public class Recruit_DAO_imple implements Recruit_DAO {


	// ◆◆◆ === 지원한 구직자 조회 === ◆◆◆ //
	@Override
	public void apply_user_search(Scanner sc,  User_DTO user, Company_DTO company) {
		// TODO Auto-generated method stub
		
	}	// end of public void apply_user_search(Scanner sc, User_DTO user, Company_DTO company, Recruit_INFO_DTO recruit)------

	
	
	
	
	
	// ◆◆◆ === 채용공고 등록 === ◆◆◆ //
	Recruit_INFO_DTO ridto_register = new Recruit_INFO_DTO();
	@Override
	public void recruit_register(Scanner sc, Company_DTO company) {
		System.out.println("\n >> --- 채용공고등록 --- <<");
		
		System.out.print("▶ 담당자명 : ");
		String manager_name = sc.nextLine();
		
		System.out.print("▶ 담당자 이메일 : ");
		String manager_email = sc.nextLine();
		
		System.out.print("▶ 채용공고명 : ");
		String recruit_title = sc.nextLine();
		
		System.out.print("▶ 채용인원수 : ");
		String recruit_people = sc.nextLine();
		
		System.out.print("▶ 기업명 : " + company.getCompany_name());
		 
		System.out.print("▶ 등록일[자동으로 입력됩니다.]");
		
		System.out.print("▶ 마감일 : ");
		String deadline = sc.nextLine();
		
		System.out.print("▶ 신입/경력 여부[신입/경력/무관] : ");
		String career = sc.nextLine();
		
		System.out.print("▶ 연봉 : ");
		String year_salary = sc.nextLine();
		
		System.out.print("▶ 채용공고내용 : ");
		String recruit_content = sc.nextLine();
		
		System.out.print("▶ 채용분야 : ");
		String recruit_field = sc.nextLine();
		
		System.out.print("▶ 근무요일 : ");
		String work_day = sc.nextLine();
		
		System.out.print("▶ 근무시간 : ");
		String work_time = sc.nextLine();
		
		Map<String, String> paraMap = new HashMap<>();	// 몇개의 변수이던간에 하나의 변수에 담아서 처리하려면?? MAP
	    
		paraMap.put("manager_name", manager_name); 
	    paraMap.put("manager_email", manager_email);
	    paraMap.put("recruit_title", recruit_title);
	    paraMap.put("recruit_people", recruit_people);
	    paraMap.put("deadline", deadline);
	    paraMap.put("career", career);
	    paraMap.put("year_salary", year_salary);
	    paraMap.put("recruit_content", recruit_content);
	    paraMap.put("recruit_field", recruit_field);
	    paraMap.put("work_day", work_day);
	    paraMap.put("work_time", work_time);
	    
	    
	    ridto_register.getManager_name();
	    ridto_register.getManager_email();
	    ridto_register.getRecruit_title();
	    ridto_register.getRecruit_people();
	    ridto_register.getRecruit_deadline();
	    ridto_register.getCareer();
	    ridto_register.getYear_salary();
	    ridto_register.getRecruit_content();
	    ridto_register.getRecruit_field();
	    ridto_register.getWork_day();
	    ridto_register.getWork_time();

		return;// TODO Auto-generated method stub
	}	// end of public void recruit_register(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)-------


	
	
	

	// ◆◆◆ === 채용공고 조회 === ◆◆◆ //
	@Override
	public void recruit_information(Scanner sc, Company_DTO company) {
		String r_choice = "";
		
		do {
			System.out.println("\n>>> ---- 채용공고 조회 메뉴 ---- <<<\n"
						 	 + "1. 진행중인 채용공고\n"
						 	 + "2. 마감된 채용공고\n"
						 	 + "3. 이전 메뉴로 돌아가기");
			
			System.out.print("▶ 메뉴번호 선택 : ");
			r_choice = sc.nextLine();
			switch (r_choice) {
				case "1":	// 진행중인 채용공고
					// current_recruit_info(company); 진행중~~~~~
					break;
					
				case "2":	// 마감된 채용공고
								
					break;
				
				case "3":	// 이전 메뉴로 돌아가기
					
					break;
	
				default:
					System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!! <<<");
					break;
			} // end of switch (r_choice)
		} while(!"3".equals(r_choice));	// end of do~while---------------
	}	// end of public void recruit_information(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------



	
	
	
	// ◆◆◆ === 채용공고 수정 === ◆◆◆ //
	@Override
	public void recruit_fix(Scanner sc, Company_DTO company) {
		// TODO Auto-generated method stub
		
	}	// end of public void recruit_fix(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------




	
	

	// ◆◆◆ === 채용공고 삭제 === ◆◆◆ //
	@Override
	public void recruit_delete(Scanner sc, Company_DTO company) {
		// TODO Auto-generated method stub
		
	}	// end of public void recruit_delete(Scanner sc, Company_DTO company, Recruit_INFO_DTO recruit)------



	
	

}
