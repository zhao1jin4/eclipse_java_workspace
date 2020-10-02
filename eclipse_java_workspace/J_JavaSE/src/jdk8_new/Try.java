package jdk8_new;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Try {
	public static void main(String[] args)throws Exception
	{
		List<String> names=new ArrayList<>();
		names.add("1");
		names.add("2");
		names.add("3");
		System.out.println(String.join("-", names));

		String[] arrStr=new String[]{"a","b","c"};

		System.out.println(String.join("-", arrStr)); 
		// JDK 8
		FileInputStream resource1 = new FileInputStream("c:/tmp/input.txt");
		FileInputStream resource2 = new FileInputStream("c:/tmp/input2.txt");
		
		try (FileInputStream rs1 = resource1; FileInputStream rs2 = resource2) {//JDK8
//		try (resource1;resource2){//JDK9

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		//resource1.read();// 流在这已经关闭
		
		
		//移除PermSize,加MetaSpace
		
	}
}
