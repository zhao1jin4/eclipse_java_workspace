package junit3;

import junit.framework.TestCase;

public class MyTestJUnit3 extends TestCase
{

	@Override
	protected void setUp() throws Exception
	{
		System.out.println("set up -----");
	}

	@Override
	protected void tearDown() throws Exception
	{
		System.out.println("tear Down  -----");
	}
	public void testAdd()
	{
		this.assertEquals("myerror reson is XXXX", 9, 5+4);
		System.out.println("test runing");
	}
	public void testMinus()
	{		System.out.println("test runing");
		this.assertEquals("myerror reson is XXXX", 9, 5-4);

	}

}
