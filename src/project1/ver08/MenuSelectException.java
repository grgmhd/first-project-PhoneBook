package project1.ver08;

// 사용자 정의 예외처리용. 메뉴 비슷한거 입력받을때마다 예외처리용으로 잘 써먹음
@SuppressWarnings("serial")
public class MenuSelectException extends Exception
{
	public MenuSelectException()
	{
		super("잘못된 숫자를 입력했습니다.");
	}
}
