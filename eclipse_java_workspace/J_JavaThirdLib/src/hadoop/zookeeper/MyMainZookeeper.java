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
	                System.out.println("�Ѿ�������" + event.getType() + "�¼���"); 
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
//		  //ʹ������   getAcl /testAclPath  �ܿ���
		 zk.addAuthInfo("digest", "myuser:mypass".getBytes());//ͬ����  addauth digest  myuser:mypass
		 System.out.println(new String(zk.getData("/testAclPath",false,null))); 
		 
	}
	public static void all() throws Exception 
	{
		 ZooKeeper zk = new ZooKeeper("localhost:2181",2000, new Watcher() { 
	            public void process(WatchedEvent event) { 
	                System.out.println("�Ѿ�������" + event.getType() + "�¼���"); 
	            } 
	        }); 
		 
			// Ids.OPEN_ACL_UNSAFE �κ��˶����Է���
			 // Ids.AUTH_IDS ������ӵ�з���Ȩ��
		 	// Ids.READ_ACL_UNSAFE  �κ��˶����Զ�
		 	//Ids.CREATOR_ALL_ACL //ʹ��addAuthInfo��Ϣ,��Ϊ�����ڵ��acl��Ϣ
      zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,  CreateMode.PERSISTENT); 
	 zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),  Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
	 System.out.println(new String(zk.getData("/testRootPath",false,null))); 
	 System.out.println(zk.getChildren("/testRootPath",true)); 
	 zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1); 
	 System.out.println("Ŀ¼�ڵ�״̬��["+zk.exists("/testRootPath",true)+"]"); 
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
				System.out.println("�Ѿ�������" + event.getType() + "�¼���"); 
			} 
		}); 
		//CreateMode.EPHEMERAL; //ephemeral ���ݵ�
		//CreateMode.EPHEMERAL_SEQUENTIAL;
		zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		System.out.println(new String(zk.getData("/testRootPath",false,null))); 
		System.out.println("Ŀ¼�ڵ�״̬��["+zk.exists("/testRootPath",true)+"]"); 
		zk.setData("/testRootPath","modifyData".getBytes(),-1); 
		zk.delete("/testRootPath",-1); 
		zk.close();
	}
}
