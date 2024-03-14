package user.domain;

public class User_DTO {

	// ==== field ====
	// insert 용
	private String user_id;		
	private String user_passwd;
	private String user_name;
	private String user_address;
	private String user_tel;
	private String user_security_num;
	private String user_email;
	private int fk_priority_code;
	private int fk_academy_code;
	
	
	/////////////////////////////
		
	// select 용
	
	
	///////////////////////////////////////
	// ==== method ====
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
	public String getUser_passwd() {
		return user_passwd;
	}
	public void setUser_passwd(String user_passwd) {
		this.user_passwd = user_passwd;
	}
	
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	
	
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	
	
	public String getUser_security_num() {
		return user_security_num;
	}
	public void setUser_security_num(String user_security_num) {
		this.user_security_num = user_security_num;
	}
	
	
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	
	public int getFk_priority_code() {
		return fk_priority_code;
	}
	public void setFk_priority_code(int fk_priority_code) {
		this.fk_priority_code = fk_priority_code;
	}
	
	
	public int getFk_academy_code() {
		return fk_academy_code;
	}
	public void setFk_academy_code(int fk_academy_code) {
		this.fk_academy_code = fk_academy_code;
	}
	
	

	
	
}
