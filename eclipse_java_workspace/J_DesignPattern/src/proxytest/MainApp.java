package proxytest;

public class MainApp
{
	public static void main(String[] args)
	{
		StudentInfoService studentInfo = (StudentInfoService) AOPFactory
				.getAOPProxyedObject("proxytest.StudentInfoServiceImpl");
		studentInfo.findInfo("Johnson");
	}

}
