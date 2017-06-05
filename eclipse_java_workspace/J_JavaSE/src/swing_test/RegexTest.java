package swing_test;
import java.util.regex .*;

public class  RegexTest

{
	public static void main(String[] args) 
	{
		String input="sdf@sdfj.om";
		String rule="\\p{Alpha}+@\\w+\\.\\p{Alpha}{2,3}";
		Boolean isright=Pattern.matches(rule, input) ;
//		System.out.println(isright);
		
/////////////////////////////////////////--------------------------------

		Pattern pattern=Pattern.compile("\\p{Alpha}+@\\w+\\.\\p{Alpha}{2,3}");
		String words[] =  pattern.split(input);

		Matcher matcher = pattern.matcher(input);


		 Pattern p = Pattern.compile("a*b");
		 Matcher m = p.matcher("sdfb");
		 boolean b = m.matches();
		System.out.println(b);

	}
}
