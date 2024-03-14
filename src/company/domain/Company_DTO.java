package company.domain;

public class Company_DTO {

	// ==== field ====
	// insert 용
	private String company_id;		// 
	private String company_passwd;
	private String company_name;
	private String business_number;
	private String company_type;
	private String ceo_name;
	private int fk_jobtype_code;
	private int fk_local_code;
	private String company_address;
	
	/////////////////////////////
		
	// select 용
	
	
	///////////////////////////////////////
	// ==== method ====
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	
	
	public int getFk_jobtype_code() {
		return fk_jobtype_code;
	}
	public void setFk_jobtype_code(int fk_jobtype_code) {
		this.fk_jobtype_code = fk_jobtype_code;
	}
	
	
	public int getFk_local_code() {
		return fk_local_code;
	}
	public void setFk_local_code(int fk_local_code) {
		this.fk_local_code = fk_local_code;
	}
	
	
	public String getCompany_passwd() {
		return company_passwd;
	}
	public void setCompany_passwd(String company_passwd) {
		this.company_passwd = company_passwd;
	}
	
	
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	
	
	public String getBusiness_number() {
		return business_number;
	}
	public void setBusiness_number(String business_number) {
		this.business_number = business_number;
	}
	
	
	public String getCompany_type() {
		return company_type;
	}
	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}
	
	
	
	public String getCeo_name() {
		return ceo_name;
	}
	public void setCeo_name(String ceo_name) {
		this.ceo_name = ceo_name;
	}
	
	
	
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	
	// === 기업정보 === //
   @Override
   public String toString() { // 기업정보를 출력하는 toString메소드 오버라이드
      
      return "=== 기업 정보 ===\n"
            + "▶ 기업명 : " + company_name + "\n"
          + "▶ 사업자등록번호 : " + business_number + "\n"
          + "▶ 업종 : " + company_type + "\n"
          + "▶ 기업대표 : " + ceo_name + "\n"
          + "▶ 기업소재지 : " + company_address + "\n";
      
   } // end of public String toString() @Override

	
	
}
