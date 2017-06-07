

object HelloWorld  { // object 是对单例类的对象
  def main(args: Array[String]): Unit = {
    
    
    
  if(args.length>0)
  {  println(args(0))  //数组使用()来取值,而且0开始的带的参数,不是程序名
     args.mkString("\n")
     assert(args==null)
  } 
  println("Hello, world!")
  var x=1
  var a=if(x>0)1 else 0  //if 可以有返回值
  var (m,n)=(1,2)  //多变量赋值
  
  var name="Lisi"
   println( name.exists(_.isUpper))
   var num =BigInt(5);//BigInt就是使用BigInteger实现的
   
   
  println(a)
 
  

   printPoint   
   
    def printPoint{ //单例对象
       println ("x 的坐标点 : ");
       println ("y 的坐标点 : " );
    }
  
  
  val name_ : Int=1;//变量名以_结尾的话 : 前要有空格
  
  
  
  
  }
 
  
}