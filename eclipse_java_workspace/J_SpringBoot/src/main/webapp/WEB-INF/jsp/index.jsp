<%@page contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
hello in index.jsp
<br/>
<c:out value="c_cout_hello"/>
<br/>

${msgFromRedis} <br/>
国际化： <spring:message code="welcome" arguments="小王,2018"   /> <br/>
 提示：<spring:message code="try"    /> <br/>
</body>
</html>


