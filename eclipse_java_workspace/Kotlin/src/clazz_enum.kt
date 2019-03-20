package clazz_enum

enum class ColorEnum{
    RED,BLACK,BLUE,GREEN,WHITE //默认名称为枚举字符名，值从0开始
}

enum class ColorEnum2(val rgb: Int) { //可带参数
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}
//inline   reified 
inline fun <reified T : Enum<T>> printAllValues() {
	//用 enumValues<T>() 和 enumValueOf<T>() 函数以泛型的方式访问枚举类中的常量 
    print(enumValues<T>().joinToString { it.name })
}
enum class RGB { RED, GREEN, BLUE }

fun main(args: Array<String>) {
    var color:ColorEnum=ColorEnum.BLUE
    println(ColorEnum.values()) //以数组的形式，返回枚举值
    println(ColorEnum.valueOf("RED"))
    println(color.name)//像Java
    println(color.ordinal)
	
	printAllValues<RGB>() // 输出 RED, GREEN, BLUE
	
	

}

