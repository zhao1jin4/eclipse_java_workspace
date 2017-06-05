package corba.two;

import java.io.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;


public class gridServer {
  
  public static ORB global_orb;
  
  

  public static void main(String[] args) {

    final short width = 100;
    final short height = 100;
    
    // 1. Initalise ORB for an application and resolve RootPOA reference
    try{
    System.out.println("Initializing the ORB");
    global_orb = ORB.init (args,null);
    
     
    org.omg.CORBA.Object poa_obj = global_orb.resolve_initial_references("RootPOA");

    org.omg.PortableServer.POA root_poa = org.omg.PortableServer.POAHelper.narrow(poa_obj);
    
    // 2. Create servants, activate them, write their IORs to files.
    //

    System.out.println("Creating objects");
   
    gridImpl grid=new gridImpl(width,height);
     
    byte[] grid_oid = root_poa.activate_object(grid);
     
    export_object(root_poa, grid_oid, "../grid.ior", gridHelper.id());


     // 3. Activate the POA Manager to allow new requests to arrive
     //

      System.out.println("Activating the POA Manager");

      org.omg.PortableServer.POAManager poa_manager = root_poa.the_POAManager();

      poa_manager.activate();


      // 4. Give control to the ORB to let it process incoming requests
      //

      System.out.println("Giving control to the ORB to process requests");

      global_orb.run();

      System.out.println ("Done");

    }
    catch(org.omg.CORBA.ORBPackage.InvalidName ex)
    {
      System.out.println("Failed to obtain root poa " + ex );
    }
    catch(org.omg.PortableServer.POAPackage.WrongPolicy ex)
    {
      System.out.println("Invalid POA policy " + ex );
    }
    catch(org.omg.PortableServer.POAPackage.ServantAlreadyActive ex)
    {
      System.out.println("POA servant already active " + ex );
    }
    catch(org.omg.PortableServer.POAManagerPackage.AdapterInactive ex)
    {
      System.out.println("POAManager activate failed" + ex );
    }

   System.out.println("gridServer exiting...");
  }
  
   /**
   * This function takes a poa and object id, builds an object
   * reference representing that object, and exports the object
   * reference to a file.
   */

  public static void export_object(POA poa, byte[] oid, String filename, String type_id)
  {
      org.omg.CORBA.Object ref = poa.create_reference_with_id(oid, type_id);

      String stringified_ref = global_orb.object_to_string(ref);

      RandomAccessFile FileStream = null;
      try
      {
        FileStream = new RandomAccessFile(filename,"rw");
        FileStream.writeBytes(stringified_ref);
        FileStream.close();
      }
      catch(Exception ex)
      {
        System.out.println("Failed to write to " + filename );
      }
    }
 
}
