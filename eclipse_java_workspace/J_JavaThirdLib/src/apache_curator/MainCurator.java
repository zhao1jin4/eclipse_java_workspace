package apache_curator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class MainCurator {

	public static void main(String[] args) throws Exception {
	/*	<dependency>
		<groupId>org.apache.curator</groupId>
		<artifactId>curator-framework</artifactId>
		<version>4.0.1</version>
	</dependency>
	
	
	curator-client-4.0.1.jar
*/
		String ip ="127.0.0.1";
		String ipPort="127.0.0.1:2181";
		
		String nodePath="/hello/world";
		String nodeValue="123";
		
		
		
		
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

		
//		client.delete().deletingChildrenIfNeeded().forPath(nodePath);
		//client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(1).forPath(nodePath); //可带withVersion
		//guaranteed 如删除失败,会一直重试
		
		
		 ACL aclIp=new ACL(Perms.READ,new Id("ip",ip));//Id构造器参数schema只可是ip(白名单)或digest(用户名密码)
		 String userPwd=DigestAuthenticationProvider.generateDigest("myuser:mypass");
		 ACL aclDigest=new ACL(Perms.READ|Perms.WRITE,new Id("digest",userPwd));
		 ArrayList<ACL> aclList=new ArrayList<>();
//		 aclList.add(aclIp);
		 aclList.add(aclDigest);
		
		
		String path=client.create()
		.creatingParentsIfNeeded() //如果一级不存会先创建再建二级目录
		.withMode(CreateMode.PERSISTENT)
		.withACL(aclList)
		.forPath(nodePath,nodeValue.getBytes());
		
		System.out.println(path);
		

		Stat stat=new Stat();
		byte[] data =client.getData().storingStatIn(stat).forPath(nodePath);
		System.out.println("data= "+new String(data));
		System.out.println("stat= "+stat);
		
		
		
		List<String> children=client.getChildren().forPath(nodePath);
		System.out.println("child have "+children);
		

		stat=client.checkExists().forPath(nodePath);
		System.out.println(nodePath+" = "+stat);//null 就不存
		
		ExecutorService executorService= Executors.newFixedThreadPool(5);
		
		 
		
		client.setData().withVersion(stat.getVersion()).forPath(nodePath,"newData".getBytes());
		
		
		//inBackground 转异步
		 client.checkExists().inBackground(new BackgroundCallback() {
				@Override
				public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
					CuratorEventType type=event.getType();
					int resultCode=event.getResultCode(); //0成功
					System.out.println("processResult type= "+type);
					System.out.println("processResult resultCode= "+resultCode);
					System.out.println("processResult getContext= "+event.getContext());
					System.out.println("processResult getPath= "+event.getPath());
					System.out.println("processResult getChildren= "+event.getChildren());
					System.out.println("processResult data= "+new String(event.getData()));
				}
			},"contextVal",executorService).forPath(nodePath);
		
		 
		 
		//监听 要 curator-recipes 包
		NodeCache cache=new NodeCache(client,nodePath);
		cache.start();
		cache.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				byte[]data =cache.getCurrentData().getData();
				System.out.println("NodeCache data= "+new String(data));
			}
		});
		
		
		PathChildrenCache pathCache=new PathChildrenCache(client,nodePath,true);//true 子节点变化时，也取内容
		pathCache.start();
		pathCache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				switch(event.getType())
				{
					 case CHILD_ADDED :
						 System.out.println("CHILD_ADDED"+event.getData().getPath());
						 break;
					 case CHILD_UPDATED:
						 System.out.println("CHILD_UPDATED"+event.getData().getPath());
						  break;
					 case CHILD_REMOVED:
						 System.out.println("CHILD_REMOVED"+event.getData().getPath());
						  break;
							 
				}
			}
		});
		
		System.in.read();
		client.close();
	}

}
