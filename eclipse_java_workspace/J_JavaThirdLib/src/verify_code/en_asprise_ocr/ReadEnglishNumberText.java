package verify_code.en_asprise_ocr;

import java.io.File;

import com.asprise.ocr.Ocr;

public class ReadEnglishNumberText {
	public static void basic() 
	{
		//֧��jpg
		String imgFile="C:/My/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/verify_code/en_asprise_ocr/code.jpg";
		Ocr.setUp(); // one time setup
		 Ocr ocr = new Ocr();
		 ocr.startEngine("eng", Ocr.SPEED_FASTEST);
		 String s = ocr.recognize(new File[] {new File(imgFile)}, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT, 0, null);
		 System.out.println("RESULT: " + s);
		 // do more recognition here ...
		 ocr.stopEngine();
	}
	
	//�����תһ��ͼƬ�е���ĸ�Ͳ�����???,��360�����ʶ�� 12306����֤��???
	//�����תһ���Ͳ���,��360�����ʶ�� 12306����֤��
	public static void compelxt() 
	{
		//֧��jpg
		String imgFile="C:/My/all_code_workspace/eclipse_java_workspace/J_JavaThirdLib/src/verify_code/en_asprise_ocr/complex_code.jpg";
		Ocr.setUp(); // one time setup
		 Ocr ocr = new Ocr();
		 ocr.startEngine("eng", Ocr.SPEED_SLOW);
		 String s = ocr.recognize(new File[] {new File(imgFile)}, Ocr.RECOGNIZE_TYPE_ALL, Ocr.OUTPUT_FORMAT_PLAINTEXT, 0, null);
		 System.out.println("RESULT: " + s);
		 // do more recognition here ...
		 ocr.stopEngine();
	}
	public static void main(String[] args) 
	{
		//basic() ;//OK
		compelxt() ;//Fail????
	}

}
