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
	                System.out.println("�Ѿ�������" + event.getType() + "�¼���"); 
	               
	            } 
	        }); 
	
//		 Perms.ALL
//		 Perms.ADMIN|Perms.CREATE|Perms.DELETE|Perms.READ|Perms.WRITE
		 
		 ACL aclIp=new ACL(Perms.READ,new Id("ip",ip));//Id����������schemaֻ����ip(������)��digest(�û�������)
		 String userPwd=DigestAuthenticationProvider.generateDigest("myuser:mypass");
		 System.out.println(userPwd);
		 ACL aclDigest=new ACL(Perms.READ|Perms.WRITE,new Id("digest",userPwd));
		 ArrayList<ACL> aclList=new ArrayList<>();
//		 aclList.add(aclIp);
		 aclList.add(aclDigest);
		 zk.create("/testAclPath", "testAclPathVal".getBytes(),aclList, CreateMode.PERSISTENT); 
//		 Ids.OPEN_ACL_UNSAFE,Ids.READ_ACL_UNSAFE
//		 Ids.CREATOR_ALL_ACL ʹ�����ǰ������ zk.addAuthInfo(
		 
//		  //ʹ������   getAcl /testAclPath  �ܿ���
		 zk.addAuthInfo("digest", "myuser:mypass".getBytes());//ͬ����  addauth digest  myuser:mypass
		 System.out.println(new String(zk.getData("/testAclPath",false,null))); 
		 
		 //  create /acl_ip_test ip:10.1.5.225:crwda
		 //  create /acl_digest_test digest:myuser:CmVSQ2nhuKrMPNW7BK6HrthawaY=:crwda   �м�myuser:CmVSQ2nhuKrMPNW7BK6HrthawaY=��ʹ��DigestAuthenticationProvider.generateDigest("myuser:mypass")���ɵ�
		 // Ҳ����ʹ�� setAcl  /acl_ip_test ip:10.1.5.225:crwda 
	}
	public static void all() throws Exception 
	{
		 ZooKeeper zk = new ZooKeeper(ipPort,2000, new Watcher() { 
	            public void process(WatchedEvent event) { 
	                System.out.println("�Ѿ�������" + event.getType() + "�¼���"); 
	                if(event.getType()==EventType.NodeChildrenChanged)
	                	 System.out.println(event.getPath()+"�Ѿ��޸���"); //���ٴ�ע���¼�����
	                	
	            } 
	        }); 
		 
			// Ids.OPEN_ACL_UNSAFE �κ��˶����Է���
			 // Ids.AUTH_IDS ������ӵ�з���Ȩ��
		 	// Ids.READ_ACL_UNSAFE  �κ��˶����Զ�
		 	//Ids.CREATOR_ALL_ACL //ʹ��addAuthInfo��Ϣ,��Ϊ�����ڵ��acl��Ϣ
      zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,  CreateMode.PERSISTENT); 
	 zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),  Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
	 zk.create("/testRootPath2", "testRootData2".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT,
				new AsyncCallback.StringCallback() {
					@Override
					public void processResult(int rc,  String path,  Object ctx,  String name) {
						//rc=0��ʾ�ɹ�,ctx�Ǵ�����"����"�ַ�
						if(rc == KeeperException.Code.NODEEXISTS.intValue()  )
							System.out.printf("�첽���� path=%s �Ѿ�����\n",path ); 
					
						 System.out.printf("�첽����rc=%d,path=%s,ctx=%s,name=%s\n",rc,path,ctx,name); 
					}
				},"����"
			);
	 System.out.println(new String(zk.getData("/testRootPath",false,null))); 
	 
	 

	 System.out.println(zk.getChildren("/testRootPath",true)); //�ӽڵ�, boolean watch �Ƿ�����ӽڵ�ı仯,Watcher���¼�
	 zk.getChildren("/testRootPath", true, new AsyncCallback.Children2Callback() {//boolean watch
		@Override
		public void processResult(int rc,  String path,  Object ctx,  List<java.lang.String> children ,Stat stat) {
			if(rc == KeeperException.Code.NONODE.intValue()  )
				System.out.printf("�첽getChildren  path=%s ������\n",path ); 
			else
				System.out.println("�첽getChildren  "+children); 
		}
	}, "����ctx");
	
	 
	 zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1); 
	 
	 
	 
	 System.out.println("Ŀ¼�ڵ�״̬��["+zk.exists("/testRootPath",true)+"]"); //boolean watch
	 zk.exists("/testRootPath", true, new StatCallback() {
		@Override
		public void processResult(int rc, String path, Object ctx, Stat stat) {
			System.out.println("�첽exists  rc="+rc); 
		}}, "ctx");
	 
	 zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(),  Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); //Watcher��NodeChildrenChanged
	
	 zk.setData("/testRootPath/testChildPathTwo","modifyChildDataOne".getBytes(),-1,new StatCallback() {
			@Override
			public void processResult(int rc, String path, Object ctx, Stat stat) {
				 System.out.println("�첽 setData rc="+rc); 
			}
		},"ctx"); 
	 
	 
	 Stat reveStat=new Stat();
	 System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo",true,reveStat))); //boolean watch
	 System.out.println("reveStat="+reveStat);
	 zk.getData("/testRootPath/testChildPathTwo",true,new DataCallback() {
		@Override
		public void processResult(int rc,  String path,  Object ctx, byte[] data, Stat stat) {
			System.out.println("�첽getData  "+new String(data)); 
		}},"ctx value");
	 
	 
	 zk.delete("/testRootPath/testChildPathTwo",-1); //-1��ʾ�κΰ汾
	 zk.delete("/testRootPath/testChildPathOne",-1,new VoidCallback() {
		@Override
		public void processResult(int rc, String path, Object ctx) {
			System.out.println("�첽delete  rc="+rc); 
		}},"ctx val"); 
	 zk.delete("/testRootPath",-1); 
	 zk.close();
	}
	

	public static void single() throws Exception 
	{
		System.out.println(System.getProperties());
		ZooKeeper zk = new ZooKeeper(ipPort,2000, new Watcher() { 
			public void process(WatchedEvent event) { 
				System.out.println("�Ѿ�������" + event.getType() + "�¼���");
				 if(event.getState()==KeeperState.SyncConnected)
	                {
	                	 System.out.println("SyncConnected�¼���"); 
	                }
			} 
		}); 
		//CreateMode.EPHEMERAL; //ephemeral ���ݵ�
		//CreateMode.EPHEMERAL_SEQUENTIAL;
		//CreateMode.PERSISTENT_SEQUENTIAL;
		zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		
		System.out.println(new String(zk.getData("/testRootPath",false,null))); 
		System.out.println("Ŀ¼�ڵ�״̬��["+zk.exists("/testRootPath",true)+"]"); 
		zk.setData("/testRootPath","modifyData".getBytes(),-1); 
		zk.delete("/testRootPath",-1); 
		zk.close();
	}
}
