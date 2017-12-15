package corba.helloApp;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
//������ֹ�д��
public class HelloClient
{
  static Hello helloImpl;

  public static void main(String args[])
    {
      try{
        // create and initialize the ORB
        ORB orb = ORB.init(args, null);

        // get the root naming context
        org.omg.CORBA.Object objRef =  orb.resolve_initial_references("NameService");//NameService,����ʹ��orbdҲ������"TNameService"��ʾ��Transient
        // Use NamingContextExt instead of NamingContext. This is 
        // part of the Interoperable naming Service.  
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
 
        // resolve the Object Reference in Naming
        String name = "Hello";
        helloImpl = HelloHelper.narrow(ncRef.resolve_str(name));

        System.out.println("Obtained a handle on server object: " + helloImpl);
        System.out.println(helloImpl.sayHello());//�����_HelloStub��sayHello����,����_invoke���� -> ����÷���˵�HelloPOA��_invoke����,���е����Լ�ʵ�ֵķ���(HelloPOATie)
        helloImpl.shutdown();//�ͻ��˵��÷�����˳�

        } catch (Exception e) {
          System.out.println("ERROR : " + e) ;
          e.printStackTrace(System.out);
          }
    }

}