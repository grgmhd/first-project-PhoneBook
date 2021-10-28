package project1.ver08;

import java.io.Serializable;

// PhoneInfo를 상속받아서 만들어진 메서드. 둘은 상속관계이기 때문에(+웬만한거 다 오버라이딩했기 때문에)
// 형변환하지 않고 PhoneInfo 타입 셋에 넣고 빼고 출력하고 해도 문제없다.
@SuppressWarnings("serial")
public class PhoneCompanyInfo extends PhoneInfo  implements Serializable
{
	String companyName;

	public PhoneCompanyInfo(String name, String phoneNumber, String companyName)
	{
		super(name, phoneNumber);
		this.companyName = companyName;
	}
	
	@Override
	public void showPhoneInfo()
	{
		super.showPhoneInfo();
		System.out.println("회사명: "+ companyName);
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
		PhoneCompanyInfo pi = (PhoneCompanyInfo)obj;
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
