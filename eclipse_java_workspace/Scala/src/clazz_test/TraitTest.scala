

package clazz_test

import scala.beans.BeanProperty
import java.util.Properties
import java.io.FileReader
import javax.swing.JFrame
import java.io.IOException
 

trait Equal {  //trait同JDK8的接口,但如果trait修改了，所有使用它的都要重新编译
  def isEqual(x: Any): Boolean
  def isNotEqual(x: Any): Boolean = !isEqual(x)
}
 abstract class Account
 {
   def save
 }
 class Point(xc: Int, yc: Int) extends Account with Equal { // 如已经有一个extends 后面就用with 一定以extends开始
//class Point(xc: Int, yc: Int) extends Equal { // 如已经有一个extends 后面就用with 一定以extends开始
   
   
   trait TraitException extends Exception
   {
     
   }
//   class MyFrame extends JFrame with TraitException //这里不能同时extends JFrame类和Exception类
//   {
//     
//   }
   class MyException extends IOException with TraitException  // IOException 和 TraitException都继承自Exception类是可以的
   {
   }
   
   
   @BeanProperty //生成getter/setter
  var x: Int = xc
  var y: Int = yc
  def isEqual(obj: Any) =
    obj.isInstanceOf[Point] &&   //Any 类型的特殊方法isInstanceOf
    obj.asInstanceOf[Point].x == x // Any 类型的强转
    
     def save //父类是abstract的，可以不加 override
   {
     println("save 100")
     println(classOf[Point]);//Predef 中的
   }
  def matchTest(x: Any ): Any  = x match {   //match 相当于switch
      case 1 => "one"
      case "two" => 2 //可是任意类型 ,每个case带break语意
      case y: Int => "scala.Int"+y  //可做类型匹配
      case _ => "many"  //最后的通配
   }

    
}

class NoParamClass{}

object Test2 {
   def main(args: Array[String])
   {
     val x=new   NoParamClass //无参数可不写()
     
      val p1 = new Point(2, 3)
      val p2 = new Point(2, 4)
      val p3 = new Point(3, 3)

      println(p1.isNotEqual(p2))
      println(p1.isNotEqual(p3))
      println(p1.isNotEqual(2))
    
       println( p1.save)
      println( p1.matchTest(1))
//      println( p1.matchTest("two"))
//      println( p1.matchTest(2))
//      println( p1.matchTest("test"))
      
      
      val firstArg = if (!args.isEmpty) args(0) else ""
        val friend = firstArg match   //match 同  if 可以有返回值
          { 
             case "salt" => "pepper" 
             case "chips" => "salsa" 
             case "eggs" => "bacon" 
             case _ => "huh?" 
          }
         println(friend)
       
   }
   
   
   
  
   
   
     
   
}