<html>
<head>
<title>显示收件箱中所有e-mail列表</title>   
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*,javax.mail.search.*"%>
<body bgcolor="#CFF1E1">
<center><h2>
  我的收件箱
</h2></center>
<%
 try{
   //获得一个session对象，并设置其属性为true
   Properties props=new Properties(); 
   Session recesession=Session.getInstance(props,null);
   recesession.setDebug(true);
   //创建一个Store对象，并根据得到的三个参数连接到邮件服务器中的用户邮箱  
   Store store=recesession.getStore("pop3"); 
   String host=(String)session.getAttribute("host"); 
   String user=(String)session.getAttribute("user"); 
   String password=(String)session.getAttribute("password"); 
   store.connect("pop."+host,user,password);
   //打开收件箱
   Folder inbox=store.getFolder("INBOX");
   inbox.open(Folder.READ_ONLY);
   //得到邮箱中的e-mail总数
   int count=inbox.getMessageCount();

   //设置过滤规则，对接收的e-mail进行过滤，
     SearchTerm st=new OrTerm(new SubjectTerm("惊喜"),new FromStringTerm("webmaster@hudax.com"));
     Message[] filtermsg=inbox.search(st);  
   //对被过滤出的e-mail设置删除标志
     for(int i=0;i<filtermsg.length;i++)
     {
        Message msg=filtermsg[i];
        if(msg!=null)
        {
           //得到被过滤出的e-mail的标题
           String filterTitle=msg.getSubject();
           //设置删除标记
           msg.setFlag(Flags.Flag.DELETED,true);
        }
     }
%>
<br>
<h3>&nbsp;收件箱中总共有<%=(count-filtermsg.length)%>封e-mail：</h3>
<%     
   //将过滤后剩余的e-mail放在发件箱中，列表显示出来
    int receindex=0;
    for(int i=1;i<=count;i++)
    {
      Message message=inbox.getMessage(i);
      //如果不是待删除的e-mail就显示出来
      if(!message.isSet(Flags.Flag.DELETED)) 
      {
         String title=message.getSubject();
         receindex++;
%>
<p>
&nbsp&nbsp&nbsp;(<%=receindex%>)&nbsp&nbsp;
标题：<a href="detailmail.jsp?msg=<%=i%>&bin=receive"><%=title%></a> 
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
<p><center>
<a href="listlogin.html">返回我的邮箱<a>
</center></p>
</body>
</html>