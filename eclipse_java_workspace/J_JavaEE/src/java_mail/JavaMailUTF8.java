package java_mail;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FromStringTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

public class JavaMailUTF8
{
//	POP3默认端口110 SMTP默认端口25 IMAP默认端口143
//	
//	cmd命令  telent smtp.163.com 25 
//	以下不能输错,不能贴粘.
//		
//	HELO smtp.163.com 	命令 
//	250 hz-b-163smtp1.163.com 9561591f-d7ff-4bd5-876a-9fefcf7846e5  返回250表示OK 
//	auth login 			命令 
//	334 VXNlcm5hbWU6 9561591f-d7ff-4bd5-876a-9fefcf7846e5 
//	USER emhhbzFqaW40	 命令 是用户名的Base64 3
//	PASS xxxxxx 		命令 是密码的Base64,这里就过不去了
//	后面还有　 
//	MAILFROM:XXX@163.COM 
//	RCPTTO:XXX@163.COM 
//	DATA 
//	354 End data with .
//	QUIT
//	--------另一套
//	 EHLO zhaopinpc
//	 250-hz-b-163smtp2.163.com
//	 250-mail
//	 250-PIPELINING
//	 250-8BITMIME
//	 250-AUTH LOGIN PLAIN
//	 250-AUTH=LOGIN PLAIN
//	 250 SARTTLS 1ad55c7f-d850-4a65-8d61-3ba8f024113f
//	 AUTH LOGIN
//	 334 VXNlcm5hbWU6 1ad55c7f-d850-4a65-8d61-3ba8f024113f
//	 xx 用户名Base64

	static Properties prop=	new Properties();
	static{
		try {
			prop.load(new FileInputStream("C:/temp/mail.properties"));
		} catch ( Exception e) {
			e.printStackTrace();
		}  
	}
	public static final String username=prop.getProperty("username");  // 是@sina.com 前面的部分
	public static final String password=prop.getProperty("password"); 
	public static final String mailTo =prop.getProperty("mailTo");   // 带@的
	public static final String filterFromMailAddr=prop.getProperty("filterFrom");//xx@sina.com
	
//	public static final String addrHost="sina.com";
//	public static final String pop3Host="pop3.sina.com";
//	public static final String smtpHost="smtp.sina.com";
//	public static final String imapHost="imap.sina.com"; 
	
	public static final String addrHost="163.com";
	public static final String pop3Host="pop3.163.com";
	public static final String smtpHost="smtp.163.com";
	public static final String imapHost="imap.163.com";
	
