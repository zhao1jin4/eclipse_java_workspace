package quiz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableTest {

	public static void main(String[] args) throws Exception, IOException {
		Child c=new Child();
		c.id=3;
		c.name="lisi";
		c.age=25;
		c.level="java ";
		
		ObjectOutputStream output=new ObjectOutputStream( new FileOutputStream("c:/tmp/io"));
		output.writeObject(c);
		output.close();
		
		ObjectInputStream input=new ObjectInputStream( new FileInputStream("c:/tmp/io"));
		Child c2=(Child)input.readObject();
		System.out.println(c2.name);
		System.out.println(c2.level);
		input.close();

	}
	
}
class Parent  
{
	int id ;
	String name;
	
}

class Child extends Parent implements Serializable //���ಢû��ʵ��Serializable,���Ը�������Բ���洢
{
	int age;
	String level;
}