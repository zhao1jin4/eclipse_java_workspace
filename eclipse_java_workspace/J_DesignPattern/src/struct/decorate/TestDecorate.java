package struct.decorate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TestDecorate {
//不改变现有对象结构的情况下，动态地给该对象增加一些职责
	//装饰模式扩展对象的功能比采用继承方式更加灵活
	 //一个包装对象（即装饰对象）
	public static void main(String[] args) throws FileNotFoundException {
		 //JDK 的示例,都 Reader的子类
		BufferedReader reader=new BufferedReader(new FileReader("c:/temp/file.txt"));
	}

}
interface Action{
	public void act1();
}
class ActionDecorator implements Action {
	
	 private Action action;
	
	 public ActionDecorator(Action action) {
	 this.action = action;
	 }
	
	
	public void act1() {
	 action.act1();
	 }
	
	 public void act2() {
	 // do nothing 
	 }
	
	} 