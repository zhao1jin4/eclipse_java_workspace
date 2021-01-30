<%@ page  isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body> 
<%
//request.getRequestDispatcher("http://www.baidu.com").forward(request, response); //forward不能跨域，报404
response.sendRedirect("http://www.baidu.com");//redirect 可以跨域
%>
</body>
</html>