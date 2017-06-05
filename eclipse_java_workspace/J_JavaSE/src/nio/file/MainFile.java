package nio.file;

import java.io.File;

public class MainFile {
	public static void main(String[] args) {
		File fileDire = new File("/home/test");// 在windows上是建立在,当C:盘上没有权限时,会D:盘上建立
		boolean isOK = fileDire.mkdirs();
		System.out.println("dir create :"+isOK);
		
	}
}
