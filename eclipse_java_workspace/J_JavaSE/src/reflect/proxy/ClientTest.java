package reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ClientTest {
    public static void main(String args[]) {
        SellFisher s = new ConcreteSellFisher();
        InvocationHandler p = new ProxySellFisher(s);
        Object obj = Proxy.newProxyInstance(s.getClass().getClassLoader(), s.getClass().getInterfaces(), p);
        ((SellFisher)obj).sellFish();
    }
}