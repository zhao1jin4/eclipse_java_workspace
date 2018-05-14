import scala.util.matching.Regex

object TestReg {
   def main(args: Array[String]) {
      val pattern = "Scala".r   //返回 Regex
      val str = "Scala is Scalable and cool"
      println(pattern findFirstIn      str)//可以省略. ( ) 用空白分隔
      println( pattern.findFirstIn(str) )
      
      println(pattern replaceFirstIn(str, "Java"))
      println(pattern replaceAllIn(str, "Java"))
      println(pattern findPrefixOf( str ))//第一个匹配的
      
      {
          val pattern = new Regex("(S|s)cala") 
          val str = "Scala is scalable and cool"
          println((pattern findAllIn str).mkString(","))   // 使用逗号 , 连接返回结果
      }
    
      
      var numLetter ="([0-9]+) ([a-z]+)".r;
      var  numLetter(num,letter)="99 wang";//存入
      println(num);
      println(letter);
      
      
      
   }
}