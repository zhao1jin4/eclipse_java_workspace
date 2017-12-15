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

    <fieldset><legend>登录</legend>
 
    <spring:bind path="accountForm.*">
		<c:if test="${status.error}">
			<font color="#FF0000">所有的错误:
				<c:forEach items="${status.errorMessages}" var="item">
					<c:out value="${item}"/><br/>
				</c:forEach>
			</font>
		</c:if>
	</spring:bind>	
	
	
	
    <%--
    <form:form modelAttribute="accountForm" > 和
    <form:form commandName="accountForm">   都 OK--%>   
    <form:form action="submitLogin.mvc" modelAttribute="accountForm" >
        <form:hidden path="id" />   
        <ul>   
        
        <%--  
	        <spring:bind path="username" >
			用户名: <input type="text" name="<c:out value="${status.expression}"/>" value="${status.value}" > <br>
			</spring:bind>
		 --%>  
            <li>
            	<form:label path="username">用户名:</form:label>
            	<form:input   path="username" /> <form:errors path="username"/>
            </li> 
           
            <li>
            	<form:label path="password">密码:</form:label>
            	<form:password    path="password" />
            </li>
            <li>
            	<form:label path="rePassword">确认密码:</form:label>
            	<form:password    path="rePassword" />
            </li>
             <li>
            	<form:label path="month">月份:</form:label>
            	<form:select path="month" items="${monthsNames}"></form:select> <!-- 可带回值的 -->
            	测试显示
            	<select>
					<c:forEach items="${monthsNames}" var="month" >
						<option  value="${month.key}">${month.value} </option>
					</c:forEach>
				</select>  
            </li>
          <li>
         	技能: <form:checkboxes items="${allSkills}"  path="skills" />
          </li>  
          
             <li>
            	<form:label path="age">年龄:</form:label>
            	<form:input    path="age" />
            </li> 
            <li>   
	            <button type="submit">登录</button>   
	            <button type="reset">重置</button>   
            </li>   
        </ul>
        
        	表单中的错误:<form:errors path="*"  />
        	
    </form:form></fieldset>  
	
	  <spring:bind path="emailSendForm.*">
		<c:if test="${status.error}">
			<font color="#FF0000">所有的错误:
				<c:forEach items="${status.errorMessages}" var="item">
					<c:out value="${item}"/><br/>
				</c:forEach>
			</font>
		</c:if>
	</spring:bind>	
	
	  <form:form action="emailSend.mvc"  modelAttribute="emailSendForm" >
	 	 <form:label path="email">邮件:</form:label>
          <form:input    path="email" /> 
          
          <br/>
          <button type="submit">登录</button>   
	  </form:form>

	  
</body>
</html>