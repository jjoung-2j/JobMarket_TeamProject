package user.model;

import java.util.Map;
import java.util.Scanner;

import company.domain.Company_DTO;
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

	// ◆◆◆ === 나의 정보 수정(전체) === ◆◆◆ //
	int updateBoard(Map<String, String> paraMap);
	
	// ◆◆◆ === 나의 추가 정보 입력(학력, 취업우대) === ◆◆◆ //
	int insert_anotherinfo(String academy_code, String priority_code, String user_id);


	


}
