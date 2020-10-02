package mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MockTioUnitTest {
	
	@InjectMocks //会注入一个实现类，可以进入方法，不是null,
	//但如果里面的@Autowired就不行了（用@InjectMocks也不行，只可@Mock再Mockito.when)
	private UserServiceImpl userSerivce;
	
	@Mock
	//@InjectMocks //没用
	private MyDao myDao;
	
	@Test
	public void testAutoWired()
	{
		Mockito.when(myDao.insertData(ArgumentMatchers.any())).thenReturn(new Long(20));
		userSerivce.printDao();
	}
}
