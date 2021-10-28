package project1.ver08;

import java.io.Serializable;

// PhoneSchoolInfo과 PhoneCompanyInfo의 부모Parent이고 오버라이딩의 대상이기 때문에 
// 이 셋으로 뭘 할때 얘 타입으로 만들면 세 클래스 타입을 전부 컨트롤할 수 있습니다.
@SuppressWarnings("serial")
public class PhoneInfo implements Serializable
{
	String name; 
	String phoneNumber; 
	
	public PhoneInfo(String name, String phoneNumber)
	{
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public void showPhoneInfo()
	{
		System.out.println("--------------------");
		System.out.println("이름: "+ name);
		System.out.println("전화번호: "+ phoneNumber);
	}
	
	@Override
	public int hashCode()
	{
		int nameHashCode = name.hashCode();
		return nameHashCode;
	}
	@Override
	public boolean equals(Object obj)
	{
		PhoneInfo pi = (PhoneInfo)obj;
		if(pi.name.equals(this.name))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
