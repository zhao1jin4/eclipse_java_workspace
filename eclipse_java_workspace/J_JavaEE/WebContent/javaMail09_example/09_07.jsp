<html>
<head>
<title>��ʾ��ͨ�ı���ʽe-mail����ϸ��Ϣ</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*,java.io.*"%>
<body>
<center><h2>
  ��ʾe-mail����ϸ��Ϣ
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
       //����ʼ�����ͨ�ı���ʽ������ʾ����ϸ����
       if(message.isMimeType("text/plain"))         
          out.print(message.getContent());
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
     