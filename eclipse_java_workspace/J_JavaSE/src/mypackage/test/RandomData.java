package mypackage.test;

import java.io.*;
public class  RandomData 
{
	public static void main(String[] args) throws Exception
	{
		RandomAccessFile file=new RandomAccessFile ("text.txt","rw");
		
		
		student s1 =new student(1,"lisi");
		student s2 =new student(2,"wwww");
		student s3 =new student(3,"sssss");

		s1.write(file);
		s2.write(file);
		s3.write(file);

		file.seek(0);
		student s =new student();

		for(long i=0;i<file.length();i=file.getFilePointer())
		{
			s.readfile(file);
			System.out.println(s.id+":"+s.name);
		}

			file.close();
			
	}
}
class student
{
	int id;
	String name;
	public student()
	{
	}
	public student(int id,String name)
	{
		this.id=id;
		this.name=name;
	}
	
	public void  write(RandomAccessFile f)throws Exception
	{
		f.writeInt(id);
		f.writeUTF(name);
	}
	public void readfile(RandomAccessFile  f)throws Exception
	{
		id=f.readInt();
		name=f.readUTF();
	}
}