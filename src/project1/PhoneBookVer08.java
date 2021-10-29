package project1;

import project1.ver08.PhoneBookManager;

public class PhoneBookVer08
{
	public static void main(String[] args)
	{
		// 저는 메뉴출력과 선택시 메소드 호출하는 부분도 모두 매니저 클래스에 넣었습니다.
		PhoneBookManager manager = new PhoneBookManager();
		manager.printMenu();
	}
}
