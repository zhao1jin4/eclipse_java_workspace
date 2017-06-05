package rmi_calculator.server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi_calculator.Calculator;
public class CalculatorServer 
{
	public static  void simpleWithCmd()
	{
		try {
			System.out.println("����������rmiregistry ���� rmiregistry 1099,��ʹrmiregistry�����ҵ� X_Stub��!");
			Calculator c = new CalculatorImpl();
			Naming.rebind("rmi://localhost:1099/CalculatorService", c);
			//���� Naming.rebind("/CalculatorService", impl)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void pureCode()
	{
		try
		{
			System.out.println("�����빦��,���Լ������е�rmiregistry,��û�л��Լ�����.");
			Calculator impl = new CalculatorImpl();
			Registry registry=null;
			try
			{
				registry= LocateRegistry.getRegistry(1099);//�˿ں� 
				registry.list();
				System.out.println("ʹ���Ѿ����ڵ�LocateRegistry!");//����Ѿ�������rmiregistry
			}catch (final Exception e)
			{  
			     registry = LocateRegistry.createRegistry(1099);//�൱��ִ�� rmiregistry 
			     System.out.println("�����µĵ�LocateRegistry");
			}
			registry.rebind("CalculatorService", impl); //�൱�ڵ��� Naming.rebind() ,��ַ��CalculatorService
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]) 
	{
		if(System.getSecurityManager()==null)
	    {
	    	System.out.println("��������װ��ȫ������");
	    	System.setSecurityManager(new RMISecurityManager());
	    }
		
		if(args.length==1)
		{	
			if("cmd".equals(args[0]))
				simpleWithCmd();
			else if("code".equals(args[0]))
				pureCode();
		}else
		{
			//simpleWithCmd();
			pureCode();
		}
			
		/*
		javac -d . rmi_calculator/*.java
		javac -d . rmi_calculator/server/*.java
		javac -d . rmi_calculator/client/*.java
		
		rmic rmi_calculator.server.CalculatorImpl ���ɴ��  CalculatorImpl_Stub Ϊ�ͻ�����
		CalculatorImpl_Stub���Ҳ����쳣,rmiregistryȥ���� CalculatorImpl_Stub���,������ rmiregistry ��Ŀ¼ҲҪ�����ҵ�������CalculatorImpl_Stub
		
		java  -Djava.security.policy=rmi_calculator/server/policy.txt  rmi_calculator.server.CalculatorServer  Ҫ��Calculator.class, CalculatorImpl.class,CalculatorServer.class,CalculatorImpl_Stub.class,policty.txt
		java  rmi_calculator.client.CalculatorClient  Ҫ��Calculator.class, CalculatorClient.class
	*/	
	}
}
