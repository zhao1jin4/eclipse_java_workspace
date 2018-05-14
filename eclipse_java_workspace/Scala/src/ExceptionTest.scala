import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException

object TestException {
   def main(args: Array[String]) {
      try {
         val f = new FileReader("input.txt")
      } catch {
         case _: FileNotFoundException => {//可用_
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

    
    
    
   
     //scala -g:notailcalls 或者 scalc 下加入 ，会得到一个长长的信息
     def bang(x: Int): Int ={
    	if( x == 0) 
    		throw new Exception("bang!") 
    	else 
    		bang(x-1)
    }
    //bang(5) 


   }
}