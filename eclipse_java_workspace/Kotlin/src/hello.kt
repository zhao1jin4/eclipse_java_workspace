package hello 
// 默认导入包
//kotlin.*
//kotlin.annotation.*
//kotlin.collections.*
//kotlin.comparisons.*
//kotlin.io.*
//kotlin.ranges.*
//kotlin.sequences.*
//kotlin.text.*
fun main(args: Array<String>) {     
   println("Hello World!")
  
	for (name in args)
	   println("arg, $name!")
	
	//println("first arg, ${args[0]}!")
	
	
	vars(1,2,3,4,5)  //变长参数
	println()
	//不可变的val  ,同scala
	val sumLambda: (Int, Int) -> Int = {x,y -> x+y}//类似scala/java,但更繁锁
    println(sumLambda(1,2))
	
	var a = 1
	val s1 = "a is $a" //模板中变量值
	println(s1)
	
	a = 2
	// 模板中的方法
	val s2 = "${s1.replace("is", "was")}, but now is $a"
	println(s2)
	
	
	//类型后面加?表示可为空
	var age: String? = "23" 
	//抛出空指针异常
	val ages = age!!.toInt()
	
	age=null
	//不做处理返回 null
	val ages1 = age?.toInt()
	//age为空返回-1
	val ages2 = age?.toInt() ?: -1
	getStringLength("123")
 
	for (i in 1..4) print(i) // 输出“1234”
	// downTo step  
	for (i in 1..4 step 2) print(i) // 输出“13”
	for (i in 4 downTo 1 step 2) print(i) // 输出“42”
	for (i in 1 until 10)     // i in [1, 10) 排除了 10
		println(i)
	
	
	val oneMillion = 1_000_000 //同Java
	
   //Kotlin 中没有基础数据类型，只有封装的数字类型
	//三个等号 === 表示比较对象地址
	var num1: Int = 10000
    println(num1 === num1) // true，值相等，对象地址相等

    //经过了装箱，创建了两个不同的对象
    val boxedA: Int? = num1 //定义一个变量 Kotlin 帮你封装了一个对象
    val anotherBoxedA: Int? = num1

    //虽然经过了装箱，但是值是相等的，都是10000
    println(boxedA === anotherBoxedA) //  false，值相等，对象地址不一样
    println(boxedA == anotherBoxedA) // true，值相等
	
	
	
	val by: Byte = 1 // OK, 字面值是静态检测的
	//val num2: Int = by // 错误 
	val num2: Int = by.toInt() // OK

//	对于Int和Long类型 位操作 
//	shl(bits) – 左移位 (Java’s <<)
//	shr(bits) – 右移位 (Java’s >>)
//	ushr(bits) – 无符号右移位 (Java’s >>>)
//	and(bits) – 与
//	or(bits) – 或
//	xor(bits) – 异或
//	inv() – 反向
	
	decimalDigitValue('6')
	
	 //[1,2,3]
    val arr = arrayOf(1, 2, 3)
    //[0,2,4]
    val b = Array(3, { i -> (i * 2) })  //size,i是下标
    println(arr[0])
    println(b[1])
	val validNumbers: IntArray = intArrayOf(1, 2, 3)
	validNumbers[0] = validNumbers[1] + validNumbers[2]
	
	val text = """
    |多行字符串 
    |abc
    """.trimMargin()// trimMargin 前置空格删除 
    println(text)
	
	 val price = """
    ${'$'}9.99 
    """  //$ 字符（它不支持反斜杠转义）
    println(price)
	
	//------when
	num1=2
	when (num1) {  //when 
		1 -> print("x == 1")
		2 -> print("x == 2") 
		else -> {  //else也有->
		print("x 不是 1 ，也不是 2")
		}
	}
    when (num1) {
	    in 1..10 -> print("x is in the range") //in 
	    in validNumbers -> print("x is valid")
	    !in 10..20 -> print("x is outside the range")  //!in
	    else -> print("none of the above")
    }
	
	var str="prefix"
	var res= when(str) {
		is String -> str.startsWith("prefix")
		else -> false
	}
	print(res)
 
	val itemsSet = setOf("apple", "banana", "kiwi")//setOf
	when {
		"orange" in itemsSet -> println("juicy")
		"apple" in itemsSet -> println("apple is fine too")
	}
	 
	var array: Array<String> = arrayOf("123","455")//arrayOf
	
	//---for
	for ((index, value) in array.withIndex()) {  //类似go
		println("the element at $index is $value")
	}
	
	val items = listOf("apple", "banana", "kiwi")//listOf
	for (item in items) {
		println(item)
	}

	for (index in items.indices) {//indices
	    println("item at $index is ${items[index]}")
	}
	//同Java一样的while,do..while,continue,break
	loop@ for (i in 1..100) {//标签
	    for (j in 1..100) {
	        if (j==3 && i==2) break@loop
	    }
	} 
	val ints = intArrayOf(1, 2, 3, 0, 4, 5, 6) //intArrayOf
   /*
	ints.forEach { //forEach接收一个lamda
        if (it == 0) return //要用it变量拿到值,这里的return 是main返回了
        print(it)
    }
     //123 
	*/
	ints.forEach lit@ {
	    if (it == 0) return@lit //这个只是返回了lamda
		//return@a 1  //意为"从标签 @a 返回 1"
	    print(it)
	} //123456
	 
	ints.forEach {
		if (it == 0) return@forEach //也可 函数同名
	    print(it)
	}
	 ints.forEach(fun(value: Int) { //像JS一样 传一个函数
        if (value == 0) return
        print(value)
    })
	
	//---class 
	Greeter("World!").greet()//实例化没有new  
	var person: Person = Person("li")
    person.lastName = "wang"
    println("lastName:${person.lastName}")
    person.no = 9
    println("no:${person.no}")
    person.no = 20
    println("no:${person.no}")
	
	
	var p2: Person = Person("li",3)
	//DontCreateMe();//不能实例化
	
	 val demo = Outer.Nested().foo() //外部类.嵌套类().嵌套类方法()/属性
    println(demo)    // == 2
	
	//inner关键字，外部类要加(),格式  外部类().内部类().
	val demo0 = Outer().Inner().foo()
    println(demo0)  
    val demo2 = Outer().Inner().innerTest()   
    println(demo2)   
	
	var test = Test();
    test.setInterFace(object : TestInterFace { //匿名内部类
        override fun test() {
            println("对象表达式创建匿名内部类的实例")
        }
    });
	  
}

