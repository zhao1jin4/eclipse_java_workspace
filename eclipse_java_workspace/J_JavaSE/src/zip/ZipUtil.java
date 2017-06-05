package zip;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
public class ZipUtil
{
	java.util.zip.GZIPInputStream g=null;
	java.util.jar.JarInputStream j=null;
	static int BUFFER_SIZE = 2048; 
	
	public static void listAllCharset()  
	{
		SortedMap<String, Charset>   map=Charset.availableCharsets();
		Set<Map.Entry <String, Charset>> set=map.entrySet();
		Iterator <Map.Entry <String, Charset>> iterator=set.iterator();
		while(iterator.hasNext())
		{
			System.out.println(iterator.next().getValue());
		}
		System.out.println("defaultCharset��:"+Charset.defaultCharset());
//		Charset  gbk=Charset.forName("GBK");
	}
	//OK
	public static void unZipFile(String zipFile,String outputDir ) throws IOException
	// outputDir��/����
	{
		File root=new File(outputDir);
		if( ! root.exists())
			root.mkdir();
		
		BufferedInputStream bufferInput=new BufferedInputStream(new FileInputStream(zipFile));
		Charset  gbk=Charset.forName("GBK");
		Charset  def=Charset.defaultCharset();//windows����GBK
        ZipInputStream zipInput = new ZipInputStream(bufferInput,def);//�粻���ڶ�����,zip�е��ļ�������ֻ����UTF8
        ZipEntry entry;
        while ((entry = zipInput.getNextEntry()) != null)
        {
            System.out.println("Extracting: " + entry);//���ļ���β��/,��mydir/
            File file=new File(outputDir + entry.getName());
            if(entry.isDirectory())
            {
            	file.mkdir();
            	continue;
            }
            int BUFFER_SIZE = 1024;
            byte data[] = new byte[BUFFER_SIZE];
            BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
            int count;
            while ((count = zipInput.read(data, 0, BUFFER_SIZE)) != -1) 
            {
                dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
        }
        zipInput.close();
	}
		
	//OK
	public static void writeZipFile(String sourceDir,String outZipFile) throws IOException 
	{
		FileOutputStream fos=new FileOutputStream(new File(outZipFile),false);//append
		ZipOutputStream zipOutput=new ZipOutputStream(new BufferedOutputStream(fos,BUFFER_SIZE));//���Բ���Charset.defaultCharset()
		File inDir=new File(sourceDir);
		loopDirIntoZip(inDir,inDir,zipOutput);
		zipOutput.close();
	}
	private static void  loopDirIntoZip(File baseDir,File sourceFile,ZipOutputStream zipOutput) throws IOException
	{
		//zipOutput.putNextEntry( new ZipEntry("a/b/c"));
		//������/��ͷ,Ҳ����ʹ��"",��һ���ԼӶ༶��Ŀ¼,Ҳ����һ��һ���ļ�
		String zipPair=sourceFile.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
		if(sourceFile.isDirectory())//������һ��һ���Ľ���,���ӵ�
		{	
			if(! zipPair.equals(""))//��һ��zipPair��"",��һ��Ŀ¼
			{
				zipPair=zipPair.substring(1);//ȥ��ͷ/
				zipPair+=File.separator; //��β��/
				ZipEntry entry= new ZipEntry(zipPair);
				zipOutput.putNextEntry(entry);
				System.out.println("Compress dir: "+entry);
			}
			File dirContents[]=sourceFile.listFiles();
			for(File f:dirContents)
				loopDirIntoZip(baseDir,f,zipOutput);//���ݹ�
		}else
		{
			BufferedInputStream itemInput = new BufferedInputStream(new FileInputStream(sourceFile),BUFFER_SIZE);
			byte data[]= new byte[BUFFER_SIZE];
			zipPair=zipPair.substring(1);//ȥ��ͷ/
			ZipEntry entry= new ZipEntry(zipPair);
			System.out.println("Compress file: "+entry);
			zipOutput.putNextEntry(entry);
			//����ZIP�����д������֮ǰ����������ʹ�� putNextEntry(entry); ��������ѹ����Ŀ���� 
			int count;
			while((count=itemInput.read(data,0,BUFFER_SIZE))!=-1)
				zipOutput.write(data,0,count);
			itemInput.close();
		}
	}
	
	
	
	
	public static void main(String[] args) throws IOException
	{
		//listAllCharset();
		ZipUtil.unZipFile("c:/temp/temp_CN.zip", "d:/temp/temp_zip_dir/");//outputDir��/����,OK
		//ZipUtil.writeZipFile("d:/temp/temp_zip_dir/","c:/temp/temp_zip_dir.zip");//OK
		
	
	}
}
