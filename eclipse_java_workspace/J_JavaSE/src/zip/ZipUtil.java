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
		System.out.println("defaultCharset是:"+Charset.defaultCharset());
//		Charset  gbk=Charset.forName("GBK");
	}
	//OK
	public static void unZipFile(String zipFile,String outputDir ) throws IOException
	// outputDir以/结束
	{
		File root=new File(outputDir);
		if( ! root.exists())
			root.mkdir();
		
		BufferedInputStream bufferInput=new BufferedInputStream(new FileInputStream(zipFile));
		Charset  gbk=Charset.forName("GBK");
		Charset  def=Charset.defaultCharset();//windows下是GBK
        ZipInputStream zipInput = new ZipInputStream(bufferInput,def);//如不传第二参数,zip中的文件名中文只可是UTF8
        ZipEntry entry;
        while ((entry = zipInput.getNextEntry()) != null)
        {
            System.out.println("Extracting: " + entry);//如文件夹尾带/,如mydir/
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
		ZipOutputStream zipOutput=new ZipOutputStream(new BufferedOutputStream(fos,BUFFER_SIZE));//可以不加Charset.defaultCharset()
		File inDir=new File(sourceDir);
		loopDirIntoZip(inDir,inDir,zipOutput);
		zipOutput.close();
	}
	private static void  loopDirIntoZip(File baseDir,File sourceFile,ZipOutputStream zipOutput) throws IOException
	{
		//zipOutput.putNextEntry( new ZipEntry("a/b/c"));
		//不能以/开头,也不能使用"",可一次性加多级子目录,也可以一级一级的加
		String zipPair=sourceFile.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
		if(sourceFile.isDirectory())//这里是一级一级的进入,复杂点
		{	
			if(! zipPair.equals(""))//第一次zipPair是"",第一级目录
			{
				zipPair=zipPair.substring(1);//去开头/
				zipPair+=File.separator; //加尾部/
				ZipEntry entry= new ZipEntry(zipPair);
				zipOutput.putNextEntry(entry);
				System.out.println("Compress dir: "+entry);
			}
			File dirContents[]=sourceFile.listFiles();
			for(File f:dirContents)
				loopDirIntoZip(baseDir,f,zipOutput);//做递归
		}else
		{
			BufferedInputStream itemInput = new BufferedInputStream(new FileInputStream(sourceFile),BUFFER_SIZE);
			byte data[]= new byte[BUFFER_SIZE];
			zipPair=zipPair.substring(1);//去开头/
			ZipEntry entry= new ZipEntry(zipPair);
			System.out.println("Compress file: "+entry);
			zipOutput.putNextEntry(entry);
			//在向ZIP输出流写入数据之前，必须首先使用 putNextEntry(entry); 方法安置压缩条目对象 
			int count;
			while((count=itemInput.read(data,0,BUFFER_SIZE))!=-1)
				zipOutput.write(data,0,count);
			itemInput.close();
		}
	}
	
	
	
	
	public static void main(String[] args) throws IOException
	{
		//listAllCharset();
		ZipUtil.unZipFile("c:/temp/temp_CN.zip", "d:/temp/temp_zip_dir/");//outputDir以/结束,OK
		//ZipUtil.writeZipFile("d:/temp/temp_zip_dir/","c:/temp/temp_zip_dir.zip");//OK
		
	
	}
}
