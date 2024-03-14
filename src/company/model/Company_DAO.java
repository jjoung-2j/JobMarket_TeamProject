package company.model;

import java.util.Scanner;

public interface Company_DAO {

	//◆◆◆ === company 탈퇴 === ◆◆◆ //
	int companyDelete(Scanner sc, String company_id);

}
