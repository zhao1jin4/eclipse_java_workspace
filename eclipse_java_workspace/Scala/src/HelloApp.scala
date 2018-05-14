import scala.util.control._
import Array._

// 就不要main方法 了,如果想访问命令行参数的话就不能用它
object HelloApp extends App 
{ 
  println("Hello, App!") //可不写分号
  

  val a123 = 1;
  { val a123 = 2 // 编译通过, 可以在一个内部范围内定义与外部范围里名称相同的变量
    println(a123) 
  }
  
    var s:String=_     // _ 表示 未给赋初值,声明要加类型
    
      
    val buf = new StringBuilder;
    buf += 'a' //char 用一个+
    buf ++= "bcdef"  //String的字串用两个+
    println( "buf is : " + buf.toString );
    
    println("hello".concat(" world ")) //字串连接
    println("hello"+" world ")  //字串连接
      
    var formatStr= printf("inteter value =%d ,buf=%s",20,buf);//格式化字串      
    
    var i=3;
    i+=1;
    i=i+1;
   //scala中没有++i,也没有i++,一定要发用i+=1或者i=i+1
     
    
      println( "%5d 10.2f".format(20,34.5));
      printf( "%5d 10.2f",20,34.5);
       
       
}
