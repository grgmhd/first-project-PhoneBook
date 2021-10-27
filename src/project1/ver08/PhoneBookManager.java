package project1.ver08;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

@SuppressWarnings("serial")
public class PhoneBookManager implements Serializable
{
	public static HashSet<PhoneInfo> phone = new HashSet<PhoneInfo>();
	
	AutoSaverT ast = new AutoSaverT();
	
	Scanner scanner = new Scanner(System.in);
	
	// 메뉴출력
	public void printMenu()
	{
		loadPhoneBook(); // 기존 데이터 불러오기
		
		while(true)
		{
			System.out.println("====================");
			System.out.println("메뉴를 선택하세요");
			System.out.println("1. 주소록 입력");
			System.out.println("2. 주소록 검색");
			System.out.println("3. 주소록 삭제");
			System.out.println("4. 주소록 출력");
			System.out.println("5. 자동저장 옵션");
			System.out.println("6. 프로그램 종료");
			System.out.println("====================");
			
			switch(selectMenu())
			{
				case MenuItem.DATAINPUT: // 연락처 입력(저장)
				{
					scanner.nextLine();
					dataInput();
					break;
				}
				case MenuItem.DATASEARCH: // 연락처 검색
				{
					scanner.nextLine();
					dataSearch();
					break;
				}
				case MenuItem.DATADELETE: // 연락처 삭제
				{
					scanner.nextLine();
					dataDelete();
					break;
				}
				case MenuItem.DATAALLSHOW: // 연락처 전체 출력
				{
					scanner.nextLine();
					dataAllShow();
					break;
				}
				case MenuItem.AUTOSAVE: // 자동저장 - 쓰레드on/off
				{
					scanner.nextLine();
					autoSaveBook();
					break;
				}
				case MenuItem.QUIT: // 종료(데이터 저장)
				{
					scanner.nextLine();
					savePhoneBook();
					scanner.close();
					return;
				}
			}
		}
	} //printMenu() 끝
	
	//메인 메뉴 선택을 choice로 받고 그 에러를 예외처리하는 메서드
	public int selectMenu()
	{
		int choice = 0;
		try
		{
			choice = scanner.nextInt();
			
			if(!(choice>=1 && choice<=6))
			{
				MenuSelectException err = new MenuSelectException();
				throw err;
			}
		}
		catch(InputMismatchException err)
		{
			System.out.println("정수만 입력가능합니다.");
			scanner.nextLine();
		}
		catch(MenuSelectException err)
		{
			System.out.println("없는 메뉴입니다.");
		}
		catch(NullPointerException err)
		{
			System.out.println("검색결과가 없습니다.");
		}
		catch(Exception err)
		{
			System.out.println("알수없는 오류 발생sel");
			err.printStackTrace();
		}
		return choice;
	} // selectMenu() 끝
	
	
	/* 연락처 입력 - 입력받은 데이터묶음은 duplCheck();로 넘겨 중복체크, 
			실질적으로 거기서 입력작업한다 */
	public void dataInput()
	{
		try
		{
			System.out.println("데이터 입력을 시작합니다");
			System.out.print("1.일반,  2.동창,  3.회사");
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			if(!(choice >=1 && choice<=3))
			{
				System.out.println("없는 메뉴입니다.");
				System.out.println("초기화면으로 돌아갑니다");
				return;
			}
			
			System.out.println("주소록에 입력합니다");
			System.out.print("이름: ");
			String name = scanner.nextLine();
			System.out.print("전화번호: ");
			String pNumber = scanner.nextLine();
			
			switch(choice)
			{
				case SubMenuItem.NORMAL: // 일반
				{
					PhoneInfo pi = new PhoneInfo(name, pNumber);
					
					duplCheck(pi);
					
					break;
				}
				case SubMenuItem.CLASSMATE: // 동창
				{
					System.out.print("전공: ");
					String major = scanner.nextLine();
					System.out.print("학년: ");
					String grade = scanner.nextLine();
					
					PhoneSchoolInfo psi 
						= new PhoneSchoolInfo(name, pNumber, major, grade);
					
					duplCheck(psi);
					
					break;
				}
				case SubMenuItem.COLLEAGUE: // 동료
				{
					System.out.println("회사명: ");
					String companyName = scanner.nextLine();
					
					PhoneCompanyInfo pci 
						= new PhoneCompanyInfo(name, pNumber, companyName);
					duplCheck(pci);
					
					break;
				}
			}
		}
		catch(Exception err)
		{
			System.out.println("에러가 발생했습니다input");
			err.printStackTrace();
			System.out.println("초기화면으로 돌아갑니다");
			scanner.nextLine();
			return;
		}
	} //dataInput() 끝
	
