package jcraft_jsch_sftp;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

import util_sftp.Transmit;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SftpTransmit implements Transmit 
{
	private ChannelSftp sftp = null;
	private String ip;
	private int port;
	private String username;
	private String password;
	
	private String keyDir;
	private String keyPhrase;
	
	private Session sshSession;
	private Channel channel;
	
	public SftpTransmit (String keyDir,String keyPhrase)
	{
		this.keyDir = keyDir;
		this.keyPhrase = keyPhrase;
	}
	public SftpTransmit(String ip,String username,String password)
	{
		this(ip,username,password,22);
	}
	public SftpTransmit(String ip,String username,String password,int port)
	{
		this.ip=ip;
		this.username=username;
		this.password=password;
		this.port=port;
	}
	public void connect() throws Exception
	{
        JSch jsch = new JSch();
        sshSession = jsch.getSession(username, ip, port);
        
        if(keyDir!=null)
        	jsch.addIdentity(this.keyDir);
        else
        	sshSession.setPassword(password);
     
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        channel = sshSession.openChannel("sftp");
        channel.connect();
        sftp = (ChannelSftp) channel;
	}
	public void close() 
	{
		try
		{
			sftp.exit();
			sftp.disconnect();
			sftp = null;
			
			channel.disconnect();
			channel=null;
			sshSession.disconnect();
			sshSession=null;
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
			 sftp.cd(serverDir);
			 File file=new File(clientDir + filename);
			 input= new FileInputStream(file);
	         sftp.put(input, file.getName());
		}catch(Exception e)
		{
			throw e;
		}finally
		{
			 try{ 
				 input.close();
				 input = null;
			 }catch(Exception e){
				//e.printStackTrace();
			 }
		}
	}
	
	public void uploadFile(InputStream in,String serverFile) throws Exception 
	{
    	if (in != null) 
    		this.sftp.put(in, serverFile);
	}
	
	// not work perfect
	public void copyFile(String serverSourceFile,String serverDestFile) throws Exception
	{
		
//		InputStream in=this.sftp.get(serverSourceFile);
//		this.sftp.put(in,serverDestFile);//here is blocking 
		
	/*	
		InputStream input=this.sftp.get(serverSourceFile);
		OutputStream output=this.sftp.put(serverDestFile);
		try 
		{
			byte[] buffer=new byte[1024*10];
			int len=0;
			while((len=input.read(buffer))!=-1)
			{
				System.out.println(len);
				output.write(buffer,0,len);// i can not do this , i think ,the get and put method use same buffer 
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				input.close();
				output.close();
			}catch(Exception e)
			{
				//e.printStackTrace();
			}
		}
	*/	
		
		//use memory  ,may be out of memory 
//		InputStream input=this.downloadFile(serverSourceFile);
//		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//		byte[] buf = new byte[10240];
//		int left = 0;
//		while((left= input.read(buf, 0, 10240))>0){
//			swapStream.write(buf,0,left);
//		}
//		InputStream copyInput = new ByteArrayInputStream(swapStream.toByteArray()); 
//		this.uploadFile(copyInput, serverDestFile);
//		input.close();
//		swapStream.close();
//		copyInput.close();
//		input = null;
//		swapStream = null;
//		copyInput = null;
		
		
		
		//use temp file ,may be no permission write a file to disk
		InputStream input=this.sftp.get(serverSourceFile);
		File temp=new File(System.getProperty("user.dir")+"/"+"ftpTemp.txt");
		FileOutputStream outputTemp=new FileOutputStream(temp);
		FileInputStream inputTemp=new FileInputStream(temp);
		OutputStream output=this.sftp.put(serverDestFile);
		try 
		{
			byte[] buffer=new byte[1024*10];
			int len=0;
			while((len=input.read(buffer))!=-1)
			{
				outputTemp.write(buffer,0,len);
			}
			
			while((len=inputTemp.read(buffer))!=-1)
			{
				output.write(buffer,0,len);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				inputTemp.close();
				outputTemp.close();
				input.close();
				output.close();
			}catch(Exception e)
			{
				//e.printStackTrace();
			}
			temp.delete();
		}
		
		
		//new version have sftp.realpath("/aa.txt");  but result is sftp absolute path
		
	}
	
	public InputStream downloadFile(String downloadFile)throws Exception 
	{
		return this.sftp.get(downloadFile);
	}
	  
  	public void downloadFile(String serverFile, String clientFile)  throws Exception 
  	{
  		FileOutputStream out=null;
		try 
		{
			File file = new File(clientFile);
			out= new FileOutputStream(file);
			this.sftp.get(serverFile,out);
		}finally
		{
			 try{ 
				 out.close();
				 out = null;
			 }catch(Exception e) {
				//e.printStackTrace();
			 }
		}
	}

	public void deleteFile(String serverDir, String filename)throws Exception 
	{
            sftp.cd(serverDir);
            sftp.rm(filename);
    }
	
	 
	public void renameMoveFile(String sourceFile, String destFile) throws Exception 
	{
		sftp.rename(sourceFile, destFile);
	}
	
	public Vector<String> listFiles(String dir) throws Exception 
	{
		Vector allFile=sftp.ls(dir);
		Vector<String> result= new Vector<String>();
		for(Object file:allFile)
		{	
			ChannelSftp.LsEntry entry=(ChannelSftp.LsEntry)file;
			SftpATTRS attr=entry.getAttrs();
			if(! attr.isDir())
				result.add(entry.getFilename());
		}
		  return result;
	}
	public Vector<String> listDirs(String dir) throws Exception 
	{
		Vector allFile=sftp.ls(dir);
		Vector<String> result= new Vector<String>();
		for(Object file:allFile)
		{	
			ChannelSftp.LsEntry entry=(ChannelSftp.LsEntry)file;
			SftpATTRS attr=entry.getAttrs();
			if(attr.isDir())
				result.add(entry.getFilename());
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
			if(e.getMessage().equals("No such file."))
				return false;
			else
				throw e;
		}
		return allFile.contains(filename);
	}
	
	public boolean isExistDir(String dir) throws Exception
	{
		String pwd=sftp.pwd();
		if("".equals(pwd))
			pwd="/";
		boolean result=true;
		
		try{
			sftp.cd(dir);
		}catch(SftpException e )
		{
			//if(e.getMessage().equals("No such file."))
			if(e.id==2)
				result=false;
			else
				throw e;
		}
		
		sftp.cd(pwd);
		return result;
	}
	
	
	public void makeDir(String dir) throws Exception
	{
		String pwd=sftp.pwd();
		String []dirs=dir.split("/");
		for(int i=0;i<dirs.length ;i++)
		{
			String d=dirs[i];
			
			if("".equals(d))
				 continue;
			
			if(!isExistDir(d))
				sftp.mkdir(d);
			sftp.cd(d);
		}
		sftp.cd(pwd);
	}
	
}
