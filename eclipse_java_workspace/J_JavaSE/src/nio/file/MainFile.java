package nio.file;

import java.io.File;

public class MainFile {
	public static void main(String[] args) {
		File fileDire = new File("/home/test");// ��windows���ǽ�����,��C:����û��Ȩ��ʱ,��D:���Ͻ���
		boolean isOK = fileDire.mkdirs();
		System.out.println("dir create :"+isOK);
		
	}
}
