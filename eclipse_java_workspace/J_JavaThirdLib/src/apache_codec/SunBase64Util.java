//package apache_codec;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import org.apache.log4j.Logger;
//
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
//public class SunBase64Util
//{
//	static Logger logger = Logger.getLogger(SunBase64Util.class);
//
//	public static void base64Decode(InputStream input,OutputStream output)
//	{
//		BASE64Decoder decoder = new BASE64Decoder();
//		try
//		{
//			decoder.decodeBuffer(input, output);
//		} catch (IOException e1)
//		{
//			logger.error("base64Decode error", e1);
//		}
//	}
//	
//	public static byte[] base64Decode(byte[] input)
//	{
//
//		byte[] pkgBin = null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try
//		{
//			pkgBin = decoder.decodeBuffer(new ByteArrayInputStream(input));
//		}catch (OutOfMemoryError er)
//		{
//			System.out.println("Java内存不足"); //是可以catch到的
//		}catch (IOException e1)
//		{
//			logger.error("base64Decode error", e1);
//		}
//		return pkgBin;
//	}
//
//	public static byte[] base64Encode(byte[] input)
//	{
//		BASE64Encoder encoder = new BASE64Encoder();
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		try
//		{
//			encoder.encode(input, output);
//		}catch (OutOfMemoryError er)
//		{
//			System.out.println("Java内存不足"); //是可以catch到的
//		}catch (IOException e)
//		{
//			logger.error("base64Encode error", e);
//		}
//		return output.toByteArray();
//		// can not use output.toByteArray().length; or output.size(); can not
//		// output.write
//
//	}
//
//	public static void base64Encode(InputStream input,OutputStream output)
//	{
//		BASE64Encoder encoder = new BASE64Encoder();
//		try
//		{
//			encoder.encode(input, output);
//		}catch (IOException e)
//		{
//			logger.error("base64Encode error", e);
//		}
//	}
//
//	
//
//	// 用SUN自带的进行BASE 64编码
//	public static String getBASE64(String s)
//	{
//		if (s == null)
//			return null;
//		return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
//	}
//	// 将 BASE64 编码的字符串 s 进行解码
//	public static String getFromBASE64(String s)
//	{
//		if (s == null)
//			return null;
//		BASE64Decoder decoder = new BASE64Decoder();
//		try
//		{
//			byte[] b = decoder.decodeBuffer(s);
//			return new String(b);
//		} catch (Exception e)
//		{
//			return null;
//		}
//	}
//
//	public static void main(String[] args)
//	{
//		String res = new String(base64Encode("zhao1jin4".getBytes()));
//		System.out.println(res);
//		String pass = new String(base64Encode("123".getBytes()));
//		System.out.println(pass);
//	}
//}
