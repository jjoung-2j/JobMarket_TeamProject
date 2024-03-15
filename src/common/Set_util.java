package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Set_util {

	// ◆◆◆ === 아이디 유효성 검사하기 === ◆◆◆ //
	public static boolean Check_id(String id) {
		boolean result = false;
		if(id == null || id.isBlank()) {	// 공백일 경우 
			System.out.println(">>> [경고] 아이디는 공백이 아닌 글자로 입력하셔야 합니다. <<<");
		} 
		else {	
			// 정규표현식 패턴 사용
			Pattern p = Pattern.compile("^[A-Za-z0-9\\_\\-]{4,20}$");
			// 시작 ^, 끝 $, A~Z,a~z,0~9 또는 _ 이나 - 중 최소 4글자에서 최대 20글자로 하기
			
			// user_id 가 정규표현식 패턴과 일치하는지 확인 정보 넣기
			Matcher m = p.matcher(id);
			
			// 확인 정보 보기
			if(m.matches())	{	// 확인 정보가 참이라면 
				result = true;
			}
			else {
				System.out.println(">>> [경고] 4~20 자리 / 영문, 숫자, 특수문자 '_','-' 만 사용 가능합니다. <<<");
				result = false;
			}	// end of if~else------------
		}	// end of if~else(유효성 검사)---------
		return result;
	}	// end of public static boolean Check_id(String user_id)-----------
	
	
	
	
	
	
	// ◆◆◆ === 비밀번호 유효성 검사하기 === ◆◆◆ //
	public static boolean Check_passwd(String passwd) {
		if(passwd == null)
			return false;
		int length = passwd.length();
		if(length < 8 || length > 16)	// 8글자에서 16글자가 아니면
			return false;
		
		// 유효성 검사를 위한 flag
		boolean flag_upper = false;		// 영문 대문자 
		boolean flag_lower = false;		// 영문 소문자
		boolean flag_number = false;	// 숫자
		boolean flag_special = false;	// 특수문자
		
		for(int i=0;i<length;i++) {
			char ch = passwd.charAt(i);
			if('가' <= ch && ch <= '힣') {			// 한글이면 
				return false;						// OUT!!
			}
			if(Character.isUpperCase(ch)) {			// 영문 대문자인 경우
				flag_upper = true;
			}
			else if(Character.isLowerCase(ch)) {	// 영문 소문자인 경우
				flag_lower = true;
			}
			else if(Character.isDigit(ch)) {		// 숫자인 경우
				flag_number = true;
			}
			else {									// 특수문자인 경우
				flag_special = true;
			}	// end of if~else---------------
		}	// end of for----------------
		
		// 하나라도 거짓이면 false 가 return 된다.(즉, 모두 참이어야 한다.)
		return flag_upper && flag_lower && flag_number && flag_special;	
		
	}	// end of public static boolean Check_passwd(String user_passwd)------

	
	
	
	
	
	
	// ◆◆◆ === 성명 유효성 검사하기 === ◆◆◆ //
	public static boolean Check_name(String name) {
		boolean result = false;
		if(name == null || name.isBlank()) {	// 공백일 경우 
			System.out.println(">>> [경고] 성명은 공백이 아닌 글자로 입력하셔야 합니다. <<<");
		} 
		else {
			// 정규표현식 패턴 사용
			Pattern p = Pattern.compile("^[A-Za-z가-힣]{2,6}$");
			// 시작 ^, 끝 $, 가~힣 최소 2글자에서 최대 6글자로 하기
			
			// user_name 가 정규표현식 패턴과 일치하는지 확인 정보 넣기
			Matcher m = p.matcher(name);
			
			// 확인 정보 보기
			if(m.matches()) {	// 확인 정보가 참이라면 
				result = true;	
			}
			else {
				System.out.println(">>> [경고] 2~6자리 / 영문, 한글만 사용 가능합니다. <<<");
				result = false;
			}	// end of if~else-----------
		}	// end of if~else------------------
		return result;
	}	// end of public static boolean Check_name(String name)--------------

	
	
	
	
	
	// ◆◆◆ === 연락처 유효성 검사하기 === ◆◆◆ //
	public static boolean Check_tel(String tel) {
		boolean result = false;
		if(tel == null || tel.isBlank()) {	// 공백일 경우 
			System.out.println(">>> [경고] 연락처는 공백이 아닌 글자로 입력하셔야 합니다. <<<");
		} 
		else {
			// 정규표현식 패턴 사용
			Pattern p = Pattern.compile("^[0-9]{1,15}$");
			// 시작 ^, 끝 $, 0~9 까지 숫자로 하기
			
			// user_tel 가 정규표현식 패턴과 일치하는지 확인 정보 넣기
			Matcher m = p.matcher(tel);
			
			// 확인 정보 보기
			if(m.matches()) {	// 확인 정보가 참이라면 
				result = true;	
			}
			else {
				System.out.println(">>> [경고] 공백이 없으며, 숫자만 사용 가능합니다. <<<");
				result =false;
			}	// end of if~else----------
		}	// end of if~else---------------
		return result;
	}	// end of public static boolean Check_tel(String tel)--------------
	
	
	
	
	
	
	// ◆◆◆ === 주민번호 유효성 검사하기 === ◆◆◆ //
	public static boolean Check_security_num(String user_security_num) {
        if(user_security_num.length() != 13) {
           return false;
        }
        else { 
           if(!("1".equals(user_security_num.substring(6,7))      // 7번째 자리가 1 이 아니면
              || "2".equals(user_security_num.substring(6,7))      // 7번째 자리가 2 이 아니면
              || "3".equals(user_security_num.substring(6,7))      // 7번째 자리가 3 이 아니면
              || "4".equals(user_security_num.substring(6,7)))) {   // 7번째 자리가 4 이 아니면
        	   return false;   
           }
           else {   // 생년월일(존재하는 월과 일이 인지 확인)
		           String str_bithday = "";
		           if("1".equals(user_security_num.substring(6,7)) 
		        		   || "2".equals(user_security_num.substring(6,7))) {
		              str_bithday = "19" + user_security_num.substring(0,6);      // 예 990520 생년월일
		           }
		           else {
		              str_bithday = "20" + user_security_num.substring(0,6);      // 예 001219 생년월일
		           }	// end of if~else---------------
		           SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
		           
		           sdformat.setLenient(false);      // 존재하지 않는 날짜는 false로 출력
		           
		           try {
		              Date date_birthday = sdformat.parse(str_bithday);   // try~catch(ParseException) 선언
		              Date now = new Date();   // 현재 날짜시각
		              String str_now = sdformat.format(now);      // 현재 날짜시각 string 타입(보고싶은 타입 변환)
		              now = sdformat.parse(str_now);            // 문자열 -> date 타입
		           
		              if(date_birthday.after(now)) {  // 생일이 오늘보다 미래일 경우
		                 return false;
		              }
		              else {                    // 생일이 오늘이거나 과거일 경우
		                 return true;
		              }		// end of if~else------------
		           } catch (ParseException e) {
		              return false;
		           }   // end of try~catch------------------
	        }   // end of if~else----------------
        }	// end of if~else--------------
     }	// end of public static boolean Check_security_num(String user_security_num)---------------




	

	// ◆◆◆ === 이메일 유효성 검사하기 === ◆◆◆ //
	public static boolean Check_email(String email) {
		boolean result = false;
		if(email == null || email.isBlank()) {	// 공백일 경우 
			System.out.println(">>> [경고] 이메일은 공백이 아닌 글자로 입력하셔야 합니다. <<<");
		} 
		else {
			// 정규표현식 패턴 사용
			Pattern p = Pattern.compile("^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]+$");
			// 시작 ^, 끝 $
			
			// user_email 가 정규표현식 패턴과 일치하는지 확인 정보 넣기
			Matcher m = p.matcher(email);
			
			// 확인 정보 보기
			if(m.matches()) { 	// 확인 정보가 참이라면 
				result = true;
			}
			else { 
				System.out.println(">>> [경고] 올바른 이메일을 입력해주세요. <<<");
				result = false;
			}	// end of if~else------------
		}	// end of if~else------------
		return result;
	}	// end of public static boolean Check_email(String user_email)----------




	
}
