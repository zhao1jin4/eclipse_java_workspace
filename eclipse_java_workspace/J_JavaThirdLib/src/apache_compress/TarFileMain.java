package apache_compress;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class TarFileMain 
{
	public static void decompressTarGzFile(String tarGzFile,String outDir) 
	{
		ArchiveInputStream in = null;
		byte buffer[]= new byte[1024];
		try {
			 //GZIPInputStream is = new GZIPInputStream(new FileInputStream(tarGzFile));
			 //in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
			//二选一
			in =new TarArchiveInputStream(new GZIPInputStream(new FileInputStream(tarGzFile), 1024)); 
			TarArchiveEntry entry = (TarArchiveEntry) in.getNextEntry();
			while (entry != null) 
			{
				String name = entry.getName();// 如果是目录以/结尾 ,手工建立目录
				if(name.endsWith("/"))
					new File(outDir+"/"+name).mkdir();
				else
				{
					FileOutputStream out = new FileOutputStream(outDir+"/"+name,false);//overwrite 
					int len=-1;
					while ((len = in.read(buffer)) != -1)
						out.write(buffer,0,len);
					out.flush();
					out.close();
				}
				entry = (TarArchiveEntry) in.getNextEntry();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { in.close();  } catch ( Exception e) { }
		}
	}

	 //只压缩一个文件 
	  protected static void compressOneTarGzFile(File srcFile, File destFile)
	  {  
	        TarArchiveOutputStream out = null;  
	        InputStream is = null;  
	        try {  
	        	out = new TarArchiveOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(destFile)), 1024));
	        	 
	        	is = new BufferedInputStream(new FileInputStream(srcFile), 1024);  
	            TarArchiveEntry entry = new TarArchiveEntry(srcFile.getName());//这里不能传绝对目录的String,如果一个文件要getName(),不然会有上级目录名在包中
	            entry.setSize(srcFile.length());  
	            out.putArchiveEntry(entry);  
	            IOUtils.copy(is, out);  
	            out.closeArchiveEntry();  

	        } catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }finally { 
	            IOUtils.closeQuietly(is);  
	            IOUtils.closeQuietly(out);  
	        }  
	    }  
    
	//递归压缩目录
	public static void compressTarGzFromFileOrDir(String srcFile, String destFile)
	{
		TarArchiveOutputStream out = null;  
	    try {
	    	out = new TarArchiveOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(destFile)), 1024));
	    	compressWalkDir(new File(srcFile),out);
	    }catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    }finally { 
	        IOUtils.closeQuietly(out);  
	    }  
	}
	private static void compressWalkDir(File from,TarArchiveOutputStream out) throws Exception
	{
    	if(from.isDirectory() )
		{
    		TarArchiveEntry entry = new TarArchiveEntry(from);
	        out.putArchiveEntry(entry);
	        out.closeArchiveEntry();
    		for(File fileItem:from.listFiles())
    			compressWalkDir(fileItem,out);
		}else //file
		{
			InputStream  input = new BufferedInputStream(new FileInputStream(from), 1024);  
            TarArchiveEntry entry = new TarArchiveEntry(from);
            entry.setSize(from.length());  
            out.putArchiveEntry(entry);  
            IOUtils.copy(input, out); 
            out.closeArchiveEntry();
			IOUtils.closeQuietly(input);
		}
	}
	public static void main(String[] args) {
		//decompressTarGzFile("c:/temp/my.tar.gz", "d:/temp"); //OK
		//compressOneTarGzFile(new File("c:/temp/file.txt"),new File("c:/temp/file.tar.gz"));//OK
		compressTarGzFromFileOrDir( "c:/temp" , "d:/temp/temp.tar.gz" );//OK
		
	}
}