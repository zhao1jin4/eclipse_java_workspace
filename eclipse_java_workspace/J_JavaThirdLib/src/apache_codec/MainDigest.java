package apache_codec;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.codec.digest.DigestUtils;

import netty41_my.MyByteArrayUtil;

public class MainDigest {

	public static void main(String[] args) {
	
		byte[] data="hello".getBytes(Charset.forName("UTF-8"));
		byte[] sha1Byte=DigestUtils.sha1(data); 
		String bytehexStr=MyByteArrayUtil.byteToHexString(sha1Byte);
		String hexStr=DigestUtils.sha1Hex(data);
		System.out.println(hexStr.equals(bytehexStr));
		
		
		String file="C:/My/soft_ware/___java/apache-cxf-3.0.3.tar.gz";
		try {
			
			String sha1Hex=DigestUtils.sha1Hex(new FileInputStream(file));
			System.out.println(sha1Hex);
			
			String md5Hex=DigestUtils.md5Hex(new FileInputStream(file));
			System.out.println(md5Hex);
			
			
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}

}
