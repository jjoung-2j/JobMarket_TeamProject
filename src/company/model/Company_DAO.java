package company.model;

import java.util.Map;
import java.util.Scanner;
import company.domain.Company_DTO;


public interface Company_DAO {

	//◆◆◆ === company 가입 === ◆◆◆ //
	int companyRegister(Company_DTO company);
		
	//◆◆◆ === company 탈퇴 === ◆◆◆ //
	int Withdrawal(Scanner sc, Company_DTO company);
	
	//◆◆◆ === 기업 로그인 === ◆◆◆ //
	Company_DTO login(Map<String, String> paraMap);
	
	//◆◆◆ === 기업 정보 메뉴 === ◆◆◆ //
	void company_info(Scanner sc, Company_DTO company);
	
	// ◆◆◆ === 채용정보 메뉴 === ◆◆◆ //
	void recruit_info(Scanner sc, Company_DTO company);

	// ◆◆◆ === 구직자정보 메뉴 === ◆◆◆ //
	void user_info(Scanner sc, Company_DTO company);
	

}
