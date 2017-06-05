<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.beans.factory.BeanFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
 import=" org.springframework.web.context.WebApplicationContext,activemq_web.Sender"%>
  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletConfig().getServletContext());
Sender send=(Sender)ctx.getBean("sender");//要在Web里运行,才会使用Tomcat的配置
send.send("hello");
 %>
</body>
</html>