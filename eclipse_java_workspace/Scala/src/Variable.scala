object VariableApp extends App 
{ 
  
   var  x=0
   var y=1
   //x=y=1  //报错因 y=1返回（）即Unit
    
   printf("hello %s","lisi") //支持%s
   var r:AnyRef=42.asInstanceOf[AnyRef] //类型转换
   
   
   lazy val rows=scala.io.Source.fromFile("c:/temp/hell.txt") //lazy 延迟初始化 每次都要检查是否初始化，不是那高效
   
   var √ =scala.math.sqrt _  // √ 可以做变量名,但要加空格  (微软拼音 按v 数学 找的)   
   var res = √ (4.0)
   println(res);
   //下面这些都可做变量名
   var ~ = -1
   var ! = 1   
   //var @ = 2 //不行
   //var # = 3 //不行
   var $ = 4
   var % = 5
   var ^ = 6
   var & = 7
   var * = 8
   var ** = 88
   var - = 11
   var + = 12
   var | = 13
   var / = 14
   var \ = 15
   var < = 1
   var <= = 1
   var > = 1
   var >= = 1
   var ? = 1
   //var : = 16//不行
  //()[]{}.,;'`"这些不行的
 
  
 //+-~! 是4个前置操作符
  var a=3;
  var x1= -a; //同   a.unary_-
  var x2= a.unary_-;
  var x3= a.unary_+;
  var x4= a.unary_~;
  var b=true;
  var x5=b.unary_!
 
  
  class User2
  {
    var age=3;
     def unary_!()//自己实现 !u
    {
      this.age+=10;
    }
//     def = (u:User )//不行的
//     {
//       this.age=u.age;
//     }
  }
   var u=new User2();
   !u;
   println(u.age);
    
    
  
    // 定义 Option
    val o1:Option[Int] = Some(5)
   
    // 创建两个不同类型元素的元组
    val t1 = (10, "Runoob") 
    
    
 
}