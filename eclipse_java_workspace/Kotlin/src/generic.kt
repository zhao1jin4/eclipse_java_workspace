package generic
//类泛型
class Box<T>(t: T) {
    var value = t
}

fun main(args: Array<String>) {
    var boxInt = Box<Int>(10)
    var boxString = Box<String>("字串")

    println(boxInt.value)
    println(boxString.value)
	
	var box: Box<Int> = Box<Int>(1)
	// 或者
	val box1 = Box(1) // 编译器会进行类型推断，1 类型 Int，所以编译器知道我们说的是 Box<Int>。

 
	val box4 = boxIn<Int>(1)
	val box5 = boxIn(1)     // 编译器会进行类型推断
	
	  val age = 23
    val name = "lisi"
    val bool = true

    doPrintln(age)    // 整型
    doPrintln(name)   // 字符串
    doPrintln(bool)   // 布尔型
	
	var x:Int 
	sort(listOf(1, 2, 3)) //Int 是 Comparable<Int> 的子类型
	//sort(listOf(HashMap<Int, String>())) // 错误：HashMap<Int, String> 不是 Comparable<HashMap<Int, String>> 的子类型
	
   var strCo: GenOut<String> = GenOut("a")
    var anyCo: GenOut<Any> = GenOut<Any>("b")
    anyCo = strCo
    println(anyCo.foo())   // 输出 a
	
	
	var strDCo = GenIn("a")
    var anyDCo = GenIn<Any>("b")
    strDCo = anyDCo
	
	 
	//星号投射 <*>    <Any?> 代指了所有类型
    val a1: A<*> = A(12, "String", Apple("苹果"))
    val a2: A<Any?> = A(12, "String", Apple("苹果"))   //和a1是一样的
    val apple = a1.t3    //参数类型为Any
    println(apple)
    val apple2 = apple as Apple   //强转成Apple类
    println(apple2.name)
    //使用数组
    val l:ArrayList<*> = arrayListOf("String",1,1.2f,Apple("苹果"))
    for (item in l){
        println(item)
    }
}
//方法 泛型
fun <T> boxIn(value: T) = Box(value)
 
fun <T> doPrintln(content: T) { 
    when (content) {
        is Int -> println("整型数字为 $content")
        is String -> println("字符串转换为大写：${content.toUpperCase()}")
        else -> println("T 不是整型，也不是字符串")
    }
}

//约束上界(upper bound)
fun <T : Comparable<T>> sort(list: List<T>) { 
}
fun <T> copyWhenGreater(list: List<T>, threshold: T): List<String>
	//多个上界约束条件，可以用 where 子句
    where T : CharSequence,
          T : Comparable<T>
{
	//lamda写法变化
    return list.filter { it > threshold }.map { it.toString() }
}

// 声明处型变（declaration-site variance）
//out 协变,只能用作输出，可以作为返回值类型但是无法作为入参的类型
class GenOut<out A>(val a: A) {
    fun foo(): A {
        return a
    }
}
//in 逆变,只能用作输入，可以作为入参的类型但是无法作为返回值的类型：
class GenIn<in A>(a: A) {
    fun foo(a: A) {
    }
}

class A<T>(val t: T, val t2 : T, val t3 : T)
class Apple(var name : String)


