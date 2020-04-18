package jdk13_new;
enum Week
{
	MONDAY, SATURDAY,SUNDAY ,
}
public class JDK13New {
	public static void main(String[] args) { 
		//switch 增强功能还是Preview阶段 ，是Preview阶段 javac --enable-preview
		/*
		Week day=Week.MONDAY;
		switch (day) {    
			case MONDAY  -> System.out.println(1);    
			case SATURDAY,SUNDAY -> System.out.println(0);   
		}
		 */
	}
}
