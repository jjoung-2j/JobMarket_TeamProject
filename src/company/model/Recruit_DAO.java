package company.model;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import user.domain.User_DTO;

public interface Recruit_DAO {

	// ◆◆◆ === 채용공고 등록 === ◆◆◆ //
	int recruit_write(Map<String, String> paraMap, Company_DTO company);

	// ◆◆◆ === 채용공고 조회(전체) 리스트 === ◆◆◆ //
	List<Recruit_INFO_DTO> recruit_info_list();
	
	// ◆◆◆ === 채용공고 조회 === ◆◆◆ //
	Recruit_INFO_DTO recruit_info(Map<String, String> paraMap);

	// ◆◆◆ === 채용공고 조회(로그인한 기업의 공고만) === ◆◆◆ //
	List<Recruit_INFO_DTO> All_recruit(Company_DTO company, Recruit_INFO_DTO ridto_register);

	// ◆◆◆ === 로그인한 기업의 채용공고 갯수 가져오기  === ◆◆◆ //
	int get_recruitlist_count(String company_id);
	
	// ◆◆◆ === 분야별공고검색 - 1. 업종별 검색 === ◆◆◆ //
	List<Recruit_INFO_DTO> search_job_type(String job_type_select, Company_DTO company);
		
	// ◆◆◆ === 분야별공고검색 - 2. 지역별 검색 === ◆◆◆ //
	List<Recruit_INFO_DTO> search_local(String local_select, Company_DTO company);
	
	// ◆◆◆ === 분야별공고검색 - 3. 경력별 검색 === ◆◆◆ //
	List<Recruit_INFO_DTO> search_career(String career_select, Company_DTO company);

	// ◆◆◆ === 분야별공고검색 - 4. 채용분야 검색 === ◆◆◆ //
	List<Recruit_INFO_DTO> search_recruit_field(String recruit_type_select, Company_DTO company);

	// ◆◆◆ === 채용공고 수정하기전 단일 컬럼 미리 보여주기 === ◆◆◆ //
	String recruit_oneview(String company_id, String infoview, String input_rcno);

	// ◆◆◆ === 채용공고 단일 컬럼 수정하기 === ◆◆◆ //
	int recruit_fixone(String infoview, String input_rcno, String fix_info);

	// ◆◆◆ === 채용공고 전체 수정 === ◆◆◆ //
	int fix_recruit_all(Map<String, String> rc_allfixmap, String input_rcno);

	// ◆◆◆ === 로그인한 기업의 채용공고 삭제전 간단하게 보여주기  === ◆◆◆ //
	List<Recruit_INFO_DTO> get_recruitlist(Company_DTO company);
	
	// ◆◆◆ === 채용공고 삭제 === ◆◆◆ //
	int delete_recruitlist(String input_rcno);

	// ◆◆◆ === 지원한 구직자 조회 === ◆◆◆ //
	void apply_user_search(Scanner sc, User_DTO user, Company_DTO company);

	

}
