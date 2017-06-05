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

	<form action="<%=request.getContextPath()%>/j_spring_security_check" method="post">

		<p>
			<label for="j_username">Username</label> <input id="j_username" name="j_username" type="text" />
		</p>
		<p>
			<label for="j_password">Password</label> <input id="j_password" name="j_password" type="password" />
		</p>

		<input type="submit" value="Login" />

	</form>

</body>
</html>