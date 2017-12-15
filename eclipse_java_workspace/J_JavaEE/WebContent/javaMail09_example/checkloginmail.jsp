<html>
<head>
<title>登陆邮件服务器验证</title>   
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*"%>
<body bgcolor="#CFF1E1">
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
   store.connect("pop."+host,user,password);
   //将三个参数放到Session中保存起来，供后续页面使用
   session.setAttribute("host",host);
   session.setAttribute("user",user);
   session.setAttribute("password",password);
   response.sendRedirect("listlogin.html");  
  }
  catch(MessagingException m) 
  { 
      out.println(m.toString()); 
      out.println("<p>登陆邮件服务器失败，<a href='loginmail.html'>请返回</a></p>");
  }  
%>
</body>
</html>
 
  