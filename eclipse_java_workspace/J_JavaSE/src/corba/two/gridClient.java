package corba.two;

import java.io.*;
import org.omg.CORBA.*;

/*
 * The javaclient will connect to the Grid server and invoke
 * operations enabling it to manipulate a 2 dimensional array.
 */
public class gridClient
{
  public static void main (String args[])
  {
    System.out.println ("The Client begin...\n");
   
    ORB orb=null;
    grid gridProxy = null;
    grid gridProxy2=null; 
    org.omg.CORBA.Object obj_ref=null;
    /*
     * Initalise the ORB for an Orbix application.
     */
  
    try {
    orb=ORB.init (args,null);

    /*
     * Establish a CORBA connection with the grid server and
     * return a proxy for it.
    */
      
   
    obj_ref=import_object(orb,"../grid.ior");	 
    gridProxy = gridHelper.narrow (obj_ref);
    gridProxy2 = gridHelper.narrow(obj_ref);
     /*
     * Get the width and height by calling the remote
     * width and height operations on the grid server.
     */
    short width  = 0;
    short height = 0;

    /*
     * Set some values in the remote grid.
     */
    short  x_coord = 2;
    short  y_coord = 4;
    try {

     gridProxy.set (x_coord, y_coord, 123);
     gridProxy2.set (x_coord, y_coord, 111);

    }
    catch (SystemException ex) {
      System.out.println ("FAIL\tException setting values in the grid.");
      System.out.println (ex.toString());
      System.exit (1);
    }

    /*
     * Check the values placed in the grid
     * were set correctly.
     */
    int val = 0,val2=0;

    x_coord = 2; y_coord = 4;
    val = gridProxy.get (x_coord, y_coord);
    val2 = gridProxy2.get (x_coord, y_coord);
    
    System.out.println ("Value for grid[2,4] is " + val);
    System.out.println ("Value2 for grid[2,4] is " + val2);

         
    }
    catch (org.omg.CORBA.SystemException ex)
    {
      System.err.println(ex);      
      System.exit(ex.minor);
    }
    catch (Exception ex)
    {
      System.err.println(ex);
      System.exit(1);
    }
    finally
    {
      if (orb != null)
      { 
        try { orb.shutdown(true); } catch (Exception ex) { /* do nothing */ }
      }
    }
     
    System.out.println ("\nGrid demo finished.");
  }
  
  public static org.omg.CORBA.Object import_object(ORB orb, String filename) 
  {
    String ior = null;
    RandomAccessFile FileStream = null;

    System.out.println("Reading object reference from " + filename);

    try 
    {
      FileStream = new RandomAccessFile(filename,"r");
      ior = FileStream.readLine();
      return orb.string_to_object(ior);
    }
    catch(Exception e)
    {
      System.out.println("Error opening file " + filename);
      return null;
    }
  }

}