fun sum(a: Int, b: Int): Int {//返回类型同scala
    return a + b
}
fun plus(a: Int, b: Int) = a - b // = 可无返回类型
public fun multi(a: Int, b: Int) = a * b   //: Int

fun printSum(a: Int, b: Int): Unit {  //Unit
    print(a + b)
}
fun vars(vararg v:Int){ // 变长参数 vararg 
    for(vt in v){
        print(vt)
    }
}
fun getStringLength(obj: Any): Int? { //Any ,返回值可为null
  if (obj is String) {//类型判断 
    return obj.length 
  }else if (obj !is String) // !is
    return null
  return null
}
fun decimalDigitValue(c: Char): Int {
    if (c !in '0'..'9')
        throw IllegalArgumentException("Out of range")
    return c.toInt() - '0'.toInt()
} 
 
class Greeter(val name: String) { //constructor可省略 
//class  Greeter constructor(val name: String) {
   fun greet() { 
      println("Hello, $name")
   }
}
class Person  constructor(firstName: String) { 
    var lastName: String = "zhang"
        get() = field.toUpperCase()  //field特殊字段 
        set

    var no: Int = 100
       // get() = field
		get   //可以简写
        set(value) {
            if (value < 10) {    
                field = value
            } else {
                field = -1  
            }
        }
    var heiht: Float = 145.4f
        private set
	//lateinit var obj: Greeter // lateinit 延迟初始化，第一次使用时
	init { //主构造函数,idea被没被调用了 ，eclipse不能编译???
        println("FirstName is $firstName")
    }
	constructor (name: String, age:Int) : this(name) {//次构造函数，this调用另一构造函数
        println("构造函数")
    } 
}
 
class DontCreateMe private constructor () {//构造器，私有，单例
}
class Customer(val customerName: String = "")//val默认值

open class Base {  //open 允许它可以被继承
    open fun f() {}// 允许子类重写
}

abstract class Derived : Base() { //继承用:类似C++,抽象成员在类中不存在具体的实现
    override abstract fun f()//override   类/方法是abstract
}
class Outer {  
    private val bar: Int = 1
    class Nested {             // 嵌套类
        fun foo() = 2
    }
	 var v = "成员属性"
     inner class Inner { //inner 内部类
        fun foo() = bar  // 访问外部类成员
        fun innerTest() {
            var o = this@Outer //this@获取外部类的成员变量
            println("内部类可以引用外部类的成员，例如：" + o.v)
        }
    }
}
 
class Test {
    var v = "成员属性" 
    fun setInterFace(test: TestInterFace) {
        test.test()
    }
}
interface TestInterFace {
    fun test()
}





