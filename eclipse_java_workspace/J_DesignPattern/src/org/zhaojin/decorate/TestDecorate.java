package org.zhaojin.decorate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TestDecorate {

	 
	public static void main(String[] args) throws FileNotFoundException {
		 //JDK 的示例
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