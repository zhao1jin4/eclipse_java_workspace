package testng;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import testng.listener.MyMethodInterceptor;
import testng.listener.MyTransformer;

/**
��Junitǿ�ĵط���֧�� ��,����,�������ܸ�ǿ,֧��listener
��֧������ķ�Χ,JUnit-4 @BeforeClass �� @AfterClass  ����������̬���� 
	@BeforeSuite
	@AfterSuite
	@BeforeTest
	@AfterTest

  
 */

//@Listeners({  MyTransformer.class, MyMethodInterceptor.class })//û��Ч������ ��xml�����ÿ��Ե�
public class MyTestNG
{
	@BeforeTest
	public void beforeTest() {
		System.out.println("@BeforeTest1");// <test>
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("@BeforeClass2"); // <class>
	}

	@AfterClass
	public void afterClass() {
		System.out.println("@AfterClass3");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("@AfterTest4");
	}
	@BeforeGroups //û�ã�����
	public void beforeGroups() {
		System.out.println("@BeforeGroups_MyTestNG");
	}

	@AfterGroups //û�ã�����
	public void afterGroups() {
		System.out.println("@AfterGroups_MyTestNG");
	}
	
	

	@Test //�����ķ�������һ��ҪΪvoid
	public void testMethod1() {
		String email = "abc";
		Assert.assertNotNull(email);
		// Assert.assertEquals(email, "123@test.com");
		System.out.println("testMethod1");
	}

	@Test(enabled = true, expectedExceptions = { ArithmeticException.class }, timeOut = 1000)
	public void divisionWithException() {
		int i = 1 / 0;
		System.out.println("After division the value of i is :" + i);
	}
	
    
	//---group
	@Test(groups = {"nosql" , "fast"})
	public void mongodb() {
		System.out.println("mongodb()");
	}

	@Test(groups = "nosql")
	public void hbase() {
		System.out.println("hbase()");
	}

	@Test(groups = "database")
	public void testConnectOracle() {
		System.out.println("testConnectOracle()");
	}
	@Test(groups = "database")
	public void testConnectMsSQL() {
		System.out.println("testConnectMsSQL()");
	}

	@Test(groups = {"nosql","database"})//��ͬʱ�ڶ������
	public void redis() {
		System.out.println("redis()");
	}
	@Test(dependsOnGroups = { "database", "nosql" })//������
	public void runFinal() {
		System.out.println("runFinal");
	}

	/*
	//���� testng_group.xml
	@Test
	@Parameters({ "param1", "param2" })//��������xml�����༶����
	public void testParam(String param1,String param2) {
		System.out.println("param1="+param1+",param2="+param2);
	}
	*/
	@Test(dataProvider = "provideNumbers")//provider������ʽ 
    public void testProviderParam(int number, int expected) {
        Assert.assertEquals(number + 10, expected);
    }
    @DataProvider(name = "provideNumbers")
    public Object[][] provideData() {
        return new Object[][] { { 10, 20 }, { 100, 110 }, { 200, 210 } };
    }
	
    //--һ��provider���ڶ෽��
    @Test(dataProvider = "dataProvider")
    public void test1(int number, int expected) {
        Assert.assertEquals(number, expected);
    }
    @Test(dataProvider = "dataProvider")
    public void test2(String email, String expected) {
        Assert.assertEquals(email, expected);
    }
    @DataProvider(name = "dataProvider")
    public Object[][] provideData(Method method)//һ��provideΪ������Է����������Ƿ���Method,�ò���group
    {
        Object[][] result = null;
        if (method.getName().equals("test1")) {
            result = new Object[][] {
                { 1, 1 }, { 200, 200 }
            };
        } else if (method.getName().equals("test2")) {
            result = new Object[][] {
                { "test@gmail.com", "test@gmail.com" },
                { "test@yahoo.com", "test@yahoo.com" }
            };
        }
        return result;
    }
    
    @Test(invocationCount = 12, threadPoolSize = 3)//ѹ������,�ɽ��Selenium
    public void testThreadPools() {
    	System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());
    }
  
}