package company.domain;

import java.util.Map;

public class Recruit_INFO_DTO {

	// ==== field ====
	// insert 용
	private String recruint_no;		
	private String company_id;
	private String hiretype_code;
	private String manager_email;
	private String manager_name;
	private String recruit_title;
	private String recruit_registerday;
	private String recruit_deadline;
	private String career;
	private String year_salary;
	private String recruit_content;
	private String recruit_people;
	

	private String recruit_field;
	private String work_day;
	private String work_time;
	
	private String hiretype_name;

	/////////////////////////////
		
	// select 용
	private Company_DTO cdto;
	
	///////////////////////////////////////
	// ==== method ====
	
	public String getRecruit_people() {
		return recruit_people;
	}

	public void setRecruit_people(String recruit_people) {
		this.recruit_people = recruit_people;
	}
	public String getRecruint_no() {
		return recruint_no;
	}

	public void setRecruint_no(String recruint_no) {
		this.recruint_no = recruint_no;
	}

	
	
	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	
	
	public String getHiretype_code() {
		return hiretype_code;
	}

	public void setHiretype_code(String hiretype_code) {
		this.hiretype_code = hiretype_code;
	}

	
	
	public String getManager_email() {
		return manager_email;
	}

	public void setManager_email(String manager_email) {
		this.manager_email = manager_email;
	}

	
	
	public String getManager_name() {
		return manager_name;
	}

	public void setManager_name(String manager_name) {
		this.manager_name = manager_name;
	}

	
	
	public String getRecruit_title() {
		return recruit_title;
	}

	public void setRecruit_title(String recruit_title) {
		this.recruit_title = recruit_title;
	}

	
	
	public String getRecruit_deadline() {
		return recruit_deadline;
	}

	public void setRecruit_deadline(String recruit_deadline) {
		this.recruit_deadline = recruit_deadline;
	}

	
	
	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	
	
	public String getYear_salary() {
		return year_salary;
	}

	public void setYear_salary(String year_salary) {
		this.year_salary = year_salary;
	}

	
	
	public String getRecruit_content() {
		return recruit_content;
	}

	public void setRecruit_content(String recruit_content) {
		this.recruit_content = recruit_content;
	}

	
	
	public String getRecruit_field() {
		return recruit_field;
	}

	public void setRecruit_field(String recruit_field) {
		this.recruit_field = recruit_field;
	}

	
	
	public String getWork_day() {
		return work_day;
	}

	public void setWork_day(String work_day) {
		this.work_day = work_day;
	}

	
	
	public String getWork_time() {
		return work_time;
	}

	public void setWork_time(String work_time) {
		this.work_time = work_time;
	}

	
	
	public String getHiretype_name() {
		return hiretype_name;
	}

	public void setHiretype_name(String hiretype_name) {
		this.hiretype_name = hiretype_name;
	}
	
	/////////////////////////////////////////////////////////
	
	public Company_DTO getCdto() {
	      return cdto;
	}
	
    public void setCdto(Company_DTO cdto) {
      this.cdto = cdto;
    }
	
	public String getRecruit_registerday() {
		return recruit_registerday;
	}

	public void setRecruit_registerday(String recruit_registerday) {
		this.recruit_registerday = recruit_registerday;
	}
    

	/////////////////////////////////////////////////////////
	
	// ◆◆◆ === 채용공고 등록 === ◆◆◆ //
	public Recruit_INFO_DTO recruit_register(Map<String, String> paraMap) {
		
		return null;
	} // end of public Recruit_INFO_DTO recruit_register(Map<String, String> paraMap)



	

	
	

	
}
