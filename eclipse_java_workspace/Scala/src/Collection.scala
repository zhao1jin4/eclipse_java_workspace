import scala.collection.mutable.ArrayBuffer
import scala.util.Sorting
import scala.util.control.Breaks

object MainCollection{
  def main(args:Array[String]):Unit=
  {
     
     var a = 0;
     var numList = List(1,2,3,4,5,6);//List不可变的
     
      for( a <- 1 to 10){  //左箭头 <- 用于为变量 a 赋值 , to包括右边界10
         println( "Value of a: " + a );
      }
      for( a <- 1.to( 10)){    // str.subString(2) 也可以写成 str subString 2 形式
         println( "Value of a: " + a );
      }
      for( a <- 1 to 10 if a%2==0){    //条件
         println( "Value %2 : " + a );
      } 
      for( a <- 1 until 10){ //until不包括右边界10
         println( "Value of a: " + a );
      }
 /*    */ 
      
      var b = 0;
      for( a <- 1 to 3; b <- 1 to 3){ //两层子循环
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
  
       val filesHere = (new java.io.File("./src")).listFiles
      def fileLines(file: java.io.File) = scala.io.Source.fromFile(file).getLines.toList
      
      def grep(pattern: String) = 
         for { file <- filesHere 
                   if file.isFile; //多个条件用;
                   if file.getName.endsWith(".scala") 
               line <- fileLines(file)  //双重循环
                   trimmed = line.trim  //中间变量
                  if trimmed.matches(pattern)
          } 
         println(file + ": " + trimmed) 
       grep(".*gcd.*")
       
    
    var changeBuffer=ArrayBuffer [Int]()
    changeBuffer+=1
    changeBuffer+=2
    changeBuffer++=Array(3,4,5,6)
    changeBuffer.insert(0, 100,101)
    println(changeBuffer)
    changeBuffer.trimEnd(2) //删最后两个
    changeBuffer.remove(0)
     println(changeBuffer)
    var  array= changeBuffer.toArray
    
    for(i <-0 until (array.length,2)) //2步长
      print(array(i)+",")
   println("max="+array.max)       
   println("sum="+array.sum)
   Sorting.quickSort(array);
    
   println(array.mkString("---", ",","==="))
    
   
  var addArray= for(i <-array ) yield  i+10
   println(addArray.mkString( "," ))
   
   
   //----
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
   var map1 = Map("one" -> 1, "two" -> 2, "three" -> 3) //是immutable.Map 不可改变的
   // var map1 = Map[String,Int]()
    //map1 +=("four"->4)//加元素 , 生成一个新的Map
    map1 +=("four".->(4)) //实际上是方法名为  -> ,可写成"four".->(4)  , ->可以任何对象
    
    println("map1="+map1)

    println(map1("four"))//取元素 ,同     map1.apply("four")
     println(map1.getOrElse("nine", "empty")) 
     
    var mapChange= scala.collection.mutable.Map (("one" , 1), ("two", 2), ("three" , 3))
    mapChange("four")=400;//同  mapChange.update("four", 400);
    mapChange.update("four", 400);
    mapChange -=("four" ) 
    
    var mapSort= scala.collection.immutable.SortedMap("one" -> 1, "two" -> 2)
    
    
    
     val ita = Iterator(20,40,2,50,69, 90)
      val itb = Iterator(20,40,2,50,69, 90)
        println("ita.size 的值: " + ita.size )
      println("itb.length 的值: " + itb.length )
//      println("最大元素是：" + ita.max )//中能调用一次方法
//      println("最小元素是：" + itb.min )
     
   //----
      //Seq 是有顺序的，IndexedSeq 是有顺序的 ,可以用下标仿问的，如ArrayBuffer
        //LinkedHashSet 可以记住插入顺序
      //SortedSet //红黑树实现
  var list=List("one","two","three")
  list match
  {
    case Nil=>0
    case h::t=>println("head="+h+",tail="+t);  //head=one,tail=List(two, three)
  }

  
    
  import scala.collection.JavaConverters._

  val source = new scala.collection.mutable.ListBuffer[Int]
  val target: java.util.List[Int] = source.asJava
  val other: scala.collection.mutable.Buffer[Int] = target.asScala
  assert(source eq other)
   
   
   val vs = java.util.Arrays.asList("hi", "bye") 
  val ss = asScalaIterator(vs.iterator)
   val ss1 = asScalaBuffer(vs) 

   val it = Iterator("Baidu", "Google", "Runoob", "Taobao")
    while (it.hasNext){
        println(it.next())
    }
  
     //---
  }
}