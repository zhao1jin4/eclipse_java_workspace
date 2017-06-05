<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<!-- commandName modelAttribute  -->
<form:form action="submitUpload.mvc" method="post" modelAttribute="uploadForm" enctype="multipart/form-data">
													　
	image:<input type="file" name="img"><BR>
	photo1:<input type="file" name="photos"><BR>
	photo2:<input type="file" name="photos"><BR>
	DES:<input type="text" name="description">
	Date <input type="text"  name="birthday" value="2014-11-11"/>
	<input type="submit" value=" 提交"/>
</form:form>

<br/>asyncUpload <br/>
<form:form action="asyncUpload.mvc" method="post"   enctype="multipart/form-data">
	image:<input type="file" name="img"><BR>
	<input type="submit" value=" 提交"/>
</form:form>

<br>
<spring:bind path="uploadForm.*">
	<c:forEach items="${status.errorMessages}" var="item">
	${item}
	</c:forEach>
 </spring:bind>
 
 
 
</body>
</html>