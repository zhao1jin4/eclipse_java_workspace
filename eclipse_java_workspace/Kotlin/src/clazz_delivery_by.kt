package clazz_delivery_by
import kotlin.reflect.KProperty
import kotlin.properties.Delegates
interface Base {   
    fun print()
}   
class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
} 
// 通过关键字 by 建立委托类
class Derived(b: Base) : Base by b //  : Base by b

fun main(args: Array<String>) {
    val b = BaseImpl(10)
    Derived(b).print() // 输出 10
	
	val e = Example()
    println(e.p)     // 访问该属性，调用 getValue() 函数
    e.p = "123"   // 调用 setValue() 函数
    println(e.p)
	
	println(lazyValue)   
    println(lazyValue)
	
	val user = User()
    user.name = "第一次赋值"
    user.name = "第二次赋值"
	
	val site = Site(mapOf(//实例化一个只读的Map
        "name" to "李四",
        "url"  to "baidu.com"
    )) 
    println(site.name)
    println(site.url)
	
	
    var map:MutableMap<String, Any?> = mutableMapOf(//可变的Map
            "name" to "abc",
            "url" to "123"
    )
	map.put("name", "Google")
    map.put("url", "www.google.com")
	
	var foo=Foo();
	foo.notNullBar = "bar"
	println(foo.notNullBar)
	
}
val lazyValue: String by lazy {  //延迟属性 Lazy
    println("computed!") //第一次调用 get() 会执行已传递给 lazy() 的 lamda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。
    "Hello"
}

class Example {
    var p: String by Delegate() //属性委托, get,set方法 使用委托的类getValue，setValue方法  
}
class Delegate {
	// getValue() 
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, 这里委托了 ${property.name} 属性"
    }
    //setValue() 
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的 ${property.name} 属性赋值为 $value")
    }
}

class User {
    var name: String by Delegates.observable("初始值") {   //可观察属性 Observable
        prop, old, new ->
        println("属性 ${prop.name} 旧值：$old -> 新值：$new")
    }
}

class Site(val map: Map<String, Any?>) {
    val name: String by map  //把属性储存在map中
    val url: String  by map
}

class Foo {
    var notNullBar: String by Delegates.notNull<String>() //属性 by Delegates.notNull<>
}
fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)//局部变量lazy初始化,lazy(xx） ???? ,by后还可以是一个函数调用（提供委托） 
}

