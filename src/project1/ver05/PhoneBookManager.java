package project1.ver05;

import java.util.Scanner;

public class PhoneBookManager
{
	private PhoneInfo[] phone = new PhoneInfo[100];
	private int numOfPhone = 0;
	
	Scanner scanner = new Scanner(System.in);
	
	// 메뉴출력
	public void printMenu()
	{
		while(true)
		{
			System.out.println("====================");
			System.out.println("메뉴를 선택하세요");
			System.out.println("1. 주소록 입력");
			System.out.println("2. 주소록 검색");
			System.out.println("3. 주소록 삭제");
			System.out.println("4. 주소록 출력");
			System.out.println("5. 프로그램 종료");
			System.out.println("====================");
			
			try
			{
				int choice = scanner.nextInt();
			
				switch(choice)
				{
					case MenuItem.dataInput: // 입력(저장)
					{
						scanner.nextLine();
						dataInput();
						break;
					}
					case MenuItem.dataSearch: // 검색
					{
						scanner.nextLine();
						dataSearch();
						break;
					}
					case MenuItem.dataDelete: // 삭제
					{
						scanner.nextLine();
						dataDelete();
						break;
					}
					case MenuItem.dataAllShow: // 출력
					{
						scanner.nextLine();
						dataAllShow();
						break;
					}
					case MenuItem.quit: // 종료
					{
						System.out.println("프로그램을 종료합니다");
						scanner.close();
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
	}
	
	// 입력
	public void dataInput()
	{
		System.out.println("데이터 입력을 시작합니다");
		System.out.print("1.일반,  2.동창,  3.회사");
		int choice = scanner.nextInt();
		scanner.nextLine();
		
		System.out.println("주소록에 입력합니다");
		System.out.print("이름: ");
		String name = scanner.nextLine();
		System.out.print("전화번호: ");
		String pNumber = scanner.nextLine();
		
		switch(choice)
		{
			case SubMenuItem.normal: // 일반
			{
				PhoneInfo pi = new PhoneInfo(name, pNumber);
				phone[numOfPhone++] = pi;
				System.out.println("주소록에 입력되었습니다");
				break;
			}
			case SubMenuItem.classmate: // 동창
			{
				System.out.print("전공: ");
				String major = scanner.nextLine();
				System.out.print("학년: ");
				String grade = scanner.nextLine();
				
				PhoneSchoolInfo psi 
					= new PhoneSchoolInfo(name, pNumber, major, grade);
				phone[numOfPhone++] = psi;
				System.out.println("주소록에 입력되었습니다");
				break;
			}
			case SubMenuItem.Colleague: // 동료
			{
				System.out.println("회사명: ");
				String companyName = scanner.nextLine();
				
				PhoneCompanyInfo pci 
					= new PhoneCompanyInfo(name, pNumber, companyName);
				phone[numOfPhone++] = pci;
				System.out.println("주소록에 입력되었습니다");
				break;
			}
			default:
			{
				System.out.println("입력이 잘못되었습니다");
			}
		}
	}
	
	// 검색
	public void dataSearch()
	{
		System.out.print("검색할 이름을 입력하세요: ");
		String search = scanner.nextLine();
		
		boolean flag = false;
		for(int i=0; i<numOfPhone; i++)
		{
			if(search.compareTo(phone[i].name)==0)
			{
				System.out.println("요청한 정보를 찾았습니다");
				phone[i].showPhoneInfo();
				flag = true;
			}
		}
		System.out.println("검색이 완료되었습니다");
		if(flag == false)
		{
			System.out.println("주소록에 저장되지 않은 이름입니다");
		}
	}
	
	// 삭제
	public void dataDelete()
	{
		System.out.print("삭제할 이름을 입력하세요: ");
		String delete = scanner.nextLine();
		
		int deleteIndex = -1;
		for(int i=0; i<numOfPhone; i++)
		{
			if(delete.compareTo(phone[i].name)==0)
			{
				phone[i] = null;
				deleteIndex = i;
				numOfPhone--;
				break;
			}
		}
		if(deleteIndex == -1)
		{
			System.out.println("주소록에 저장되지 않은 이름입니다");
		}
		else
		{
			for(int i=deleteIndex; i<numOfPhone; i++)
			{
				phone[i] = phone[i+1];
			}
			System.out.printf("입력한 정보가 삭제되었습니다(%d)%n", 
															deleteIndex);
		}
	}
	
	// 주소록 전체 출력
	public void  dataAllShow()
	{
		for(int i=0; i<numOfPhone; i++)
		{
			phone[i].showPhoneInfo();
		}
		System.out.println("주소록에 저장된 정보가 출력되었습니다");
	}
}
