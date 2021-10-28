package project1.ver09;

import java.sql.SQLException;
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
					case 1: // 입력(저장)
					{
						scanner.nextLine();
						dataInput();
						break;
					}
					case 2: // 검색
					{
						scanner.nextLine();
						dataSearch();
						break;
					}
					case 3: // 삭제
					{
						scanner.nextLine();
						dataDelete();
						break;
					}
					case 4: // 출력
					{
						scanner.nextLine();
						dataAllShow();
						break;
					}
					case 5: // 종료
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
	@SuppressWarnings("static-access")
	public void dataInput()
	{
		ConnectOracle connOracle = new ConnectOracle("kosmo", "1234");
		try
		{
			String query = "INSERT INTO phonebook_tb VALUES " 
							+ " (seq_phonebook.nextval, ?, ?, ?)";
			connOracle.psmt = connOracle.conn.prepareStatement(query);
			
			System.out.println("주소록을 입력합니다");
			String name = connOracle.scanValue("이름");
			String phoneNumber = connOracle.scanValue("전화번호");
			String birthday = connOracle.scanValue("생년월일");
			
			try
			{
				connOracle.psmt.setString(1, name);
				connOracle.psmt.setString(2, phoneNumber);
				connOracle.psmt.setString(3, birthday);
			}
			catch(Exception err)
			{
				System.out.println("에러가 발생했습니다");
				err.printStackTrace();
			}
			
			System.out.println("주소록에 입력되었습니다");
			
			int affected = connOracle.psmt.executeUpdate();
			System.out.println(affected +"행이 입력되었습니다");
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		finally
		{
			connOracle.close();
		}
	}
	
	// 검색
	public void dataSearch()
	{
		ConnectOracle connOracle = new ConnectOracle("kosmo", "1234");
		try
		{
			connOracle.stmt = connOracle.conn.createStatement();
			
			System.out.println("검색을 시작합니다.");
			String search =connOracle.scanValue("찾으시는 이름");
			
			String query = "SELECT * FROM phonebook_tb WHERE name='"+search+"'";
			System.out.println("query : "+query);
			connOracle.rsSet = connOracle.stmt.executeQuery(query);
			
			while(connOracle.rsSet.next())
			{
				String idx = connOracle.rsSet.getString("1");
				String name = connOracle.rsSet.getString("2");
				String phoneNumber = connOracle.rsSet.getString("3");
				String birthday = connOracle.rsSet.getString("4");
				
				System.out.printf
					("%s %s %s %s\n", idx, name, phoneNumber, birthday);
			}
			System.out.println();
			
		}
		catch(SQLException err)
		{
			System.out.println("쿼리에 오류가 발생했습니다");
			err.printStackTrace();
		}
		catch(Exception err)
		{
			System.out.println("에러가 발생했습니다");
			err.printStackTrace();
		}
		finally
		{
			connOracle.close();
			return;
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
