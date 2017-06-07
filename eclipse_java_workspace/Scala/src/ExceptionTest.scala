import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException

object TestException {
   def main(args: Array[String]) {
      try {
         val f = new FileReader("input.txt")
      } catch {
         case ex: FileNotFoundException => {
            println("Missing file exception")
         }
         case ex: IOException => {
            println("IO Exception")
         }
      } finally {
         println("Exiting finally...")
      }
      
      
      def f(): Int = try { return 1 } finally { return 2 }// 调用f()产生结果值2 
      def g(): Int = try { 1 } finally { 2 }  //调用g()产生1
    //还是避免从finally子句中返回值
    println(f()) 
    println(g())


   }
}