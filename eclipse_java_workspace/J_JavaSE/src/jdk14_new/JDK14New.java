package jdk14_new;
enum Week
{
	MONDAY, SATURDAY,SUNDAY ,
}
public class JDK14New {
	public static void main(String[] args) { 
		//switch增强 功能不是Preview阶段了
		Week day=Week.MONDAY;
		switch (day) {    
			case MONDAY  -> System.out.println(1);    
			case SATURDAY,SUNDAY -> System.out.println(0);   
		}
		 
		java.lang.Record x; //是Preview阶段 javac --enable-preview
		
		 
	}
}
