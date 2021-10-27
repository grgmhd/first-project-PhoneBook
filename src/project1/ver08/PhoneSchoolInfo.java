package project1.ver08;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PhoneSchoolInfo extends PhoneInfo  implements Serializable
{
	String major;
	String grade;
	public PhoneSchoolInfo(String name, String phoneNumber, String major, String grade)
	{
		super(name, phoneNumber);
		this.major = major;
		this.grade = grade;
	}
	
	@Override
	public void showPhoneInfo()
	{
		super.showPhoneInfo();
		System.out.println("전공: "+ major);
		System.out.println("학년: "+ grade);
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
		PhoneSchoolInfo pi = (PhoneSchoolInfo)obj;
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
