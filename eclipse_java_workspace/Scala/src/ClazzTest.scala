

class Point(xc: Int, yc: Int) {
   var x: Int = xc
   var y: Int = yc

   def move(dx: Int, dy: Int) {
      x = x + dx
      y = y + dy
      println ("x 的坐标点: " + x);
      println ("y 的坐标点: " + y);
   }
}

class Location(  val xc: Int,   val yc: Int,  
   val zc :Int) extends Point(xc, yc){
   var z: Int = zc

   def move(dx: Int, dy: Int, dz: Int) {
      x = x + dx
      y = y + dy
      z = z + dz
      println ("x 的坐标点 : " + x);
      println ("y 的坐标点 : " + y);
      println ("z 的坐标点 : " + z);
   }
}


 

class ArrayElement( // 请注意，小括号
    val contents: Array[String] )  //即是构造参数,又是属性
    extends AnyRef
    
 class LineElement(s: String) extends ArrayElement(Array(s))//继承时初始化,像C++
 
    
object Test {
   def main(args: Array[String]) {
      val loc = new Location(10, 20, 15);
      // 移到一个新的位置
      loc.move(10, 10, 5);
      
       val s="2123"; // val 与 var 不同的是, val指定了值后不能再次赋值,相当于final
      println(s);
      println(s);
      
      var x=new ArrayElement(Array("a","b"))
      println(x.contents mkString " , " )
     
      
      var testSuper:MyQueue=new MyQueue()
      testSuper.put(-1);
      testSuper.put(0);
      testSuper.put(1);
      
      print("super res "+testSuper.buf.mkString(","))
      
   }
}


 
abstract class IntQueue {  //trait 或 abstract class 都可
  def put(x: Int)
} 
trait Incrementing extends IntQueue
{ abstract override def put(x: Int) {//必须在trait中，必须是 abstract override 
  super.put(x + 1) //在trait中super其实是子类的实现
  } 
}
 
trait Filtering extends IntQueue 
{ abstract override def put(x: Int) 
  { if (x >= 0) super.put(x) }
}

import scala.collection.mutable.ArrayBuffer
class BasicIntQueue   extends IntQueue  //也要 extends IntQueue 
{
  val buf = new ArrayBuffer[Int]
  def put(x: Int) { 
    buf+=x
    println(" put in BasicIntQueue ")
  } 
}

class MyQueue extends BasicIntQueue with Incrementing with Filtering //1,2
//class MyQueue extends BasicIntQueue with Filtering with Incrementing   // 0,1,2
//次序非常重要。 最右侧特质的方法首先被调用。如果那个方法调用了super，它调用其左侧特质的方法，   







