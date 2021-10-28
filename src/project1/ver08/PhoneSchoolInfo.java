package project1.ver08;

import java.io.Serializable;

//PhoneInfo를 상속받아서 만들어진 메서드2222. 둘은 상속관계이기 때문에(+웬만한거 다 오버라이딩했기 때문에)
//형변환하지 않고 PhoneInfo 타입 셋에 넣고 빼고 출력하고 해도 문제없다.
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
