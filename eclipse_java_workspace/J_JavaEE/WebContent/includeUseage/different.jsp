<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--@include file="one.jsp" --%> <!--指令, 先引入文件再编译,可能有变量名重定义的错误 -->
<jsp:include page="one.jsp"></jsp:include><!--动作 , 引入文件的执行后的静态结果 -->
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
<%
	int one=11;//使用<%@include会报错
%>
</body>
</html>