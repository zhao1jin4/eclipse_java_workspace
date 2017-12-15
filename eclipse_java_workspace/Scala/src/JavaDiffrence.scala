 
object MainJavaDifference  {  
  def main(args: Array[String]): Unit = {
    
    
    def greet() {
      println("hi") 
   }
  println( greet() == () )//返回true
  
    var line = "" 
    while ((line = scala.io.StdIn.readLine()) != "") // 不起作用 
    println("Read: "+ line)
  //编译这段代码时，Scala会警告你使用!=比较类型为Unit和String的值将永远产生true。
    //而在Java里，赋值语句可以返回被赋予的那个值，同样情况下标准输入返回的一条记录在Scala的赋值语句中永远产生unit值，()。
     
  
  Thread.`yield`()  //yield关健字
  
  class WontCompile {
      private var f = 0 
      //def f = 1 // 编译不过，因为字段和方法重名,因为无参数函数，调用时可以不写（），如果这样就区分不出哪个了
   }
  
  //Scala类与Java类不同在于它们还继承自一个名为ScalaObject的特别的记号特质
  
  
  
  } 
}