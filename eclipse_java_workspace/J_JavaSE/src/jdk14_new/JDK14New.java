package jdk14_new;
enum Week
{
	MONDAY, SATURDAY,SUNDAY ,
}
public class JDK14New {
	public static void main(String[] args) { 
		//switch��ǿ ���ܲ���Preview�׶���
		Week day=Week.MONDAY;
		switch (day) {    
			case MONDAY  -> System.out.println(1);    
			case SATURDAY,SUNDAY -> System.out.println(0);   
		}
		 
		java.lang.Record x; //��Preview�׶� javac --enable-preview
		
		 
	}
}
