package corba.two;

/**
* simpleDemo/gridHolder.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 grid.idl
* 2010年11月1日 星期一 下午04时30分10秒 CST
*/

public final class gridHolder implements org.omg.CORBA.portable.Streamable
{
  public corba.two.grid value = null;

  public gridHolder ()
  {
  }

  public gridHolder (corba.two.grid initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = corba.two.gridHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    corba.two.gridHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return corba.two.gridHelper.type ();
  }

}
