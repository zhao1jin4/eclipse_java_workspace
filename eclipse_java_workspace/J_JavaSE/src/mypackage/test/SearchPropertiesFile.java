package mypackage.test;



import java.io.*;
import java.util.Properties;


public class SearchPropertiesFile
{
	public static void main(String[] args)throws Exception
	{
		Properties pro=new Properties();
		FileInputStream file=new FileInputStream("SearchPropertiesFile.properties");
		pro.load(file);
		String ext=(String)pro.get("ext");
		String path=(String)pro.get("filename");
		
	}
}
