package spring_aop_aspectj;

import org.springframework.stereotype.Service;

@Service("abcService")
public class ABCService 
{
	public void businessMethod()throws Exception
	{
		System.out.println("��ҵ������");
		//throw new Exception("ҵ���쳣");
	}
	public String businessMethod(String arg0)
	{
		System.out.println("������ arg0 �� ��ҵ������");
		return "OK";
	}
}
