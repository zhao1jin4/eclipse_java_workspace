package corba.two;

/**
* simpleDemo/gridHolder.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� grid.idl
* 2010��11��1�� ����һ ����04ʱ30��10�� CST
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
