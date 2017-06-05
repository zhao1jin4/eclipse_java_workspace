package myservlet.tag;

import java.io.UnsupportedEncodingException;

public class MyELFunc  {
	//类的方法要是public static 的
	public static String toGBK(String str,String charset) throws UnsupportedEncodingException
	{
		return new String(str.getBytes(charset),"GBK");
	}
	public static String toUTF8(String str,String charset) throws UnsupportedEncodingException
	{
		return new String(str.getBytes(charset),"UTF8");
	}
}
