package user.model;

import java.util.Map;
import java.util.Scanner;
import company.domain.Company_DTO;
import user.domain.User_DTO;

public interface Recruit_apply_DAO {

	// ◆◆◆ === 채용지원 === ◆◆◆ //
	void recruit_apply(Scanner sc, User_DTO user, Company_DTO company);
	
	// ◆◆◆ === 지원현황 === ◆◆◆ //
	void recruit_apply_situation(Scanner sc, Company_DTO company);

	// ◆◆◆ === 구인회사 조회 === ◆◆◆ //
	Company_DTO company_search(Map<String, String> paraMap);

}
