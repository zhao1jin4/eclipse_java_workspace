<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Login</h1>
	SpringSecurity中的错误:${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message} <br/>
	<div id="login-error">从Controller中传来的错误:${error}</div>
	 <!--  默认三个参数名是username,password,remember-me   提交默认 /login-->
	<form action="<%=request.getContextPath()%>/j_spring_security_check" method="post">
		<section>
			<label for="j_username">Username</label> <input   name="j_username" type="text" />
		</section>
		<section>
			<label for="j_password">Password</label> <input   name="j_password" type="password" />
		</section>
		<section>
			<label for="j_remember-me">Remember me</label> <input  name="j_remember-me" type="checkbox" />
		</section>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

		<input type="submit" value="Login" />

	</form>

</body>
</html>