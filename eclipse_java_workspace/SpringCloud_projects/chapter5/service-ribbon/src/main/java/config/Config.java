package config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
//包名不能在@SpringBootApplication所在的包下,或 @ComponentScan(excludeFilters= {@ComponentScan.Filter(type=FilterType.ANNOTATION,value=我的自定义@.class)} )


@Configuration
public class Config {
	//按照源码复制做修改
	@Bean
	public IRule ribbonRule(IClientConfig config) {
		return new RandomRule();//ribbon随机请求
	}
}
