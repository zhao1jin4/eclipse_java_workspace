package corba.helloApp;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
//这个文件是自己手写的
class HelloImpl extends HelloPOA {
	  private ORB orb;

	  public void setORB(ORB orb_val) {
	    orb = orb_val; 
	  }
	    
	  // implement sayHello() method
	  public String sayHello() {
	    return "\nHello world !!\n";
	  }
	    
	  // implement shutdown() method
	  public void shutdown() {
	    orb.shutdown(false);//服务端退出,为客户端调用
	  }
	}
public class HelloServer {

  public static void main(String args[]) {
    try{
      // create and initialize the ORB
      ORB orb = ORB.init(args, null);

      // get reference to rootpoa and activate the POAManager
      POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      rootpoa.the_POAManager().activate();

      // create servant and register it with the ORB
      HelloImpl helloImpl = new HelloImpl();
      helloImpl.setORB(orb); 


      // get object reference from the servant
      //--使用idlj -fall Hello.idl对应的方法
//      org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
//      Hello href = HelloHelper.narrow(ref);
      
    //--使用idlj -fallTIE Hello.idl 对应的方法
      // create a tie, with servant being the delegate.
      HelloPOATie tie = new HelloPOATie(helloImpl, rootpoa);
      // obtain the objectRef for the tie
      // this step also implicitly activates the object
      Hello href = tie._this(orb);
    //--
      
      // get the root naming context
      org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");//NameService,对于使用orbd也可以用"TNameService"表示是Transient
      // Use NamingContextExt which is part of the Interoperable
      // Naming Service (INS) specification.
      NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

      // bind the Object Reference in Naming
      String name = "Hello";
      NameComponent path[] = ncRef.to_name( name );
      ncRef.rebind(path, href);

      System.out.println("HelloServer ready and waiting ...");

      // wait for invocations from clients
      orb.run();//会一直阻塞,除非调用  orb.shutdown(
    } 
        
      catch (Exception e) {
        System.err.println("ERROR: " + e);
        e.printStackTrace(System.out);
      }
          
      System.out.println("HelloServer Exiting ...");
        
  }
}