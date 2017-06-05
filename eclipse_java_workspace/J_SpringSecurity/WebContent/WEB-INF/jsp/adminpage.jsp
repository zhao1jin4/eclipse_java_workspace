<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Admin Page</h1>
	<p>管理员页面</p>
	
	<a href="<%=request.getContextPath()%>/auth/logout.mvc">退出登录</a> <br/>
	<a href='<spring:url value="/auth/logout.mvc" />'>退出登录</a>
</body>
</html>