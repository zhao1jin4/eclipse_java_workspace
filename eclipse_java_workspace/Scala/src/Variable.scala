object VariableApp extends App 
{ 
  
 var  x=0
 var y=1
 //x=y=1  //报错因 y=1返回（）即Unit
  
 printf("hello %s","lisi") //支持%s
 var r:AnyRef=42.asInstanceOf[AnyRef] //类型转换
 
 
 lazy val rows=scala.io.Source.fromFile("c:/temp/hell.txt") //lazy 延迟初始化
 
 
}