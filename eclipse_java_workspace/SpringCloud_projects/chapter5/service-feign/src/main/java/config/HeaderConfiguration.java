package config;

import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

 

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Target;

@Configuration
public class HeaderConfiguration 
	implements RequestInterceptor  //feign传header
{
   //实现RequestInterceptor了再加feign.compression.request.enabled=true 就没用 （有 Accept-Encoding: gzip）
    
	@Override
	public void apply(RequestTemplate template) { 
		
		System.out.println("请求参数为："+ template.queryLine());//?param=123456
		 //feign传header
	    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
	     
	    
	    //attributes 是null原因为openfeign 启用了hystrix,切换了Thread,导致threadLocal中的东西丢失，导致RequestContextHolder.getRequestAttributes()为null
	    //attributes 是null原因为openfeign 启用了 circuitbreaker, 使用了线程池，
	    //源码 初始化在FeignClientFactoryBean，最后实例化jdk代理handler类FeignCircuitBreakerInvocationHandler，当调用时会到FeignCircuitBreakerInvocationHandler -> Resilience4JCircuitBreaker
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
               String name = headerNames.nextElement();//Content-length 不能传
                if(name.equalsIgnoreCase("AuthenToken") )
                {
                    String values = request.getHeader(name);
                    template.header(name, values);//不会替换已经存在的，会增加重复的
                }

            }
            //feign.compression.request.enabled=true 配置也不会增加头
//            template.header("Accept-Encoding", "gzip"); //测试用
            System.out.println("feign interceptor request header:"+template);
            System.out.println("--end");
        }
      
	}
 	
}

	
	/*
	 2020.0.0	版本去除了hystrix 
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import feign.hystrix.SetterFactory;

	//为开启Hystrix ，使用新的ThreadLocal导致中的数据丢失，导致RequestContextHolder.getRequestAttributes()为null（修改为使用SEMAPHORE），
	@Bean
	public SetterFactory setterFactory(){
		SetterFactory setterFactory =new SetterFactory() {
			@Override
			public HystrixCommand.Setter create(Target<?> target, Method method) {

				String groupKey = target.name();
				String commandKey = Feign.configKey(target.type(), method);

				HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter()
						//设置统计指标60秒为一个时间窗口
						.withMetricsRollingStatisticalWindowInMilliseconds(1000 * 60)
						//超过80%失败率
						.withCircuitBreakerErrorThresholdPercentage(80)
						//操作5个开启短路器
						.withCircuitBreakerRequestVolumeThreshold(5)
						//设置线程隔离
						.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
						//设置断路器的开启时间为60秒
						.withCircuitBreakerSleepWindowInMilliseconds(1000 * 60);

				return HystrixCommand.Setter
						.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
						.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
						.andCommandPropertiesDefaults(setter);
			}
		};
		return setterFactory;
	}
}
*/ 