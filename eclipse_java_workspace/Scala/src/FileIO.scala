import java.io.PrintWriter
import java.io.File
import java.nio.file.Files
import java.nio.file.SimpleFileVisitor
 


object FileIO {
  def main(args: Array[String]): Unit = {
      print("请输入  : " )
        //  val line1 = Console.readLine //老的
      val line =  scala.io.StdIn.readLine   //()可以省略 
      println("谢谢，你输入的是: " + line)
      
      
        print("请输入 Num : " )
       var numRead=scala.io.StdIn.readInt();
       println("谢谢，你输入的是: " + numRead)
       
      var source=scala.io.Source.fromFile("c:/tmp/hello_utf8.txt" )
      
      //因.scala文件是UTF8的,中文文件件名也是UTF8的
      source.foreach{ 
         print 
      }
      
      source.close();
      
     var strArray= scala.io.Source.fromString("123 1234").mkString.split("\\s+");
     var nums=strArray.map(_.toDouble);
     
      
      for(line <-scala.io.Source.fromURL("http://www.hao123.com").getLines() )
        //getLines返回Iterator[String] 用<-来遍历 
       { 
         println(line.length + ":" + line) 
      }
      
      for(line <-scala.io.Source.fromFile("c:/tmp/hello_utf8.txt" ).getLines().toList)
      {
         println("-"*4 +line);// 字符可以*
      }
      
      
      //scala 没有提供读二进制文件功能,和写文件功能  ，要用java
      
     
      
      var out= new PrintWriter("c:/tmp/out.txt");
      out.printf("%5d %10.2f\n",20.asInstanceOf[AnyRef],34.5.asInstanceOf[AnyRef]);//一定要转换为AnyRef
      out.write( "%5d %10.2f\n".format(20,34.5));//更好用
      out.close();
        
 
      
      def subdirs(dir:File):Iterator[File]=
      {
        var children=dir.listFiles().filter(_.isDirectory());
        children.toIterator ++ children.toIterator.flatMap(subdirs _) // Iterator 可以 ++ 
      }
      for(d <- subdirs(new File("c:/tmp")))
          println(d);
      
      
      
       
  }
  
  
  @SerialVersionUID(12L) //可以不写就生成默认的
  class User extends Serializable{
    
  }
}