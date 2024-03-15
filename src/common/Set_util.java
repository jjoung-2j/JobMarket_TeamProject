package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Set_util {

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
			if('가' <= ch && ch <= '힣') 		// 한글이면 
				return false;				// OUT!!
			if(Character.isUpperCase(ch))		// 영문 대문자인 경우
				flag_upper = true;
			else if(Character.isLowerCase(ch))	// 영문 소문자인 경우
				flag_lower = true;
			else if(Character.isDigit(ch))		// 숫자인 경우
				flag_number = true;
			else								// 특수문자인 경우
				flag_special = true;
		}	// end of for----------------
		
		// 하나라도 거짓이면 false 가 return 된다.(즉, 모두 참이어야 한다.)
		return flag_upper && flag_lower && flag_number && flag_special;	
		
	}	// end of public static boolean Check_passwd(String user_passwd)------

	
	
	
	
	
	// ◆◆◆ === 주민번호 유효성 검사하기 === ◆◆◆ //
	public static boolean Check_security_num(String user_security_num) {
        if(user_security_num.length() != 13)
           return false;
        else { 
           
           if(!("1".equals(user_security_num.substring(6,7))      // 7번째 자리가 1 이 아니면
              || "2".equals(user_security_num.substring(6,7))      // 7번째 자리가 2 이 아니면
              || "3".equals(user_security_num.substring(6,7))      // 7번째 자리가 3 이 아니면
              || "4".equals(user_security_num.substring(6,7))))   // 7번째 자리가 4 이 아니면
        	   return false;   
	        else {   // 생년월일(존재하는 월과 일이 인지 확인)
		           String str_bithday = "";
		           if("1".equals(user_security_num.substring(6,7)) || "2".equals(user_security_num.substring(6,7)))
		              str_bithday = "19" + user_security_num.substring(0,6);      // 예 990520 생년월일
		           else 
		              str_bithday = "20" + user_security_num.substring(0,6);      // 예 001219 생년월일
		           
		           SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
		           
		           sdformat.setLenient(false);      // 존재하지 않는 날짜는 false로 출력
		           
		           try {
		              Date date_birthday = sdformat.parse(str_bithday);   // try~catch(ParseException) 선언
		              Date now = new Date();   // 현재 날짜시각
		              String str_now = sdformat.format(now);      // 현재 날짜시각 string 타입(보고싶은 타입 변환)
		              now = sdformat.parse(str_now);            // 문자열 -> date 타입
		           
		              if(date_birthday.after(now))   // 생일이 오늘보다 미래일 경우
		                 return false;
		              else                     // 생일이 오늘이거나 과거일 경우
		                 return true;
		           } catch (ParseException e) {
		              return false;
		           }   // end of try~catch------------------
	        }   // end of if~else
        }
     }	// end of public static boolean Check_security_num(String user_security_num)---------------

	
	
	/* ~~~ 진행중!!!!
	// ◆◆◆ === 이메일 유효성 검사하기 === ◆◆◆ //
	public static boolean Check_email(String email) {
		if(email == null)
			return false;
		int length = email.length();
		
		// 유효성 검사를 위한 flag
		boolean flag_upper = false;		// 영문 대문자 
		boolean flag_lower = false;		// 영문 소문자
		boolean flag_number = false;	// 숫자
		boolean flag_at = false;		// @ 문자
		
		for(int i=0;i<length;i++) {
			char ch = email.charAt(i);
			if('가' <= ch && ch <= '힣') 		// 한글이면 
				return false;				// OUT!!
			if(Character.isUpperCase(ch))		// 영문 대문자인 경우
				flag_upper = true;
			else if(Character.isLowerCase(ch))	// 영문 소문자인 경우
				flag_lower = true;
			else if(Character.isDigit(ch))		// 숫자인 경우
				flag_number = true;
			else if(email.indexOf("@") == email.lastIndexOf("@"))
				flag_at = true;
			else
				break;
		}	// end of for----------------
		
		// 하나라도 거짓이면 false 가 return 된다.(즉, 모두 참이어야 한다.)
		return flag_upper && flag_lower && flag_number && flag_at;	
	}
	*/
}
