package junit3;

import junit.framework.Assert;
import junit.framework.TestCase;

public class HelloTest extends TestCase
{

	
	public void testAdd()
	{
		int x=1;
		int y=2;
		assertEquals(x, y);
		Assert.fail();
		Assert.assertEquals(x,y);
	}
	
	
	public void testMinus()
	{
		int x=1;
		int y=2;
		int z=0;
		assertEquals(x, x/y);
	}
	
	@Override
	protected void setUp() throws Exception
	{
		System.out.println("setUP");
	}

	@Override
	protected void tearDown() throws Exception
	{
		System.out.println("TearDown");
	}

}
