<html>
<head>
<title>显示所有e-mail列表</title>   
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*"%>
<body>
<center><h2>
  显示所有e-mail列表
</h2></center>
<%
 try{
   //获得一个session对象，并设置其属性为true
   Properties props=new Properties(); 
   Session recesession=Session.getInstance(props,null);
   recesession.setDebug(true);
   //创建一个Store对象，并根据得到的三个参数连接到邮件服务器中的用户邮箱  
   Store store=recesession.getStore("pop3"); 
   String host=request.getParameter("host");
   String user=request.getParameter("user");
   String password=request.getParameter("password");
   store.connect(host,user,password);
   //打开收件箱
   Folder inbox=store.getFolder("INBOX");
   inbox.open(Folder.READ_ONLY);
   //得到邮箱中的e-mail总数
   int count=inbox.getMessageCount();
%>
<h3>收件箱中有<%=count%>封e-mail：</h3>
<%   
   //循环显示所有的e-mail的标题
   for(int i=1;i<=count;i++)
   {
      Message message=inbox.getMessage(i);
      //如果不是待删除的e-mail就显示出来
      if(!message.isSet(Flags.Flag.DELETED)) 
      {
         String title=message.getSubject();
%>
<p>
(<%=i%>)&nbsp&nbsp&nbsp;
标题：<a href="09_08.jsp?msg=<%=i%>&host=<%=host%>&user=<%=user%>&password=<%=password%>"><%=title%></a> 
</p>   
<% 
      }
   }
   //关闭收件箱和到邮件服务器的连接
   inbox.close(true);
   store.close();
 }catch(MessagingException m) 
 { 
    out.println(m.toString()); 
 } 
%>   
</body>
</html>