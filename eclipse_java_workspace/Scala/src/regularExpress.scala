import scala.util.matching.Regex

object TestReg {
   def main(args: Array[String]) {
      val pattern = "Scala".r   //返回 Regex
      val str = "Scala is Scalable and cool"
      println(pattern findFirstIn      str)//可以省略. ( ) 用空白分隔
      println( pattern.findFirstIn(str) )
      
      println(pattern replaceFirstIn(str, "Java"))
      
      {
           val pattern = new Regex("(S|s)cala") 
          val str = "Scala is scalable and cool"
          
          println((pattern findAllIn str).mkString(","))   // 使用逗号 , 连接返回结果
      
      }
    
   }
}