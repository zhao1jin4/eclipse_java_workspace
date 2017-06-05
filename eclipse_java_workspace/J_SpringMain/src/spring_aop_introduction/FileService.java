package spring_aop_introduction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileService implements IService
{
	String fileName;
	File file;
	public FileService(String fileName)
	{
		this.fileName=fileName;
		file=new File(fileName);
	}
	public void write(String str)
	{
		try
		{
			FileOutputStream out=new FileOutputStream(file);
			out.write(str.getBytes());
			out.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
