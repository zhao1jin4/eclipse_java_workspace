package util_sftp;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpTransmit implements Transmit 
{
 
	private String ip;
	private int port;
	private String username;
	private String password;
	
	private FTPClient ftpClient;
	
	public FtpTransmit(String ip,String username,String password)
	{
		this(ip,21,username,password);
	}
	public FtpTransmit(String ip,int port,String username,String password)
	{
		this.ip=ip;
		this.username=username;
		this.password=password;
		this.port=port;
	}
	public void connect() throws Exception
	{
	    this.ftpClient = new FTPClient();
        this.ftpClient.connect(this.ip, this.port);
        this.ftpClient.login(this.username, this.password);
        this.ftpClient.setFileType( FTP.BINARY_FILE_TYPE);
//        ftpClient.setControlEncoding("UTF-8");//ÕâÀïÉèÖÃ±àÂë
//        this.ftpClient.enterLocalActiveMode();
//        System.out.println(this.ftpClient.getReplyString());
//        if (FTPReply.isPositiveCompletion(this.ftpClient.getReplyCode()))
//        {
//    
//        }
      
	}
	public void close() 
	{
		try
		{
		    if (this.ftpClient != null)
		    {
		        this.ftpClient.logout();
		        this.ftpClient.disconnect();
		        this.ftpClient=null;
		    }
		}catch(Exception e)
		{
			//e.printStackTrace();
		}
	}
	public void uploadFile(String clientDir, String serverDir, String filename)throws Exception 
	{
		FileInputStream input=null;
		try
		{
			File file=new File(clientDir +File.separator+ filename);
			input= new FileInputStream(file);
			
			ftpClient.changeWorkingDirectory(serverDir);
			this.ftpClient.storeFile(file.getName() , input);
		}finally
		{
		    IOUtils.closeQuietly(input);
		}
	}
	
	public void uploadFile(InputStream input,String serverFile) throws Exception 
	{
	    this.ftpClient.storeFile(serverFile , input);
	}
	
	public void copyFile(String serverSourceFile,String serverDestFile) throws Exception
	{
        //use memory  ,may be out of memory 
      InputStream input=this.downloadFile(serverSourceFile);
      ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
      byte[] buf = new byte[10240];
      int left = 0;
      while((left= input.read(buf, 0, 10240))>0){
          swapStream.write(buf,0,left);
      }
      InputStream copyInput = new ByteArrayInputStream(swapStream.toByteArray()); 
      this.uploadFile(copyInput, serverDestFile);
      input.close();
      swapStream.close();
      copyInput.close();
      input = null;
      swapStream = null;
      copyInput = null;
	}
	
	public InputStream downloadFile(String downloadFile)throws Exception 
	{
	    return this.ftpClient.retrieveFileStream(downloadFile);
	}
	  
  	public void downloadFile(String serverFile, String clientFile)  throws Exception 
  	{
  		FileOutputStream out=null;
  		InputStream input=null;
		try 
		{
			File file = new File(clientFile);
			out= new FileOutputStream(file);
			input=this.ftpClient.retrieveFileStream(serverFile);
			byte buffer[]=new byte[1024*1024*1];
			int len=0;
			while ((len=input.read(buffer))!=-1)
	        {
			    out.write(buffer,0,len);
	        }
			
		}finally
		{
		    IOUtils.closeQuietly(input);
		    IOUtils.closeQuietly(out);
		}
	}

	public void deleteFile(String serverDir, String filename)throws Exception 
	{
	    this.ftpClient.changeWorkingDirectory(serverDir);
	    this.ftpClient.dele(filename);
    }
	
	 
	public void renameMoveFile(String sourceFile, String destFile) throws Exception 
	{
	    ftpClient.rename(sourceFile, destFile);
	}
	
	public Vector<String> listFiles(String dir) throws Exception 
	{
	    this.ftpClient.changeWorkingDirectory(dir);
	    FTPFile[] allFile=this.ftpClient.listFiles(dir);
		Vector<String> result= new Vector<String>();
		for(FTPFile file:allFile)
		{	
			if(! file.isDirectory())
				result.add(file.getName());
		}
		return result;
	}
	public Vector<String> listDirs(String dir) throws Exception 
	{
		  
	    this.ftpClient.changeWorkingDirectory(dir);
        FTPFile[] allFile=this.ftpClient.listFiles(dir);
        Vector<String> result= new Vector<String>();
        for(FTPFile file:allFile)
        {   
            if(file.isDirectory())
                result.add(file.getName());
        }
        return result;
	}
	
	public boolean isExistFile(String file)throws Exception
	{

	    Vector<String>  allFile=null;
        String dir=file.substring(0,file.lastIndexOf("/")+1);
        String filename=file.substring(file.lastIndexOf("/")+1);
        try{
            allFile=this.listFiles(dir);
        }catch(Exception e){
           System.err.println(e.getMessage());
            return false;
        }
        return allFile.contains(filename);
	}
	
	public boolean isExistDir(String dir) throws Exception
	{
	    String pwd=ftpClient.printWorkingDirectory();
        if("".equals(pwd))
            pwd="/";
        boolean result=true;
        
        try{
            ftpClient.changeWorkingDirectory(dir);
        }catch(IOException e )
        {
            System.err.println(e.getMessage());
           result=false;
        }
		return result;
	}
	
	
	public void makeDir(String dir) throws Exception
	{
		String pwd=ftpClient.printWorkingDirectory();
		
		String []dirs=dir.split("/");
		for(int i=0;i<dirs.length ;i++)
		{
			String d=dirs[i];
			
			if("".equals(d))
				 continue;
			
			if(!isExistDir(d))
			    this.ftpClient.makeDirectory(d);
			this.ftpClient.changeWorkingDirectory(d);
		}
		
		this.ftpClient.changeWorkingDirectory(pwd);
	}
	
}
