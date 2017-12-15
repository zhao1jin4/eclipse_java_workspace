<html>
<head>
<title>发送e-mail</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,javax.mail.internet.*,javax.activation.*,java.util.*"%>
<body bgcolor="#CFF1E1">
<% 
 //获取用户信息参数和邮件主机名
 String host=(String)session.getAttribute("host"); 
 String user=(String)session.getAttribute("user"); 
 String password=(String)session.getAttribute("password"); 

 try{ 
      //获得属性，并生成Session对象 
      Properties props=new Properties(); 
      Session sendsession; 
      Transport transport; 
      sendsession = Session.getInstance(props, null); 
      //向属性中写入SMTP服务器的地址
      props.put("mail.smtp.host", "smtp."+host);
      //设置SMTP服务器需要权限认证
      props.put("mail.smtp.auth","true");
      //设置输出调试信息
      sendsession.setDebug(true);
      //根据Session生成Message对象
      Message message = new MimeMessage(sendsession); 
      //设置发信人地址
      message.setFrom(new InternetAddress(user+"@"+host)); 
      //设置收信人地址
      message.setRecipient(Message.RecipientType.TO,new InternetAddress(request.getParameter("to"))); 
      //设置e-mail标题 
      message.setSubject(new String(request.getParameter("subject").getBytes("ISO8859_1"),"GBK"));
      //设置e-mail发送时间
      message.setSentDate(new Date()); 
      //设置e-mail内容
      message.setText(new String(request.getParameter("text").getBytes("ISO8859_1"),"GBK"));
      //获得attachment参数
      String attachment=new String(request.getParameter("attachment").getBytes("ISO8859_1"),"GBK");
	//如有附件
	if (!attachment.equals(""))
	{	
	    //建立第一部分：文本正文
	    BodyPart messageBodyPart=new MimeBodyPart();
	    messageBodyPart.setText(new String(request.getParameter("text").getBytes("ISO8859_1"),"GBK"));	
	    // 建立多个部分Multipart实例
	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);
	    // 建立第二部分：附件	 
	    messageBodyPart=new MimeBodyPart();
	    // 获得附件
	    DataSource source=new FileDataSource(attachment);
	    //设置附件的数据处理器
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    // 设置附件文件名
	    messageBodyPart.setFileName(attachment);
	    // 加入第二部分
	    multipart.addBodyPart(messageBodyPart);    
    	    // 将多部分内容放到e-mail中
    	    message.setContent(multipart);
        }else{
            //如无附件，则按纯文本格式处理
	    message.setText(new String(request.getParameter("text").getBytes("ISO8859_1"),"GBK"));  
        }        
      //保存对于e-mail的修改
      message.saveChanges();
      //根据Session生成Transport对象
      transport=sendsession.getTransport("smtp"); 
      //连接到SMTP服务器
      transport.connect("smtp."+host,user,password);
      //发送e-mail
      transport.sendMessage(message,message.getAllRecipients());
      //关闭Transport连接
      transport.close();
%>
<p>e-mail成功发送到<%=request.getParameter("to")%>！</p>
<p>附件<%=attachment%>一并发送成功！</p>
<% 
   }
   catch(MessagingException m) 
   { 
      out.println(m.toString()); 
   } 
%> 
<center><h3>
<br><a href="listlogin.html">请返回</a> 
</h3></center>
</body>
</html>      