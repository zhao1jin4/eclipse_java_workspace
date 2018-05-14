package clazz_test

import javax.swing.JFrame
import java.io.IOException

object ThisTest extends App{
  
   
   class Outer(name:String)
   { 
       out=> //指向Outer.this
      class Inner{
       def desc=out.name
      
     }
   }
   val outer=new Outer("lisi")//必须是val 
   //var in = out.new Inner();//Java做法
   var in = new outer.Inner ;//Scala做法
   println(in.desc)
   
   
   
   trait LoggedException {
     this:Exception => //只能是Exception或者子类  才能 extends 这个
       def log {
         println(getMessage());//getMessage() = this.getMessage() 
       }
   }
//   var wrong=new JFrame   with LoggedException{ //JFrame不是Exception的子类不行
//   }
   var right=new IOException  with LoggedException{
   }
   
   
   
   trait LogMethodException 
   {
     this: {  def getMessage():String  } => // 子类必须有  这个方法签名
   }
    var rightMethod=new IOException  with LogMethodException{
    }
//    var wrongMethod=new JFrame  with LogMethodException{//JFrame 没有getMessage方法 
//    }
    
    
    
    
}