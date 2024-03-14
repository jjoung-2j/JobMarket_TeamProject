package user.model;

import java.util.Scanner;

public interface User_DAO {

	// ◆◆◆ === user 탈퇴 === ◆◆◆ //
	int memberDelete(Scanner sc, String user_id);

}
