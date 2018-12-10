<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page import="org.apache.shiro.SecurityUtils" %>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Query</title>
</head>
<body>

	<h1> Query </h1>
	
	<shiro:hasPermission name="employee:delete">
 	 	<a href="<%=request.getContextPath()%>/employee/delete.mvc">delete employee ,Permission employee:delete</a> <br/>
	</shiro:hasPermission>
	<shiro:hasRole name="adminRole">
	 	<a href="<%=request.getContextPath()%>/employee/create.mvc">add new employee , adminRole</a> <br/>
	</shiro:hasRole>
	
 
 
</body>
</html>