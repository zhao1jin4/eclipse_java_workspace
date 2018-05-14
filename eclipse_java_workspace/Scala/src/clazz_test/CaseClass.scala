

package clazz_test

object Test1
{
   def main(args: Array[String]) 
   {
 
   	val alice =  Person("Alice", 25)//无new 调用apply
	  val bob = new Person("Bob", 32)
   	val charlie = new Person("Charlie", 32)
   
    for (person <- List(alice, bob, charlie)) {
    	person match {
            case Person("Alice", 25) => println("Hi Alice!")  //根据构造器决定是否相同,不一定是对的,如果参数过多很繁锁
            case Person("Bob", 32) => println("Hi Bob!")
            case Person(name , age) => //调用unapply方法 
               println("Age: " + age + " year, name: " + name + "?")
         }
      }
   	
   	
     
   }
   
   // 样例类，自动有 apply,unapply 
   case class Person(name: String, age: Int)
}