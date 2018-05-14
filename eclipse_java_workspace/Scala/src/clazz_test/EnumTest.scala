package clazz_test

object  ColorEnum extends Enumeration  
{
  //枚举类型是 ColorEnum.Value
  type ColorEnum=Value;//内部别名 ，外面就可以用ColorEnum,而不是ColorEnum.Value
  val Red,Blue=Value; 
  val Green=Value(30,"greenName");
  val Pink=Value("pinkName"); //id=31，最近的 + 1
}



object EnumMain extends App
{
   doJudge(ColorEnum.Red);
   doJudge(ColorEnum.withName("Blue"));//取某个
   doJudge(ColorEnum(30)); 
   
   printAllEnum();
   def  doJudge(  color:ColorEnum.Value):Unit= //枚举类型是 ColorEnum.Value
  {
    if(color==ColorEnum.Red)
      println(color.id+"---"+color);//toString反回name
    else
       println(color.id+"==="+color);
  }
  
   import clazz_test.ColorEnum._
   def printAllEnum( ) 
   {
     for(c <- ColorEnum.values)
     {
        var color:ColorEnum=c;//可以用ColorEnum,而不是ColorEnum.Value
        println(color.id+"  xxx  "+color);
     }
   }
   
}