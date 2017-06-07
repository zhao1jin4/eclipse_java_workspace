

package clazz_test

object Test1
{
   def main(args: Array[String]) 
   {
   	val alice = new Person("Alice", 25)
	  val bob = new Person("Bob", 32)
   	val charlie = new Person("Charlie", 32)
   
    for (person <- List(alice, bob, charlie)) {
    	person match {
            case Person("Alice", 25) => println("Hi Alice!")  //根据构造器决定是否相同,不一定是对的,如果参数过多很繁锁
            case Person("Bob", 32) => println("Hi Bob!")
            case Person(name, age) =>
               println("Age: " + age + " year, name: " + name + "?")
         }
      }
   	
   	
     
   }
   
   
   // 样例类
   case class Person(name: String, age: Int)
}