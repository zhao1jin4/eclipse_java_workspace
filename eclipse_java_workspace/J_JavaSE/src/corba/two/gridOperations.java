package corba.two;


/**
* simpleDemo/gridOperations.java .
* �� IDL-to-Java ������������ֲ�����汾 "3.2" ����
* ���� grid.idl
* 2010��11��1�� ����һ ����04ʱ30��10�� CST
*/

public interface gridOperations 
{
  short height ();
  short width ();
  void set (short row, short col, int value);
  int get (short row, short col);
} // interface gridOperations
