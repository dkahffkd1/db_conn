package Ex01.main;

import java.util.Scanner;
import Ex01.service.MemberService;
import Ex01.service.MemberServiceimp;

public class MainClass {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		MemberService ms = new MemberServiceimp();
		int num;
		while(true) {
			System.out.println("1.회원 보기");
			System.out.println("2.회원 수정");
			System.out.println("3.종 료");
			System.out.println(">>> : ");
			num  = input.nextInt();
			switch(num) {
			case 1: ms.memberView();
			break;
			case 2:ms.modify();
			break;
			case 3: System.out.println("프로그램 종료!!"); 
			return;
			} 
		}
	} 
}
