package my_code.validation;

import java.util.Date;

import org.kie.api.runtime.rule.RuleContext;


public class ValidationService {

	public static void error(RuleContext kcontext, Object context, String msg) {
		System.out.println("Error级别:" + msg);
	}

	
	public static void warning(RuleContext kcontext, Object context, String msg) {
		System.out.println("警告级别：" + msg);
	}

	/**
	 * @return number of years between today and specified date
	 */
	public static int yearsPassedSince(Date date) {
		return 2;
	}

}
