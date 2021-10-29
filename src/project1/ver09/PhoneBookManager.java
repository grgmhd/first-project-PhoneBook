package project1.ver09;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Scanner;

//메뉴출력과 메소드 호출까지 전부 매니저 클래스에서 진행합니다. main 메소드는 거들뿐

public class PhoneBookManager
{
	// jdbc 연결 관련된 부분은 9단계 패키지에 같이 들어있는 ConnectOracle 클래스를 불러와서 사용합니다
	ConnectOracle connOracle = new ConnectOracle("kosmo", "1234");
	
	// 대부분 ConnectOracle 클래스의 scanValue()를 사용했기 때문에 메뉴선택에서만 사용중
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
					case 1: // 연락처 입력(저장) psmt
					{
						scanner.nextLine();
						dataInput();
						break;
					}
					case 2: // 연락처 검색 stmt
					{
						scanner.nextLine();
						dataSearch();
						break;
					}
					case 3: // 연락처 삭제 psmt
					{
						scanner.nextLine();
						dataDelete();
						break;
					}
					case 4: // 연락처 출력 stmt
					{
						scanner.nextLine();
						dataAllShow();
						break;
					}
					case 5: // 종료(jdbc연결을 이때 끊음)
					{
						System.out.println("프로그램을 종료합니다");
						connOracle.close();
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
				System.out.println("잘못 입력하셨습니다. 다시 선택해주세요menu");
				scanner.nextLine();
			}
		}
	} //printMenu() 끝
	
	// 연락처 입력 (PreparedStatement 사용)
	// primary key를 시퀀스로 생성한 idx에 걸었기 때문에 이름을 중복저장하는 것이 허용됩니다.
	// 따라서 이름이 중복되었다고 오류가 뜨지 않고 이름에 대한 부분은 에러를 예외처리하지 않았습니다.
	// SQLDataException 를 예외처리한 부분은 생년월일의 date타입에 잘못 입력했을 경우에 대한 것입니다.
	public void dataInput()
	{
		try
		{
			String query = "INSERT INTO phonebook_tb VALUES " 
							+ " (seq_phonebook.nextval, ?, ?, ?)";
			connOracle.psmt = connOracle.conn.prepareStatement(query);
			
			System.out.println("주소록을 입력합니다");
			String name = connOracle.scanValue("이름");
			String phoneNumber = connOracle.scanValue("전화번호");
			String birthday = connOracle.scanValue("생년월일");
			
			connOracle.psmt.setString(1, name);
			connOracle.psmt.setString(2, phoneNumber);
			connOracle.psmt.setString(3, birthday);
			
			connOracle.psmt.executeUpdate();
			
			System.out.println("주소록에 입력되었습니다");
		}
		catch(SQLDataException err)
		{
			System.out.println("잘못 입력하셨습니다");
		}
		catch(Exception err)
		{
			System.out.println("에러가 발생했습니다input");
			err.printStackTrace();
		}
	} //dataInput() 끝
	
	// 검색 (정적 Statement 사용)
	public void dataSearch()
	{
		try
		{
			connOracle.stmt = connOracle.conn.createStatement();
			
			System.out.println("주소록 검색을 시작합니다.");
			String search = connOracle.scanValue("찾으시는 이름");
			
			String query = "SELECT * FROM phonebook_tb WHERE name like '%"+search+"%'";
			//쿼리문 확인용
//			System.out.println("query : "+query);
			connOracle.rsSet = connOracle.stmt.executeQuery(query);
			
			//출력할 내용이 있는지 확인용 변수
			boolean flag = true;
			
			//next()가 true,false값을 반환하기때문에 가능한 구조입니다
			while(connOracle.rsSet.next())
			{
				System.out.println("요청한 정보를 찾았습니다");
				String idx = connOracle.rsSet.getString("idx");
				String name = connOracle.rsSet.getString("name");
				String phoneNumber = connOracle.rsSet.getString("phoneNumber");
				String birthday = connOracle.rsSet.getString("birthday").substring(0, 10);
				
				System.out.println("----------------------------------");
				System.out.printf
					("%2s  %s  %s %s\n", idx, name, phoneNumber, birthday);
				System.out.println("----------------------------------");
				flag = false;
			}
			// 출력할 내용이 없어 while문에 들어가지 못했다면 flag가 바뀌지 못합니다
			// (boolean이 아니라 숫자나 문자타입으로 해도 상관 X 정해진 내용으로 바꾸기만 하면 됩니다)
			// flag가 초기화된 그대로라면 if문 안으로 바로 딸려가서 메세지를 출력하고 메소드밖으로 튕겨냅니다
			if(flag==true)
			{
				System.out.println("주소록에 저장되지 않은 이름입니다");
				return;
			}
			// while문을 무사히 통과해서 flag가 바뀌었다면 if문을 무시하고 이 메세지에 닿게 됩니다.
			System.out.println("검색이 완료되었습니다");
		}
		catch(SQLException err)
		{
			System.out.println("쿼리에 오류가 발생했습니다");
			err.printStackTrace();
		}
		catch(Exception err)
		{
			System.out.println("에러가 발생했습니다sch");
			err.printStackTrace();
		}
	}//dataSearch() 끝
	
	// 연락처 삭제 (PreparedStatement 사용)
	public void dataDelete()
	{
		System.out.println("연락처 삭제를 시작합니다.");
		
		try
		{
			String query = "DELETE FROM phonebook_tb WHERE name=?";
			
			connOracle.psmt = connOracle.conn.prepareStatement(query);
			
			String delete = connOracle.scanValue("삭제하실 이름");
			connOracle.psmt.setString(1, delete);
			
			//쿼리문 확인용
//			System.out.println("query : "+query);
			
			int affected = connOracle.psmt.executeUpdate();
			
			// executeUpdate()가 몇개의 행에 영향을 미쳤는지 int값으로 반환하기 때문에 가능한 구조입니다.
			if(affected == 0)
			{
				System.out.println("주소록에 저장되지 않은 이름입니다");
			}
			else
			{
				System.out.println("주소록에서 연락처가 삭제되었습니다");
				System.out.println(affected +"행이 삭제되었습니다");
			}
		}
		catch(Exception err)
		{
			System.out.println("에러가 발생했습니다del");
			err.printStackTrace();
		}
	}//dataDelete() 끝
	
	// 주소록 전체 출력 (정적 Statement 사용)
	public void dataAllShow()
	{
		try
		{
			connOracle.stmt = connOracle.conn.createStatement();
			String query = "SELECT * FROM phonebook_tb";
			//쿼리문 확인용
//			System.out.println("query : "+query);
			connOracle.rsSet = connOracle.stmt.executeQuery(query);
			
			//출력할 내용이 있는지 확인용 변수
			boolean flag = true;
			
			//next()가 true,false값을 반환하기때문에 가능한 구조입니다
			while(connOracle.rsSet.next())
			{
				String idx = connOracle.rsSet.getString("idx");
				String name = connOracle.rsSet.getString("name");
				String phoneNumber = connOracle.rsSet.getString("phoneNumber");
				String birthday = connOracle.rsSet.getString("birthday").substring(0, 10);
				
				System.out.println("----------------------------------");
				System.out.printf
					("%2s  %s  %s %s\n", idx, name, phoneNumber, birthday);
				System.out.println("----------------------------------");
				flag = false;
			}
			if(flag==true)
			{
				System.out.println("저장된 연락처가 없습니다");
				return;
			}
			System.out.println("주소록에 저장된 정보가 출력되었습니다");
		}
		catch(SQLException err)
		{
			System.out.println("쿼리에 오류가 발생했습니다");
			err.printStackTrace();
		}
		catch(Exception err)
		{
			System.out.println("에러가 발생했습니다show");
			err.printStackTrace();
		}
	}//dataAllShow() 끝
}//PhoneBookManager 클래스 끝. main 메소드에 아무것도 없기 때문에 실질적인 프로그램의 끝
