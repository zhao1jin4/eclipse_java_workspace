package corba.two;


/**
* simpleDemo/gridPOA.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 grid.idl
* 2010年11月1日 星期一 下午04时30分10秒 CST
*/

public abstract class gridPOA extends org.omg.PortableServer.Servant
 implements corba.two.gridOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_height", new java.lang.Integer (0));
    _methods.put ("_get_width", new java.lang.Integer (1));
    _methods.put ("set", new java.lang.Integer (2));
    _methods.put ("get", new java.lang.Integer (3));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // simpleDemo/grid/_get_height
       {
         short $result = (short)0;
         $result = this.height ();
         out = $rh.createReply();
         out.write_short ($result);
         break;
       }

       case 1:  // simpleDemo/grid/_get_width
       {
         short $result = (short)0;
         $result = this.width ();
         out = $rh.createReply();
         out.write_short ($result);
         break;
       }

       case 2:  // simpleDemo/grid/set
       {
         short row = in.read_short ();
         short col = in.read_short ();
         int value = in.read_long ();
         this.set (row, col, value);
         out = $rh.createReply();
         break;
       }

       case 3:  // simpleDemo/grid/get
       {
         short row = in.read_short ();
         short col = in.read_short ();
         int $result = (int)0;
         $result = this.get (row, col);
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:simpleDemo/grid:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public grid _this() 
  {
    return gridHelper.narrow(
    super._this_object());
  }

  public grid _this(org.omg.CORBA.ORB orb) 
  {
    return gridHelper.narrow(
    super._this_object(orb));
  }


} // class gridPOA
