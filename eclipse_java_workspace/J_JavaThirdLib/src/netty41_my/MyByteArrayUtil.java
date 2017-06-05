package netty41_my;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

 
public class MyByteArrayUtil 
{
	public static String byteToHexString(byte bytes[]) 
	{
		//char HEX[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len=bytes.length;
		StringBuffer buffer = new StringBuffer(2 * len);
		for (int i = 0;i < len; i++) 
		{	
			String c0 =Integer.toHexString( (bytes[i] & 0xf0) >> 4 );  //>>>是无符号右移,左补0
			String c1 =Integer.toHexString(  bytes[i] & 0x0f       );
			//或者用
			//char c0 = HEX[ (bytes[i] & 0xf0) >> 4 ];
			//char c1 = HEX[  bytes[i] &  0x0f ];
			buffer.append(c0);
			buffer.append(c1);
		}
		return buffer.toString();
	}
	
	 public static byte[] hexStringToBtye(String hex) 
	 {
		  byte[] hexBuf = hex.getBytes();
		  int len = hexBuf.length;
		  byte[] resultBuf = new byte[len / 2];
		  for (int i = 0; i < len; i = i + 2) 
		  {
			   String two = new String(hexBuf, i, 2);//每次取两个
			   resultBuf[i / 2] = (byte) Integer.parseInt(two, 16);
		  }
		 //int len = hex.length();//可能会是奇数   /2 是错误的
		  return resultBuf;
	}
	
	 public static int  byteArrayToInt( byte[] buf) throws Exception 
	{
		if(buf.length!=4)
			throw new Exception("字节数组 转int 长度不为4");
		ByteArrayInputStream byteInput = new ByteArrayInputStream(buf);
		DataInputStream dataIntput = new DataInputStream(byteInput);
		return  dataIntput.readInt();
	}//int res=byteArrayToInt(new byte[] {0,0,5,9}); //十六进制509=1289
	
	 public static int byteArrayToInt(byte[] b, int offset) throws Exception 
	{
		if(b.length - offset !=4 )
			throw new Exception("字节数组 转int 长度不为4");
	   int value= 0;
	   for (int i = 0; i < 4; i++) 
	   {
		   int shift= (4 - 1 - i) * 8;
		   value +=(b[i + offset] & 0x000000FF) << shift;
	   }
	   return value;
	 }
	//----
	public static byte[] intToByteArray2(int i) throws Exception
	{
	  ByteArrayOutputStream buf = new ByteArrayOutputStream();  
	  DataOutputStream out = new DataOutputStream(buf);  
	  out.writeInt(i);  
	  byte[] b = buf.toByteArray();
	  out.close();
	  buf.close();
	  return b;
	}//根据返回byte[] 可以查看下标为0的是高位
	
	private static byte[] intToByteArray1(int i) 
	{  
		byte[] result = new byte[4];  
		result[0] = (byte)((i >> 24) & 0xFF);
		result[1] = (byte)((i >> 16) & 0xFF);
		result[2] = (byte)((i >> 8) & 0xFF);
		result[3] = (byte)(i & 0xFF);
		return result;
	}
}
