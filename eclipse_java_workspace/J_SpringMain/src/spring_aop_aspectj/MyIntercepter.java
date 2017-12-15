package spring_aop_aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyIntercepter 
{	
	
	
	//����?????????
	//@Pointcut("execution(* spring_aop_aspectj.ABCService.*(..))")
	//@Pointcut("execution(* spring_aop_aspectj..*.*(..))")
	//private void myMethodPointcut(){}//�Ƕ�������
	
	//@Before("myMethodPointcut()")//��Ӧ����@Pointcut������
	//@Before("spring_aop_aspectj.ABCService.businessMethod()")
	
	
	
	@Before("execution(* spring_aop_aspectj.ABCService.*(..))")//OK ,execution�����пո�,����@Pointcut
	public void myBefore()
	{
		System.out.println("ҵ�񷽷�֮ǰ");
	}
	
	@Before("execution(* spring_aop_aspectj.ABCService.*(..)) && args(username)")//usernameҪ�Ͳ�����һ��
	public void myBefore(String username)
	{
		System.out.println("ҵ�񷽷�֮ǰ,�õ�����:"+username);
	}
	
	@AfterReturning("execution(* spring_aop_aspectj.ABCService.*(..))")
	public void myAfterReturning()
	{
		System.out.println("ҵ�񷽷�֮��@AfterReturning");
	}
	
	@AfterReturning(pointcut="execution(* spring_aop_aspectj.ABCService.*(..))",returning="result")//result�뷽��������Ӧ
	public void myAfterReturning(String result)//��ҵ�񷽷�����String��
	{
		System.out.println("ҵ�񷽷�֮��@AfterReturning,�õ�����ֵ:"+result);
	}
	
	@After("execution(* spring_aop_aspectj.ABCService.*(..))")//��@AfterReturning֮��,�൱����finally����
	public void myAfter()
	{
		System.out.println("ҵ�񷽷� ���� @After");
	}
	
	@AfterThrowing("execution(* spring_aop_aspectj.ABCService.*(..))")//�൱����catch����
	public void myException()
	{
		System.out.println("ҵ���쳣������");
	}
	
	@AfterThrowing(pointcut="execution(* spring_aop_aspectj.ABCService.*(..))",throwing="exception")//�൱����catch����
	public void myException(Exception exception)
	{
		System.out.println("ҵ���쳣������,�쳣ԭ����:"+exception.getMessage());
	}
	
	@Around("execution(* spring_aop_aspectj.ABCService.*(..))")
	public void myAround(ProceedingJoinPoint point) throws Throwable //�����в���ProceedingJoinPoint
	{
		System.out.println("���� @Around");//��@Before֮��
		//����Ȩ���ж�
		point.proceed();//��������������,�ſɵ��õ�ҵ�񷽷�
		System.out.println("�˳� @Around"); //��@AfterReturning֮��,@After֮ǰ 
	}
	
}
