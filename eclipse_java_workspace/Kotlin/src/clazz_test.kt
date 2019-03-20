/*
classModifier: 类属性修饰符，标示类本身特性。
abstract    // 抽象类  
final       // 类不可继承，默认属性
enum        // 枚举类
open        // 类可继承，类默认是final的
annotation  // 注解类
accessModifier: 访问权限修饰符

private    // 仅在同一个文件中可见
protected  // 同一个文件中或子类可见
public     // 所有调用的地方都可见
internal   // 同一个模块中可见
*/
//Kotlin 中所有类都继承该 Any 类 ,同scala

package clazz_test 
fun main(args: Array<String>) {
	val s =  Student("Runoob", 18, "S12346", 89)
    println("学生名： ${s.name}")
    println("年龄： ${s.age}")
	
	val c =  C()
    c.f();
	
	var user = User("Li")
    user.Print()
	
	val l = mutableListOf(1, 2, 3) //mutableListOf 类似scala
	
	printFoo(ExtChild())
	
	var t = null
    println(t.toString())
	
	
    println("no:${MyClass.no}")
    MyClass.foo()
	
	
	val extC: ExtC = ExtC()
    val extD: ExtD = ExtD()
    extC.caller(extD)
	
	
	TheC().caller(D())   // 输出 "D.foo in C"
    C1().caller(D())  // 输出 "D.foo in C1"   可以多态调用
    TheC().caller(D1())  // 输出 "D.foo in C"
	
	val jack = UserData(name = "Jack", age = 1)
	val olderJack = jack.copy(age = 2)//data 类型的类，copy复制时修改属性
	println(jack)
	println(olderJack)
	
	val (name, age) = jack //像scala
	println("$name, $age years of age")
	//标准库提供了 Pair 和 Triple 
	
}

open class Person(var name : String, var age : Int){//open的类也可有构造函数
	open fun study(){       // 允许子类重写
	        println("我毕业了")
    }
}

 class Student(name : String, age : Int, var no : String, var score : Int) : Person(name, age) {
  
}
//如子类没有主构造函数，则必须在每一个二级构造函数中用 super 关键字初始化基类
 class Student2 : Person {
    constructor(name : String, age : Int) : super(name,age)
	{
    } 
    constructor(name : String, age : Int,  no: String): super(name,age)
	{
    }
	override fun study(){ // 重写方法
        println("我在读大学")
    }
}
 
open class A {
    open fun f () { print("A") }
    fun a() { print("a") }
}
interface B {//同Java 8 方法可以有实现
	fun f() { print("B") } //接口的成员变量默认是 open 的
    fun b() { print("b") }
}

class C() : A() , B{ //多继承
	 
    override fun f() {
        super<A>.f() //指定父类方法 
        super<B>.f()
    }
}
//可以用一个var属性重写一个val属性，但是反过来不行
interface Foo {
	// 接口中的属性只能是抽象的，不允许初始化值，接口不会保存属性值，实现接口时，必须重写属性
    val count: Int
	var name:String
} 
class Bar1(override val count: Int,override var name: String ) : Foo
class Bar2 : Foo {
    override var count: Int = 0
	override var name: String = "123" 
}

class User(var name:String) 
fun User.Print(){ //类的扩展函数 ,像js的prototype
    println("用户名 $name")
}

open class ExtParent {
   open fun one() { println("ExtParent函数") }
}
class ExtChild: ExtParent() {
	 override fun one() { println("ExtChild函数") }
}
fun ExtParent.foo() = "parent"  
fun ExtChild.foo() = "child" 
fun ExtChild.one() = "one"
fun printFoo(c: ExtParent) {
    println(c.foo())  //传子类也是父类的方法 ，扩展函数不支持多态 
    println(c.one())//当  扩展函数  和 类函数 重名时 优先用类函数
}

//就可以null.toString();
fun Any?.toString(): String {//Any加?
    if (this == null) return "null" // 可用this
    return toString()
}
//对属性进行扩展,没有(),允许定义在类或者kotlin文件中，不允许定义在函数中,不允许被初始化，只能由显式提供的 getter 定义,扩展属性只能被声明为 val
//同Java的<T>
val <T> List<T>.lastIndex: Int
    get() = size - 1

class MyClass {
    companion object { }   // 生成伴生对像名为"Companion"
}
fun MyClass.Companion.foo() {
    println("伴随对象的扩展函数")
}
val MyClass.Companion.no: Int //伴随对象的扩展属性
    get() = 10
//通常扩展函数或属性定义在顶级包下，包之外的用import导入扩展的函数名
 
class ExtD {
    fun bar() { println("D bar") }
	fun bar1() { println("D bar1") }
}
class ExtC {
    fun baz() { println("C baz") }
    fun bar1() { println("C bar1") }  //  同名方法
    fun ExtD.foo() { //定义另一个类的扩展
		bar()   // 调用 D.bar ，另一个类的函数
		baz()   // 调用 C.baz  ，本类的函数
		bar1()  //同名默认调用ExtD
		this@ExtC.bar1()  //使用this@ 指定
	}
    fun caller(d: ExtD) {
        d.foo()   // 调用扩展函数
    }
}
//ExtC 被成为分发接受者，而 ExtD 为扩展接受者

open class D {
}

class D1 : D() {
}

open class TheC {
    open fun D.foo() {
        println("D.foo in C")
    }

    open fun D1.foo() {
        println("D1.foo in C")
    }

    fun caller(d: D) {
        d.foo()    
    }
}
class C1 : TheC() {
    override fun D.foo() {
        println("D.foo in C1")
    }
    override fun D1.foo() {
        println("D1.foo in C1")
    }
}
/*
只包含数据的类，关键字为 data 根据所有声明的属性提取以下函数：
equals() / hashCode()
toString() 
componentN()  按属性声明顺序排列
copy()  
*/
data class UserData(val name: String, val age: Int)

