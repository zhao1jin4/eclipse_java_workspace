<html>
<head>
<title>显示普通文本格式e-mail的详细信息</title>
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*,java.io.*"%>
<body>
<center><h2>
  显示e-mail的详细信息
</h2></center>
<%
  //得到要察看的e-mail的编号msg，即要看第几封e-mail
  String msg=request.getParameter("msg");
  //得到邮件服务器要验证的参数
  String host=request.getParameter("host");
  String user=request.getParameter("user");
  String password=request.getParameter("password");	
  try{
     //获得一个session对象，并设置其属性为true
     Properties props=new Properties(); 
     Session recesession=Session.getInstance(props,null);
     recesession.setDebug(true);
     //创建一个Store对象，并根据得到的三个参数连接到邮件服务器中的用户邮箱
     Store store=recesession.getStore("pop3"); 
     store.connect(host,user,password);
     //打开收件箱
     Folder inbox=store.getFolder("INBOX");
     inbox.open(Folder.READ_ONLY);
     //根据msg参数得到该e-mail
     int imsg=Integer.parseInt(msg);
     Message message=inbox.getMessage(imsg);
     Address[] address;
%>
<table width=85% align="center">
  <tr>
    <td>发件人：</td>
    <td>    
    <%
       //得到发件人的地址
       address=message.getFrom();
       if(address!=null)
          for(int i=0;i<address.length;i++)
            out.print(address[i]+"&nbsp&nbsp");
       else
          out.println("无");
    %>
    </td>
  </tr>
  <tr>
    <td>发送时间：</td>
    <td>    
    <%
       //得到e-mail发出的时间
       Date sentdate=message.getSentDate();
       if(address!=null)
          out.print(sentdate.toString());
       else
          out.println("无");
    %>
    </td>
  </tr>
  <tr>
    <td>收件人：</td>
    <td> 
    <%
       //得到收件人的地址列表
       address=message.getRecipients(Message.RecipientType.TO);
       if(address!=null)
          for(int i=0;i<address.length;i++)
            out.print(address[i]+"&nbsp&nbsp");
       else
          out.println("无");
    %>
    </td>
  </tr>
  <tr>
    <td>抄送：</td>
    <td>
    <%
       //得到抄送人的地址
       address=message.getRecipients(Message.RecipientType.CC);
       if(address!=null)
          for(int i=0;i<address.length;i++) {
             out.print(address[i]+"&nbsp&nbsp");
          }
       else 
          out.println("无");
    %>
    </td>
  </tr>
  <tr>
    <td>标题：</td>
    <td><%=message.getSubject()%></td>
  </tr>
  <tr>
    <td colspan=2>
    <br>&nbsp&nbsp&nbsp;
    <%
       //如果邮件是普通文本形式，则显示其详细内容
       if(message.isMimeType("text/plain"))         
          out.print(message.getContent());
    %>
    </td>
  </tr>
</table>
<%	
   //关闭收件箱和到邮件服务器的连接
   inbox.close(true);
   store.close();
 }catch(MessagingException m) 
 { 
    out.println(m.toString()); 
 } 
%>
<p><center>
<a href="09_06.jsp?host=<%=host%>&user=<%=user%>&password=<%=password%>">返回收件箱<a>
</center></p>
</body>
</html> 
     