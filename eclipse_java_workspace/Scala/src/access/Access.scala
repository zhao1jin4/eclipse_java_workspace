
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
	 print(y);
}
	
	 
	