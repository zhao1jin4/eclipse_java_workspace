<html>
<head>
<title>����e-mail</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,javax.mail.internet.*,javax.activation.*,java.util.*"%>
<body bgcolor="#CFF1E1">
<% 
 //��ȡ�û���Ϣ�������ʼ�������
 String host=(String)session.getAttribute("host"); 
 String user=(String)session.getAttribute("user"); 
 String password=(String)session.getAttribute("password"); 

 try{ 
      //������ԣ�������Session���� 
      Properties props=new Properties(); 
      Session sendsession; 
      Transport transport; 
      sendsession = Session.getInstance(props, null); 
      //��������д��SMTP�������ĵ�ַ
      props.put("mail.smtp.host", "smtp."+host);
      //����SMTP��������ҪȨ����֤
      props.put("mail.smtp.auth","true");
      //�������������Ϣ
      sendsession.setDebug(true);
      //����Session����Message����
      Message message = new MimeMessage(sendsession); 
      //���÷����˵�ַ
      message.setFrom(new InternetAddress(user+"@"+host)); 
      //���������˵�ַ
      message.setRecipient(Message.RecipientType.TO,new InternetAddress(request.getParameter("to"))); 
      //����e-mail���� 
      message.setSubject(new String(request.getParameter("subject").getBytes("ISO8859_1"),"GBK"));
      //����e-mail����ʱ��
      message.setSentDate(new Date()); 
      //����e-mail����
      message.setText(new String(request.getParameter("text").getBytes("ISO8859_1"),"GBK"));
      //���attachment����
      String attachment=new String(request.getParameter("attachment").getBytes("ISO8859_1"),"GBK");
	//���и���
	if (!attachment.equals(""))
	{	
	    //������һ���֣��ı�����
	    BodyPart messageBodyPart=new MimeBodyPart();
	    messageBodyPart.setText(new String(request.getParameter("text").getBytes("ISO8859_1"),"GBK"));	
	    // �����������Multipartʵ��
	    Multipart multipart = new MimeMultipart();
	    multipart.addBodyPart(messageBodyPart);
	    // �����ڶ����֣�����	 
	    messageBodyPart=new MimeBodyPart();
	    // ��ø���
	    DataSource source=new FileDataSource(attachment);
	    //���ø��������ݴ�����
	    messageBodyPart.setDataHandler(new DataHandler(source));
	    // ���ø����ļ���
	    messageBodyPart.setFileName(attachment);
	    // ����ڶ�����
	    multipart.addBodyPart(messageBodyPart);    
    	    // ���ಿ�����ݷŵ�e-mail��
    	    message.setContent(multipart);
        }else{
            //���޸������򰴴��ı���ʽ����
	    message.setText(new String(request.getParameter("text").getBytes("ISO8859_1"),"GBK"));  
        }        
      //�������e-mail���޸�
      message.saveChanges();
      //����Session����Transport����
      transport=sendsession.getTransport("smtp"); 
      //���ӵ�SMTP������
      transport.connect("smtp."+host,user,password);
      //����e-mail
      transport.sendMessage(message,message.getAllRecipients());
      //�ر�Transport����
      transport.close();
%>
<p>e-mail�ɹ����͵�<%=request.getParameter("to")%>��</p>
<p>����<%=attachment%>һ�����ͳɹ���</p>
<% 
   }
   catch(MessagingException m) 
   { 
      out.println(m.toString()); 
   } 
%> 
<center><h3>
<br><a href="listlogin.html">�뷵��</a> 
</h3></center>
</body>
</html>      