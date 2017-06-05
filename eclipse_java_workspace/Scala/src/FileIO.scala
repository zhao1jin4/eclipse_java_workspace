

object FileIO {
  def main(args: Array[String]): Unit = {
      print("请输入  : " )
        //  val line1 = Console.readLine //老的
      val line =  scala.io.StdIn.readLine   //()可以省略 
      println("谢谢，你输入的是: " + line)
      
      
      //因.scala文件是UTF8的,中文文件件名也是UTF8的
       scala.io.Source.fromFile("c:/tmp/hello_utf8.txt" ).foreach{ 
         print 
      }
      
      for(line <- scala.io.Source.fromFile("c:/tmp/hello_utf8.txt" ).getLines() )
        //getLines返回Iterator[String] 用<-来遍历 ,空行就没有了???
       { 
         println(line.length + ":" + line) 
      }
      
      for(line <-scala.io.Source.fromFile("c:/tmp/hello_utf8.txt" ).getLines().toList)
      {
         println("-"*4 +line);// 字符可以*
      }
      
  }
}