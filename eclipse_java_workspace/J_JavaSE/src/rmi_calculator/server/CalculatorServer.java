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
			System.out.println("必须先运行rmiregistry 或者 rmiregistry 1099,并使rmiregistry可以找到 X_Stub类!");
			Calculator c = new CalculatorImpl();
			Naming.rebind("rmi://localhost:1099/CalculatorService", c);
			//或者 Naming.rebind("/CalculatorService", impl)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void pureCode()
	{
		try
		{
			System.out.println("纯代码功能,可以兼容已有的rmiregistry,如没有会自己创建.");
			Calculator impl = new CalculatorImpl();
			Registry registry=null;
			try
			{
				registry= LocateRegistry.getRegistry(1099);//端口号 
				registry.list();
				System.out.println("使用已经存在的LocateRegistry!");//如果已经运行了rmiregistry
			}catch (final Exception e)
			{  
			     registry = LocateRegistry.createRegistry(1099);//相当于执行 rmiregistry 
			     System.out.println("建立新的的LocateRegistry");
			}
			registry.rebind("CalculatorService", impl); //相当于调用 Naming.rebind() ,地址是CalculatorService
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]) 
	{
		if(System.getSecurityManager()==null)
	    {
	    	System.out.println("创建并安装安全管理器");
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
		
		rmic rmi_calculator.server.CalculatorImpl 生成存根  CalculatorImpl_Stub 为客户端用
		CalculatorImpl_Stub报找不到异常,rmiregistry去加载 CalculatorImpl_Stub类的,在运行 rmiregistry 的目录也要可以找到正常的CalculatorImpl_Stub
		
		java  -Djava.security.policy=rmi_calculator/server/policy.txt  rmi_calculator.server.CalculatorServer  要有Calculator.class, CalculatorImpl.class,CalculatorServer.class,CalculatorImpl_Stub.class,policty.txt
		java  rmi_calculator.client.CalculatorClient  要有Calculator.class, CalculatorClient.class
	*/	
	}
}
