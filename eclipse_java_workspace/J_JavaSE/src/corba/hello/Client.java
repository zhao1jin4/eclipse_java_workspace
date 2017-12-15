package corba.hello;

import java.awt.*;
import java.awt.event.*;

public class Client 
{
    static int
    run(org.omg.CORBA.ORB orb, String[] args)
	throws org.omg.CORBA.UserException
    {
	//
	// Get "hello" object
	//
	org.omg.CORBA.Object obj =
	    orb.string_to_object("relfile:/Hello.ref");
	if(obj == null)
	{
	    System.err.println("hello.Client: cannot read IOR from Hello.ref");
	    return 1;
	}

	Hello hello = HelloHelper.narrow(obj);

	//
	// Main loop
	//
	System.out.println("Enter 'h' for hello or 'x' for exit:");

	int c;

	try
	{
	    String input;
	    do
	    {
		System.out.print("> ");
		java.io.DataInputStream dataIn =
		    new java.io.DataInputStream(System.in);
		java.io.BufferedReader in = new java.io.BufferedReader(
		    new java.io.InputStreamReader(dataIn));
		input = in.readLine();

		if(input.equals("h"))
		    hello.say_hello();
	    }
	    while(!input.equals("x"));
	}
	catch(java.io.IOException ex)
	{
	    System.err.println("Can't read from `" + ex.getMessage() + "'");
	    return 1;
	}

	return 0;
    }

    public static void
    main(String args[])
    {
	int status = 0;
	org.omg.CORBA.ORB orb = null;
   // Set properties for ORB
	java.util.Properties props = System.getProperties();
	props.put("org.omg.CORBA.ORBClass", "com.ooc.CORBA.ORB");
	props.put("org.omg.CORBA.ORBSingletonClass",
		  "com.ooc.CORBA.ORBSingleton");

	try
	{   // Init ORB for client application
       orb = org.omg.CORBA.ORB.init(args, props);
       // Run the applciation
	    status = run(orb, args);
	}
	catch(Exception ex)
	{
	    ex.printStackTrace();
	    status = 1;
	}

	if(orb != null)
	{
	    //
	    // Since the standard ORB.destroy() method is not present in
	    // JDK 1.2.x, we must cast to com.ooc.CORBA.ORB so that this
	    // will compile with all JDK versions
	    //
	    try
	    {
	    	orb.destroy();
	    	orb.shutdown(true);
	    	
//	    	((com.ooc.CORBA.ORB)orb).destroy();
	    }
	    catch(Exception ex)
	    {
		ex.printStackTrace();
		status = 1;
	    }
	}

	System.exit(status);
    }
}
