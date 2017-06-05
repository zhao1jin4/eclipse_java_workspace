package apache_codec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MainClass
{
	public static void main(String[] args)
	{
		
		byte [] buffer="这是一个中文".getBytes();
//		String bin="c:/temp/Blue hills.jpg";
//		try {
//			
//			File file=new File(bin);
//			int len=(int)file.length();
//			buffer =new byte[len];
//			FileInputStream input=new FileInputStream(bin);
//			input.read(buffer, 0, len);
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		
		
		byte[] encoded_buffer=base64Encode(buffer);
			
		System.out.println("==========My= SUN==========");
		System.out.println(new String(encoded_buffer));
		
		
		byte[]apache=Base64Util.encodeAsBytes(buffer);
		System.out.println("==========My=====apache======");
		System.out.println(new String(apache));
		
		
		//--------------before is same
		
		System.out.println("==========My=====apache==ISO-8859-1====");
		try
		{
			System.out.println(new String(apache,"ISO-8859-1"));
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
		byte[] apaceEncoded=ApacheBase64Util.base64Encode("这是一个中文".getBytes());
		System.out.println(new String(apaceEncoded));

		byte[] sunEncoded=SunBase64Util.base64Encode("这是一个中文".getBytes());
		System.out.println(new String(sunEncoded));
		
		//JDK
		byte[] encoded=Base64.getEncoder().encode("这是一个中文".getBytes());
		System.out.println(new String(encoded));
		
		byte[] decoded=Base64.getDecoder().decode(encoded);
		System.out.println(new String(decoded));
		
		
	}
	
	public static synchronized byte[] base64Decode(byte[] pkgBin_before)
	{

		byte[]pkgBin=null;
		BASE64Decoder  decoder=new BASE64Decoder ();
		try
		{
			pkgBin=decoder.decodeBuffer(new ByteArrayInputStream(pkgBin_before));
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		return pkgBin;
	}
	public static synchronized byte[] base64Encode(byte[] pkgBin_before)
	{
		BASE64Encoder  encoder=new BASE64Encoder ();
		ByteArrayOutputStream output=new ByteArrayOutputStream();
		try
		{
			encoder.encode(pkgBin_before,output);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return output.toByteArray(); 
		//can not use output.toByteArray().length; or output.size();    can not output.write
		
	}

}
