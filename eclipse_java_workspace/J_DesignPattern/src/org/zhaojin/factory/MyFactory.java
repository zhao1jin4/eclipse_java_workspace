package org.zhaojin.factory;

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

abstract class AbstractFactory
{
	
}
public class MyFactory extends AbstractFactory
{
	
	public  static MyObject newMyObject()
	{
		return new MyObject();
	}
	 public static void main(String[] args) throws Exception 
	 {
		 //JDK 的示例
		 DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();//本身抽象工厂类,但实现是多态
		 DocumentBuilder builder= factory.newDocumentBuilder();//抽象工厂类只能产生一种抽象产品类,没有new,　产品＝工厂.方法
		 
		 MyFactory f=new MyFactory();
		 
		 
		 
		 
		  //简单工厂 或者静态工厂(DateFormat抽象工厂类,但实现是多态,没有new,　产品＝工厂.方法)
		 DateFormat format=DateFormat.getInstance();//java.text.SimpleDateFormat的实例,工厂角色和抽像角色合并
		 String str= format.format(new Date());// 抽象产品 　　抽象工厂类 同一个？？？

		 DateFormat format1= DateFormat.getInstance();//java.text.SimpleDateFormat的实例
		 System.out.println( format == format1);
		 System.out.println(format.hashCode());
		 System.out.println(format1.hashCode());
		 
		 
		 DateFormat formatFull=DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL, Locale.CHINA);
		 System.out.println(formatFull.format(new Date()));// 2012年7月31日 星期二 上午10时49分30秒 GMT+08:00
		 
		 DateFormat formatLong=DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG, Locale.CHINA);
		 System.out.println(formatLong.format(new Date()));//2012年7月31日 上午10时42分04秒
		
		 DateFormat formatMedium=DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM, Locale.CHINA);
		 System.out.println(formatMedium.format(new Date()));// 2012-7-31 10:44:09
		 
		 DateFormat formatShort=DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT, Locale.CHINA);
		 System.out.println(formatShort.format(new Date()));// 12-7-31 上午10:43
		 
		 XMLReader xmlReader=XMLReaderFactory.createXMLReader();
		 InputSource input= new InputSource(new FileInputStream("test.xml"));
		 xmlReader.parse(input);
		 
		 
		// Collection.iterator工厂方法,
		 URL url=new URL("http://www.baidu.com");//工厂方法　（使用new建立工厂类，但没有的抽像工厂）
		 URLConnection conn=url.openConnection();//URLConnection是抽象的
		  //JMS的工厂方法
		 //ConnectionFactory  创建 Connection, 再建Session,再建createProducer,createConsumer
		 
		 
		 
		 //抽象工厂
		 Calendar.getInstance();//产生 Calendar抽象类的具体实现
		 Connection sqlConn=DriverManager.getConnection("");//java.sql包
		 Statement statment=sqlConn.createStatement();
		 
		 
		 

	 }

}
class MyObject{
	
}


