package company.domain;

public class JobType_DTO {

	// ==== field ====
	// insert 용
	private int jobtype_code;
	private String jobtype_name;
	
	/////////////////////////////
		
	// select 용
	
	
	///////////////////////////////////////
	// ==== method ====
	public int getJobtype_code() {
		return jobtype_code;
	}
	public void setJobtype_code(int jobtype_code) {
		this.jobtype_code = jobtype_code;
	}
	
	
	public String getJobtype_name() {
		return jobtype_name;
	}
	public void setJobtype_name(String jobtype_name) {
		this.jobtype_name = jobtype_name;
	}
	
	
}
