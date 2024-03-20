package user.model;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import user.domain.Recruit_Apply_DTO;
import user.domain.User_DTO;

public interface Recruit_apply_DAO {

	// ◆◆◆ === 구인회사 조회 === ◆◆◆ //
	Company_DTO company_search(Map<String, String> paraMap);

	// ◆◆◆ === 지원한 공고 조회 === ◆◆◆ //
	List<User_DTO> applylist(Recruit_apply_DAO radao, User_DTO user);

	// ◆◆◆ === 합격여부 조회 === ◆◆◆ //
	int check_success(Scanner sc, User_DTO user, String num);

	// ◆◆◆ === 채용공고에 채용지원서 넣기 === ◆◆◆ //
	int my_recruit_apply(Map<String, String> paraMap, int paper_code);

	// ◆◆◆ === 채용지원 === ◆◆◆ //
	int recruit_apply(String search_recruint_no, Scanner sc, User_DTO user);

	// ◆◆◆ === 나의 채용지원 === ◆◆◆ //
	int my_recruit_apply(Map<String, String> paraMap);
	
}
