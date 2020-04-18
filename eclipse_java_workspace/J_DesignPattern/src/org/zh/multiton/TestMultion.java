package org.zh.multiton;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestMultion {
	public static void main(String[] args) 
	{
		//骰子,有个数限制,必须自己创建
		
		
		ResourceBundle bundle=getResourceBundle("zh","CN");
		String hello=bundle.getString("hello");
		System.out.println(hello);
		
		ResourceBundle bundle_en=getResourceBundle("en","US");
		String hello_en=bundle_en.getString("hello");
		System.out.println(hello_en);
		
		
		NumberFormat format =NumberFormat.getPercentInstance(new Locale("zh","CN"));
		String result=format.format(0.7632);
		 System.out.println(result);
		 
		 
		NumberFormat currencyFormat =NumberFormat.getCurrencyInstance(new Locale("zh","CN"));
		String currencyResult=currencyFormat.format(333222);
		System.out.println(currencyResult);
		 
	}
	
	public static ResourceBundle getResourceBundle(String lang,String country)
	{
		ResourceBundle bundle=ResourceBundle.getBundle("org/zhaojin/multiton/message",new Locale(lang,country));
		return bundle;
	}

}
