package apache_net_ftp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

//TEST OK
public class ContinueFTP {

  private FTPClient ftpClient = new FTPClient();

  public ContinueFTP() {
    ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
  }

  public boolean connect(String hostname, int port, String username, String password) throws IOException {
    ftpClient.connect(hostname, port);
    if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
      if (ftpClient.login(username, password)) {
        return true;
      }
    }
    disconnect();
    return false;
  }
  
  // 自已加的,测试OK
  public boolean upload(String local,String remote) throws IOException
{
    ftpClient.enterLocalPassiveMode();
    
    FTPFile[] files = ftpClient.listFiles(remote);  
    long remoteSize = -1;
    for (FTPFile ftpFile : files) {
		if(ftpFile.getName().equals("test.txt"))
			 remoteSize = ftpFile.getSize();  
	}
   
    boolean result=false;
    File f = new File(local);
    InputStream ins = new FileInputStream(f);
    if ( remoteSize<f.length() && remoteSize!=-1)
    {
      ins.skip(remoteSize);   
      ftpClient.setRestartOffset(remoteSize);
      result = ftpClient.storeFile(remote, ins);
      ins.close();
    } else
    {
          result = ftpClient.storeFile(remote, ins);
          ins.close();
    }
    return result;
  }
  public boolean download(String remote, String local) throws IOException {
	    ftpClient.enterLocalPassiveMode();
	    boolean result;
	    File f = new File(local);
	    if (f.exists()) {
	      OutputStream out = new FileOutputStream(f, true);
	      ftpClient.setRestartOffset(f.length());
	      result = ftpClient.retrieveFile(remote, out);
	      out.close();
	    } else {
	      OutputStream out = new FileOutputStream(f);
	      result = ftpClient.retrieveFile(remote, out);
	      ftpClient.sendCommand("mkdir test");
	      ftpClient.mkd("test");//就是mkdir
	      ftpClient.makeDirectory("");
	      ftpClient.changeWorkingDirectory("..");
	      out.close();
	    }
	    return result;
	  }
	 
  public void disconnect() throws IOException {
    if (ftpClient.isConnected()) {
     ftpClient.disconnect();
    }
  }
 
  public static void main(String[] args){
    ContinueFTP myFtp = new ContinueFTP();
    try {
      myFtp.connect("127.0.0.1", 21, "zh", "lzj");
    //  myFtp.download("test.txt","G:/test.txt");
      myFtp.upload("G:/test.txt","test.txt");
      myFtp.disconnect();
    } catch (IOException e) {
      System.out.println("连接FTP出错:"+e.getMessage());
    }
  }
}
