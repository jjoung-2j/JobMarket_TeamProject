package user.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.Local_DTO;
import common.Set_util;

public class User_DTO {

	// ==== field ====
	// insert 용
	private String user_id;		
	private String user_passwd;
	private String user_name;
	private String user_address;
	private String user_tel;
	private String user_security_num;
	private String user_email;
	private int fk_priority_code;
	private int fk_academy_code;
	
	private String priority_name;
	private String academy_name;
	
	/////////////////////////////
		
	// select 용
	private Local_DTO local = new Local_DTO();
	private Paper_DTO paper = new Paper_DTO();
	private Recruit_Apply_DTO rcapply = new Recruit_Apply_DTO();
	
	
	
	///////////////////////////////////////
	// ==== method ====
	// === 개인 아이디  === //
		public String getUser_id() {
			
			return user_id;
		}
		public void setUser_id(String user_id) {
			
			if(user_id == null || user_id.isBlank())	// 공백일 경우 
				System.out.println(">>> [경고] 아이디는 공백이 아닌 글자로 입력하셔야 합니다. <<<");
			else {
				// 정규표현식 패턴 사용
				Pattern p = Pattern.compile("^[A-Za-z0-9\\_\\-]{4,20}$");
				// 시작 ^, 끝 $, A~Z,a~z,0~9 또는 _ 이나 - 중 최소 4글자에서 최대 20글자로 하기
				
				// user_id 가 정규표현식 패턴과 일치하는지 확인 정보 넣기
				Matcher m = p.matcher(user_id);
				
				// 확인 정보 보기
				if(m.matches())		// 확인 정보가 참이라면 
					this.user_id = user_id;
				else
					System.out.println(">>> [경고] 4~20 자리 / 영문, 숫자, 특수문자 '_','-' 사용가능 <<<");
			}	// end of if~else(유효성 검사)---------
		}	// end of public void setUser_id(String user_id)---------
		
		
		// === 비밀번호  === //
		public String getUser_passwd() {
			return user_passwd;
		}
		public void setUser_passwd(String passwd) {
			// 비밀번호 유효성 검사하기
			if(Set_util.Check_passwd(passwd))	// 검사가 통과하면
				this.user_passwd =  passwd;
			else	// 유효성 검사를 통과하지 못하면
				System.out.println(">>> [경고] 8~16자리/ 영문, 숫자, 특수문자 조합 <<<");
		}	// end of public void setUser_passwd(String user_passwd)-------------
		
		
		// === 성명  === //
		public String getUser_name() {
			return user_name;
		}
		public void setUser_name(String user_name) {
			// 정규표현식 패턴 사용
			Pattern p = Pattern.compile("^[A-Za-z가-힣]{2,6}$");
			// 시작 ^, 끝 $, 가~힣 최소 2글자에서 최대 6글자로 하기
			
			// user_name 가 정규표현식 패턴과 일치하는지 확인 정보 넣기
			Matcher m = p.matcher(user_name);
			
			// 확인 정보 보기
			if(m.matches()) 	// 확인 정보가 참이라면 
				this.user_name = user_name;	
			else 
				System.out.println(">>> [경고] 2~6자리/ 영문, 한글, 숫자 사용가능 <<<");			
		}	// end of public void setName(String name)----------------
		
		
		// === 주소  === //
		public String getUser_address() {
			return user_address;
		}
		public void setUser_address(String user_address) {
			this.user_address = user_address;
		}
		
		
		// === 연락처  === //
		public String getUser_tel() {
			return user_tel;
		}
		public void setUser_tel(String user_tel) {
			// 정규표현식 패턴 사용
			Pattern p = Pattern.compile("^[0-9]{1,15}$");
			// 시작 ^, 끝 $, 0~9 까지 숫자로 하기
			
			// user_name 가 정규표현식 패턴과 일치하는지 확인 정보 넣기
			Matcher m = p.matcher(user_tel);
			
			// 확인 정보 보기
			if(m.matches()) 	// 확인 정보가 참이라면 
				this.user_tel = user_tel;	
			else 
				System.out.println(">>> [경고] 숫자만 사용 가능합니다. <<<");
		}	// end of public void setUser_tel(String user_tel)--------------
		
		
		// === 주민번호  === //
		public String getUser_security_num() {
			return user_security_num;
		}
		public void setUser_security_num(String user_security_num) {
			if(Set_util.Check_security_num(user_security_num))
				this.user_security_num = user_security_num;
			else
				System.out.println(">>> [경고] 주민번호 13자리를 올바르게 입력해주세요 <<<");
		}	// end of public void setUser_security_num(String user_security_num)------
		
		
		// === 이메일  === //
		public String getUser_email() {
			return user_email;
		}
		public void setUser_email(String email) {
			// 이메일 유효성 검사하기 !!! 진행중~~~
			/*
			 * if(Set_util.Check_email(email))	// 검사가 통과하면
			 */
				this.user_email =  email;
			/*else	// 유효성 검사를 통과하지 못하면
				System.out.println(">>> [경고] 8~16자리/ 영문, 숫자, 특수문자 조합 <<<");
			*/
		}
	
	
	public int getFk_priority_code() {
		return fk_priority_code;
	}
	public void setFk_priority_code(int fk_priority_code) {
		this.fk_priority_code = fk_priority_code;
	}
	
	
	public int getFk_academy_code() {
		return fk_academy_code;
	}
	public void setFk_academy_code(int fk_academy_code) {
		this.fk_academy_code = fk_academy_code;
	}
	

	public String getPriority_name() {
		return priority_name;
	}
	public void setPriority_name(String priority_name) {
		this.priority_name = priority_name;
	}
	
	
	public String getAcademy_name() {
		return academy_name;
	}
	public void setAcademy_name(String academy_name) {
		this.academy_name = academy_name;
	}

	
	
	public Local_DTO getLocal() {
		return local;
	}
	public void setLocal(Local_DTO local) {
		this.local = local;
	}
	public Paper_DTO getPaper() {
		return paper;
	}
	public void setPaper(Paper_DTO paper) {
		this.paper = paper;
	}
	public Recruit_Apply_DTO getRcapply() {
		return rcapply;
	}
	public void setRcapply(Recruit_Apply_DTO rcapply) {
		this.rcapply = rcapply;
	}
	
	
}
