package proxytest;

public class StudentInfoServiceImpl implements StudentInfoService
{

	public void findInfo(String studentName)
	{
		System.out.println("你目前输入的名字是:" + studentName);
	}

}
