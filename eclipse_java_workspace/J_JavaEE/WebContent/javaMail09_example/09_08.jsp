<html>
<head>
<title>��ʾe-mail����ϸ��Ϣ</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.Part,javax.mail.*,java.util.*,java.io.*"%>
<body>
<center><h2>
  ���մ�������e-mail
</h2></center>
<%
  //�õ�Ҫ�쿴��e-mail�ı��msg����Ҫ���ڼ���e-mail
  String msg=request.getParameter("msg");
  //�õ��ʼ�������Ҫ��֤�Ĳ���
  String host=request.getParameter("host");
  String user=request.getParameter("user");
  String password=request.getParameter("password");	
  try{
     //���һ��session���󣬲�����������Ϊtrue
     Properties props=new Properties(); 
     Session recesession=Session.getInstance(props,null);
     recesession.setDebug(true);
     //����һ��Store���󣬲����ݵõ��������������ӵ��ʼ��������е��û�����
     Store store=recesession.getStore("pop3"); 
     store.connect(host,user,password);
     //���ռ���
     Folder inbox=store.getFolder("INBOX");
     inbox.open(Folder.READ_ONLY);
     //����msg�����õ���e-mail
     int imsg=Integer.parseInt(msg);
     Message message=inbox.getMessage(imsg);
     Address[] address;
%>
<table width=85% align="center">
  <tr>
    <td>�����ˣ�</td>
    <td>    
    <%
       //�õ������˵ĵ�ַ
       address=message.getFrom();
       if(address!=null)
          for(int i=0;i<address.length;i++)
            out.print(address[i]+"&nbsp&nbsp");
       else
          out.println("��");
    %>
    </td>
  </tr>
  <tr>
    <td>����ʱ�䣺</td>
    <td>    
    <%
       //�õ�e-mail������ʱ��
       Date sentdate=message.getSentDate();
       if(address!=null)
          out.print(sentdate.toString());
       else
          out.println("��");
    %>
    </td>
  </tr>
  <tr>
    <td>�ռ��ˣ�</td>
    <td> 
    <%
       //�õ��ռ��˵ĵ�ַ�б�
       address=message.getRecipients(Message.RecipientType.TO);
       if(address!=null)
          for(int i=0;i<address.length;i++)
            out.print(address[i]+"&nbsp&nbsp");
       else
          out.println("��");
    %>
    </td>
  </tr>
  <tr>
    <td>���ͣ�</td>
    <td>
    <%
       //�õ������˵ĵ�ַ
       address=message.getRecipients(Message.RecipientType.CC);
       if(address!=null)
          for(int i=0;i<address.length;i++) {
             out.print(address[i]+"&nbsp&nbsp");
          }
       else 
          out.println("��");
    %>
    </td>
  </tr>
  <tr>
    <td>���⣺</td>
    <td><%=message.getSubject()%></td>
  </tr>
  <tr>
    <td colspan=2>
    <br>&nbsp&nbsp&nbsp;
    <%
       //body��һ����������Ƿ����˸����������ӵĲ��������û��body��������ֻ��ʾ�м��������Լ����ǵ���������
       if(request.getParameter("body")==null)
       {
          //�����һ���ಿ�����ݵ�e-mail
          if(message.isMimeType("multipart/*"))
          {
             //��ô����e-mail�Ķಿ�����ݵ�Multipart����
             Multipart multipart = (Multipart)message.getContent();
             //���λ�ȡMultipart�����ÿ������
             for(int i = 0;i < multipart.getCount();i++)
             {
               //�õ�ÿ�����ֵ�����
               Part p = multipart.getBodyPart(i);
               String disposition = p.getDisposition();
               //����ò������Ǹ������ݣ�������ø�������������
               if ((disposition != null) &&(disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE)))
               {
                  String filename=p.getFileName();
                  filename=javax.mail.internet.MimeUtility.decodeText(filename);
%>
<!--����Ҫ���ڼ���e-mail�ĵڼ�������-->
<p>
����:
<a href="09_08.jsp?msg=<%=message.getMessageNumber()%>&host=<%=host%>&user=<%=user%>&password=<%=password%>&body=<%=i%>">
<%=filename%>
</a>
</p>
<%
               }
               else if(disposition==null)
               {
                  //����ò�������ͨ�ı����ݣ��޸���������������ı�����
                  if(p.isMimeType("text/plain")){
                      out.print(p.getContent());
                  }else {
                      //����ò��������⸽�����Ͳ�������
                  }
               }
             }
          }
          //�������ͨ�ı���ʽ��e-mail������ʾ����ϸ����
          else if(message.isMimeType("text/plain")) 
          {
              out.print(message.getContent());
          }
       } 
     //�����body�������������û�����˸������ص����ӣ���ʱ�Ϳ���������Ӧ�ĸ�����
     else 
     {
        //�õ�Ҫ���صĸ����ı��
	int attachNo=Integer.parseInt(request.getParameter("body"));
	Part p;
	//������С��0���������һ�������ֵ�email
	if(attachNo<0) p=message;
	//������һ���ಿ����ɵ�email���õ������Ķ�Ӧ����
	else{
		Multipart multipart=(Multipart)message.getContent();
		p=multipart.getBodyPart(attachNo);
	}
	//���ø�������������
	response.setContentType(p.getContentType());
	//�������ظ�����ͷ��Ϣ
	String s=p.getFileName();
	s=javax.mail.internet.MimeUtility.decodeText(s);
	if (p.getFileName()!=null){
		response.setHeader("Content-Disposition","attachment; filename=\""+s+"\"");
	}
	//�������ع����е��������������
	OutputStream fout=response.getOutputStream();
	InputStream fin=p.getInputStream();
	//���ظ���
	int c=fin.read();
	while(c!=-1) 
	{
	   fout.write(c); 
           c=fin.read();
	}
     }
%>
    </td>
  </tr>
</table>
<%	
   //�ر��ռ���͵��ʼ�������������
   inbox.close(true);
   store.close();
 }catch(MessagingException m) 
 { 
    out.println(m.toString()); 
 } 
%>
<p><center>
<a href="09_06.jsp?host=<%=host%>&user=<%=user%>&password=<%=password%>">�����ռ���<a>
</center></p>
</body>
</html> 