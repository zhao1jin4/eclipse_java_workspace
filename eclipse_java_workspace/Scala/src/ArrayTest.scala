import scala.util.control._
import Array._
object ArrayTest extends App
{
  
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
      
      var twoMatrix=new Array[Array[Int]](10);
      for( i <- 0 until  twoMatrix.length)
      {
        twoMatrix(i)=new Array[Int](i+1)//第二维是变长的
      }
      
      var myMatrix = ofDim[Int](3,3) //Array.ofDim 二维数组
      
      // 创建矩阵
      for (i <- 0 to 2) {
         for ( j <- 0 to 2) {
            myMatrix(i)(j) = j;
         }
      }
      
       var myList1 = Array(1.9, 2.9, 3.4, 3.5)
      var myList2 = Array(8.9, 7.9, 0.4, 1.5)
       var myList3 =myList1 ++ myList2     //ArrayOps
     // var myList3 =  concat( myList1, myList2) //合并数组
      for ( x <- myList3 ) {
         println( x )
      }
      
       var zipList=for ( (item1, itme2) <-  myList1 zip  myList2) yield item1 + itme2
       println(zipList mkString "_" )
           
      // range() 方法最后一个参数为步长，默认为 1：
      var myList11 = range(10, 20, 2)
      var myList22 = range(10,20)
     for ( x <- myList11 ) {
         print( " " + x )
      }
      println()
      for ( x <- myList22 ) {
         print( " " + x )
      }
}