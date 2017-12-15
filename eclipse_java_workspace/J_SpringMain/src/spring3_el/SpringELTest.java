package spring3_el;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpringELTest {

	public static void testEL()
	{
		//��ѳ�����.jar ��ClassPathXmlApplicationContext("spring*.xml")��֧��ʹ��ͨ���*
		ApplicationContext contex=new ClassPathXmlApplicationContext("spring3_el/el_beans.xml");
		User user=contex.getBean("user",User.class);
		System.out.println(user.getSalary());
		System.out.println(user.getUsername());
		System.out.println(user.getHomeDir());//�ò���??????
		//-----
		ExpressionParser parser=new SpelExpressionParser();
		Expression exp=parser.parseExpression("(5+2)*3");
		System.out.println(exp.getValue());//21
		
		//-----
		User u=new User();
		u.setUsername("����");
		EvaluationContext context=new StandardEvaluationContext(u);//������
		exp=parser.parseExpression("username");//�൱��u.getUsername();
		System.out.println(exp.getValue(context));
		System.out.println(exp.getValue(u));
		//-----
		
	}
	public static void testEL1()
	{
		List<Integer> primes = new ArrayList<Integer>();
		primes.addAll(Arrays.asList(2,3,5,7,11,13,17));
		// create parser and set variable 'primes' as the array of integers
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariable("primes",primes);
		// all prime numbers > 10 from the list (using selection ?{...})
		// evaluates to [11, 13, 17]
		List<Integer> primesGreaterThanTen =
		(List<Integer>) parser.parseExpression("#primes.?[#this>10]").getValue(context);
		//?��ʾ������ʽ��ȫȡ��
		
		
		String name = parser.parseExpression("null?:'Unknown'").getValue(String.class);
		System.out.println(name); // 'Unknown'
		
		
		//String city = parser.parseExpression("PlaceOfBirth?.City").getValue(context, String.class);
		//��ֹNullPoint,�����getCity()
		
		String randomPhrase = parser.parseExpression("random number is #{T(java.lang.Math).random()}", new TemplateParserContext()).getValue(String.class);
		System.out.println(randomPhrase);  
	}
	public static void main(String[] args) {
		 testEL();
	}

}
