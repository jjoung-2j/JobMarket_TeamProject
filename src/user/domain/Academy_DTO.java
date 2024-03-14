package user.domain;

public class Academy_DTO {

	// ==== field ====
	// insert 용
	private int academy_code;		
	private String academy_name;

	/////////////////////////////
		
	// select 용
	
	
	///////////////////////////////////////
	// ==== method ====
	public int getAcademy_code() {
		return academy_code;
	}
	public void setAcademy_code(int academy_code) {
		this.academy_code = academy_code;
	}
	
	
	public String getAcademy_name() {
		return academy_name;
	}
	public void setAcademy_name(String academy_name) {
		this.academy_name = academy_name;
	}

	
	
}
