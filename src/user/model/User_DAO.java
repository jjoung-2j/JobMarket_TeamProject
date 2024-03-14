package user.model;

import java.util.Map;
import java.util.Scanner;

import company.domain.Company_DTO;
import company.domain.Recruit_INFO_DTO;
import user.domain.Paper_DTO;
import user.domain.Recruit_Apply_DTO;
import user.domain.User_DTO;

public interface User_DAO {

	// ◆◆◆ === user 회원가입 === ◆◆◆ //
	int userRegister(User_DTO user);
	
	// ◆◆◆ === user 탈퇴 === ◆◆◆ //
	int Withdrawal(Scanner sc, User_DTO user);
	
	// ◆◆◆ === user 로그인 === ◆◆◆ //
	User_DTO user_login(Map<String, String> paraMap);

	// ◆◆◆ === 나의 정보 메뉴 === ◆◆◆ //
	void user_info_menu(Scanner sc, User_DTO user);

	// ◆◆◆ ===  이력서 관리 메뉴 === ◆◆◆ //
	void Paper_menu(Scanner sc, User_DTO user);

	// ◆◆◆ === 입사지원 제안 관리 메뉴 === ◆◆◆ //
	void Recruit_apply_menu(Scanner sc, User_DTO user, Company_DTO company);



}
