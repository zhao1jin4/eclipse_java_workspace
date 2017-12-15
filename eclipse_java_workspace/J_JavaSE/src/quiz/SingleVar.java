package quiz;

//类的初始化顺序  类的初始化顺序
//static 属性/块 从书写顺序上到下初始化 ->   非static字段->构造器 

 
public class SingleVar {
	public static SingleVar s=new SingleVar(); //static 字段第3个执行 ,  static 属性/块 从书写顺序上到下初始化
	static
	{
		int x=0;
		x=x+1;
	}
	
	
	public static int counter2=0;//有=号会修改值 
	public static int counter1;//无=号不修改值 
	
	int y=0; //非 static字段  第1 个执行,是因为static SingleVar s=new SingleVar();在第一行
	private SingleVar()//构造器 第2个执行
	{
		counter1++;
		counter2++;
	}
	public static SingleVar getInstance()
	{
		return s;
	}
	
	public static void main(String[] args) {
		SingleVar a=SingleVar.getInstance();
		System.out.println(a); 
		System.out.println(SingleVar.counter1);//1
		System.out.println(SingleVar.counter2);//0,这里是因为, 构造方法中counter2++ ,后再做 counter2=0;
	}
}
