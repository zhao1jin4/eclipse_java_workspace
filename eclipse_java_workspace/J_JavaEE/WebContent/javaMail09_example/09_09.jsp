<html>
<head>
<title>对接收的e-mail进行过滤</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*,javax.mail.search.*"%>
<body>
<center><h2>
  对接收的e-mail进行过滤
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
<h3>总共收到<%=count%>封e-mail,其中：</h3> 
<%     
     //设置过滤规则，对接收的e-mail进行过滤，
     SearchTerm st=new OrTerm(new SubjectTerm("惊喜"),new FromStringTerm("webmaster@hudax.com"));
     Message[] filtermsg=inbox.search(st);
%>
有<%=filtermsg.length%>封e-mail被过滤掉： 
<%    
     //将被过滤出的e-mail放在垃圾箱中，并对其设置删除标志
     for(int i=0;i<filtermsg.length;i++)
     {
        Message msg=filtermsg[i];
        if(msg!=null)
        {
           //得到被过滤出的e-mail的标题
           String filterTitle=msg.getSubject();
           //设置删除标记
           msg.setFlag(Flags.Flag.DELETED,true);
%>
<p>
&nbsp&nbsp&nbsp;(<%=i+1%>)&nbsp&nbsp;标题：<%=filterTitle%> 
</p>
<%     
        }
     }
%>
收件箱中还剩<%=(count-filtermsg.length)%>封e-mail：
<%
     
    //将剩余的e-mail放在发件箱中，列表显示出来
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
标题：<a href="09_08.jsp?msg=<%=i%>&host=<%=host%>&user=<%=user%>&password=<%=password%>"><%=title%></a> 
</p> 
<%     }
     }   
     //关闭收件箱和到邮件服务器的连接
     inbox.close(true);
     store.close();
     //输出删除的e-mail总数
     out.println("<br><h3>共删除"+filtermsg.length+"封e-mail.</h3>");
  }catch(MessagingException m) 
  { 
    out.println(m.toString()); 
  } 
%>
</body>
</html>  