package spring_aop_introduction;

import org.aopalliance.aop.Advice;
import org.springframework.aop.support.DefaultIntroductionAdvisor;


public class MixinAdvisor extends DefaultIntroductionAdvisor
{

	public MixinAdvisor()
	{
		//super(new LockMixinIntercepter(),ILockable.class);
		super(new LockMixinIntercepter());//�Զ���ӿ�
	
	}

}
