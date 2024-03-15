package common;

public class Local_DTO {

	// ==== field ====
	// insert 용
	private int local_code;			// 지역코드
	private String local_name;		// 지역명
	private String city_name;		// 도시명
	
	/////////////////////////////
		
	// select 용
	
	
	
	///////////////////////////////////////
	// ==== method ====
	public int getLocal_code() {
		return local_code;
	}
	public void setLocal_code(int local_code) {
		this.local_code = local_code;
	}
	
	
	public String getLocal_name() {
		return local_name;
	}
	public void setLocal_name(String local_name) {
		this.local_name = local_name;
	}
	
	
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	
	
	
}
