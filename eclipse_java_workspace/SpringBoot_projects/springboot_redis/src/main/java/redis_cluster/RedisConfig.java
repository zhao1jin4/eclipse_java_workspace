package redis_cluster;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
 
@Configuration
public class RedisConfig {

	@Value("${spring.redis.cluster.nodes}")
	private String clusterNodes;

	//video teach 
	@Bean
	public JedisCluster getJedisCluster() {
		String[] nodes = clusterNodes.split(",");
		Set<HostAndPort> hosts = new HashSet<>();
		for (String node : nodes) 
		{
			String[] ipPort = node.split(":");
			hosts.add(new HostAndPort(ipPort[0], Integer.parseInt(ipPort[1])));
		}
		JedisCluster cluster = new JedisCluster(hosts);//这里有问题，把ＩＰ变成了127.0.0.1
		//可能是 cluster配置问题，cluster nodes查看是127.0.0.1
		//redis-trib.rb外网IP，bind外网IP
		//./redis-trib.rb create --replicas 1 172.16.35.35:7000 172.16.35.35:7001 172.16.35.35:7002 172.16.35.35:7003 172.16.35.35:7004 172.16.35.35:7005
		
		return cluster;
	}
	
 
//	public @Bean RedisConnectionFactory connectionFactory() {
//		List<String> list=Arrays.asList(clusterNodes.split(","));
//	    return new JedisConnectionFactory(
//	            new RedisClusterConfiguration(list));//但hostname还是localhost
//	}
	
} 
 