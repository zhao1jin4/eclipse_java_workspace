package clazz_test

import java.io.PrintStream
import scala.beans.BeanProperty
import java.util.Properties
import java.io.FileReader

object ContructorTest extends App {
  
  println(new Ant().env.length)
  
  var ant:Ant=new Ant();
  ant.synchronized{//同java synchronized 关健字
     ant.age+=2;
  }
  
  
  
}

class Creture
{
  
  val range:Int =10
  var env:Array[Int]=new Array[Int](range);
}
//class Ant extends Creture
//{
//  override val range:Int=2; //没有效果,env长度是0
//}
 // scalac 中加 -Xcheckinit  未初始化字段被仿问就抛异常

class Ant extends  
{
   override val range:Int=2; //OK
   var age:Int=20;
   
   var fileName:String="";
   
   lazy val out=new PrintStream(fileName); //lazy 每次都要检查是否初始化，不是那高效
} with Creture






 class Persion(@BeanProperty var age:Int)//主构造器 也可 private
   {
      var _name:String="";
     def this(name:String)//辅助构造器
     {
       this(20);//调用主构造器,必须第一行调用
       _name=name;
       println(age);
     }
   }
   
   class MyProg
   {
     private val pros=new Properties;
     pros.load(new FileReader("my.properties"));
     //以上是主构造器
     
      def this(name:String)//辅助构造器
     {
       this();
       println("main contructor")
      
     }
   }
   //new MyProg("lisi");
   



