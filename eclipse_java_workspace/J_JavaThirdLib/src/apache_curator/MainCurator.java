package apache_curator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event.EventType;
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
		
		
		
		
//		RetryPolicy retryPolicy=new ExponentialBackoffRetry(1000,3);//baseSleepTimeMs,  maxRetries ÿ������ʱ��������
//		RetryPolicy retryPolicy=new RetryNTimes(5,1000);//retryCount ������Դ�����elapsedTimeMs
		RetryPolicy retryPolicy=new RetryUntilElapsed(5000,1000);//maxElapsedTimeMs������Զ೤ʱ��,   sleepMsBetweenRetries ÿ������ʱ����
//		CuratorFramework client=CuratorFrameworkFactory.newClient(ipPort,500,5000, retryPolicy);
		
		
		
		List<AuthInfo> authInfos =new ArrayList<>();
		AuthInfo auth=new AuthInfo("digest", "myuser:mypass".getBytes());
		authInfos.add(auth);
		
		CuratorFramework client= CuratorFrameworkFactory.builder().connectString(ipPort)
		.sessionTimeoutMs(5000)
		.connectionTimeoutMs(5000)
//		.authorization("digest", "myuser:mypass".getBytes()) //ͬ����  addauth digest  myuser:mypass
		.authorization(authInfos)
		.retryPolicy(retryPolicy)
		.build();
		
		 
			 
		client.start();

		
//		client.delete().deletingChildrenIfNeeded().forPath(nodePath);
		//client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(1).forPath(nodePath); //�ɴ�withVersion
		//guaranteed ��ɾ��ʧ��,��һֱ����
		
		//addauth digest myuser:mypass
		 ACL aclIp=new ACL(Perms.READ,new Id("ip",ip));//Id����������schemaֻ����ip(������)��digest(�û�������)
		 String userPwd=DigestAuthenticationProvider.generateDigest("myuser:mypass");
		 ACL aclDigest=new ACL(Perms.READ|Perms.WRITE,new Id("digest",userPwd));
		 ArrayList<ACL> aclList=new ArrayList<>();
