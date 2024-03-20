package user.domain;

import common.Local_DTO;

public class Paper_DTO {

	// ==== field ====
		// insert 용
		private String paper_code;		
		private String fk_user_id;
		private String fk_license_code;
		private String fk_local_code;
		private String user_security_num;
		private String career;
		private String hope_money;
		
		
		private String license_name;
		private String license_day;
		private String license_company;

		private String paper_name;
	    private String paper_registerday;
	    private int paper_no;
	    
	    
	    
		private String hope_local_name;
	    private String hope_city_name;
	    private String career_company_name;
	    private String career_startday;
	    private String career_endday;
	    private String career_content;

		
		
		/////////////////////////////
			
		// select 용
		private Local_DTO local = new Local_DTO();		// 지역
		
		///////////////////////////////////////
		// ==== method ====
		public String getPaper_code() {
			return paper_code;
		}
		public void setPaper_code(String string) {
			this.paper_code = string;
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
		
		
		public String getFk_user_id() {
			return fk_user_id;
		}
		public void setFk_user_id(String fk_user_id) {
			this.fk_user_id = fk_user_id;
		}
		public String getFk_license_code() {
			return fk_license_code;
		}
		public void setFk_license_code(String fk_license_code) {
			this.fk_license_code = fk_license_code;
		}
		public String getFk_local_code() {
			return fk_local_code;
		}
		public void setFk_local_code(String fk_local_code) {
			this.fk_local_code = fk_local_code;
		}
		public String getHope_money() {
			return hope_money;
		}
		public void setHope_money(String hope_money) {
			this.hope_money = hope_money;
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
	
		
		public String getPaper_name() {
			return paper_name;
		}
		public void setPaper_name(String paper_name) {
			this.paper_name = paper_name;
		}
		public String getPaper_registerday() {
			return paper_registerday;
		}
		public void setPaper_registerday(String paper_registerday) {
			this.paper_registerday = paper_registerday;
		}
		public int getPaper_no() {
			return paper_no;
		}
		public void setPaper_no(int paper_no) {
			this.paper_no = paper_no;
		}
		public String getHope_local_name() {
			return hope_local_name;
		}
		public void setHope_local_name(String hope_local_name) {
			this.hope_local_name = hope_local_name;
		}
		public String getHope_city_name() {
			return hope_city_name;
		}
		public void setHope_city_name(String hope_city_name) {
			this.hope_city_name = hope_city_name;
		}
		public String getCareer_company_name() {
			return career_company_name;
		}
		public void setCareer_company_name(String career_company_name) {
			this.career_company_name = career_company_name;
		}
		public String getCareer_startday() {
			return career_startday;
		}
		public void setCareer_startday(String career_startday) {
			this.career_startday = career_startday;
		}
		public String getCareer_endday() {
			return career_endday;
		}
		public void setCareer_endday(String career_endday) {
			this.career_endday = career_endday;
		}
		public String getCareer_content() {
			return career_content;
		}
		public void setCareer_content(String career_content) {
			this.career_content = career_content;
		}
		public Local_DTO getLocal() {
			return local;
		}
		public void setLocal(Local_DTO local) {
			this.local = local;
		}
}
