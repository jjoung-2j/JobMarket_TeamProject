package company.model;

import java.util.Map;
import java.util.Scanner;

import company.domain.Company_DTO;

public interface Company_DAO {

	//◆◆◆ === company 가입 === ◆◆◆ //
	int companyRegister(Company_DTO company);
		
	//◆◆◆ === company 탈퇴 === ◆◆◆ //
	int Withdrawal(Scanner sc, Company_DTO company);
	
	//◆◆◆ === company 로그인 === ◆◆◆ //
	Company_DTO login(Map<String, String> paraMap);
	
	//◆◆◆ === company 정보메뉴 === ◆◆◆ //
	void company_menu2(Scanner sc, Company_DTO company);

}
