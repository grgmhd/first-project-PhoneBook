package project1.ver08;

@SuppressWarnings("serial")
public class MenuSelectException extends Exception
{
	public MenuSelectException()
	{
		super("잘못된 숫자를 입력했습니다.");
	}
}
