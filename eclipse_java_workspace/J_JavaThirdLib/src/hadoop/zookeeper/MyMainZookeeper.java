package hadoop.zookeeper;

import java.util.ArrayList;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;



public class MyMainZookeeper {

	public static void testAcl() throws Exception 
	{
		
		 ZooKeeper zk = new ZooKeeper("10.1.5.225:2581",2000, new Watcher() { 
	            public void process(WatchedEvent event) { 
	                System.out.println("已经触发了" + event.getType() + "事件！"); 
	            } 
	        }); 
	
		 ACL aclIp=new ACL(Perms.READ,new Id("ip","10.1.5.225"));
		 String userPwd=DigestAuthenticationProvider.generateDigest("myuser:mypass");
		 System.out.println(userPwd);
		 ACL aclDigest=new ACL(Perms.READ,new Id("digest",userPwd));
		 ArrayList<ACL> aclList=new ArrayList<>();
		 aclList.add(aclIp);
		 aclList.add(aclDigest);
		 zk.create("/testAclPath", "testAclPathVal".getBytes(),aclList, CreateMode.PERSISTENT); 
//		  //使用命令   getAcl /testAclPath  能看到
		 zk.addAuthInfo("digest", "myuser:mypass".getBytes());//同命令  addauth digest  myuser:mypass
		 System.out.println(new String(zk.getData("/testAclPath",false,null))); 
		 
	}
	public static void all() throws Exception 
	{
		 ZooKeeper zk = new ZooKeeper("localhost:2181",2000, new Watcher() { 
	            public void process(WatchedEvent event) { 
	                System.out.println("已经触发了" + event.getType() + "事件！"); 
	            } 
	        }); 
		 
			// Ids.OPEN_ACL_UNSAFE 任何人都可以访问
			 // Ids.AUTH_IDS 创建者拥有访问权限
		 	// Ids.READ_ACL_UNSAFE  任何人都可以读
		 	//Ids.CREATOR_ALL_ACL //使用addAuthInfo信息,作为建立节点的acl信息
      zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,  CreateMode.PERSISTENT); 
	 zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),  Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
	 System.out.println(new String(zk.getData("/testRootPath",false,null))); 
	 System.out.println(zk.getChildren("/testRootPath",true)); 
	 zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1); 
	 System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]"); 
	 zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), 
	   Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
	 System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo",true,null))); 
	 zk.delete("/testRootPath/testChildPathTwo",-1); 
	 zk.delete("/testRootPath/testChildPathOne",-1); 
	 zk.delete("/testRootPath",-1); 
	 zk.close();
	}
	public static void main(String[] args) throws Exception 
	{
		//all();
		//single();
		testAcl();
		
		
//		System.getProperties().get("java.io.tmpdir");
//		java.io.File.createTempFile("", null);
	}

	public static void single() throws Exception 
	{
		System.out.println(System.getProperties());
		ZooKeeper zk = new ZooKeeper("localhost:2181",2000, new Watcher() { 
			public void process(WatchedEvent event) { 
				System.out.println("已经触发了" + event.getType() + "事件！"); 
			} 
		}); 
		//CreateMode.EPHEMERAL; //ephemeral 短暂的
		//CreateMode.EPHEMERAL_SEQUENTIAL;
		zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		System.out.println(new String(zk.getData("/testRootPath",false,null))); 
		System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]"); 
		zk.setData("/testRootPath","modifyData".getBytes(),-1); 
		zk.delete("/testRootPath",-1); 
		zk.close();
	}
}
