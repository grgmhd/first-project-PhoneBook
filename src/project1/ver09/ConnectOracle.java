package project1.ver09;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConnectOracle
{
	public static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	public static final String ORACLE_URL = "jdbc:oracle:thin://@localhost:1521:xe";
	
	public static Connection conn;
	public static PreparedStatement psmt; // 동적쿼리 실행을 위한 객체
	public static Statement stmt;
	public static ResultSet rsSet;
	
	//디폴트생성자
	public ConnectOracle() {}
	
	//인자생성자1 : 아이디, 패스워드를 인자로 받음
	public ConnectOracle(String user, String password)
	{
		try
		{
			Class.forName(ORACLE_DRIVER);
			connect(user, password);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		}
	}
	
	// 인자생성자2 : 드라이버명, 아이디, 패스워드를 인자로 받음
	public ConnectOracle(String driver, String user, String password)
	{
		try
		{
			Class.forName(driver);
			connect(user, password);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		}
	}
	
	// DB 연결
	public void connect(String user, String password)
	{
		try
		{
			conn = DriverManager.getConnection(ORACLE_URL, user, password);
		}
		catch(SQLException e)
		{
			System.out.println("데이터베이스 연결 오류");
			e.printStackTrace();
		}
	}
	// 오버라이딩용. 쿼리실행은 각 클래스에서 기술함.
	public void execute() {}
	
	//자원반납
	public static void close()
	{
		try
		{
			if(conn!=null) conn.close();
			if(psmt!=null) psmt.close();
			if(rsSet!=null) rsSet.close();
			System.out.println("자원반납 완료");
		}
		catch(Exception e)
		{
			System.out.println("자원반납시 오류발생");
			e.printStackTrace();
		}
	}
	// 사용자로부터 입력값을 받기 위한 메서드
	public static String scanValue(String title)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print(title +": ");
		String inputStr = scan.nextLine();
		/*
		equalsIgnoreCase()
			: equals()와 동일하게 문자열이 같은지를 비교하는 메소드로
			대소문자를 구분없이 비교할 수 있다.
		 */
		return inputStr;
	}
}
