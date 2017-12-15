package jcraft_jsch_sftp;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import util_sftp.SftpTransmit;
import util_sftp.Transmit;

import com.jcraft.jsch.SftpException;
public class SftpTest 
{
	@BeforeClass
	public static void init()
	{
	}
	@AfterClass
	public static void destory()
	{
		
	}
	
	
	@Test
	public void testIsExitDir()
	{
		Transmit sftp=null;
		boolean isExist=false;
		try
		{
			//sftp=new SftpTransmit("127.0.0.1", "zh", "lzj", 22);
			sftp=new SftpTransmit("c:/temp/","123");//Œ¥≤‚ ‘
			sftp.connect();
//			isExist=sftp.isExistDir("/testDir");
//			assertTrue(isExist);
			
			isExist=sftp.isExistDir("/testNotDir");
			assertFalse(isExist);
		}catch(Exception e)
		{
			e.printStackTrace();	
		}finally{
			sftp.close();
		}
	}
	
	@Test
	public void testIsExitFile()
	{
		Transmit sftp=null;
		boolean isExist=false;
		try
		{
			sftp=new SftpTransmit("127.0.0.1", "zh", "lzj", 22);
			sftp.connect();
			isExist=sftp.isExistFile("/cc/upload.txt");
			assertFalse(isExist);
			
//			isExist=sftp.isExistFile("/uploadNot.txt");
//			assertFalse(isExist);
		}catch(Exception e)
		{
			e.printStackTrace();	
		}finally{
			sftp.close();
		}
	}
	@Test
	public void testSftpCopyFile()throws Exception
	{
		boolean isOK=false;
		Transmit sftp=null;
		try {
				sftp=new SftpTransmit("127.0.0.1", "zh", "lzj", 22);
				sftp.connect();
				sftp.copyFile("/buyer.txt", "/testDir/buyer_22.txt");
				isOK=sftp.isExistFile("/testDir/buyer_22.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			sftp.close();
		}
		assertTrue(isOK);
	}
	
	@Test
	public void testSftpMakeDir()throws Exception
	{
		boolean isOK=false;
		Transmit sftp=null;
		try {
				sftp=new SftpTransmit("127.0.0.1", "zh", "lzj", 22);
				sftp.connect();
				sftp.makeDir("/aa/bb/testMkDir");
				isOK=sftp.isExistDir("/aa/bb/testMkDir");
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			sftp.close();
		}
		assertTrue(isOK);
	}
	
	
}
