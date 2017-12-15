package instrument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyClassFileTransformer implements ClassFileTransformer 
{
	// 修改TransClass返回2,保存.class文件为.class.2
	public static final String classNumberReturns2 = "D:/eclipse_java_workspace/J_JavaSE/bin/instrument/TransClass.class.2";
	public static byte[] getBytesFromFile(String fileName) {
		try {
			File file = new File(fileName);
			InputStream is = new FileInputStream(file);
			long length = file.length();
			byte[] bytes = new byte[(int) length];
			
			
			
			is.read(bytes);
			
//			int offset = 0;
//			int numRead = 0;
//			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
//				offset += numRead;
//			}
//
//			if (offset < bytes.length) {
//				throw new IOException("Could not completely read file " + file.getName());
//			}
			
			is.close();
			return bytes;
		} catch (Exception e) {
			System.out.println("error occurs in _ClassTransformer!" + e.getClass().getName());
			return null;
		}
	}

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) 
			throws IllegalClassFormatException {
//		System.out.println("className="+className);
		if (!className.equals("instrument/TransClass")) {
			return null;
		}
//		System.out.println("-- transform");
		return getBytesFromFile(classNumberReturns2);
		
	}
	//public static void main(String[] args) {
	//	getBytesFromFile(classNumberReturns2);
	//}
}