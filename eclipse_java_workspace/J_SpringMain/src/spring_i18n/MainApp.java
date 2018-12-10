package spring_i18n;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(input));
		
			BufferedOutputStream out=new BufferedOutputStream (System.out);
			OutputStreamWriter writer=new OutputStreamWriter(out);
			BufferedWriter bufferWriter=new BufferedWriter(writer);
			String line=null;
			while((line=bufferedReader.readLine())!=null)
			{
				bufferWriter.write(line);
			}
			
			/*
			byte[]b =new byte[10];
			while (input.read(b)!=-1)
			{
			     out.write(b);
			}
			input.close();
			out.close();
			*/
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void i18nMessage()
	{
		
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_i18n/i18n_beans.xml");
		Object [] obj={new String("zhaojin"),new Date().toString()};
		String str=context.getMessage("welcome",obj,"there is no message",Locale.US);
		System.out.println(str); 
		 
		
	}
	public static void main(String[] args) 
	{
		readFile();
		System.out.println("-------------"); 
		i18nMessage();
	}
}
