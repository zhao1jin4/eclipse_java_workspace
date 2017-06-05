package corba.helloApp;


/**
* corba/helloApp/HelloHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Hello.idl
* Thursday, August 23, 2012 4:27:49 PM GMT+08:00
*/

abstract public class HelloHelper
{
  private static String  _id = "IDL:corba/helloApp/Hello:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.helloApp.Hello that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.helloApp.Hello extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (corba.helloApp.HelloHelper.id (), "Hello");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.helloApp.Hello read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_HelloStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.helloApp.Hello value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static corba.helloApp.Hello narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.helloApp.Hello)
      return (corba.helloApp.Hello)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba.helloApp._HelloStub stub = new corba.helloApp._HelloStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static corba.helloApp.Hello unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.helloApp.Hello)
      return (corba.helloApp.Hello)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba.helloApp._HelloStub stub = new corba.helloApp._HelloStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
