package hadoop.hdfs;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

public class MainHDFS
{
	public static void creatFile() throws IOException 
	{
		Configuration conf=new Configuration();
		FileSystem fs=  FileSystem.get(conf);
		Path path=new Path("/java");//hadoop jar 运行
		fs.delete(path, true);//recursive
		fs.mkdirs(path);
		fs.copyFromLocalFile(new Path("/home/zhaojin/.ssh/known_hosts"), path);
		//---
		DistributedFileSystem dfs =(DistributedFileSystem)fs;
		FileStatus status=fs.getFileStatus(new Path(path,"known_hosts"));
		System.out.println("modification time:"+status.getModificationTime());
		
		BlockLocation[] locations=fs.getFileBlockLocations(status, 1, 10);
		for(int i=0;i<locations.length;i++)
		{
			System.out.println("----block "+i+" in "+Arrays.asList( locations[i].getHosts() ));
		}
		System.out.println("----block-size:"+status.getBlockSize());
		
	}
	public static void printNodeInfo() throws IOException 
	{

		Configuration conf=new Configuration();
		FileSystem fs=  FileSystem.get(conf);
		DistributedFileSystem dfs =(DistributedFileSystem)fs;//要使用hadoop jar 运行才可
		DatanodeInfo[] infos=dfs.getDataNodeStats();//所有群集中的节点
		for(DatanodeInfo info:infos)
		{
			System.out.println("----host:"+info.getHostName());
		}
	}
	public static void main(String[] args) throws Exception 
	{
		creatFile();
		printNodeInfo();
	}

}
