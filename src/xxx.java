import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashSet;
import project1.ver08.PhoneInfo;


public class xxx
{
	public static void main(String[] args)
	{
		HashSet<PhoneInfo> phone = new HashSet<PhoneInfo>();
		
		try
		{
			ObjectInputStream objIn 
				= new ObjectInputStream
					(new FileInputStream("src/project1/ver08/AutoSaveBook.txt"));
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
		
		for(PhoneInfo pi: phone)
			pi.showPhoneInfo();
		System.out.println("--------------------");
	}
}