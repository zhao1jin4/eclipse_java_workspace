<html>
<head>
<title>�Խ��յ�e-mail���й���</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*,javax.mail.search.*"%>
<body>
<center><h2>
  �Խ��յ�e-mail���й���
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
<h3>�ܹ��յ�<%=count%>��e-mail,���У�</h3> 
<%     
     //���ù��˹��򣬶Խ��յ�e-mail���й��ˣ�
     SearchTerm st=new OrTerm(new SubjectTerm("��ϲ"),new FromStringTerm("webmaster@hudax.com"));
     Message[] filtermsg=inbox.search(st);
%>
��<%=filtermsg.length%>��e-mail�����˵��� 
<%    
     //�������˳���e-mail�����������У�����������ɾ����־
     for(int i=0;i<filtermsg.length;i++)
     {
        Message msg=filtermsg[i];
        if(msg!=null)
        {
           //�õ������˳���e-mail�ı���
           String filterTitle=msg.getSubject();
           //����ɾ�����
           msg.setFlag(Flags.Flag.DELETED,true);
%>
<p>
&nbsp&nbsp&nbsp;(<%=i+1%>)&nbsp&nbsp;���⣺<%=filterTitle%> 
</p>
<%     
        }
     }
%>
�ռ����л�ʣ<%=(count-filtermsg.length)%>��e-mail��
<%
     
    //��ʣ���e-mail���ڷ������У��б���ʾ����
    int receindex=0;
    for(int i=1;i<=count;i++)
    {
      Message message=inbox.getMessage(i);
      //������Ǵ�ɾ����e-mail����ʾ����
      if(!message.isSet(Flags.Flag.DELETED)) 
      {
         String title=message.getSubject();
         receindex++;
%>      
<p>
&nbsp&nbsp&nbsp;(<%=receindex%>)&nbsp&nbsp;
���⣺<a href="09_08.jsp?msg=<%=i%>&host=<%=host%>&user=<%=user%>&password=<%=password%>"><%=title%></a> 
</p> 
<%     }
     }   
     //�ر��ռ���͵��ʼ�������������
     inbox.close(true);
     store.close();
     //���ɾ����e-mail����
     out.println("<br><h3>��ɾ��"+filtermsg.length+"��e-mail.</h3>");
  }catch(MessagingException m) 
  { 
    out.println(m.toString()); 
  } 
%>
</body>
</html>  