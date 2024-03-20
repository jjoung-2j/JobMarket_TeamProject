package user.model;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import user.domain.Paper_DTO;
import user.domain.User_DTO;

public interface User_DAO {

	// ◆◆◆ === user 회원가입 === ◆◆◆ //
	int userRegister(User_DTO user);
	
	// ◆◆◆ === 회원 가입시 입력한 아이디가 존재하는지 존재하지 않는지 알아오기 === ◆◆◆ //
	boolean is_exist_user_id(String user_id);
	
	// ◆◆◆ === user 탈퇴 === ◆◆◆ //
	int Withdrawal(Scanner sc, User_DTO user);
	
	// ◆◆◆ === user 로그인 === ◆◆◆ //
	User_DTO user_login(Map<String, String> paraMap);

	// ◆◆◆ === 나의 정보 보기 === ◆◆◆ //
	User_DTO view_userinfo(User_DTO user);
	
	// ◆◆◆ === 나의 정보 수정 === ◆◆◆ //
	int fix_info(User_DTO user, String infoview, String fix_info);

	// ◆◆◆ === 나의 정보 수정(기존 정보 불러오기) === ◆◆◆ //
	int updateBoard(Map<String, String> paraMap);
	
	// ◆◆◆ === 나의 정보 수정전 정보 가져오기 === ◆◆◆ //
	String check_user_info(String chk, String user_id);

	// ◆◆◆ === 나의 추가 정보 입력(학력, 취업우대) === ◆◆◆ //
	int insert_anotherinfo(String academy_code, String priority_code, String user_id);

	
	
	
	// ◆◆◆ ===  이력서 조회 === ◆◆◆ //
	List<Paper_DTO> paper_info(User_DTO user);
	
	// ◆◆◆ === 희망지역 검색 === ◆◆◆ //
	Paper_DTO hope_local(Scanner sc, User_DTO user);
	
	// ◆◆◆ === 이력서 신입 === ◆◆◆ //
	int career_detail_new(User_DTO user);

	// ◆◆◆ === 이력서 경력 === ◆◆◆ //
	int career_detail(User_DTO user);
	
	// ◆◆◆ === 이력서 작성 === ◆◆◆ //
	int write_paper_sql(User_DTO user);
	
	// ◆◆◆ === 이력서 보여주기 === ◆◆◆ //
	Paper_DTO view_paper(String paper_code, User_DTO user);

	// ◆◆◆ === 나의 이력서 수정하기 === ◆◆◆ //
	int update_paper(Map<String, String> paraMap, String paper_code);
	
	
	
	
	// ◆◆◆ === 이력서가 존재하는지 확인하기  === ◆◆◆ //
	boolean check_paper(int paper_code, User_DTO user);

	// ◆◆◆ ===  관리자 탈퇴 처리 === ◆◆◆ //
	int remove();

	// ◆◆◆ === 아이디 찾기  === ◆◆◆ //
	String findid(User_DTO user);

	// ◆◆◆ === 비밀번호 찾기  === ◆◆◆ //
	User_DTO findpasswd(User_DTO user);

	

	

	

	


	


}
