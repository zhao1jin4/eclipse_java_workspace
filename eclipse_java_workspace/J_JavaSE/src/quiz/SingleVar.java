package quiz;

//��ĳ�ʼ��˳��  ��ĳ�ʼ��˳��
//static ����/�� ����д˳���ϵ��³�ʼ�� ->   ��static�ֶ�->������ 

 
public class SingleVar {
	public static SingleVar s=new SingleVar(); //static �ֶε�3��ִ�� ,  static ����/�� ����д˳���ϵ��³�ʼ��
	static
	{
		int x=0;
		x=x+1;
	}
	
	
	public static int counter2=0;//��=�Ż��޸�ֵ 
	public static int counter1;//��=�Ų��޸�ֵ 
	
	int y=0; //�� static�ֶ�  ��1 ��ִ��,����Ϊstatic SingleVar s=new SingleVar();�ڵ�һ��
	private SingleVar()//������ ��2��ִ��
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
		System.out.println(SingleVar.counter2);//0,��������Ϊ, ���췽����counter2++ ,������ counter2=0;
	}
}
