package user.model;

import java.util.Map;
import java.util.Scanner;

import user.domain.User_DTO;

public interface User_DAO {

	// ◆◆◆ === user 회원가입 === ◆◆◆ //
	int userRegister(User_DTO user);
	
	// ◆◆◆ === user 탈퇴 === ◆◆◆ //
	int Withdrawal(Scanner sc, User_DTO user);
	
	// ◆◆◆ === user 로그인 === ◆◆◆ //
	User_DTO user_login(Map<String, String> paraMap);

}
