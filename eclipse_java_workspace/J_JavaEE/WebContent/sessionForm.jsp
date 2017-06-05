<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true" isThreadSafe="true" errorPage="/error.jsp" autoFlush="true" buffer="8kb" isELIgnored="false"
 %> <!-- buffer用于out对象 -->
 
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:useBean id="my" class="myservlet.MySessionUser" scope="request" >
</jsp:useBean>
<jsp:setProperty property="*" name="my"/> <!-- * 表示请求的参数的名与Bean的属性名对应 ,自动传入-->
<jsp:setProperty property="name" name="my" value="lisi"/> 
name的值是:<jsp:getProperty property="name" name="my"/>

<%--
<jsp:forward page=""></jsp:forward> //同response.getDispatcher.forward
 --%>
<%!
//!表示声明变量,在其它地方可以使用
%>

<%
	config.getInitParameter("debbug");//ServletConfig
	page.getClass().getName();//Object
	pageContext.getELContext();//PageContext
	application.getAttribute("user");//ServletContext
	//request,response,session,out,exception
	
%>
<!-- 如有禁用Cookie使用response.encodeURL("");会自动加jsessionid的参数 ,危险可以把链接发给其它去点-->
	<form action="<%=response.encodeURL("life")%>"  method="post" > 
		username:<input type="text" name="username" />	<br/>
		<input type="submit" value="提交" />	<br/>
	</form>
</body>
</html>