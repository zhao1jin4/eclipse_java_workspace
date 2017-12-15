package spring_aop_aspectj;

import org.springframework.stereotype.Service;

@Service("abcService")
public class ABCService 
{
	public void businessMethod()throws Exception
	{
		System.out.println("做业务处理方法");
		//throw new Exception("业务异常");
	}
	public String businessMethod(String arg0)
	{
		System.out.println("带参数 arg0 的 做业务处理方法");
		return "OK";
	}
}
