<html>
<head>
<title>发送e-mail</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,javax.mail.internet.*,javax.activation.*,java.util.*"%>
<body>
<% 
 try{ 
      //获得属性，并生成Session对象 
      Properties props=new Properties(); 
      Session sendsession; 
      Transport transport; 
      sendsession = Session.getInstance(props, null); 
      //向属性中写入SMTP服务器的地址
      props.put("mail.smtp.host", "smtp.163.com");
      //设置SMTP服务器需要权限认证
      props.put("mail.smtp.auth","true");
      //设置输出调试信息
      sendsession.setDebug(true);
      //根据Session生成Message对象
      Message message = new MimeMessage(sendsession); 
      //设置发信人地址
      message.setFrom(new InternetAddress(request.getParameter("from"))); 
      //设置收信人地址
      message.setRecipient(Message.RecipientType.TO,new InternetAddress(request.getParameter("to"))); 
      //设置e-mail标题 
      message.setSubject(new String(request.getParameter("subject").getBytes("ISO8859_1"),"GBK"));
      //设置e-mail发送时间
      message.setSentDate(new Date()); 
      //设置e-mail内容
      message.setText(new String(request.getParameter("text").getBytes("ISO8859_1"),"GBK"));
      //保存对于Email的修改
      message.saveChanges();
      //根据Session生成Transport对象
      transport=sendsession.getTransport("smtp"); 
      //连接到SMTP服务器
      transport.connect("smtp.163.com","yanyujiecn","yyj6479");
      //发送e-mail
      transport.sendMessage(message,message.getAllRecipients());
      //关闭Transport连接
      transport.close();
%>
<h3>e-mail发送成功！</h3>
<% 
   }
   catch(MessagingException m) 
   { 
      out.println(m.toString()); 
   } 
%>  
</body>
</html>      