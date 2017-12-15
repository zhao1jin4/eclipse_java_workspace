package jcraft_jsch_sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class TestJSCH_SFTP {

    public ChannelSftp connect(String host, int port, String username,
            String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            
            
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return sftp;
    }
    public void upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException{
        return sftp.ls(directory);
    }
    
    public static void main(String[] args) {
    	TestJSCH_SFTP sf = new TestJSCH_SFTP();   
    	 String host = "127.0.0.1";
    	 String username = "zh";
         String password = "lzj";
        int port = 22;
        
        String directory = "/";
        String uploadFile = "C:/TEMP/ftp_client/upload.txt";
        
      //  String downloadFile = "upload.txt";
        String saveFile = "C:\\TEMP\\download.txt";
        String deleteFile = "delete.txt";
        
        ChannelSftp sftp=sf.connect(host, port, username, password);
        try{
        	
        	sftp.rename("/upload.txt.tmp", "/testDir/upload.txt");//同 linux mv 可重命名,也可移动文件
        	
	        //sf.upload(directory, uploadFile, sftp);
	        //sf.download(directory, downloadFile, saveFile, sftp);
	        //sf.delete(directory, deleteFile, sftp);
   
       
//            sftp.cd(directory);
//            sftp.mkdir("testDir");
//            System.out.println("finished");
        }catch(Exception e){
            e.printStackTrace();
        }   
    }     
} 