<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>你的权限不够!</h1>
	<p>只有拥有Admin权限才能访问!</p>
	<a href="<%=request.getContextPath()%>/auth/logout.mvc">退出登录</a>
</body>
</html>