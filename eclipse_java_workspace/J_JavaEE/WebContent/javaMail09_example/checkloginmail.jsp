<html>
<head>
<title>��½�ʼ���������֤</title>   
</head>
<%@ page contentType="text/html;charset=GB2312"%>
<%@ page import="javax.mail.*,java.util.*"%>
<body bgcolor="#CFF1E1">
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
   store.connect("pop."+host,user,password);
   //�����������ŵ�Session�б���������������ҳ��ʹ��
   session.setAttribute("host",host);
   session.setAttribute("user",user);
   session.setAttribute("password",password);
   response.sendRedirect("listlogin.html");  
  }
  catch(MessagingException m) 
  { 
      out.println(m.toString()); 
      out.println("<p>��½�ʼ�������ʧ�ܣ�<a href='loginmail.html'>�뷵��</a></p>");
  }  
%>
</body>
</html>
 
  