package junit4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//TestSuit 
@RunWith(Suite.class)
@SuiteClasses({OneTest.class})//可传多个测试类
public class Test 
{
	
}
