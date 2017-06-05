package jdknew;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AutomaticResourceManagement {
	
	private static void customBufferStreamCopy(File source, File target) {
	    InputStream fis = null;
	    OutputStream fos = null;
	    try {
	        fis = new FileInputStream(source);
	        fos = new FileOutputStream(target);
	  
	        byte[] buf = new byte[8192];
	  
	        int i;
	        while ((i = fis.read(buf)) != -1) {
	            fos.write(buf, 0, i);
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close(fis);
	        close(fos);
	    }
	}
	  
	private static void close(Closeable closable) {
	    if (closable != null) {
	        try {
	            closable.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	
	
	//---------使用try-with-resources语句 AutomaticResourceManagement
	private static void customBufferStreamCopy1(File source, File target) {
	    try (InputStream fis = new FileInputStream(source);
	        OutputStream fos = new FileOutputStream(target))
	    {//try() 必须实现 AutoCloseable 接口 就不用在 finally中close()了
	  
	        byte[] buf = new byte[8192];
	  
	        int i;
	        while ((i = fis.read(buf)) != -1) {
	            fos.write(buf, 0, i);
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
