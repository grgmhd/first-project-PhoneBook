package project1.ver08;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;

@SuppressWarnings("serial")
public class AutoSaverT extends Thread implements Serializable
{
	HashSet<PhoneInfo> phone = PhoneBookManager.phone;
	ObjectOutputStream objOut;
	
	
	public AutoSaverT()
	{
		try
		{
			objOut = new ObjectOutputStream
						(new FileOutputStream
							("src/project1/ver08/AutoSaveBook.txt"));
		}
		catch(IOException err)
		{
			System.out.println("입력 과정에서 에러");
		}
		catch(Exception err)
		{
			System.out.println("자동저장 입력 중 에러가 발생했습니다");
		}
		
	}
	
	@Override
	public void run()
	{
		System.out.println("자동저장을 시작합니다");
			while(true)
			{
				try
				{
					for(PhoneInfo pi: phone)
					{
						objOut.writeObject(pi);
					}
					System.out.println("자동저장 되었습니다");
					Thread.sleep(5000);
				}
				catch(InterruptedException err)
				{
					System.out.println("쓰레드 진행중 에러");
					Thread.currentThread().interrupt();
					err.printStackTrace();
					return;
				}
				catch(Exception err)
				{
					System.out.println("자동저장 진행중 에러가 발생했습니다");
				}
			}
	}
	
	
}
