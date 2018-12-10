<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>

	<h1>Login</h1>
	 
	<div style="color:red"> ${error} </div>
<a href="<%=request.getContextPath()%>/main.jsp">main </a> <br/>
	<form action="<%=request.getContextPath()%>/login.ser" method="post">
		<section>
			<label for="j_username">Username</label> <input  name="j_username" type="text" />
		</section>
		<section>
			<label for="j_password">Password</label> <input  name="j_password" type="password" />
		</section>
		<section>
			<label for="j_rememberMe">remeber me?</label> <input  name="j_rememberMe" type="checkbox" />
		</section>
		<section>
			<input type="submit" value="Login" />
		</section>
	</form>

</body>
</html>