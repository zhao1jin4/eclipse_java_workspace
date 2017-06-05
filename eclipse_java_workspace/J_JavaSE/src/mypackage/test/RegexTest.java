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
			//appendReplacement java doc中的示例修改
			 Pattern p = Pattern.compile("<(\\w+)></(\\w+)>");//把XML没有内容的两个标签一组修改为一个标签
			 Matcher m = p.matcher("<person><name></name> <age></age></person>");
			 StringBuffer sb = new StringBuffer();
			 while (m.find())
			 {
				 System.out.println(m.group()); // 是整个表达式
				 System.out.println(m.group(0)); // 0组是整个表达式
				 System.out.println(m.group(1)); //我们只要捕获组1的数字 
				 m.appendReplacement(sb,"<"+m.group(1)+"/>");
			 }
			 m.appendTail(sb);
			 System.out.println(sb.toString());
		}
		
		
		//Pattern p=Pattern.compile("(.{3,10})[0-9]"); //如是(.{3,10})[0-9] 贪婪的Greedy ，显示的是aaaa5bbbb6(先全吃(10个)，再吐)
		//Pattern p=Pattern.compile("(.{3,10}?)[0-9]");//如是 (.{3,10}?)[0-9] 勉强的（Reluctant ）显示的是aaaa5（先吃3wh ,再一个一个的吃）
		Pattern p=Pattern.compile("(.{3,10}+)[0-9]");  //如是 (.{3,10}+)[0-9] 占有的(Possessive )全吃(10个)，但不吐，所以没有匹配的，但在尾加一个数可匹配
		String s="aaaa5bbbb6"; 
		Matcher m=p.matcher(s);
		 while (m.find())
		 {
			 System.out.println(m.group());  
		 }
	
		 
		 
		 
		 // (?=exp) 匹配exp前面的位置       , javaDoc 上说 (?=X) X, via zero-width positive lookahead  
		 //p=Pattern.compile(".{3}(?=a)"); //输出444;  
		   p=Pattern.compile("(?=a).{3}"); //输出是a66, (?=a)放前把自己带进去 a
		  
		  //(?!exp) 匹配后面跟的不是exp的位置   ,javaDoc 上说 (?<!X) X, via zero-width negative lookbehind 
		// p=Pattern.compile(".{3}(?<!a)"); //输出444 和a66两组
		  	
		  // (?<=exp) 匹配exp后面的位置  ,javaDoc 上说 (?<=X) X, via zero-width positive lookbehind 
		  p=Pattern.compile(".{3}(?<=a)");// 输出是44a　,放前把自己带进去 a
		  m=p.matcher("444a66b");
		  while (m.find())
		 {
			 System.out.println(m.group());  
		 }
		 
		 
	}
}
