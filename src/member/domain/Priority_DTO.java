package member.domain;

public class Priority_DTO {

	// ==== field ====
	// insert 용
	private int priority_code;		
	private String priority_name;
	
	/////////////////////////////
		
	// select 용
	
	///////////////////////////////////////
	// ==== method ====
	public int getPriority_code() {
		return priority_code;
	}
	public void setPriority_code(int priority_code) {
		this.priority_code = priority_code;
	}
	
	
	public String getPriority_name() {
		return priority_name;
	}
	public void setPriority_name(String priority_name) {
		this.priority_name = priority_name;
	}

	

}
