package corba.two;


/**
* simpleDemo/gridHelper.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 grid.idl
* 2010年11月1日 星期一 下午04时30分10秒 CST
*/

abstract public class gridHelper
{
  private static String  _id = "IDL:simpleDemo/grid:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.two.grid that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.two.grid extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (corba.two.gridHelper.id (), "grid");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.two.grid read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_gridStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.two.grid value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static corba.two.grid narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.two.grid)
      return (corba.two.grid)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba.two._gridStub stub = new corba.two._gridStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static corba.two.grid unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.two.grid)
      return (corba.two.grid)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba.two._gridStub stub = new corba.two._gridStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
