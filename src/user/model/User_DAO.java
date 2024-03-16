package user.model;

import java.util.Map;
import java.util.Scanner;

import company.domain.Company_DTO;
import user.domain.User_DTO;

public interface User_DAO {

	// ◆◆◆ === user 회원가입 === ◆◆◆ //
	int userRegister(User_DTO user);
	
	// ◆◆◆ === user 탈퇴 === ◆◆◆ //
	int Withdrawal(Scanner sc, User_DTO user);
	
	// ◆◆◆ === user 로그인 === ◆◆◆ //
	User_DTO user_login(Map<String, String> paraMap);

	// ◆◆◆ === 나의 정보 보기 === ◆◆◆ //
	User_DTO view_userinfo(User_DTO user);
	
	// ◆◆◆ === 나의 정보 수정 === ◆◆◆ //
	void change_information(Scanner sc, User_DTO user);

	// ◆◆◆ ===  이력서 조회 === ◆◆◆ //
	void paper_info(Scanner sc, User_DTO user);

	// ◆◆◆ ===  이력서 작성 === ◆◆◆ //
	void write_paper(Scanner sc, User_DTO user);

	// ◆◆◆ ===  이력서 수정 === ◆◆◆ //
	void change_paper(Scanner sc, User_DTO user);

	



}
