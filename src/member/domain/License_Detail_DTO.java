package member.domain;

public class License_Detail_DTO {

	// ==== field ====
	// insert 용
	private String license_code;		
	private String license_name;
	private String license_day;
	private String license_company;
	

	/////////////////////////////
		
	// select 용
	
	
	///////////////////////////////////////
	// ==== method ====
	public String getLicense_code() {
		return license_code;
	}
	public void setLicense_code(String license_code) {
		this.license_code = license_code;
	}
	
	
	public String getLicense_name() {
		return license_name;
	}
	public void setLicense_name(String license_name) {
		this.license_name = license_name;
	}
	
	
	public String getLicense_day() {
		return license_day;
	}
	public void setLicense_day(String license_day) {
		this.license_day = license_day;
	}
	
	
	public String getLicense_company() {
		return license_company;
	}
	public void setLicense_company(String license_company) {
		this.license_company = license_company;
	}
	
	
}
