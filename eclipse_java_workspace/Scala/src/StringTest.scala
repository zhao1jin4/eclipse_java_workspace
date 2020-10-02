object StringTest extends App
{
    println( "hello"(4)) //输出 o
    //scala.collection.immutable.StringOps 的apply 方法  ,String 是隐式转换这个类
    println( "hello".intersect("World")) //lo  并不是相邻的
    println( "hello".distinct) //helo
     
    var num:BigInt=1234;
    println(num*num);//java要用multiply
    
     
  
}