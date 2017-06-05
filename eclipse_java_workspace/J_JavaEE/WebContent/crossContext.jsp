<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:redirect url="/portal" context="/pluto"> <!--Tomcat的 <Context 加 crossContext="true" -->
	</c:redirect> <!-- 后面的不会被执行 -->
	
	c:redirect后面的内容这个不会被显示
</body>
</html>