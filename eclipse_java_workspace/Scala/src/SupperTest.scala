 
object SuperTest {
   def main(args: Array[String]) {
      
      var testSuper:MyQueue=new MyQueue()
      testSuper.put(-1);
      testSuper.put(0);
      testSuper.put(1);
      
      print("super res "+testSuper.buf.mkString(","))
      
   }
}


 
abstract class IntQueue //trait 或 abstract class 都可
{  
  def put(x: Int)
} 
trait Incrementing extends IntQueue
{ 
  abstract override def put(x: Int) //必须在trait中，必须是 abstract override 
  {
    super.put(x + 1) //在trait中super其实是子类的实现
  } 
}
trait Filtering extends IntQueue 
{ abstract override def put(x: Int) 
  {
    if (x >= 0)
      super.put(x)
  }
}
import scala.collection.mutable.ArrayBuffer
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
//即当数为0时,Filter未调到super,即当数为1时,Filter 调到super再向左调用Incrementing，因又调了super，再向左调BasicIntQueue







