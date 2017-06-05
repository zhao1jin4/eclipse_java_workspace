<%@page  contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" 		uri="http://java.sun.com/jstl/fmt"%>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
 
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
 
 国际化<br/>
 <fmt:message key="title"/><br/>
 <spring:message code="title"/>

<%-- 
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="zh_CN"/>
没用???
  --%>
<fmt:message key="employee_query"/>
<br/>

<form action="">
	<spring:message code="employee_id"/><input type="text" name="byid" />
</form>


<a href="<spring:url value="/employee/xxxxxx.mvc"/>">新增加员工spring</a>  
<a href="<c:url value="/employee/xxxxxx.mvc"/>">新增加员工 c</a>
<table border="1">
	<tr>
		<td>ID</td>
		<td>姓名</td>
		<td>操作</td>
	</tr>
	<c:forEach items="${allEmployee}" var="emp">
		<tr>
			<td>${emp.employee_id}</td>
			<td>${emp.first_name}</td>
			<td><!-- 使用spring:url ,c:url /开头,会把项目名加上 -->
				<a href="<spring:url value="/employee/update.mvc?id=${emp.employee_id}"/>">修改</a> &nbsp; 
				<a href="<c:url value="/employee/delete.mvc?id=${emp.employee_id}"/>">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>


<hr/>


</body>
</html>