<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 
</head>
<body>
	<h1>Common Page</h1>
	<p>每个人都能访问的页面.</p>
	
	welcome :<security:authentication property="name" /> <!--得到登录使用的用户名  --> <br/>
	
	loginNotify session_key: ${sessionScope.session_key} <br/>
	<!-- access="hasPermission(#domain,'read') or hasPermission(#domain,'write')" -->
	<security:authorize access="hasRole('ROLE_ADMIN')">    
	
		<a href="<%=request.getContextPath()%>/main/admin.mvc"> Go AdminPage </a><br />
	</security:authorize>
	
	
	对应配置中 logout-url="" 不用.mvc,不用自己定义这个Controller,POST提交
	<form method="post" action="<%=request.getContextPath()%>/securityLogout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" value="退出登录提交"/>
	</form>
	 
	 <br>
	 
	 
	 <br>开启   secured-annotations="enabled"  @Secure 要放在接口的方法上 <br />
	 <a href="<%=request.getContextPath()%>/main/test.mvc?method=getHello">getHello</a>  ROLE_USER可以 <br/>
	 <a href="<%=request.getContextPath()%>/main/test.mvc?method=setHello">setHello</a> ROLE_ADMIN可以 <br/>
	 <a href="<%=request.getContextPath()%>/main/test.mvc?method=initAdmin">initAdmin</a> ROLE_ADMIN可以 <br/>
	 <a href="<%=request.getContextPath()%>/main/test.mvc?method=other">other</a> ROLE_ADMIN可以 ,无效???????<br/>
	 
	  <a href="main/session.mvc">spring session redis and security(use ngnix Round Robin)ROLE_USER可以 (没有使用外部系统登录)  测试成功</a> <br/>
	
</body>
</html>