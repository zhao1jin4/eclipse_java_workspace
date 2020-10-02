<%@page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%//isErrorPage="true"
	//response.setStatus(HttpServletResponse.SC_OK);
%> 
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Failure  

<% 
Exception ex = (Exception)request.getAttribute("exception") ;//只对defaultErrorView指定的有值
if(ex!=null)//这里有值的
{
	System.out.println("ex："+ex.toString());
	out.println("ex："+ex.toString());
}

%>
</body>
</html>