package clazz_sealed


fun main(args: Array<String>) {
	var res=eval(Const(3.0));
	println(res);//3.0
	
	var num1:Expr=Const(4.0);
	var num2:Expr=Const(5.0);
	var expr:Expr= Sum(num1,num2);
	println(expr);//显示 Sum(e1=Const(number=4.0), e2=Const(number=5.0))
	
	//println(Sum(Const(4.0),(Const(4.0))));//Sum(e1=Const(number=4.0), e2=Const(number=4.0))
	
	
	execute(Operator.Show);
	execute(Operator.Hide);
	execute(Operator.Translate(10f,20f)); 
}
/*
sealed 修饰类，密封类可以有子类,所有的子类都必须要内嵌在密封类中
sealed 不能修饰 interface ,abstract class(会报 warning,但是不会出现编译错误)
密封类是枚举类的扩展
*/

sealed class Expr // sealed
data class Const(val number: Double) : Expr()//data 类  ：继承
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()//object创建单例 ：继承

fun eval(expr: Expr): Double = when (expr) { //sealed子类用在 when 中像 scala
	//when下的所有的sealed项目必须全部增加，否则编译报错
    is Const -> expr.number //sealed子类用 is
    is Sum -> eval(expr.e1) + eval(expr.e2)//不会递归调用
    NotANumber -> Double.NaN  //object子类不用is , NaN
}

sealed class  Operator {
    object Show: Operator()//定义在内部
    object Hide: Operator()
	class Translate(val x: Float,val y: Float): Operator()
} 
fun execute( op: Operator) = when (op) {
    Operator.Show -> println("Show")
    Operator.Hide -> println("Hide")
	is Operator.Translate -> println("Translate x=${op.x},y=${op.y}")  
}
/* 
class Ui(val uiOps:List = emptyList())//List报错
 {
	// operator运行符重载
    operator fun plus(uiOp: Operator) = Ui(uiOps + uiOp) //List + 加元素
}
val ui = Ui() +
        Operator.Show +
        Operator.Translate(20f,30f)
*/


