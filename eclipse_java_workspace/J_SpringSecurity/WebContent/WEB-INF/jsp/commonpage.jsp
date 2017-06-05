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
	<security:authorize ifAllGranted="ROLE_ADMIN,ROLE_USER"><!-- ifAnyGranted=""  ifNotGranted="" -->
		<a href="<%=request.getContextPath()%>/main/admin.mvc"> Go AdminPage </a><br />
	</security:authorize>
	
	<a href="<%=request.getContextPath()%>/auth/logout.mvc">退出登录</a>
	对应配置中logout logout-url="/auth/logout" 
	 
	 <br>
	 
	 
	 <br>开启   secured-annotations="enabled"  @Secure 要放在接口的方法上 <br />
	 <a href="<%=request.getContextPath()%>/main/test.mvc?method=getHello">getHello</a>  ROLE_USER可以 <br/>
	 <a href="<%=request.getContextPath()%>/main/test.mvc?method=setHello">setHello</a> ROLE_ADMIN可以 <br/>
	 <a href="<%=request.getContextPath()%>/main/test.mvc?method=initAdmin">initAdmin</a> ROLE_ADMIN可以 <br/>
	 <a href="<%=request.getContextPath()%>/main/test.mvc?method=other">other</a> ROLE_ADMIN可以 ,无效???????<br/>
	 
	  
</body>
</html>