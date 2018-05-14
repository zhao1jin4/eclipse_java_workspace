

object ClosePackage extends App{
    //----函数返回值是一个函数
   def plusBy2(x:Double)=(y:Double)=>x+y
   println( plusBy2(2.0)(3.0) )
   //简写方式
   def plusBy(x:Double)(y:Double) = x+y
   println(  plusBy(2.0)(3.0) )
   
   
    def curriedSum2(x: Int)=(y: Int) => x + y//全写
   println(curriedSum2(1)(2))
   val onePlus2 = curriedSum2(1)//这种不要占位
   
   def curriedSum(x: Int)(y: Int) = x + y
    val onePlus = curriedSum(1)_   //_是 第二个参数列表的占位
    println( onePlus(2) )
    
   
    //----闭包     函数返回值是一个函数 可以把变量保存起来
    var factor = 3  
    val multiplier = (i:Int) => i * factor   //闭包,函数指针多了一个可以保存变量 ,这里是用=>
    println( "muliplier(1) value = " +  multiplier(1) )  
 
    //函数返回值是一个闭包 
    def mulBy(input:Double) = (x:Double)=>input*x
    var closeFunc= mulBy(30);
    println( closeFunc(3));
    println( mulBy(2)(5) )//调用方式 2
    
  
    def makeIncreaser(more: Int) = (x: Int) => x + more//每次函数被调用时都会创建一个新闭包
    val inc1 = makeIncreaser(1)
    println(inc1(10))
     
    
    //------------参数   是一个函数
    
    def valueAtOneQuater(f:(Double)=>Double) = //参数   是一个函数(函数指针) 是高价函数
    {
        println(f(0.25)) 
    }
    valueAtOneQuater(Math.ceil _)
    valueAtOneQuater((num:Double)=> Math.ceil(num))
    valueAtOneQuater(x=>x*3) //如参数在=>只出现一次可用 _
    valueAtOneQuater(_ *3)
    
    def matchStr( f: (String,String)=>Boolean) :Unit=
    {
      if( f("hello","HELLO") )
      {
        println("match");
      }else
         println("not match");
    }
    matchStr( (a:String,b:String)=>a.equalsIgnoreCase(b) )
    matchStr(  _.equalsIgnoreCase(_) )
    
    
    def twice(op: Double => Double, x: Double) = op(op(x))
     println(   twice(x => x + 1, 5) )
     println(   twice(_ + 1, 5) )//最简式
    
     
    //------------ 
    
   val f = (_: Int) + (_: Int)//将扩展成带两个参数的函数
   println(f(5, 10))
       
    def sum(a: Int, b: Int, c: Int) = a + b + c
    val a = sum _ //三个缺失整数参数
    println(  a(1, 2, 3) )  //实际上是应用了apply方法
    println( a.apply(1, 2, 3) )
    val b = sum(1, _: Int, 3)
    println( b(2) )

     //------------ 
   
    
    
    
}