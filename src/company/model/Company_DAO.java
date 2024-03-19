package company.model;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import company.domain.Company_DTO;
import user.domain.User_DTO;


public interface Company_DAO {

	// ◆◆◆ === 기업 가입 === ◆◆◆ //
	int companyRegister(Company_DTO company, Map<String, String> paraMap);
	
	// ◆◆◆ === 기업회원 가입시 입력한 기업 아이디가 존재하는지 존재하지 않는지 알아오기 === ◆◆◆ //
	boolean is_exist_company_id(String company_id);

	// ◆◆◆ === 입력한 도시명이 테이블에 존재하는지 확인 === ◆◆◆ //
	boolean list_cityname_check(String local, String city);
	
	// ◆◆◆ === 기업 로그인 === ◆◆◆ //
	Company_DTO login(Map<String, String> paraMap);
		
	// ◆◆◆ === 기업 탈퇴 === ◆◆◆ //
	int Withdrawal(Scanner sc, Company_DTO company);
	
	// ◆◆◆ === 기업 추가정보 입력 === ◆◆◆ //
	int company_detail_input(Map<String, String> paraMap, String company_id);
	
	// ◆◆◆ === 모든 구직자 조회 === ◆◆◆ //
	List<User_DTO> All_user();

	// ◆◆◆ === 모든 기업 조회 (관리자 히든 메뉴) === ◆◆◆ //
	List<Company_DTO> All_company();

	// ◆◆◆ == 기업 탈퇴 처리 == ◆◆◆ //
	int remove();

	

}
