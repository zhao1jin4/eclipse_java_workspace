package junit3;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MyTestSuit extends TestCase
{

	public static Test suite()
	{
		TestSuite suit=new TestSuite();
	//	suit.addTest(new HelloTest());
		suit.addTestSuite(HelloTest.class);
		
		return suit;
		
	}
}
