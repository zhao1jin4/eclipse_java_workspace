

object FunctionTest extends App{
  
      printVeryChar("a","b","c")
     def printVeryChar(c:String*)=  //动态参数个数
     {
        c.foreach( (x:String) => println(x) ) //=>函数文本
        //c.foreach( x => println(x) )
        //c.foreach{ x => println(x) }
        //c.foreach(println)//如果只由一个参数
        
        for(x<-c) //for 遍历
          println(x)
     }
     printEveryInt(10 to 15:_*) // to 返回 Range.Inclusive 要用 :_*
     def printEveryInt(i:Int*) 
     { 
        println(i)
     }
       
    def echo(args: String*) = for (arg <- args) println(arg)
    val arr = Array("What's", "up", "doc?")
    
    echo(arr: _*)
    
      def defaultParam(name:String="world"):String= //参数默认值
     {
       return "hello " +name
     }
     println(defaultParam("wang"))
     println(defaultParam())
     
   def addInt( a:Int, b:Int ) : Int =  //定义函数,参数必须要有:及类型
   {
      var sum:Int = 0
      sum = a + b
       return sum  //可不写return
   }
     println(addInt(3,3))
     println(addInt(b=3,a=4)) //可以使用参数名修改顺序
     
    def add(x:Int) = (y:Int) =>x+y
    //简写法
    def add2(x:Int) (y:Int) =x+y //多参数写法2,如果函数只有一行代码可以不写{}
    println(add2(5)(3))
    
    def add=( a:Int, b:Int ) => a+b
    println(add(5,3))
    
    var addVar=( a:Int, b:Int ) => a+b
    println(addVar(5,3))
    
   def printMe( ) : Unit = {  //定义函数返回Unit 相当于void
      println("Hello, Scala!")
   }
   
   printMe  //无参数 调用 可以不加()
    def printMe2   = { //无参数 定义 可以不加()
      println("Hello, Scala2!")
   }
   printMe2
   
   
    
   var res=(1 to 9 ).reduceLeft( _*_)//(((1*2)*3)*4)
   println(res);
   
   def sum(a:Int,b:Int,c:Int)=a+b+c
   
   var func=sum _  //sum _自动产生的类里的apply方法，
   println(func(1,2,3)) 
   var func2=sum(1, _:Int,5)  //缺失参数
   println(func2(4));
}