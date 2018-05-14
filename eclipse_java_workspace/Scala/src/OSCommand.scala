import java.io.File
import java.net.URL


object OSCommand {
  def main(args:Array[String])
  {
    import sys.process._
    //如scala>下执行要多加import scala.language.postfixOps 
    var res:Int="ls -la .." !  // !就是  ProcessBuilder ProcessImplicits stringToProcess 执行shell , windows下不行
    //要有空行
    
    println("res="+res) //成功返回0，日志输出有结果，但拿不到 
    
    var resConole="ls -al .." !! // 返回字串结果
    
    println("resConole="+resConole)
    
    println("---pipe---" )
    "ls -al .." #| "grep scala" ! //  管道
   
    "ls -al .." #> new File("ls.txt") ! 
    
    "echo aaaaaaaaaaaaaaaa" #>> new File("ls.txt") !
    
    "ls -lrt" #>> new File("ls.txt") ! 
     
    println("---input file---" )
    "grep scala" #< new File("ls.txt") ! 
    
   println("---input URL---" )
    "grep jquery" #< new URL("http://www.scala-lang.org/download/") ! //不支持https
    
    
   // a #&& b  如果a成功 再执行b了 
   // a #|| b  如果a不成功再执行b了
    
    val p=Process("ls ",new File("/tmp"),("LANG","en_US"));//在指定目录，指定环境变量 执行命令
    p!
    
  }
}