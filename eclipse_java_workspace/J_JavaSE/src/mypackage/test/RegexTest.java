package mypackage.test;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class  RegexTest

{
	public static void main(String[] args) 
	{
		Object o=null;
		if(o instanceof String)
		{
			String x=(String)o;
		}
		
		{
			String input="sdf@sdfj.om";
			String rule="\\p{Alpha}+@\\w+\\.\\p{Alpha}{2,3}";
			Boolean isright=Pattern.matches(rule, input) ;
			System.out.println(isright);
			
	/////////////////////////////////////////--------------------------------
	
			Pattern pattern=Pattern.compile("\\p{Alpha}+@\\w+\\.\\p{Alpha}{2,3}");
			String words[] =  pattern.split(input);
	
			Matcher matcher = pattern.matcher(input);
	
	
			 Pattern p = Pattern.compile("a*b");
			 Matcher m = p.matcher("sdfb");
			 boolean b = m.matches();
			System.out.println(b);
			
			
			Pattern p1=Pattern.compile("(')|(%)|(_)");
			Matcher m1=p1.matcher("my%st_u0'");
			String res=m1.replaceAll("");
			System.out.println(res);
		}
		
		{		
			String e = "^([0-9]{11}|[0-9]{13})+$";
			Pattern p=Pattern.compile(e);
			Matcher m=p.matcher("86130a11112222");
			System.out.println(m.matches());
			 
		
		}
		
		

		{  
			//appendReplacement java doc�е�ʾ���޸�
			 Pattern p = Pattern.compile("<(\\w+)></(\\w+)>");//��XMLû�����ݵ�������ǩһ���޸�Ϊһ����ǩ
			 Matcher m = p.matcher("<person><name></name> <age></age></person>");
			 StringBuffer sb = new StringBuffer();
			 while (m.find())
			 {
				 System.out.println(m.group()); // ���������ʽ
				 System.out.println(m.group(0)); // 0�����������ʽ
				 System.out.println(m.group(1)); //����ֻҪ������1������ 
				 m.appendReplacement(sb,"<"+m.group(1)+"/>");
			 }
			 m.appendTail(sb);
			 System.out.println(sb.toString());
		}
		
		
		//Pattern p=Pattern.compile("(.{3,10})[0-9]"); //����(.{3,10})[0-9] ̰����Greedy ����ʾ����aaaa5bbbb6(��ȫ��(10��)������)
		//Pattern p=Pattern.compile("(.{3,10}?)[0-9]");//���� (.{3,10}?)[0-9] ��ǿ�ģ�Reluctant ����ʾ����aaaa5���ȳ�3wh ,��һ��һ���ĳԣ�
		Pattern p=Pattern.compile("(.{3,10}+)[0-9]");  //���� (.{3,10}+)[0-9] ռ�е�(Possessive )ȫ��(10��)�������£�����û��ƥ��ģ�����β��һ������ƥ��
		String s="aaaa5bbbb6"; 
		Matcher m=p.matcher(s);
		 while (m.find())
		 {
			 System.out.println(m.group());  
		 }
	
		 
		 
		 
		 // (?=exp) ƥ��expǰ���λ��       , javaDoc ��˵ (?=X) X, via zero-width positive lookahead  
		 //p=Pattern.compile(".{3}(?=a)"); //���444;  
		   p=Pattern.compile("(?=a).{3}"); //�����a66, (?=a)��ǰ���Լ�����ȥ a
		  
		  //(?!exp) ƥ�������Ĳ���exp��λ��   ,javaDoc ��˵ (?<!X) X, via zero-width negative lookbehind 
		// p=Pattern.compile(".{3}(?<!a)"); //���444 ��a66����
		  	
		  // (?<=exp) ƥ��exp�����λ��  ,javaDoc ��˵ (?<=X) X, via zero-width positive lookbehind 
		  p=Pattern.compile(".{3}(?<=a)");// �����44a��,��ǰ���Լ�����ȥ a
		  m=p.matcher("444a66b");
		  while (m.find())
		 {
			 System.out.println(m.group());  
		 }
		 
		 
	}
}
