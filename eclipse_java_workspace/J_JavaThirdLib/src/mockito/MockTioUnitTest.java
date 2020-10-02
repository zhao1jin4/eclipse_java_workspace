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
	
	@InjectMocks //��ע��һ��ʵ���࣬���Խ��뷽��������null,
	//����������@Autowired�Ͳ����ˣ���@InjectMocksҲ���У�ֻ��@Mock��Mockito.when)
	private UserServiceImpl userSerivce;
	
	@Mock
	//@InjectMocks //û��
	private MyDao myDao;
	
	@Test
	public void testAutoWired()
	{
		Mockito.when(myDao.insertData(ArgumentMatchers.any())).thenReturn(new Long(20));
		userSerivce.printDao();
	}
}
