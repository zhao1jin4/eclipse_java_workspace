package junit4;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {
	public static void main(String[] args) {

		Result result = JUnitCore.runClasses(OneTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		if (result.wasSuccessful()) {
			System.out.println("所有测试用列执行成功");
		}

	}

}
