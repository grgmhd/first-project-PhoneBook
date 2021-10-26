package project1;

import java.util.Scanner;
import project1.ver02.PhoneInfo;

public class PhoneBookVer02
{
	public static void main(String[] args)
	{
		menu();
	} //close main
	
	public static void menu()
	{
		Scanner scanner = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("메뉴를 선택하세요");
			System.out.println("1. 주소록 입력");
			System.out.println("2. 프로그램 종료");
			
			try
			{
				int choice = scanner.nextInt();
			
				switch(choice)
				{
					case 1:
					{
						scanner.nextLine();
						System.out.println("주소록을 입력합니다");
						System.out.println("이름: ");
						String name = scanner.nextLine();
						System.out.println("전화번호: ");
						String pNumber = scanner.nextLine();
						System.out.println("생년월일: ");
						String birth = scanner.nextLine();
						PhoneInfo pi = new PhoneInfo(name, pNumber, birth);
						pi.showPhoneInfo();
						break;
					}
					case 2:
					{
						System.out.println("프로그램을 종료합니다");
						return;
					}
					default:
					{
						System.out.println
							("잘못 입력하셨습니다. 다시 선택해주세요");
					}
				}
			}
			catch(Exception err)
			{
				System.out.println("잘못 입력하셨습니다. 다시 선택해주세요");
				err.printStackTrace();
			}
		}
	} //close menu
}
