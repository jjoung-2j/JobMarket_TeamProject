package company.model;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import user.domain.User_DTO;

public interface Recruit_DAO {

	// ◆◆◆ === 지원한 구직자 조회 === ◆◆◆ //
	void apply_user_search(Scanner sc, User_DTO user, Company_DTO company);

	// ◆◆◆ === 채용공고 등록 === ◆◆◆ //
	int recruit_write(Map<String, String> paraMap, Company_DTO company);

	// ◆◆◆ === 채용공고 조회(전체) === ◆◆◆ //
	List<Recruit_INFO_DTO> recruit_info_list();

	// ◆◆◆ === 채용공고 조회(로그인한 기업의 공고만) === ◆◆◆ //
	List<Recruit_INFO_DTO> All_recruit(Company_DTO company, Recruit_INFO_DTO ridto_register);


	

}
