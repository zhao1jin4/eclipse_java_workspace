<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 	这里是开发部
 	
 	<%
 		if(request.isUserInRole("manager"))
 			out.println("开发部经理");
 		else
 			out.println("程序员");
 	%>
</body>
</html>