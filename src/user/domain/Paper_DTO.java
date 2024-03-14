package user.domain;

public class Paper_DTO {

	// ==== field ====
	// insert 용
	private int paper_code;		
	private String user_id;
	private String license_code;
	private int local_code;
	private String user_security_num;
	private String career;
	private String hope_money;

	/////////////////////////////
		
	// select 용
	
	
	///////////////////////////////////////
	// ==== method ====
	public int getPaper_code() {
		return paper_code;
	}
	public void setPaper_code(int paper_code) {
		this.paper_code = paper_code;
	}
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
	public String getLicense_code() {
		return license_code;
	}
	public void setLicense_code(String license_code) {
		this.license_code = license_code;
	}
	
	
	public int getLocal_code() {
		return local_code;
	}
	public void setLocal_code(int local_code) {
		this.local_code = local_code;
	}
	
	
	public String getUser_security_num() {
		return user_security_num;
	}
	public void setUser_security_num(String user_security_num) {
		this.user_security_num = user_security_num;
	}
	
	
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	
	
	public String getHope_money() {
		return hope_money;
	}
	public void setHope_money(String hope_money) {
		this.hope_money = hope_money;
	}
	
	
}
