 
package clazz_test
import scala.collection.mutable.ArrayBuffer
object SuperTest {
   def main(args: Array[String]) {
      
      var testSuper:MyQueue=new MyQueue()
      testSuper.put(-1);
     // testSuper.put(0);
     // testSuper.put(1);
      
      print("super res "+testSuper.buf.mkString(","))
      
   }
}


 
abstract class IntQueue //trait 或 abstract class 都可
{  
   println("constructor IntQueue");
   
  def put(x: Int)
} 
trait Incrementing extends IntQueue
{ 
  println("constructor Incrementing"); // trait 构造器
     
  abstract override def put(x: Int) //必须在trait中，必须是 abstract override 
  {
    println("increment before put");
    super.put(x + 1) //在trait中super其实是子类的实现
    println("increment after put");
  } 
}
trait Filtering extends IntQueue 
{ 
   println("constructor Filtering");
  abstract override def put(x: Int) 
  {
   println("Filtering before put");
    if (x >= 0)
      super.put(x) //同 super[IntQueue].put(x);
    println("Filtering after put");
  }
}
class BasicIntQueue   extends IntQueue  //也要 extends IntQueue 
{
  val buf = new ArrayBuffer[Int]
  def put(x: Int)
  { 
    buf+=x
    println(" put in BasicIntQueue ")
  } 
}
class MyQueue extends BasicIntQueue with Incrementing with Filtering //1,2
//class MyQueue extends BasicIntQueue with Filtering with Incrementing   // 0,1,2
//次序非常重要。 最右侧特质的方法首先被调用。如果那个调用了super，它调用其左侧特质的方法
//如果没有调用super就不会调用左侧的了
//即当数为0时,Filter未调到super,即当数为1时,Filter 调到super再向左调用Incrementing，因又调了super，再向左调BasicIntQueue


//构造从左到右, 如extends 多个trait 共用 一个trait,已经被构造的,不会再次构造了