//		 aclList.add(aclIp);
		 aclList.add(aclDigest);
		
		boolean exists= checkExist(client,nodePath);//�Լ��ķ��� 
		if(!exists)
		{
			String path=client.create()
			.creatingParentsIfNeeded() //���һ��������ȴ����ٽ�����Ŀ¼
			.withMode(CreateMode.PERSISTENT)
			.withACL(aclList)
			.forPath(nodePath,nodeValue.getBytes());
			
			System.out.println(path);
		
		}

		Stat stat=new Stat();
		byte[] data =client.getData().storingStatIn(stat).forPath(nodePath);
		System.out.println("data= "+new String(data));
		System.out.println("stat= "+stat);
		
		
		
		List<String> children=client.getChildren().forPath(nodePath);
		System.out.println("child have "+children);
		

		stat=client.checkExists().forPath(nodePath);
		System.out.println(nodePath+" = "+stat);//null �Ͳ���
		
		ExecutorService executorService= Executors.newFixedThreadPool(5);
		
		 
		
		client.setData().withVersion(stat.getVersion()).forPath(nodePath,"newData".getBytes());
		
		
		//inBackground ת�첽
		 client.checkExists().inBackground(new BackgroundCallback() {
				@Override
				public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
					CuratorEventType type=event.getType();
					int resultCode=event.getResultCode(); //0�ɹ�
					System.out.println("processResult type= "+type);
					System.out.println("processResult resultCode= "+resultCode);
					System.out.println("processResult getContext= "+event.getContext());
					System.out.println("processResult getPath= "+event.getPath());
					System.out.println("processResult getChildren= "+event.getChildren());
					if(event.getData()!=null)
						System.out.println("processResult data= "+new String(event.getData()));
				}
			},"contextVal",executorService).forPath(nodePath);
		
		 
		 
		//���� Ҫ curator-recipes ��
		NodeCache cache=new NodeCache(client,nodePath);
		cache.start();
		cache.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				byte[]data =cache.getCurrentData().getData();
				System.out.println("NodeCache data= "+new String(data));
			}
		});
		
		
		PathChildrenCache pathCache=new PathChildrenCache(client,nodePath,true);//true �ӽڵ�仯ʱ��Ҳȡ����
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
		
		
		client.checkExists().watched().forPath(nodePath);
		client.getCuratorListenable().addListener(new CuratorListener() {
			@Override
			public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception { 
				CuratorEventType type=event.getType();
				if(type==CuratorEventType.WATCHED)
				{
					WatchedEvent watchEvent=event.getWatchedEvent();//����zookeeper��WatchedEvent
					EventType evtType=watchEvent.getType();
					System.out.println("watched path="+watchEvent.getPath());
					client.checkExists().watched().forPath(nodePath);//��Ҫ�ٴ�wached
				}
				//CuratorEventType.EXISTS ,CuratorEventType.DELETE ,CuratorEventType.CREATE
			}
		});
		distributeLock(client,nodePath);
		
		registerService(client,"book");
		Object obj=findService(client,"book");
		System.in.read();
		client.close();
	}
	 
	public static boolean checkExist(CuratorFramework client,String nodePath) throws Exception
	{
		Stat stat= client.checkExists().forPath(nodePath);
		return stat!=null;
	}
	 
	public static void distributeLock(CuratorFramework client,String nodePath) throws Exception
	{
		//�ֲ�ʽ��Ӧ��,�����Լ�дһ��aop���� @ClusterLock("/lock/order")��ʵ��
		InterProcessMutex lock=new InterProcessMutex(client,nodePath);
		try 
		{
			System.out.println("acquire lock ..."); 
			if(lock.acquire(10,TimeUnit.SECONDS))
			{
				System.out.println("geted lock");
				Thread.sleep(1000*3);//ģ��ʹ��ʱ��  
			}else
			{
				System.out.println("get lock timeout");
			}
		}finally {
			lock.release();
		}
		
	}
	public static void registerService(CuratorFramework client,String serviceName) throws Exception
	{
		//Ҫ curator-x-discovery-4.0.1.jar
		//��������
		ServiceInstanceBuilder<Map> service=ServiceInstance.builder();
		service.address("127.0.0.1");
		service.port(8080);
		service.name(serviceName);//������zk�ڵ�
		Map<String,String> payload=new HashMap<>();
		payload.put("url","/api/v3/book");
		service.payload(payload);//payload �Ŷ�����Ϣ�������κ���
		ServiceInstance<Map> instance=service.build();
				
		ServiceDiscovery  discovery=ServiceDiscoveryBuilder.builder(Map.class)
				.client(client)
				.serializer(new JsonInstanceSerializer<Map>(Map.class))
				.basePath("/service")
				.build();
		//����ע�� 
		discovery.registerService(instance);
		discovery.start();
		//ls /service/book
		//get /service/book/<uu-id>
	}
	public static Object findService(CuratorFramework client,String serviceName) throws Exception
	{
		//���ҷ���
		ServiceDiscovery  discovery=ServiceDiscoveryBuilder.builder(Map.class)
				.client(client)
				.serializer(new JsonInstanceSerializer<Map>(Map.class))
				.basePath("/service")
				.build(); 
		discovery.start(); 
		Collection<ServiceInstance<Map>> all = discovery.queryForInstances(serviceName);
		if(all.isEmpty())
			return null;
		else
		{
			//����ֻҪ��һ������
			ServiceInstance<Map>  service= new ArrayList<ServiceInstance<Map> >(all).get(0);
			System.out.println(service.getPayload());
			System.out.println(service.getAddress()); 
			return   service;
		}
		
	}
	public static void leaderSelector(CuratorFramework client,String serviceName) throws Exception
	{	
		//ѡleader
		LeaderSelectorListenerAdapter listener=new LeaderSelectorListenerAdapter() {
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {
				//�쵼�ڵ㣬�����������˳��쵼 ��zk���ٴ�����ѡ���쵼
			}
		};
		LeaderSelector selector=new LeaderSelector(client,"/schedule",listener);
		selector.autoRequeue();
		selector.start(); 
	}
}
