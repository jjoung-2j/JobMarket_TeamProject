package main;

<<<<<<< HEAD
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("2222");
	}
=======
import java.util.Scanner;

import Controller.Controller;

public class Main {

	public static void main(String[] args) {
		
		Controller ctrl = new Controller();
		
		Scanner sc = new Scanner(System.in);

		ctrl.menu_Start(sc);
		
		sc.close();
		System.out.println("~~~ 프로그램 종료 ~~~");
	
	}	// end of public static void main(String[] args)-------------
>>>>>>> develop

}
