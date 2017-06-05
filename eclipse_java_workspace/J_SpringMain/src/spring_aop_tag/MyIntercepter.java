package spring_aop_tag;

import org.aspectj.lang.ProceedingJoinPoint;

public class MyIntercepter 
{	
	
	public void myBefore()
	{
		System.out.println("ҵ�񷽷�֮ǰ");
	}
	
	public void myBefore(String username)
	{
		System.out.println("ҵ�񷽷�֮ǰ,�õ�����:"+username);
	}
	
	public void myAfterReturning()
	{
		System.out.println("ҵ�񷽷�֮��@AfterReturning");
	}
	
	public void myAfterReturning(String result)
	{
		System.out.println("ҵ�񷽷�֮��@AfterReturning,�õ�����ֵ:"+result);
	}
	
	public void myAfter()
	{
		System.out.println("ҵ�񷽷� ���� @After");
	}
	
	public void myException()
	{
		System.out.println("ҵ���쳣������");
	}
	
	public void myException(Exception exception)
	{
		System.out.println("ҵ���쳣������,�쳣ԭ����:"+exception.getMessage());
	}
	public void myAround(ProceedingJoinPoint point) throws Throwable //�����в���ProceedingJoinPoint
	{
		System.out.println("���� @Around");//��@Before֮��
		//����Ȩ���ж�
		point.proceed();//��������������,�ſɵ��õ�ҵ�񷽷�
		
		StringBuffer message = new StringBuffer();
		message.append(point.getTarget().getClass().getSimpleName()).append(".").append(point.getSignature().getName()).append(";");
		System.out.println(message.toString()); 
		
		System.out.println("�˳� @Around"); //��@AfterReturning֮��,@After֮ǰ 
	}
	
}
