<html>
<head>
<title>��ʾ����e-mail�б�</title>   
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*"%>
<body>
<center><h2>
  ��ʾ����e-mail�б�
</h2></center>
<%
 try{
   //���һ��session���󣬲�����������Ϊtrue
   Properties props=new Properties(); 
   Session recesession=Session.getInstance(props,null);
   recesession.setDebug(true);
   //����һ��Store���󣬲����ݵõ��������������ӵ��ʼ��������е��û�����  
   Store store=recesession.getStore("pop3"); 
   String host=request.getParameter("host");
   String user=request.getParameter("user");
   String password=request.getParameter("password");
   store.connect(host,user,password);
   //���ռ���
   Folder inbox=store.getFolder("INBOX");
   inbox.open(Folder.READ_ONLY);
   //�õ������е�e-mail����
   int count=inbox.getMessageCount();
%>
<h3>�ռ�������<%=count%>��e-mail��</h3>
<%   
   //ѭ����ʾ���е�e-mail�ı���
   for(int i=1;i<=count;i++)
   {
      Message message=inbox.getMessage(i);
      //������Ǵ�ɾ����e-mail����ʾ����
      if(!message.isSet(Flags.Flag.DELETED)) 
      {
         String title=message.getSubject();
%>
<p>
(<%=i%>)&nbsp&nbsp&nbsp;
���⣺<a href="09_08.jsp?msg=<%=i%>&host=<%=host%>&user=<%=user%>&password=<%=password%>"><%=title%></a> 
</p>   
<% 
      }
   }
   //�ر��ռ���͵��ʼ�������������
   inbox.close(true);
   store.close();
 }catch(MessagingException m) 
 { 
    out.println(m.toString()); 
 } 
%>   
</body>
</html>