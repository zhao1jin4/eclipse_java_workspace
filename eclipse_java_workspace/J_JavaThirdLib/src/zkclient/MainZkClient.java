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
		System.out.println("����OK");
		
		IZkChildListener childListender=	new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> currentChild) throws Exception {
				System.out.println(parentPath+"���ӽڵ�ı���,�����ӽڵ��� "+currentChild);
			}
		} ;
		//JDK8,������unsubscribeChildChanges
//		zkClient.subscribeChildChanges(rootPath, (parentPath, currentChild) ->   {
//			System.out.println(parentPath+"���ӽڵ�ı���,�����ӽڵ��� "+currentChild);
//		});
		zkClient.subscribeChildChanges(rootPath,childListender );//Ҳ���Լ��������ڵĽڵ�,һ���������յ�
		
		UserLogin session=new UserLogin();
		session.setLastLogin(new Date());
		session.setUserName("lisi");
		//��Ҫʵ��Serializable�ӿ�
		String path=zkClient.create(rootPath, session, CreateMode.PERSISTENT);
		System.out.println(path+"������");
		
		 
		Stat stat=new Stat();
		UserLogin zkData=zkClient.readData(rootPath,stat);
		System.out.println("������"+zkData );
		System.out.println("���� stat getAversion="+stat.getAversion());
		
		
		boolean exist=zkClient.exists(rootPath);
		System.out.println(rootPath+"����? "+exist );
		
		List<String> childNames=zkClient.getChildren(rootPath);
		System.out.println(rootPath+"���ӽڵ���"+childNames );

		
		String childPath=zkClient.createPersistentSequential(rootPath.concat("/childOne"), "ChildOneVal");
		System.out.println(childPath+"������");

		IZkDataListener changeListender=new IZkDataListener() {
			@Override
			public void handleDataDeleted(String path) throws Exception {
				System.out.println(path+"����ɾ��");
			}
			
			@Override
			public void handleDataChange(String path, Object newVal) throws Exception {
				System.out.println(path+"�����޸���,����Ϊ"+newVal);
			}
		};
		zkClient.subscribeDataChanges(childPath, changeListender);
		zkClient.writeData(childPath, "new ChildOneVal");
		Thread.sleep(10);//�粻��,���ܻ�ֻ��Ӧ���һ�β���
		zkClient.delete(childPath);
		
//		zkClient.delete(rootPath);//ֻ��ɾû���ӽڵ��
		zkClient.deleteRecursive(rootPath); 
		
		zkClient.unsubscribeDataChanges(childPath, changeListender);
		zkClient.unsubscribeChildChanges(rootPath, childListender);
	
		System.out.println("���н����Ľڵ�ɾ����");
	}
	 
}
