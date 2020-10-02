package phantomjs;

import java.io.InputStream;

public class WebGenBrowserScreenShot {

	public static void main(String[] args) {
		try {
			savePage("https://www.baidu.com/","c:/tmp/my.png","D:\\NEW_\\eclipse_java_workspace\\J_JavaThirdLib/src/phantomjs/");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void savePage(String url, String imgPath, String dir) throws Exception {     
	    //phantomjs.exe --ignore-ssl-errors=yes ./snapshot.js https://www.baidu.com/  c:/tmp/my.png  √¸¡Ó≤‚ ‘OK 
	    StringBuffer buffer = new StringBuffer();  
	    buffer.append( "phantomjs.exe  --ignore-ssl-errors=yes ")     
	    .append(dir + "snapshot.js   ").append(url + "  ").append( imgPath);  
	    
	    Process process = Runtime.getRuntime().exec(buffer.toString());  
	    InputStream eis = process.getErrorStream();  
        byte[] buf = new byte[1024];  
        int len = 0;  
        while ((len = eis.read(buf)) != -1) {  
            System.out.println(new String(buf, 0, len));  
        }  
        eis.close();  

        InputStream is = process.getInputStream();  

        buf = new byte[1024];  
        while ((len = is.read(buf)) != -1) {  
            System.out.println(new String(buf, 0, len));  
        }  
        is.close();  

	}
}
