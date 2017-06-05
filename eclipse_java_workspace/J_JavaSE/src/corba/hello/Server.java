package corba.hello;
import org.omg.PortableInterceptor.IORInterceptor_3_0;
public class Server {
	static int run(org.omg.CORBA.ORB orb, String[] args)
			throws org.omg.CORBA.UserException {
		IORInterceptor_3_0 x=null;
		//
		// 1. Resolve Root POA and Get a reference to the POA manager
		//
		//
		org.omg.PortableServer.POA rootPOA = org.omg.PortableServer.POAHelper
				.narrow(orb.resolve_initial_references("RootPOA"));

		//
		org.omg.PortableServer.POAManager manager = rootPOA.the_POAManager();

		//
		// 2. Create implementation object and Save reference
		//
		Hello_impl helloImpl = new Hello_impl();
		Hello hello = helloImpl._this(orb);
		//
		//
		try {
			String ref = orb.object_to_string(hello);
			String refFile = "Hello.ref";
			java.io.FileOutputStream file = new java.io.FileOutputStream(
					refFile);
			java.io.PrintWriter out = new java.io.PrintWriter(file);
			out.println(ref);
			out.flush();
			file.close();
		} catch (java.io.IOException ex) {
			System.err.println("hello.Server: can't write to `"
					+ ex.getMessage() + "'");
			return 1;
		}
		//
		// 3. Activate POA Manager to allow new requests to arrive
		//
		manager.activate();
		// 4. Give control to ORB and Run implementation
		orb.run();

		return 0;
	}

	public static void main(String args[]) {
		// Set properties for ORB environment
		java.util.Properties props = System.getProperties();
		props.put("org.omg.CORBA.ORBClass", "com.ooc.CORBA.ORB");
		props.put("org.omg.CORBA.ORBSingletonClass", "com.ooc.CORBA.ORBSingleton");

		int status = 0;
		org.omg.CORBA.ORB orb = null;

		try { // Init ORB for an server application
			orb = org.omg.CORBA.ORB.init(args, props);
			// Run the server application
			status = run(orb, args);
		} catch (Exception ex) {
			ex.printStackTrace();
			status = 1;
		}

		if (orb != null) {
			//
			// Since the standard ORB.destroy() method is not present in
			// JDK 1.2.x, we must cast to com.ooc.CORBA.ORB so that this
			// will compile with all JDK versions
			//
			try {
				 orb.destroy();
				orb.shutdown(true);
//				 ((com.ooc.CORBA.ORB)orb).destroy();

			} catch (Exception ex) {
				ex.printStackTrace();
				status = 1;
			}
		}

		System.exit(status);
	}
}
