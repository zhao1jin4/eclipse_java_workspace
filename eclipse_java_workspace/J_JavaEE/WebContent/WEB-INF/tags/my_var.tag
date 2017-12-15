<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@tag pageEncoding="UTF-8" body-content="scriptless" dynamic-attributes="numColumn"%>
<%@ attribute name="great" fragment="true" %>
<%@ attribute name="less" fragment="true" %>
<%@ variable name-given="sum" variable-class="java.lang.Integer" %>
<%--
<%@variable name-from-attribute="var" alias="result" %>
 --%>
<% 
	//.tag中有jspContext隐含对象,是PageContext的基类,没有page,pageContext,exception,其它同JSP
	jspContext.setAttribute("myAttr","test123");
%>


<c:if test="${not empty numColumn}">
	<c:forEach items="${numColumn}" var="num">
		<c:set var="sum" value="${num.value + sum}" />
	</c:forEach>
	<c:if test="${sum >= 1000}" >
		<jsp:invoke fragment="great" />
	</c:if>
	<c:if test="${sum < 1000}" >
		<jsp:invoke fragment="less" />
	</c:if>  
</c:if>



 
 
 