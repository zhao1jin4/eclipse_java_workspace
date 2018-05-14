package hadoop.zookeeper;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;



public class MyMainZookeeper {

	
	public static String ip="127.0.0.1";
	public static String ipPort="127.0.0.1:2181";
	public static void main(String[] args) throws Exception 
	{
//		all();
//		single();
//		testAcl();
		//testSubscribe();//ZKClient
		
//		System.getProperties().get("java.io.tmpdir");
//		java.io.File.createTempFile("", null);
	}
	 
	public static void testAcl() throws Exception 
	{
		
		 ZooKeeper zk = new ZooKeeper(ipPort,2000, new Watcher() { 
	            public void process(WatchedEvent event) { 
	                System.out.println("已经触发了" + event.getType() + "事件！"); 
	               
	            } 
	        }); 
	
//		 Perms.ALL
//		 Perms.ADMIN|Perms.CREATE|Perms.DELETE|Perms.READ|Perms.WRITE
		 
		 ACL aclIp=new ACL(Perms.READ,new Id("ip",ip));//Id构造器参数schema只可是ip(白名单)或digest(用户名密码)
		 String userPwd=DigestAuthenticationProvider.generateDigest("myuser:mypass");
		 System.out.println(userPwd);
		 ACL aclDigest=new ACL(Perms.READ|Perms.WRITE,new Id("digest",userPwd));
		 ArrayList<ACL> aclList=new ArrayList<>();
//		 aclList.add(aclIp);
		 aclList.add(aclDigest);
		 zk.create("/testAclPath", "testAclPathVal".getBytes(),aclList, CreateMode.PERSISTENT); 
//		 Ids.OPEN_ACL_UNSAFE,Ids.READ_ACL_UNSAFE
//		 Ids.CREATOR_ALL_ACL 使用这个前必须用 zk.addAuthInfo(
		 
//		  //使用命令   getAcl /testAclPath  能看到
		 zk.addAuthInfo("digest", "myuser:mypass".getBytes());//同命令  addauth digest  myuser:mypass
		 System.out.println(new String(zk.getData("/testAclPath",false,null))); 
		 
		 //  create /acl_ip_test ip:10.1.5.225:crwda
		 //  create /acl_digest_test digest:myuser:CmVSQ2nhuKrMPNW7BK6HrthawaY=:crwda   中间myuser:CmVSQ2nhuKrMPNW7BK6HrthawaY=是使用DigestAuthenticationProvider.generateDigest("myuser:mypass")生成的
		 // 也可以使用 setAcl  /acl_ip_test ip:10.1.5.225:crwda 
	}
	public static void all() throws Exception 
	{
		 ZooKeeper zk = new ZooKeeper(ipPort,2000, new Watcher() { 
	            public void process(WatchedEvent event) { 
	                System.out.println("已经触发了" + event.getType() + "事件！"); 
	                if(event.getType()==EventType.NodeChildrenChanged)
	                	 System.out.println(event.getPath()+"已经修改了"); //可再次注册事件监听
	                	
	            } 
	        }); 
		 
			// Ids.OPEN_ACL_UNSAFE 任何人都可以访问
			 // Ids.AUTH_IDS 创建者拥有访问权限
		 	// Ids.READ_ACL_UNSAFE  任何人都可以读
		 	//Ids.CREATOR_ALL_ACL //使用addAuthInfo信息,作为建立节点的acl信息
      zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,  CreateMode.PERSISTENT); 
	 zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),  Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
	 zk.create("/testRootPath2", "testRootData2".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT,
				new AsyncCallback.StringCallback() {
					@Override
					public void processResult(int rc,  String path,  Object ctx,  String name) {
						//rc=0表示成功,ctx是传来的"创建"字符
						if(rc == KeeperException.Code.NODEEXISTS.intValue()  )
							System.out.printf("异步创建 path=%s 已经存在\n",path ); 
					
						 System.out.printf("异步创建rc=%d,path=%s,ctx=%s,name=%s\n",rc,path,ctx,name); 
					}
				},"创建"
			);
	 System.out.println(new String(zk.getData("/testRootPath",false,null))); 
	 
	 

	 System.out.println(zk.getChildren("/testRootPath",true)); //子节点, boolean watch 是否观心子节点的变化,Watcher的事件
	 zk.getChildren("/testRootPath", true, new AsyncCallback.Children2Callback() {//boolean watch
		@Override
		public void processResult(int rc,  String path,  Object ctx,  List<java.lang.String> children ,Stat stat) {
			if(rc == KeeperException.Code.NONODE.intValue()  )
				System.out.printf("异步getChildren  path=%s 不存在\n",path ); 
			else
				System.out.println("异步getChildren  "+children); 
		}
	}, "传参ctx");
	
	 
	 zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1); 
	 
	 
	 
	 System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]"); //boolean watch
	 zk.exists("/testRootPath", true, new StatCallback() {
		@Override
		public void processResult(int rc, String path, Object ctx, Stat stat) {
			System.out.println("异步exists  rc="+rc); 
		}}, "ctx");
	 
	 zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(),  Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); //Watcher的NodeChildrenChanged
	
	 zk.setData("/testRootPath/testChildPathTwo","modifyChildDataOne".getBytes(),-1,new StatCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, Stat stat) {
				 System.out.println("异步 setData rc="+rc); 
			}
		},"ctx"); 
	 
	 
	 Stat reveStat=new Stat();
	 System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo",true,reveStat))); //boolean watch
	 System.out.println("reveStat="+reveStat);
	 zk.getData("/testRootPath/testChildPathTwo",true,new DataCallback() {
		@Override
		public void processResult(int rc,  String path,  Object ctx, byte[] data, Stat stat) {
			System.out.println("异步getData  "+new String(data)); 
		}},"ctx value");
	 
	 
	 zk.delete("/testRootPath/testChildPathTwo",-1); //-1表示任何版本
	 zk.delete("/testRootPath/testChildPathOne",-1,new VoidCallback() {
		@Override
		public void processResult(int rc, String path, Object ctx) {
			System.out.println("异步delete  rc="+rc); 
		}},"ctx val"); 
	 zk.delete("/testRootPath",-1); 
	 zk.close();
	}
	

	public static void single() throws Exception 
	{
		System.out.println(System.getProperties());
		ZooKeeper zk = new ZooKeeper(ipPort,2000, new Watcher() { 
			public void process(WatchedEvent event) { 
				System.out.println("已经触发了" + event.getType() + "事件！");
				 if(event.getState()==KeeperState.SyncConnected)
	                {
	                	 System.out.println("SyncConnected事件！"); 
	                }
			} 
		}); 
		//CreateMode.EPHEMERAL; //ephemeral 短暂的
		//CreateMode.EPHEMERAL_SEQUENTIAL;
		//CreateMode.PERSISTENT_SEQUENTIAL;
		zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		
		System.out.println(new String(zk.getData("/testRootPath",false,null))); 
		System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]"); 
		zk.setData("/testRootPath","modifyData".getBytes(),-1); 
		zk.delete("/testRootPath",-1); 
		zk.close();
	}
}
