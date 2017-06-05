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

    <fieldset><legend>用户信息</legend>   
    <ul>   
        <li><label>用户名:</label><c:out value="${account.username}" /></li>   
         
		<li><label>第二个form的邮件值是:</label><c:out value="${email}" /></li>   
    </ul>   
    </fieldset>  
    <!-- 
 RedirectAttributes 的值 :   ${myTestredirectAttr }  只3.1 ???
 -->
 
<hr/>

<br/>
request,ModelMap中的sessionAttr的requestScope值 是:${requestScope.sessionAttr}
<br/>
request,ModelMap中的sessionAttr的sessionScope值 是:${sessionScope.sessionAttr}
<%
Object inReq=request.getAttribute("sessionAttr");
Object inSes=session.getAttribute("sessionAttr");
System.out.println("in Requset scope:"+inReq);
System.out.println("in Session scope:"+inSes);
%>

<br/>
sessionEmp  :${sessionScope.sessionEmp} <br/>
sessionEmp 的last_name:${sessionScope.sessionEmp.last_name}
 
 
</body>
</html>