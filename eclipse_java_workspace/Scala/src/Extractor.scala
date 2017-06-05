object TestExtract {
   def main(args: Array[String]) {
      
      println ("Apply 方法 : " + apply1("Zara", "gmail.com")); 
      println ("Unapply 方法 : " + unapply1("Zara@gmail.com"));
      println ("Unapply 方法 : " + unapply1("Zara Ali"));

   }
   // 注入方法 (可选)
   def apply1(user: String, domain: String) = {
      user +"@"+ domain
   }

   // 提取方法（必选）
   def unapply1(str: String): Option[(String, String)] = {  //Option用法,表示可有可无,有的话用Some,没有用None 
      val parts = str split "@"
      if (parts.length == 2){
         Some(parts(0), parts(1))   // Some 和None 都继承自Option
         Some( "aa","aa11" ) //后面会覆盖前面的
      }else{
         None
      }
   }
}


object TestExtractClass {
   def main(args: Array[String]) 
   {
      val x = TestExtractClass(5) //会调用apply方法
      println(x)  //打印的是10
      println(x.getClass())//int
      x match
      {
        // case 10  => println("值是10")  
         case TestExtractClass(num)  //如果match中 unapply 被调用,  num 是调用unapply 返回的,函数返回类型Option
                 => println(x + " 是 " + num + " 的两倍！")  
         case _ => println("无法计算")
      }
   }
   
   def apply(x: Int) = x*2
     
   def unapply(z: Int): Option[Int] =  //z 是10 ,返回类型Option
     if (z%2==0) 
       Some(z/2)
     else 
       None
}



