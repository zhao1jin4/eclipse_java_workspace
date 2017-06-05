package zkclient;

import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class MainZkClient 
{
	public static void main(String[] args) throws Exception
	{
		String rootPath="/testZkClient";
		
		ZkClient zkClient = new ZkClient("10.1.5.225:2581",10000,10000,new SerializableSerializer());
		System.out.println("连接OK");
		
		IZkChildListener childListender=	new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> currentChild) throws Exception {
				System.out.println(parentPath+"的子节点改变了,现有子节点有 "+currentChild);
			}
		} ;
		//JDK8,但不能unsubscribeChildChanges
//		zkClient.subscribeChildChanges(rootPath, (parentPath, currentChild) ->   {
//			System.out.println(parentPath+"的子节点改变了,现有子节点有 "+currentChild);
//		});
		zkClient.subscribeChildChanges(rootPath,childListender );//也可以监听不存在的节点,一但建立会收到
		
		UserLogin session=new UserLogin();
		session.setLastLogin(new Date());
		session.setUserName("lisi");
		//类要实现Serializable接口
		String path=zkClient.create(rootPath, session, CreateMode.PERSISTENT);
		System.out.println(path+"建立了");
		
		 
		Stat stat=new Stat();
		UserLogin zkData=zkClient.readData(rootPath,stat);
		System.out.println("读到了"+zkData );
		System.out.println("读到 stat getAversion="+stat.getAversion());
		
		
		boolean exist=zkClient.exists(rootPath);
		System.out.println(rootPath+"存在? "+exist );
		
		List<String> childNames=zkClient.getChildren(rootPath);
		System.out.println(rootPath+"的子节点有"+childNames );

		
		String childPath=zkClient.createPersistentSequential(rootPath.concat("/childOne"), "ChildOneVal");
		System.out.println(childPath+"建立了");

		IZkDataListener changeListender=new IZkDataListener() {
			@Override
			public void handleDataDeleted(String path) throws Exception {
				System.out.println(path+"数据删了");
			}
			
			@Override
			public void handleDataChange(String path, Object newVal) throws Exception {
				System.out.println(path+"数据修改了,新数为"+newVal);
			}
		};
		zkClient.subscribeDataChanges(childPath, changeListender);
		zkClient.writeData(childPath, "new ChildOneVal");
		Thread.sleep(10);//如不加,可能会只响应最后一次操作
		zkClient.delete(childPath);
		
//		zkClient.delete(rootPath);//只可删没有子节点的
		zkClient.deleteRecursive(rootPath); 
		
		zkClient.unsubscribeDataChanges(childPath, changeListender);
		zkClient.unsubscribeChildChanges(rootPath, childListender);
	
		System.out.println("所有建立的节点删除了");
	}
	 
}
