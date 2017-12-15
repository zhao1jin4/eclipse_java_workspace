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
			System.out.println(file.getName() + "������ΪUTF-8 BOM");
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
//			FileReader reader =  new  FileReader( file);//û��ָ�������� InputStreamReader �еı���,��ϵͳĬ�ϵ��ַ���������
//			String encoding=reader.getEncoding();//����ʼ����GBK(ϵͳĬ�ϵ��ַ���������)
//			BufferedReader buffer =new BufferedReader(reader);  
//			String line=buffer.readLine();//���ϵͳ���벻һ��,����
//			System.out.println(line); 
//			buffer.close();
//		}
//			

 		
//			OutputStreamWriter utf8Writer=new  OutputStreamWriter(new FileOutputStream(file,false), Charset.forName("UTF-8"));
//			utf8Writer.write("����һ������");
//			utf8Writer.close();
		
			//��InputStreamReader����FileReader
			BufferedReader reader =new BufferedReader(new  InputStreamReader(new FileInputStream(file),Charset.forName("UTF-8")));
			System.out.println(reader.readLine());
			// windows�� ���Ǽ��±���ΪUTF-8���ļ�,��ʼ�ж����ַ�,��UTF8 BOMͷ,��Ҫ���ɴ�BOMͷ��UTF8�ļ�, ʹ��notepad++�½��ļ��������UTF-8
			reader.close();
	      
			isUTF8BOMFile(file);
	}
}
