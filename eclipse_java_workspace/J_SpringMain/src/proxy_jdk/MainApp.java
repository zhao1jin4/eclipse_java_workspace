package proxy_jdk;

public class MainApp {
	public static void main(String[] args)
	{
		//����Ҫ�нӿڲ���
		
		Business bus=new BusinessImpl();
		ProxyFactory proxy=new ProxyFactory();
		proxy.setTarget(bus);
		Business o=(Business)proxy.createProxy();
		o.methodA();
	}
}