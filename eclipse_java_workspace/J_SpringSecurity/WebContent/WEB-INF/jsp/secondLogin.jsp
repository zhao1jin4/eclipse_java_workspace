<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>
	<h1>second Login</h1>
	可能没用error:${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}<br/>
	<a href="<%=request.getContextPath()%>/index.html">index.html</a> <br/> 	
</body>
</html>