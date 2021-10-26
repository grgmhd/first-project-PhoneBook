package project1.ver08;

import java.io.Serializable;

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
}
