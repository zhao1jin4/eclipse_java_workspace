package hadoop.zookeeper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.zookeeper.server.ZooKeeperServerMain;


public class MyMainZookeeperServer extends ZooKeeperServerMain //类里有main方法, 也是调用 initializeAndRun
{
 
	public static void main(String[] args)
	{  
		try {
		
		   File tmpDir = new File(System.getProperties().get("java.io.tmpdir").toString());
		   File confFile = new File(tmpDir, "zoo.cfg");

           FileWriter fwriter = new FileWriter(confFile);
           fwriter.write("tickTime=2000\n");
           fwriter.write("initLimit=10\n");
           fwriter.write("syncLimit=5\n");

           File dataDir = new File(tmpDir, "data");
           if (!dataDir.exists() )
        	   dataDir.mkdir() ;
           String df = org.apache.commons.lang.StringUtils.replace(dataDir.toString(),"\\","/");
           fwriter.write("dataDir=" + df + "\n");

           fwriter.write("clientPort=3181\n");
           fwriter.flush();
           fwriter.close();
           
           MyMainZookeeperServer  main=new MyMainZookeeperServer();
		   main.initializeAndRun( new String[ ]{ confFile.toString() } );
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
