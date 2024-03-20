package user.domain;

import common.Local_DTO;

public class User_DTO {

	// ==== field ====
	// insert 용
	private String user_id;				// 개인아이디
	private String user_passwd;			// 비밀번호
	private String user_name;			// 성명
	private String user_address;		// 주소
	private String user_tel;			// 연락처
	private String user_security_num;	// 주민번호
	private String user_email;			// 이메일
	private int fk_priority_code;		// 취업우대코드
	private int fk_academy_code;		// 학력코드
	
	private String priority_name;		// 취업우대명
	private String academy_name;		// 학력명
	
	/////////////////////////////
		
	// select 용
	private Local_DTO local = new Local_DTO();		// 지역
	private Paper_DTO paper = new Paper_DTO();		// 이력서
	private Recruit_Apply_DTO rcapply = new Recruit_Apply_DTO();	// 채용지원
	
	private String age;    // 나이 
	private String gender; // 성별
	
	
	///////////////////////////////////////
	// ==== method ====
	// === 개인 아이디  === //
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}	// end of public void setUser_id(String user_id)---------
	
	
	// === 비밀번호  === //
	public String getUser_passwd() {
		return user_passwd;
	}
	public void setUser_passwd(String passwd) {
			this.user_passwd = passwd;
	}
	
	
	
	// === 성명  === //
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
	
	// === 주소  === //
	public String getUser_address() {
		return user_address;
	}
	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}
	
	
	// === 연락처  === //
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	
	
	// === 주민번호  === //
	public String getUser_security_num() {
		return user_security_num;
	}
	public void setUser_security_num(String user_security_num) {
			this.user_security_num = user_security_num;
	}
	
	
	// === 이메일  === //
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
	

	public String getPriority_name() {
		return priority_name;
	}
	public void setPriority_name(String priority_name) {
		this.priority_name = priority_name;
	}
	
	
	public String getAcademy_name() {
		return academy_name;
	}
	public void setAcademy_name(String academy_name) {
		this.academy_name = academy_name;
	}

	
	
	public Local_DTO getLocal() {
		return local;
	}
	public void setLocal(Local_DTO local) {
		this.local = local;
	}
	public Paper_DTO getPaper() {
		return paper;
	}
	public void setPaper(Paper_DTO paper) {
		this.paper = paper;
	}
	public Recruit_Apply_DTO getRcapply() {
		return rcapply;
	}
	public void setRcapply(Recruit_Apply_DTO rcapply) {
		this.rcapply = rcapply;
	}
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
