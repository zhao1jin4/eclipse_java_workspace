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
	
	<hr/> ------------Cookie 测试  <br/>
	cookie.website = 
	<c:if test="${cookie.website != null}" >
		 ${cookie.website.value}  <br/>
	</c:if>
	
	
	<hr/> ------------ session 测试 ,禁用Cookie <br/>
	<%
		//禁用Cookie,IE中tools->internet options->privacy->advanced->选中override automatic cookie handling,再选两个block
		//对localhost无效,要使用本机IP
		out.println("sessionObj:"+session.getAttribute("sessionObj"));
		out.println("<BR/> 是否新会话:"+session.isNew());
		out.println("<BR/> ID="+session.getId());
	%>
	
	
</body>
</html>