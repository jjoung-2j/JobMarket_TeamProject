package company.domain;

import common.Local_DTO;

public class Company_DTO {

	// ==== field ====
	// insert 용
	private String company_id;		
	private String company_passwd;
	private String company_name;
	private String business_number;
	private String ceo_name;
	private int fk_jobtype_code;
	private int fk_local_code;
	private String company_address;
	
	// JobType
	private String jobtype_name;
	
	/////////////////////////////
		
	// select 용
	private Local_DTO local = new Local_DTO();
	private Company_type_DTO company_type_detail = new Company_type_DTO();
	private Recruit_INFO_DTO recruit = new Recruit_INFO_DTO();
	



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
	
	
	

	public String getJobtype_name() {
		return jobtype_name;
	}
	public void setJobtype_name(String jobtype_name) {
		this.jobtype_name = jobtype_name;
	}
	
	
	
    public Local_DTO getLocal() {
		return local;
	}
	public void setLocal(Local_DTO local) {
		this.local = local;
	}
	
	
	
	
	public Company_type_DTO getCompany_type_detail() {
		return company_type_detail;
	}

	public void setCompany_type_detail(Company_type_DTO company_type_detail) {
		this.company_type_detail = company_type_detail;
	}
	
	
	
	
	public Recruit_INFO_DTO getRecruit() {
		return recruit;
	}

	public void setRecruit(Recruit_INFO_DTO recruit) {
		this.recruit = recruit;
	}
	
	


}
