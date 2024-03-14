package company.domain;

public class Company_type_DTO {

	// ==== field ====
	private int employee_num;
	private String public_status;
	private String begin_day;
	private String capital_money;
	private int companylist_num;
	
	
	///////////////////////////////////////
	// ==== method ====
	public int getEmployee_num() {
		return employee_num;
	}
	public void setEmployee_num(int employee_num) {
		this.employee_num = employee_num;
	}
	public String getPublic_status() {
		return public_status;
	}
	public void setPublic_status(String public_status) {
		this.public_status = public_status;
	}
	public String getBegin_day() {
		return begin_day;
	}
	public void setBegin_day(String begin_day) {
		this.begin_day = begin_day;
	}
	public String getCapital_money() {
		return capital_money;
	}
	public void setCapital_money(String capital_money) {
		this.capital_money = capital_money;
	}
	public int getCompanylist_num() {
		return companylist_num;
	}
	public void setCompanylist_num(int companylist_num) {
		this.companylist_num = companylist_num;
	}
}
