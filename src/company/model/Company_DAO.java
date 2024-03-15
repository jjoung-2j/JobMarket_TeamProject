package company.model;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import company.domain.Company_DTO;
import user.domain.User_DTO;


public interface Company_DAO {

	//◆◆◆ === company 가입 === ◆◆◆ //
	int companyRegister(Company_DTO company);
		
	//◆◆◆ === company 탈퇴 === ◆◆◆ //
	int Withdrawal(Scanner sc, Company_DTO company);
	
	//◆◆◆ === 기업 로그인 === ◆◆◆ //
	Company_DTO login(Map<String, String> paraMap);
	
	// ◆◆◆ === 기업 추가정보 입력 === ◆◆◆ //
	void company_detail_info(Scanner sc, Company_DTO company);

	// ◆◆◆ === 모든 구직자 조회 === ◆◆◆ //
	List<User_DTO> All_user();
	

}
