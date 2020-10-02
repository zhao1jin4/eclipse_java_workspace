<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@page import="org.apache.shiro.SecurityUtils" %>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Main</title>
</head>
<body>

	<h1>首页不用登录 </h1>
		<shiro:guest>
			你没有登录，点击 <a href="<%=request.getContextPath()%>/login.mvc">这里</a> 登录
		</shiro:guest>  <br/>
		
		<a href="<%=request.getContextPath()%>/initLoginNoFilter.mvc">登录不使用Filter</a>   <br/>
		
		<a href="<%=request.getContextPath()%>/clearCache.mvc">clear cache</a> <br/>
		
		<shiro:authenticated>authenticated</shiro:authenticated>  <br/>
		<shiro:notAuthenticated>notAuthenticated</shiro:notAuthenticated>  <br/>
		
	 ${request.contextPath} 用法错误<br/>
	 权限没有缓存，每次都重新取数据？？？<br/>
	<a href="<%=request.getContextPath()%>/employee/create.mvc">employee create </a> <br/>
 	<a href="${pageContext.request.contextPath}/employee/query.mvc">employee query </a> <br/>
	<a href="${pageContext.request.contextPath}/employee/delete.mvc">employee delete </a> <br/>
	<a href="${pageContext.request.contextPath}/logout.mvc">logout   </a>  <br/>
	
	Hello, <shiro:principal/> <br/>

<shiro:authenticated> <!-- 如不加 进入页时 报nullpoint -->
	Hello, <shiro:principal type="shiro_main.UserInfo" property="fullName"/>  相当于取session对象的属性名 	<br/>
</shiro:authenticated>
	
	Hello, <%= SecurityUtils.getSubject().getPrincipal()  %> <br/>
	
	<shiro:hasPermission name="employee:delete">
 	 	<a href="<%=request.getContextPath()%>/employee/delete.mvc">delete employee ,Permission employee:delete</a> 
	</shiro:hasPermission>
	<shiro:lacksPermission name="employee:delete">
 		你没有 employee:delete 权限
 	</shiro:lacksPermission>
 	<br/>
	<shiro:hasRole name="adminRole">
	 	<a href="<%=request.getContextPath()%>/employee/create.mvc">add new employee , adminRole</a> <br/>
	</shiro:hasRole>
	
	<hr/>
	spring session : ${sessionScope.mySessionAttr}
 
</body>
</html>