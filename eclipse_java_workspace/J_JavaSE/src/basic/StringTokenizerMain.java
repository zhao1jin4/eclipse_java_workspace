package basic;

import java.util.StringTokenizer;

public class StringTokenizerMain {

	public static void main(String[] args) {
	 
		StringTokenizer tokenizer=new StringTokenizer("hello,world~abc","~,");
		while(tokenizer.hasMoreElements())
		{
			//System.out.println(tokenizer.nextToken());
			System.out.println(tokenizer.nextElement());//nextElement()��nextToken()Ч����ͬ
		}
	}

}
