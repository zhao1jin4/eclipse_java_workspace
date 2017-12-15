package test

//默认情况 Scala 总会引入 java.lang._ , scala._ 和 Predef._ 
import java.util.{ Date, Locale }
import java.text.DateFormat._

object FrenchDate {
  def main(args: Array[String]) {
    val now = new Date 
    val df = getDateInstance(LONG, Locale.FRANCE)
    println(df format now)
    
    //变量
    var myVar : String = "Foo" 
    
    //多行字符串用   三个双引号  来表示分隔符
    val foo = """第一行 
                              第二行
                              第n行"""
    
    // 元组给变量赋值
    val (myVar1: Int, myVar2: String) = Tuple2(40, "Foo")
    println(myVar1)
    
    val pair = (99, "Luftballons") //元组
    println(pair._1) //开头
    println(pair._2)
    
   val (id: Int, _)=pair  //可不要元组中的元素
   
    
  }
}

 