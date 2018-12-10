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
 <fmt:message key="title"   /> <!-- spring MVC 中带参数var就不行了?? --><br/>
 <spring:message code="title" arguments="王2,张2"   />  <br/>

<%--  spring MVC 中没用???  
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="zh_CN"/>
 --%>
employee_query fmt:<fmt:message key="employee_query"  />  <br/>
employee_query spring : <spring:message code="employee_query"  /> <br/>


<form action="<%=request.getContextPath() %>/employee/submitQuery.mvc">
	<spring:message code="employee_id"/> :123 <br/>
	
	first_name ：<input type="text" name="first_name" value="王"/> <br/>
	otherParam ：<input type="text" name="otherParam" value="other"/> <br/>
	bootstrap daterangepicker <input type="text" name="createTimeRange" value="2017/01/25 12:12:12 - 2017/03/25  13:13:13"/>
	<input type="submit" /><br/>
</form>

<br/>

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