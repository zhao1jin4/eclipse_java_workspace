<html>
<head>
<title>����e-mail</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,javax.mail.internet.*,javax.activation.*,java.util.*"%>
<body>
<% 
 try{ 
      //������ԣ�������Session���� 
      Properties props=new Properties(); 
      Session sendsession; 
      Transport transport; 
      sendsession = Session.getInstance(props, null); 
      //��������д��SMTP�������ĵ�ַ
      props.put("mail.smtp.host", "smtp.163.com");
      //����SMTP��������ҪȨ����֤
      props.put("mail.smtp.auth","true");
      //�������������Ϣ
      sendsession.setDebug(true);
      //����Session����Message����
      Message message = new MimeMessage(sendsession); 
      //���÷����˵�ַ
      message.setFrom(new InternetAddress(request.getParameter("from"))); 
      //���������˵�ַ
      message.setRecipient(Message.RecipientType.TO,new InternetAddress(request.getParameter("to"))); 
      //����e-mail���� 
      message.setSubject(new String(request.getParameter("subject").getBytes("ISO8859_1"),"GBK"));
      //����e-mail����ʱ��
      message.setSentDate(new Date()); 
      //����e-mail����
      message.setText(new String(request.getParameter("text").getBytes("ISO8859_1"),"GBK"));
      //�������Email���޸�
      message.saveChanges();
      //����Session����Transport����
      transport=sendsession.getTransport("smtp"); 
      //���ӵ�SMTP������
      transport.connect("smtp.163.com","yanyujiecn","yyj6479");
      //����e-mail
      transport.sendMessage(message,message.getAllRecipients());
      //�ر�Transport����
      transport.close();
%>
<h3>e-mail���ͳɹ���</h3>
<% 
   }
   catch(MessagingException m) 
   { 
      out.println(m.toString()); 
   } 
%>  
</body>
</html>      