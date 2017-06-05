package junit4;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
@RunWith(value = Parameterized.class)
public class CalculatorTest
{
	private int expected;
	private int para1;
	private int para2;
	@Parameterized.Parameters
	public static Collection<Integer[]> getParameters()//����Collection
	{
		return Arrays.asList(new Integer[][] { { 3, 3, 2 }, // expected, para1, para2
											   { 1, 1, 1 } });// expected, para1, para2
	}
	public CalculatorTest(int expected, int para1, int para2)//���캯���Ĳ������ͱ���ȫһ�� 
	{
		this.expected = expected;
		this.para1 = para1;
		this.para2 = para2;
	}
	@Test
	public void testPlus()
	{
		int acutal=Math.max(para1, para2);
		assertEquals(expected, acutal);
	}
}
