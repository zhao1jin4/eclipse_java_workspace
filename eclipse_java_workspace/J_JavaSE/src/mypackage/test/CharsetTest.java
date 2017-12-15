package mypackage.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

class CharsetTest 
{
	public static boolean isUTF8BOMFile(File file) throws Exception 
	{
		InputStream in = new FileInputStream(file);
		byte[] b = new byte[3];
		in.read(b);
		in.close();
		if (b[0] == -17 && b[1] == -69 && b[2] == -65)	//EF  BB  BF
		{	
			System.out.println(file.getName() + "：编码为UTF-8 BOM");
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception 
	{
		Properties pro = System.getProperties();
		System.out.println(pro.get("file.encoding"));// file.encoding=GBK

		// File file=new File("c:/temp/a.txt");
		File file=new File("c:/temp/notepad_utf8bom.txt");
		//System.getProperty("file.encoding")
		
//		{
//			FileReader reader =  new  FileReader( file);//没有指定父类中 InputStreamReader 中的编码,按系统默认的字符集来编码
//			String encoding=reader.getEncoding();//所以始终是GBK(系统默认的字符集来编码)
//			BufferedReader buffer =new BufferedReader(reader);  
//			String line=buffer.readLine();//如和系统编码不一样,乱码
//			System.out.println(line); 
//			buffer.close();
//		}
//			

 		
//			OutputStreamWriter utf8Writer=new  OutputStreamWriter(new FileOutputStream(file,false), Charset.forName("UTF-8"));
//			utf8Writer.write("这是一个测试");
//			utf8Writer.close();
		
			//用InputStreamReader代替FileReader
			BufferedReader reader =new BufferedReader(new  InputStreamReader(new FileInputStream(file),Charset.forName("UTF-8")));
			System.out.println(reader.readLine());
			// windows下 如是记事本另为UTF-8的文件,开始有多余字符,是UTF8 BOM头,不要生成带BOM头的UTF8文件, 使用notepad++新建文件保存就是UTF-8
			reader.close();
	      
			isUTF8BOMFile(file);
	}
}
