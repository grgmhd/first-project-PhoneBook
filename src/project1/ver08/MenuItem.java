package project1.ver08;

public interface MenuItem
{
	// MenuItem.DATAINPUT은 우측항에 써있는대로 그냥 1이라는 뜻이다. 
	// 메소드를 오버라이딩해서 구현할 목적으로 만든 인터페이스가 아니라서 가져갈때 구현implements 할 필요 없습니다
	int DATAINPUT = 1;
	int DATASEARCH = 2;
	int DATADELETE = 3;
	int DATAALLSHOW = 4;
	int AUTOSAVE = 5;
	int QUIT = 6;
}
