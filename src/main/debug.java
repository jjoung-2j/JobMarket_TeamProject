package main;

import common.Set_util;

public class debug {

	public static void main(String[] args) {
		String input = "연 2000만원";
		
		String input_arr[] = input.split("만원");
		input = String.join("", input_arr);
		
		input_arr = input.split("연");
		input = String.join("", input_arr);
		
		input_arr = input.split(" ");
		input = String.join("", input_arr);
		
		System.out.println(input);
		
        
	}

}
