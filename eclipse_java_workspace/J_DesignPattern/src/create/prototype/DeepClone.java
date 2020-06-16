package create.prototype;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DeepClone
{
	  public static void main(String[] args) throws Exception 
      {
		  Person p=new Person();
		  p.name="zhang";
		  p.age=23;
		  Addr a=new Addr();
		  a.no=2000;
		  a.street="X·";
		  p.addr=a;
		  
		  Person p2=(Person)p.clone();
		  p.addr.street="YYy·";
		  Person p3=(Person)p.deepClone();
		  p.addr.street="zzz·";
		  
		  System.out.println(p2.addr == p.addr);
		  System.out.println(p2.addr.street);
		  
		  System.out.println(p3.addr == p.addr);
		  System.out.println(p3.addr.street);
		  
		  
      }
}

class Addr implements Serializable
{
	String street;
	int no;
}
class Person implements Serializable,Cloneable
{
	String name;
	int age;
	Addr addr;
	
	protected Object clone() throws CloneNotSupportedException {
		Person p=new Person();
		p.name=name;
		p.age=age;
		p.addr=addr;
		return p;
	}
	protected Object deepClone() throws Exception {
		ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream("c:/temp/Person.bin"));
		output.writeObject(this);
		output.close();
		
		ObjectInputStream input=new ObjectInputStream(new FileInputStream("c:/temp/Person.bin"));
		Person p3=(Person)input.readObject();
		input.close();
		
		return p3;
	}
}