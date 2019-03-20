package clazz_object

/*

fun countClicks(window: JComponent) {
    var clickCount = 0
    var enterCount = 0
	//匿名内部类的对象用于方法的参数中：
    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++   //在对象表达中可以方便的访问到作用域中的其他变量:
        }

        override fun mouseEntered(e: MouseEvent) {
            enterCount++
        }
    })
    // ……
}
*/


open class A(x: Int) {
    public open val y: Int = x
}
interface B {
}
//象可以继承于某个基类，或者实现其他接口:
val ab: A = object : A(1), B {
    override val y = 15
}
val site = object { //类的定义直接得到一个对象
        var name: String = "李"
        var url: String = "baidu.com"
    }

class C {
    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }
    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        val x: String = "x"
    } 
    fun bar() {
        val x1 = foo().x        // 没问题
        //val x2 = publicFoo().x  // 错误：未能解析的引用“x”
    }
}

object DataProviderManager {
	var name:String="init";
    fun registerDataProvider ()  {
		println(name);
    } 
}

class Site {
    var name = "站点"
    object DeskTop{
        var url = "bing.com"
        fun showName(){
           // print{"desk legs $name"} // 错误，不能访问到外部类的方法和变量
        }
    }
}

//类内部的对象声明可以用 companion 关键字标记，这样它就与外部类关联在一起，我们就可以直接通过外部类访问到对象的内部元素。
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}
val instance = MyClass.create()   // 访问到对象的内部元素


class MyClass2 {
    companion object { //可以省略掉该对象的对象名，然后使用 Companion 替代需要声明的对象名 ,关键字 companion 只能使用一次。
    }
} 
val x = MyClass2.Companion

interface Factory<T> {
    fun create(): T
}
class MyClass3 {
    companion object : Factory<MyClass3> { //companion object 可以继承
        override fun create(): MyClass3 = MyClass3()
    }
}
fun main(args: Array<String>) {
	DataProviderManager.registerDataProvider() //object 单例的所有方法都可直接引用
	var data1 = DataProviderManager 
	data1.name = "test"
	println(data1.name);
	
	 var site = Site()
   // site.DeskTop.url // 错误，不能通过外部类的实例访问到该对象
    Site.DeskTop.url // 正确
}


