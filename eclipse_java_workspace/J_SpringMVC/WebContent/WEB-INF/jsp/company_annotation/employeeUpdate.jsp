<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" 		uri="http://java.sun.com/jstl/fmt"%>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<br/>
 员工的修改
 <form action="update.mvc" method="post">
	 <input type="hidden" name="employee_id" value="${emp.employee_id}"/>
 	<input type="text" name="first_name"  value="${emp.first_name}"/>
 	<input type="submit" value="提交">
 </form>
 
 
</body>
</html>