<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Other Controller中默认规则, return Object
<br/>
单个类:${employee.first_name}
<br/>
集合:<br/>
<c:forEach items="${employeeList}" var="item">
	<c:out value="${item.first_name}"/>,<c:out value="${item.last_name}"/> <br/>
</c:forEach>

Spring Map:<br/>
<c:out value="${myKey1}"/> 
</body>
</html>