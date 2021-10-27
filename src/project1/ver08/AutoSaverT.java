package project1.ver08;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class AutoSaverT extends Thread
{
	
	
	@Override
	public void run()
	{
		try
		{
			System.out.println("자동저장을 시작합니다");
			ObjectOutputStream objOut 
				= new ObjectOutputStream
					(new FileOutputStream("src/project1/ver08/AutoSaveBook.txt"));
			
			// manager 클래스에서 set을 private으로 선언했기때문에 필요한것
			// public으로 선언하면 생략가능
			PhoneBookManager manager = new PhoneBookManager();
			HashSet<PhoneInfo> phone = manager.getPhone();
			
			while(true)
			{
				for(PhoneInfo pi: phone)
				{
					objOut.writeObject(pi);
				}
				System.out.println("자동저장 되었습니다");
				sleep(5000);
			}
		}
		catch(IOException err)
		{
			System.out.println("입력 과정에서 에러");
		}
		catch(InterruptedException err)
		{
			System.out.println("쓰레드 진행중 에러");
			Thread.currentThread().interrupt();
			err.printStackTrace();
		}
		catch(Exception err)
		{
			System.out.println("자동저장 중 에러가 발생했습니다");
		}
	}
	
	
}
