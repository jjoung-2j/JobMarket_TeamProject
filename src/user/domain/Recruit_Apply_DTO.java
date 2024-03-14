package user.domain;

public class Recruit_Apply_DTO {

	// ==== field ====
	// insert 용
	private String recruit_no;		
	private int	paper_code;
	private String apply_motive;
	private String apply_day;

	/////////////////////////////
		
	// select 용
	
	
	///////////////////////////////////////
	// ==== method ====
	public String getRecruit_no() {
		return recruit_no;
	}
	public void setRecruit_no(String recruit_no) {
		this.recruit_no = recruit_no;
	}
	
	
	public int getPaper_code() {
		return paper_code;
	}
	public void setPaper_code(int paper_code) {
		this.paper_code = paper_code;
	}
	
	
	public String getApply_motive() {
		return apply_motive;
	}
	public void setApply_motive(String apply_motive) {
		this.apply_motive = apply_motive;
	}
	
	
	public String getApply_day() {
		return apply_day;
	}
	public void setApply_day(String apply_day) {
		this.apply_day = apply_day;
	}
	
	
}
