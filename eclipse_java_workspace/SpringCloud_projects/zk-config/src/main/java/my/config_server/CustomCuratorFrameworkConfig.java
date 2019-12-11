package my.config_server;

 
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
import org.springframework.context.annotation.Bean;
/**
 * resources/META-INF/spring.factories
org.springframework.cloud.bootstrap.BootstrapConfiguration=\
my.project.CustomCuratorFrameworkConfig,\
my.project.DefaultCuratorFrameworkConfig
 */
@BootstrapConfiguration
public class CustomCuratorFrameworkConfig {
  @Bean  // 设置auth  密码测试成功
  public CuratorFramework curatorFramework() {
	    String ip ="127.0.0.1";
		String ipPort="127.0.0.1:2181";//可读配置
//		RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);//baseSleepTimeMs,  maxRetries 每次重试时间逐渐增加
//		RetryPolicy retryPolicy=new RetryNTimes(5,1000);//retryCount 最大重试次数，elapsedTimeMs
		RetryPolicy retryPolicy=new RetryUntilElapsed(5000,1000);//maxElapsedTimeMs最多重试多长时间,   sleepMsBetweenRetries 每次重试时间间隔
//		CuratorFramework client=CuratorFrameworkFactory.newClient(ipPort,500,5000, retryPolicy);
		
		List<AuthInfo> authInfos =new ArrayList<>();
		AuthInfo auth=new AuthInfo("digest", "myuser:mypass".getBytes());
		authInfos.add(auth);
		
		CuratorFramework client= CuratorFrameworkFactory.builder().connectString(ipPort)
		.sessionTimeoutMs(5000)
		.connectionTimeoutMs(5000)
//		.authorization("digest", "myuser:mypass".getBytes()) //同命令  addauth digest  myuser:mypass
		.authorization(authInfos)
		.retryPolicy(retryPolicy)
		.build();
		client.start();
		return client;
  }
}