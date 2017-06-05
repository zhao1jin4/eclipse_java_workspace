package jdk8_new;

import java.io.PrintStream;
import java.util.Arrays;

//@FunctionalInterface //即只可有一个未实现的方法,如不加这个默认就是
interface IntegerMath 
{
    int operation_(int a, int b);
}

interface MethodRef
{
	void processStr(String s) ;
}
interface MethodStaticRef 
{
	void processArray(int [] s) ;
}
interface MethodRefNoneStatic
{
	void processStr(PrintStream stream,String s) ;
}

interface MethodConstructRef
{
	void processStr(char[] s) ;
}
interface lambdaPrintInterface 
{
    void print();
}
interface lambdaOneInterface 
{
	double discount (double num);
	
	default double calc(double num) //这个不影响  ->,因必须是@FunctionalInterface 
	{
		System.out.println( "invoke calc");
		return num*3;
	}
}
class  Print 
{
	void doPrint(lambdaPrintInterface p)
	{
		System.out.println("befor print something") ;
		p.print();
		System.out.println("after print something") ;
	}
	
}
public class lambdaTest 
{
    public int operateBinary(int a, int b, IntegerMath op)
    {
        return op.operation_(a, b);
    }
 
    public static void main(String... args) {
 
        lambdaTest myApp = new lambdaTest();
        IntegerMath subtraction = (a, b) ->{ return a - b;};//如使用lambda ,必须接口是@FunctionalInterface,类似于匿名内部类
        IntegerMath addition = (c, d) -> c + d; //如实现只有一行，可省{}和return
       
        System.out.println("40 + 2 = " + myApp.operateBinary(40, 2, addition));
        System.out.println("20 - 10 = "  + myApp.operateBinary(20, 10, subtraction));
        
        
        lambdaTest();
        testLambda();
        
        
    }
    
    static  void testLambda()
    {
    
    }
    public static void lambdaTest( ) {
    	lambdaPrintInterface print = ()-> System.out.print("test");//无参数只写（）
    	
    	double num=5;//这里可以不用加final
    	lambdaOneInterface one = x -> x*0.9-num;  //如果只一个形参可以省()， -> 只可以用于@FunctionalInterface的方法
    	//num=6;// 和内部类一样，引用外部变量必须为final,这里只是可以不写，如后面有修改就会报错
    	System.out.println("discount="+one.discount(20));
    	
    	new Print().doPrint(  ()-> System.out.println("print something")  );// 类做自动适应
    	
    	MethodRef ref= (s)->System.out.println(s);
    	ref.processStr("method ref string ");
    	
    	MethodRef instanceRef=  System.out::println ;//实例方法 引用:: ，要求接口中的方法参数和被引用方法的参数有相同类型和个数的
    	instanceRef.processStr("method instance ref string :: ");
    	
    	MethodStaticRef staticRef=Arrays::sort;//类的静态方法引用 ::
    	int[] array=new int[]{6,9,4,5,8,1};
    	staticRef.processArray(array);
    	System.out.println(Arrays.toString(array));
    	
    	
    	MethodRefNoneStatic objectRef=PrintStream::println;//类的非静态方法引用 :: ,接口的唯一方法的第一个参数一定要是PrintStream类型
    	objectRef.processStr(System.out,"method noneStatic ref string");
    	
    	MethodConstructRef constructRef= String::new;//构造函数引用，接口方法与构造函数声明结构相同
    	constructRef.processStr(new char[]{'中','国'});
    	
    }
}