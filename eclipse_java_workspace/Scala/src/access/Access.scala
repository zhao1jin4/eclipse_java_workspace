
package access
class Outer{
    class Inner{
    private def f(){println("f")}
    class InnerMost{
        f() // 正确
        }
    }
   // (new Inner).f() //错误 ,在嵌套类情况下，外层类甚至不能访问被嵌套类的private成员
}


// 没有指定任何的修饰符，则默认为 public
// protected 成员 因为它只允许保护成员在定义了该成员的的类的子类中被访问。
//而在java中，用protected关键字修饰的成员，除了定义了该成员的类的子类可以访问，同一个包里的其他类也可以进行访问。
class Super
{
  protected def f() {println("f")}
}
class Sub extends Super
{
    f()
}

//Scala没有静态成员,单例对象用object关键字替换了class ,定义object 名与class名相同时叫伴生对象,不与伴生同名的对象叫孤立对象
//单例对象不带参数,不能用new 
class Other
{
		//(new Super).f() //错误

  
	  def add(b: Byte): Unit = {//方法参数的一个重要特征是它们都是val
     // b += 1 // 编译不过，因为b是val 
	   b+1 //如果没有发现任何显式的返回语句，Scala方法将返回方法中最后一个计
    }
	  
	 def f(): Unit = "this String gets lost"// 可以把任何类型转换为Unit
   def g() { "this String gets lost too" } //没有 = ,定义结果类型为Unit 
	 def h() = { "this String gets returned!" }//有= ,返回 类型为String
	 
	
	 
}

object AccessApp extends App { 
   var x=1;
	 var y=2;
	 y=x +  //以 + 结尾表示命令并没有结束 ,比字串连接
	 2
	 println(y);
	 
	  	def processFile(filename: String, width: Int) 
     	{ 
     	  def processLine(filename:String, width:Int, line:String) //把函数定义在另一个函数中,仅在包含它的代码块中可见
       	{ 
     	    if (line.length > width) print(filename+": "+line) 
     	  } 
       	val source = scala.io.Source.fromFile(filename) 
       	for (line <- source.getLines)
       	{ 
       	  processLine(filename, width, line)
       	}
      }
	  	
	  	
     val f = (_: Int) + (_: Int)//将扩展成带两个参数的函数
     println(f(5, 10))
     
     
    def curriedSum(x: Int)(y: Int) = x + y
    val onePlus = curriedSum(1)_   //_是 第二个参数列表的占位
    println( onePlus(2) )
    
    def twice(op: Double => Double, x: Double) = op(op(x))
     println(   twice(x => x + 1, 5) )
     println(   twice(_ + 1, 5) )//最简式
    
    
    def sum(a: Int, b: Int, c: Int) = a + b + c
    val a = sum _ //三个缺失整数参数
    println(  a(1, 2, 3) )  //实际上是应用了apply方法
    println( a.apply(1, 2, 3) )
    val b = sum(1, _: Int, 3)
    println( b(2) )

    
    def makeIncreaser(more: Int) = (x: Int) => x + more//每次函数被调用时都会创建一个新闭包
    val inc1 = makeIncreaser(1)
    println(inc1(10))
    
    
    
    private def filesHere = (new java.io.File("./src")).listFiles
    def filesMatching(query: String, matcher: (String, String) => Boolean) =
    {
        for (file <- filesHere; if matcher(file.getName, query)) yield file
    }
    def filesEnding(query: String) = filesMatching(query, _.endsWith(_))//第一个 _ 对应matcher的第一个参数file.getName,等同下面
    println(filesEnding(".scala").mkString("\n"))
    
    def filesEnding2=(fileName: String, query: String) => fileName.endsWith(query)//同上面
    println(filesMatching(".scala",filesEnding2).mkString("\n"))
    
    
    def withPrintWriter(file: java.io.File)(op: java.io.PrintWriter => Unit)
    { val writer = new java.io.PrintWriter(file)
      try { op(writer) } finally { writer.close() } 
    }
    val file = new java.io.File("c:/temp/date.txt") 
    withPrintWriter(file) { writer => writer.println(new java.util.Date) } //参数可以用{}传
    
    
    var assertionsEnabled = true 
    def myAssert(predicate: () => Boolean) =   //叫名参数
      if (assertionsEnabled && !predicate()) 
        throw new AssertionError
    
    myAssert(() => 5 > 3)//
    //myAssert(5 > 3)  //编译报错
    
    def byNameAssert(predicate: => Boolean) =   //省了() ，但还和平常的多了=> ,有先后的差别
    { print("in byNameAssert")
      if (assertionsEnabled && !predicate) throw new AssertionError //这里第二步才开始产生true
    }  
    byNameAssert(5 > 3) 
    
    def boolAssert(predicate: Boolean) = 
    {     print("in boolAssert")
        if (assertionsEnabled && !predicate) throw new AssertionError
    }
     boolAssert(5 > 3)//这里第一步产生true
     /*
     boolAssert(x / 0 == 0)
     byNameAssert(x / 0 == 0)
     */
     
}
	
	 
	