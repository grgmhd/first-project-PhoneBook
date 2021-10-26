package project1.ver08;

import java.io.Serializable;

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
}
