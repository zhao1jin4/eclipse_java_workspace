package spring_i18n;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;

public class MainApp {

	public static void readFile()
	{
		
		ApplicationContext context= new GenericApplicationContext();
		Resource res=context.getResource("classpath:message_en.properties");
		System.out.println(res.getFilename());
		try
		{
			InputStream input=res.getInputStream();
			byte[]b =new byte[10];
			BufferedOutputStream out=new BufferedOutputStream (System.out);
			while (input.read(b)!=-1)
			{
			
			out.write(b);
			}
			input.close();
			out.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void i18nMessage()
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_i18n/i18n_beans.xml");
		Object [] obj={new String("zhojjin"),new Date().toString()};
		String str=context.getMessage("welcome",obj,"there is no message",Locale.US);
		System.out.println(str); 
	}
	public static void main(String[] args) 
	{
		readFile();
		i18nMessage();
	}
}