	// dataInput()에서 입력받은 데이터묶음을 넘겨받아 중복체크, 프로그램에 저장
	public void duplCheck(PhoneInfo pi)
	{
		for(PhoneInfo def: phone)
		{	
			/* pi == def로 하기에는 캐스팅타입에 문제가 생겼으나 
				딱히 해결할 방법이 떠오르지 않아 equals는 사용하지 않았다 */
			if(pi.hashCode() == def.hashCode())
			{
				System.out.println("이미 저장된 데이터입니다");
				System.out.println("덮어쓸까요? Y(y)/N(n)");
				try
				{
					int cover = System.in.read();
					
					if(cover=='Y' || cover=='y')
					{
						phone.remove(def);
						phone.add(pi);
						System.out.println("덮어쓰기가 완료되었습니다");
						return;
					}
					else if(cover=='N' || cover=='n')
					{
						System.out.println("입력이 취소되었습니다");
						return;
					}
					else
					{
						System.out.println("입력이 잘못되었습니다");
						System.out.println("초기화면으로 돌아갑니다");
						scanner.nextLine();
						return;
					}
				}
				catch(Exception err)
				{
					System.out.println("에러가 발생했습니다dupl");
					System.out.println("초기화면으로 돌아갑니다");
					scanner.nextLine();
					return;
				}
			}
		}
		// 중복되지 않는 데이터는 그냥 저장된다.
		phone.add(pi);
		System.out.println("주소록에 입력되었습니다");
		return;
	} // duplCheck() 끝
	
	// 연락처 검색
	public void dataSearch()
	{
		System.out.print("검색할 이름을 입력하세요: ");
		String search = scanner.nextLine();
		
		boolean flag = false;
		
		Iterator<PhoneInfo> itr = phone.iterator();
		while(itr.hasNext())
		{
			PhoneInfo pi = itr.next();
			
			if(search.compareTo(pi.name)==0)
			{
				System.out.println("요청한 정보를 찾았습니다");
				pi.showPhoneInfo();
				flag = true;
				return;
			}
		}
		if(flag == false)
		{
			System.out.println("주소록에 저장되지 않은 이름입니다");
		}
	} //dataSearch() 끝
	
	// 연락처 삭제
	public void dataDelete()
	{
		System.out.print("삭제할 이름을 입력하세요: ");
		String delete = scanner.nextLine();
		
		boolean flag = false;
		
		Iterator<PhoneInfo> itr = phone.iterator();
		while(itr.hasNext())
		{
			PhoneInfo pi = itr.next();
			
			if(delete.compareTo(pi.name)==0)
			{
				System.out.println("입력한 정보가 삭제되었습니다");
				phone.remove(pi);
				flag = true;
				return;
			}
		}
		if(flag == false)
		{
			System.out.println("주소록에 저장되지 않은 이름입니다");
		}
	} //dataDelete() 끝
	
	// 주소록에 저장된 전체 데이터 출력
	public void  dataAllShow()
	{
		for(PhoneInfo pi: phone)
			pi.showPhoneInfo();
		System.out.println("--------------------");
		System.out.println("주소록에 저장된 정보가 출력되었습니다");
	} //dataAllShow() 끝
	
	// 종료시 입력된 데이터 저장
	public void savePhoneBook()
	{
		try
		{
			System.out.println("입력된 정보를 저장합니다");
			ObjectOutputStream objOut 
				= new ObjectOutputStream
					(new FileOutputStream("src/project1/ver08/PhoneBook.obj"));
			for(PhoneInfo pi: phone)
			{
				objOut.writeObject(pi);
			}
			objOut.close();
			System.out.println("프로그램을 종료합니다");
		}
		catch(Exception err)
		{
			System.out.println("저장중 에러가 발생했습니save");
			err.printStackTrace();
			return;
		}
	} //savePhoneBook() 끝
	
	// 시작할때 기존에 저장된 주소록 불러오기
	public void loadPhoneBook()
	{
		try
		{
			ObjectInputStream objIn 
				= new ObjectInputStream
					(new FileInputStream("src/project1/ver08/PhoneBook.obj"));
			while(true)
			{
				PhoneInfo pi = (PhoneInfo)objIn.readObject();
				phone.add(pi);
				if(pi==null)
				{
					objIn.close();
					break;
				}
			}
		}
		catch(Exception err) {} // 시끄럽다. 무시. 무한루프끝나면 어차피 닫아준다
		System.out.println("기존에 저장된 주소록을 불러왔습니다");
	} //loadPhoneBook() 끝
	
	////////////////// 자동저장 쓰레드 on/off 옵션 (진행중) ////////////////////
	public void autoSaveBook()
	{
		try
		{
			System.out.println("자동저장 하시겠습니까?");
			System.out.println("1.On, 2.Off");
			
			int svOpt = scanner.nextInt();
			
			if(!(svOpt >=1 && svOpt<=2))
			{
				System.out.println("없는 메뉴입니다.");
				System.out.println("초기화면으로 돌아갑니다");
				return;
			}
			
			ast.setName("autoSaver");
			ast.setDaemon(true);
			
			switch(svOpt)
			{
				case 1: //on
				{
//					if()
//					{
//						System.out.println("이미 자동저장 진행중입니다");
//						return;
//					}
					
					ast.start();
				}
				case 2: //off
				{
					ast.interrupt();
				}
				default:
				{
					System.out.println("입력이 잘못되었습니다");
					System.out.println("초기화면으로 돌아갑니다");
					scanner.nextLine();
					return;
				}
			}
		}
		catch(Exception err)
		{
			System.out.println("에러가 발생했습니다auto");
			System.out.println("초기화면으로 돌아갑니다");
			scanner.nextLine();
			return;
		}
	} //autoSaveBook() 끝
	
	
	
} // class PhoneBookManager 끝.
