package servlet4_new;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;

@WebServlet(urlPatterns= {"/servlet4Push"})
public class Servle4Push extends HttpServlet 
{
/*
 * 
---- Tomcat 9 打开HTTP 2(只能在https下运行)  
下载 tomcat-native-1.2.18-openssl-1.1.1-win32-bin.zip 解压后的bin/x64/下的 tcnative-1.dll 放PATH中能找到的地方
bin/openssl 命令可用
    openssl genrsa -out server.key 2048
    openssl rsa -in server.key -out server.key
    openssl req -new -x509 -key server.key -out ca.crt -days 3650
把生成的文件放tomcat/conf目录
修改tomcat 9 server.xml中打开
<Connector port="8443" protocol="org.apache.coyote.http11.Http11AprProtocol"
               maxThreads="150" SSLEnabled="true" >
	<UpgradeProtocol className="org.apache.coyote.http2.Http2Protocol" />
	<SSLHostConfig>
		<Certificate certificateKeyFile="conf/localhost-rsa-key.pem"
					 certificateFile="conf/localhost-rsa-cert.pem"
					 certificateChainFile="conf/localhost-rsa-chain.pem"
					 type="RSA" />
	</SSLHostConfig>
</Connector>
修改证书路径 为
	    <Certificate certificateKeyFile="conf/server.key"
                         certificateFile="conf/ca.crt"/>

https://localhost:8443/ 访问项目
----
 */
	//Chrome network 标签打开protocol列值是h2,tomcat9/logs/localhost_access_log.x.txt 显示是HTTP/2.0
	// https://www.ibm.com/developerworks/cn/java/j-javaee8-servlet4/index.html
	//HTTP/2  服务器推送
	//JSF 2.3 中的服务器推送
	/*
	 Chrome工具 自带的工具 chrome://net-internals/ ->  HTTP/2->点表格中的ID列的值->复选一行
	 看报文    HTTP2_SESSION_RECV_PUSH_PROMISE 有 
	  --> id = 1
      --> promised_stream_id = 2
    */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PushBuilder pushBuilder=req.newPushBuilder();//如果不支持返回null
		if (pushBuilder != null)
		{
			System.out.println("can push");
			//push 非阻塞
		   pushBuilder.path("img/bing.png").push();
		   pushBuilder.path("js/md5.js").push();
		   
//			pushBuilder.path("/").queryString("");
//			pushBuilder.push();
		}else
		{
			System.out.println("can not  push");
		}
		//req.getRequestDispatcher("servlet4.jsp").forward(req, resp);
		getServletContext().getRequestDispatcher("/servlet4.jsp").forward(req, resp);
		//servlet4.jsp 中有引用  img/bing.png 和 js/md5.js 文件


	}
	

}
