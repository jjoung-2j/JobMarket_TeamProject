package user.model;

import java.util.Scanner;

import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import user.domain.Paper_DTO;
import user.domain.Recruit_Apply_DTO;
import user.domain.User_DTO;

public interface Recruit_apply_DAO {

	// ◆◆◆ === 구인회사 조회 === ◆◆◆ //
	void company_search(Scanner sc, User_DTO user, Company_DTO company);

	// ◆◆◆ === 채용공고 조회 === ◆◆◆ //
	void recruit_search(Scanner sc, User_DTO user, Company_DTO company);

	// ◆◆◆ === 채용지원 === ◆◆◆ //
	void recruit_apply(Scanner sc, User_DTO user, Company_DTO company);
	
	// ◆◆◆ === 지원현황 === ◆◆◆ //
	void recruit_apply_situation(Scanner sc, Company_DTO company);

	

}
