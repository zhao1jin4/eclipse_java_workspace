import scala.util.control._
import Array._
// 就不要main方法 了
object HelloApp extends App { 
  println("Hello, App!") //可不写分号
  

  
     var a = 0;
     var numList = List(1,2,3,4,5,6);//List不可变的
      for( a <- 1.to( 10)){    // str.subString(2) 也可以写成 str subString 2 形式
         println( "Value of a: " + a );
      }
      for( a <- 1 to 10 if a%2==0){    //条件
         println( "Value %2 : " + a );
      }
      var s:String=_     // _ 表示 未给赋初值,声明要加类型
      

      for( a <- 1 to 10){  //左箭头 <- 用于为变量 a 赋值 , to包括右边界10
         println( "Value of a: " + a );
      }
      
      
      for( a <- 1 until 10){ //until不包括右边界10
         println( "Value of a: " + a );
      }
 /* 
      
      var b = 0;
      for( a <- 1 to 3; b <- 1 to 3){
         println( "Value of a: " + a );
         println( "Value of b: " + b );
      }
    
   

      for( a <- numList ){
         println( "Value of a: " + a );
      }
      for( a <- numList
           if a != 3; if a < 8 ){//过滤数据
         println( "Value of a: " + a );
      }
      
      
      var retVal = for{ a <- numList 
                        if a != 3; if a < 8
                      }yield a   // 保存在集合中，循环结束后将返回该集合。 
      // 输出返回值
      for( a <- retVal){
         println( "Value of a: " + a );
      }
    
                      
      val loop = new Breaks;  //break方式scala.util.control.Breaks
      loop.breakable {
         for( a <- numList){
            println( "Value of a: " + a );
            if( a == 4 ){
               loop.break;
            }
         }
      }
 */       
      
      printVeryChar("a","b","c")
      
     def printVeryChar(c:String*)=  //动态参数个数
     {
        c.foreach( (x:String) => println(x) ) //=>函数文本
//      c.foreach( x => println(x) )
        //c.foreach(println)//如果只由一个参数
        
        //c.foreach{ x => println(x) }
        
        for(x<-c) //for 遍历
          println(x)
     }
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
   
   var factor = 3  
    val multiplier = (i:Int) => i * factor   //闭包,函数指针,这里是用=>
    println( "muliplier(1) value = " +  multiplier(1) )  
 
 
    val buf = new StringBuilder;
    buf += 'a' //char 用一个+
    buf ++= "bcdef"  //String的字串用两个+
    println( "buf is : " + buf.toString );
    
    println("hello".concat(" world ")) //字串连接
    println("hello"+" world ")  //字串连接
      
    var formatStr= printf("inteter value =%d ,buf=%s",factor,buf);//格式化字串      
    
    var i=3;
    i+=1;
    i=i+1;
   //scala中没有++i,也没有i++,一定要发用i+=1或者i=i+1
    
    var z:Array[String] = new Array[String](3) //声明方式1
    var z1 = new Array[String](3)//声明方式2
    z(0) = "Runoob"; //同 z.update(0, "Runoob");
    z(1) = "Baidu"; z(4/2) = "Google"
   
    var z3 = Array("Runoob", "Baidu", "Google") //实际上调用apply方法的可变参数
      
    var myList = Array(1.9, 2.9, 3.4, 3.5)
      
      // 输出所有数组元素
      for ( x <- myList ) {
         println( x )
      }

      // 计算数组所有元素的总会
      var total = 0.0;
      for ( i <- 0 to (myList.length - 1)) {
         total += myList(i);
      }
      println("总和为 " + total);
      
      
      var myMatrix = ofDim[Int](3,3) //Array.ofDim 二维数组
      
      // 创建矩阵
      for (i <- 0 to 2) {
         for ( j <- 0 to 2) {
            myMatrix(i)(j) = j;
         }
      }
      
       var myList1 = Array(1.9, 2.9, 3.4, 3.5)
      var myList2 = Array(8.9, 7.9, 0.4, 1.5)

      var myList3 =  concat( myList1, myList2) //合并数组
      for ( x <- myList3 ) {
         println( x )
      }
       
       
      // range() 方法最后一个参数为步长，默认为 1：
      var myList11 = range(10, 20, 2)
      var myList22 = range(10,20)
     for ( x <- myList1 ) {
         print( " " + x )
      }
      println()
      for ( x <- myList2 ) {
         print( " " + x )
      }
      
      
      // 定义整型 List
    var x1 = List(1,2,3,4)  //List不可变的
    var x12 = List(6,7,8)  
    var x22 = x1:::x12;//List有个叫“:::”的方法实现叠加功能
    println(x22)
      
    var theList =77::x12 //::把新元素放在已有List之前，返回结果List
    var rlist=1::2::3::Nil //Nil有:: ,右操作数
    println(rlist)
    
    println(theList(2));//下标0开头
    //List没有append，使用：： 再reverse 或ListBuffer 提供append再toList
    //scala.collection.mutable.ListBuffer 
     println( theList.reverse)
     println(theList.isEmpty) 
     println(theList.length) 
     println(theList.head)//返回第一个
     println(theList.last)//返回最后一个
     println(theList.init)//返回除最后一个以外
     println(theList.tail)//返回除第一个以外
     println(theList.drop(2));//返回去前2个的列表
     println(theList.dropRight(2));//返回去后2个的列表
     println(theList.map(s=> s+1));
     println(theList.count(s=> s>7));
     println(theList.filter(s=> s>7));
     println(theList.mkString(","));
    println(theList.sortWith((s,t)=> s<t));//小到大
     println(theList.forall(s=>s>7));//全部成功返回true
     println(theList.exists(s=>s>7))
    theList.foreach(n=>print(n+" "));
    
     
    // 定义 Set
    var set1 = Set(1,3,5,7) //调用apply方法
    set1+=8 //加元素，可变和不可变都有+= ,不变的返回新的
    set1.+=(9) //也可这样写
   println("set1="+set1)
    
    // 定义 Map
   //var map1 = Map("one" -> 1, "two" -> 2, "three" -> 3)
    var map1 = Map[String,Int]()
    //map1 +=("four"->4)//加元素
    map1 +=("four".->(4)) //实际上是方法名为  -> ,可写成"four".->(4)  , ->可以任何对象
    println("map1="+map1)
    println(map1("four"))//取元素
    
    // 创建两个不同类型元素的元组
    val x4 = (10, "Runoob")
    
    // 定义 Option
    val x5:Option[Int] = Some(5)
   val it = Iterator("Baidu", "Google", "Runoob", "Taobao")
      
      while (it.hasNext){
         println(it.next())
      }
   

     val ita = Iterator(20,40,2,50,69, 90)
      val itb = Iterator(20,40,2,50,69, 90)
        println("ita.size 的值: " + ita.size )
      println("itb.length 的值: " + itb.length )
//      println("最大元素是：" + ita.max )//中能调用一次方法
//      println("最小元素是：" + itb.min )
     

}
