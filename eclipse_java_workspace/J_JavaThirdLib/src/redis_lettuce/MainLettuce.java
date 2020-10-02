package redis_lettuce;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;

public class MainLettuce {
	public static void main(String[] args) {
//		single();
//		cluster();//not test
	}
	public static void single()
	{
		//注意 jboss-client.jar了有netty的类
//		RedisClient redisClient = RedisClient.create("redis://password@localhost:6379/0");
//		RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
//		RedisClient redisClient = RedisClient.create(RedisURI.create("localhost", 6379));

		RedisURI redisUri =RedisURI.Builder.redis("localhost", 6379).withPassword("").withDatabase(1)
				//.withSsl(true)
				.build();
		RedisClient redisClient = RedisClient.create(redisUri);
		
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> syncCommands = connection.sync();

		syncCommands.set("key", "Hello, Redis!");

		connection.close();
		redisClient.shutdown();
	}
	public static void async()
	{
		RedisClient client = RedisClient.create(RedisURI.create("localhost", 6379));
		RedisAsyncCommands<String, String> commands = client.connect().async();
		RedisFuture<String> future = commands.get("key");
		
		//future.thenAccept(System.out::println);
		future.thenAcceptAsync(System.out::println);//开新的线程使用ForkJoinPool
		try {
			future.get(5,TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
		

	}
	public static void cluster()
	{
		 RedisClusterClient redisClient = RedisClusterClient.create("redis://password@localhost:7379");

        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();

        System.out.println("Connected to Redis");

        connection.close();
        redisClient.shutdown();
	}
	
}
