package spring_junit;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.easymock.IMocksControl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class UserDAOMock implements    BeanPostProcessor  
{
	private static String RESULT="defaultMockName";
	public static UserDAO resetMockObject(UserDAO clientMock,final String res)
	{
		
		EasyMock.reset(clientMock);
		clientMock.getTest();
		EasyMock.expectLastCall().andStubReturn("newMock="+res);
		
		//”√’‚∏ˆverify ß∞‹???
//	  EasyMock.expect(clientMock.getTest()).andAnswer(new IAnswer<String>(){
//            @Override
//            public String answer() throws Throwable {
//                return "newMock="+res;
//            }
//        });
		  
		EasyMock.replay(clientMock);
		EasyMock.verify(clientMock);
		return clientMock;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if ("userDao".equals(beanName) || bean instanceof  UserDAO)
		{
			IMocksControl mocksControl = EasyMock.createControl();
			 
			UserDAO clientMock=mocksControl.createMock(UserDAO.class);
			return resetMockObject(clientMock,RESULT);
		}else 
			return bean;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
}
