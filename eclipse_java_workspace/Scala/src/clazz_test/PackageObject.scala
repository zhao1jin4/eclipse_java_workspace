package clazz_test
package one //子级包 第二行 
import java.util.{Map => _, _} //引用了除Map之外的所有
import java.util.{HashMap => JavaHashMap} // 重命名成员

//默认就导入了下面三个
import scala._
import java.lang._
import Predef._

package object two   //要声明在package同级
{
  var defaultName="default";
}

package two  
{
  class Person
  {
    var name=defaultName;
  }
  abstract class pkg2 
  {
    var p: clazz_test.one.two.Person;
 
  }
}














