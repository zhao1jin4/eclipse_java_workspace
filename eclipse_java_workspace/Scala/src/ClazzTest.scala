

class Point(xc: Int, yc: Int) {
   var x: Int = xc
   var y: Int = yc

   def move(dx: Int, dy: Int) {
      x = x + dx
      y = y + dy
      println ("x 的坐标点: " + x);
      println ("y 的坐标点: " + y);
   }
}

class Location(  val xc: Int,   val yc: Int,  
   val zc :Int) extends Point(xc, yc){
   var z: Int = zc

   def move(dx: Int, dy: Int, dz: Int) {
      x = x + dx
      y = y + dy
      z = z + dz
      println ("x 的坐标点 : " + x);
      println ("y 的坐标点 : " + y);
      println ("z 的坐标点 : " + z);
   }
}


 

class ArrayElement( // 请注意，小括号
    val contents: Array[String] )  //即是构造参数,又是属性
    extends AnyRef
    
 class LineElement(s: String) extends ArrayElement(Array(s))//继承时初始化,像C++
 
    
object ClassTest {
   def main(args: Array[String]) {
      val loc = new Location(10, 20, 15);
      // 移到一个新的位置
      loc.move(10, 10, 5);
      
       val s="2123"; // val 与 var 不同的是, val指定了值后不能再次赋值,相当于final
      println(s);
      println(s);
      
      var x=new ArrayElement(Array("a","b"))
      println(x.contents mkString " , " )
      
   }
}

 