	public static void receivePop3Mail() throws Exception
	{
		Properties props = new Properties();
		Session recesession = Session.getInstance(props, null);
		recesession.setDebug(true);
		Store store = recesession.getStore("pop3");
		store.connect(pop3Host, username, password);
		
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		int count = inbox.getMessageCount();

	 
		// 设置过滤规则，对接收的e-mail进行过滤，
		SearchTerm st = new OrTerm(new SubjectTerm("笔记"), new FromStringTerm(filterFromMailAddr));//OrTerm 是或的原则
		Message[] filtermsg = inbox.search(st);
		// 对被过滤出的e-mail设置删除标志
		for (int i = 0; i < filtermsg.length; i++) 
		{
			Message msg = filtermsg[i];
			if (msg != null) 
			{
				// 得到被过滤出的e-mail的标题
				String filterTitle = msg.getSubject();
				System.out.println("被过滤的邮件:" + filterTitle);
				
				// 设置删除标记
				msg.setFlag(Flags.Flag.DELETED, true);//修改进入[已删除]列表,但不是在邮箱中是已删除???
			}
		}
		System.out.println("收件箱中总共有" + (count - filtermsg.length) + "封e-mail");
		 
		
		//列表显示出来
	    for(int j=1;j<=count;j++)
	    {
		      Message message=inbox.getMessage(j);
		      //如果不是待删除的e-mail就显示出来
		      if(message.isSet(Flags.Flag.DELETED))
		    	  continue;
		      
	         String title=message.getSubject();
	         System.out.println("---------邮件标题:"+title);
	         //------------邮件细节
	         Address[] address=message.getFrom();//javax.mail.internet.InternetAddress
	         if(address!=null)
	        	  for(int i=0;i<address.length;i++)
	            	 System.out.print("发件人:"+((InternetAddress)address[i]).getAddress());
	         
	         Date sentdate=message.getSentDate();
	         if(sentdate!=null)
	        	 System.out.print("发出的时间:"+sentdate.toString());
	         
	         address=message.getRecipients(Message.RecipientType.TO);
	         if(address!=null)
	            for(int i=0;i<address.length;i++)
	            	 System.out.print("收件人:"+address[i]);
	         
	         address=message.getRecipients(Message.RecipientType.CC);
	         if(address!=null)
	            for(int i=0;i<address.length;i++) 
	            	System.out.print("抄送人:"+address[i]);
	         
	        //如果是一个多部分内容的e-mail
	        if(message.isMimeType("multipart/*"))
	        {
	           //获得代表该e-mail的多部分内容的Multipart对象
	           Multipart multipart = (Multipart)message.getContent();
	           //依次获取Multipart对象的每个部分
	           for(int i = 0;i < multipart.getCount();i++)
	           {
	             //得到每个部分的属性
	             Part p = multipart.getBodyPart(i);
	             String disposition = p.getDisposition();
	             
	             //如果该部分中是附件内容，则输出该附件的下载链接
	             if ((disposition != null) &&(disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE)))
	             {
	                String filename=p.getFileName();
	                filename=javax.mail.internet.MimeUtility.decodeText(filename);//中文OK
	                System.out.println("符件:"+filename+",mime:"+p.getContentType());
	   	         
	                int num=message.getMessageNumber();
	                System.out.println("邮件索引:"+num);

	                InputStream input=p.getInputStream();//下载附件
	              }else if(disposition==null)
                 {
                    if(p.isMimeType("text/plain"))
                    {
                    	  System.out.print("只处理的文本:"+p.getContent());
                    }else   if(p.isMimeType("text/html"))
                    {   
                    	System.out.println("=====HTMLcontent:"+p.getContent());
                    }else//如type:multipart/alternative
                    {
                    	System.out.println("=====not text/plain or text/html ===type:"+p.getContentType()+"\n----content:"+p.getContent());
                    }
                 }
               }
	        }else if(message.isMimeType("text/plain"))  //如果是普通文本形式的e-mail，则显示其详细内容
            {
            	System.out.print("邮件文本:"+message.getContent());
            }else//几乎没很少有这种情况
            {
            	System.out.print("========other ContentType:"+message.getContentType());
            }
         } 
 		inbox.close(true);
 		store.close();
	}
	public static void receiveIMAPMail() throws MessagingException 
	{
		Properties props = System.getProperties();
		Session sess = Session.getInstance(props, null);
		sess.setDebug(true);
		Store st = sess.getStore("imap");//还可是 imaps
		st.connect(imapHost, username, password);
		
		Folder fol = st.getFolder("INBOX");
		if (fol.exists())
		{
			for (Folder f : fol.list()) 
			{
				System.out.printf("-----box:%s", f.getName());//只有一个INBOX
			}
			fol.open(Folder.READ_ONLY);
			Message[] msgs=fol.getMessages();
			for (Message m : msgs) 
			{
				System.out.printf("-----/n来自%s /n标题%s/n大小%d/n",convertAddress2String(m.getFrom()), m.getSubject(), m.getSize());
			}
			fol.close(false);
		} 
		st.close();
	}
	public static void sendHTMLAttachmentMail() throws MessagingException // TestOK
	{
		String subject = "subject from javamail 标题";
		String bodyText = "hello !,this is java mail test body 正文.";
		String attachment = "c:/temp/图片.jpg";
		boolean isSendAttach=true;

		
		Properties props = new Properties();
		Session sendsession = Session.getInstance(props, null);
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", "true");// 设置SMTP服务器需要权限认证
		sendsession.setDebug(true);
		Message message = new MimeMessage(sendsession);
		message.addHeader("Content-type", "text/html");//对HTML格式的邮件
		
		message.setFrom(new InternetAddress(username + "@" + addrHost));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
		message.setSubject(subject);
		message.setSentDate(new Date());

		if (isSendAttach) 
		{
			// 建立第一部分：文本正文
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8><div style='color:red;font-size:50px'>" + bodyText+"</div>", "text/html;charset=UTF-8");//对HTML格式的邮件
			// 建立多个部分Multipart实例
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			
			
			
			// 建立第二部分：附件
			messageBodyPart = new MimeBodyPart();
			// 获得附件
			DataSource source = new FileDataSource(attachment);
			// 设置附件的数据处理器
			messageBodyPart.setDataHandler(new DataHandler(source));
			// 设置附件文件名
			
			String fileName=source.getName(); 
			try {
				fileName=MimeUtility.encodeText(fileName);//在收件箱中的中文OK
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			messageBodyPart.setFileName(fileName); 
			
			
			// 加入第二部分
			multipart.addBodyPart(messageBodyPart);
			// 将多部分内容放到e-mail中
			message.setContent(multipart);
		}  
		
		message.saveChanges();
		Transport transport = sendsession.getTransport("smtp");
		transport.connect(smtpHost, username, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}
	public static String convertAddress2String(Address[] a)
	{
		if (a == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		for (Address adr : a) {
			InternetAddress ia = (InternetAddress) adr;
			sb.append(ia.getAddress());
		}
		return sb.toString();

	}
	public static void main(String[] args) throws Exception {
		//sendHTMLAttachmentMail();//测试成功 OK
		receivePop3Mail();//测试OK,不会删邮件,删标志不会移动删件箱中
		//receiveIMAPMail();//OK
	}
}
