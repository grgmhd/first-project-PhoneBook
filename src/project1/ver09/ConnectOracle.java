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
	// 변하면 큰일나는 내용이라서 final로 선언했습니다. static은 섭섭하니까 넣음(안넣어도 작동합니다).
	public static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	public static final String ORACLE_URL = "jdbc:oracle:thin://@localhost:1521:xe";
	
	public static Connection conn;
	public static PreparedStatement psmt; // 동적쿼리 실행을 위한 객체
	public static Statement stmt; // 정적쿼리 실행을 위한 객체
	public static ResultSet rsSet; // 실행결과를 반환받는 set
	
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
	
	//자원반납
	public static void close()
	{
		try
		{
			if(conn!=null) conn.close();
			if(psmt!=null) psmt.close();
			if(stmt!=null) stmt.close();
			if(rsSet!=null) rsSet.close();
		}
		catch(Exception e)
		{
			System.out.println("자원반납시 오류발생");
			e.printStackTrace();
		}
	}
	// 사용자로부터 무엇에 대한 입력을 받을것인지 표시하고, 입력받은 값을 반환하는 메소드입니다
	public static String scanValue(String title)
	{
		Scanner scan = new Scanner(System.in);
		System.out.print(title +": ");
		String inputStr = scan.nextLine();
		
		return inputStr;
	}
}
