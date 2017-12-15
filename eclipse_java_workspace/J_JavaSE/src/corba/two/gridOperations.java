package corba.two;


/**
* simpleDemo/gridOperations.java .
* 由 IDL-to-Java 编译器（可移植），版本 "3.2" 生成
* 来自 grid.idl
* 2010年11月1日 星期一 下午04时30分10秒 CST
*/

public interface gridOperations 
{
  short height ();
  short width ();
  void set (short row, short col, int value);
  int get (short row, short col);
} // interface gridOperations
