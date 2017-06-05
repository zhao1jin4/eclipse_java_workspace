package jdk8_new;

import java.io.PrintStream;
import java.util.Arrays;

//@FunctionalInterface //��ֻ����һ��δʵ�ֵķ���,�粻�����Ĭ�Ͼ���
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
	
	default double calc(double num) //�����Ӱ��  ->,�������@FunctionalInterface 
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
        IntegerMath subtraction = (a, b) ->{ return a - b;};//��ʹ��lambda ,����ӿ���@FunctionalInterface,�����������ڲ���
        IntegerMath addition = (c, d) -> c + d; //��ʵ��ֻ��һ�У���ʡ{}��return
       
        System.out.println("40 + 2 = " + myApp.operateBinary(40, 2, addition));
        System.out.println("20 - 10 = "  + myApp.operateBinary(20, 10, subtraction));
        
        
        lambdaTest();
        testLambda();
        
        
    }
    
    static  void testLambda()
    {
    
    }
    public static void lambdaTest( ) {
    	lambdaPrintInterface print = ()-> System.out.print("test");//�޲���ֻд����
    	
    	double num=5;//������Բ��ü�final
    	lambdaOneInterface one = x -> x*0.9-num;  //���ֻһ���βο���ʡ()�� -> ֻ��������@FunctionalInterface�ķ���
    	//num=6;// ���ڲ���һ���������ⲿ��������Ϊfinal,����ֻ�ǿ��Բ�д����������޸ľͻᱨ��
    	System.out.println("discount="+one.discount(20));
    	
    	new Print().doPrint(  ()-> System.out.println("print something")  );// �����Զ���Ӧ
    	
    	MethodRef ref= (s)->System.out.println(s);
    	ref.processStr("method ref string ");
    	
    	MethodRef instanceRef=  System.out::println ;//ʵ������ ����:: ��Ҫ��ӿ��еķ��������ͱ����÷����Ĳ�������ͬ���ͺ͸�����
    	instanceRef.processStr("method instance ref string :: ");
    	
    	MethodStaticRef staticRef=Arrays::sort;//��ľ�̬�������� ::
    	int[] array=new int[]{6,9,4,5,8,1};
    	staticRef.processArray(array);
    	System.out.println(Arrays.toString(array));
    	
    	
    	MethodRefNoneStatic objectRef=PrintStream::println;//��ķǾ�̬�������� :: ,�ӿڵ�Ψһ�����ĵ�һ������һ��Ҫ��PrintStream����
    	objectRef.processStr(System.out,"method noneStatic ref string");
    	
    	MethodConstructRef constructRef= String::new;//���캯�����ã��ӿڷ����빹�캯�������ṹ��ͬ
    	constructRef.processStr(new char[]{'��','��'});
    	
    }
}