package spring_aopaliance_clazz;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
         BeanOne one=new BeanOne();
        
         BeanOne proxyOne;
    
         
         Pointcut pc=new ControlFlowPointcut(Test.class,"runfoo");
         Advice advice=new SimpleAdvise();
         Advisor advisor=new DefaultPointcutAdvisor(pc,advice);
         
         
         //����BeanOne����
         ProxyFactory pf1=new ProxyFactory();
         pf1.addAdvisor(advisor);
         pf1.setTarget(one);
         proxyOne=(BeanOne)pf1.getProxy();
         
        
         
         //ֱ�ӵ���foo
         proxyOne.foo();
         //ͨ��runfoo��������foo
         Test.runfoo(proxyOne);
         
         
    }
    public static void runfoo(BeanOne beanOne){
        beanOne.foo();
    }

}
