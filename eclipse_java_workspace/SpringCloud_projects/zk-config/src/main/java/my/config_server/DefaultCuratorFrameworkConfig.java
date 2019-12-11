package my.config_server;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.BootstrapConfiguration;
/**
 * resources/META-INF/spring.factories
org.springframework.cloud.bootstrap.BootstrapConfiguration=\
my.project.CustomCuratorFrameworkConfig,\
my.project.DefaultCuratorFrameworkConfig
 */

@BootstrapConfiguration
public class DefaultCuratorFrameworkConfig {

  @Autowired
  public void zookeeperConfig(CuratorFramework curator) {
	  //如何设置auth???
//	  curator.addAuthInfo("digest", "user:password".getBytes());
	  /*
	try {
		 List<ACL> acls = new ArrayList<ACL>();
		  Id userId = new Id("digest", DigestAuthenticationProvider.generateDigest("myuser:mypass"));
//		  acls.add(new ACL(Perms.ALL, userId));
		  acls.add(new ACL(Perms.CREATE | Perms.READ| Perms.WRITE| Perms.DELETE| Perms.ADMIN, userId)); //crwda
		  curator.setACL().withACL(acls).forPath("/config/testZookeeperApp,dev/zkPass"); //这里也要auth ,路径不能写死
		  
		  
	} catch (Exception e) {
		e.printStackTrace();
	}
 */
  }

}