<html>
<head>
<title>�������ʼ����˵�����վ</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*,javax.mail.search.*"%>
<body bgcolor="#CFF1E1">
<center><h2>
  �ҵĻ���վ
</h2></center>
<%
  try{
     //���һ��session���󣬲�����������Ϊtrue
     Properties props=new Properties(); 
     Session recesession=Session.getInstance(props,null);
     recesession.setDebug(true);
     //����һ��Store���󣬲����ݵõ��������������ӵ��ʼ��������е��û�����
     Store store=recesession.getStore("pop3"); 
     String host=(String)session.getAttribute("host"); 
     String user=(String)session.getAttribute("user"); 
     String password=(String)session.getAttribute("password"); 
     store.connect("pop."+host,user,password);
     //���ռ���
     Folder inbox=store.getFolder("INBOX");
     inbox.open(Folder.READ_ONLY);
     //�õ������е�e-mail����
     int count=inbox.getMessageCount();
    
     //���ù��˹��򣬶Խ��յ�e-mail���й��ˣ�
     SearchTerm st=new OrTerm(new SubjectTerm("��ϲ"),new FromStringTerm("webmaster@hudax.com"));
     Message[] filtermsg=inbox.search(st);
%>
<br>
<h3>&nbsp;��<%=filtermsg.length%>��e-mail������Ϊ�����ʼ���</h3> 
<%    
    //�������˳���e-mail���ڻ���վ�У�����������ɾ�����
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
&nbsp&nbsp&nbsp;(<%=i+1%>)&nbsp&nbsp;���⣺
<a href="detailmail.jsp?msg=<%=msg.getMessageNumber()%>&bin=recycle"><%=filterTitle%></a>
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
<p><center>
<a href="listlogin.html">�����ҵ�����<a>
</center></p>
</body>
</html>  