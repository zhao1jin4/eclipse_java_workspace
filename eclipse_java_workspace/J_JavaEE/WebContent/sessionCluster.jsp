<%@page import="java.util.Enumeration"%>
<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	要在web.xml中加  <distributable/> <br/>
	
	context:${pageContext.request.contextPath} <br>
	 server info : ${pageContext.request.localAddr}:${pageContext.request.localPort} <br>
    <br>
    session ID : ${pageContext.session.id} <br>
    <br>
    
    <%
   
    
    String type=request.getParameter("type");
    String key=request.getParameter("key");
    String value=request.getParameter("value");
    
   
 
    if("add".equals(type))
    {
    	session.setAttribute(key,value);
    	
    }else if("del".equals(type))
    {
    	session.removeAttribute(key);
    }
    
    Enumeration i=session.getAttributeNames();
    while(i.hasMoreElements())
    {
    	String name=i.nextElement().toString();
    	out.println( "<br/>Key="+name+",value="+session.getAttribute(name));
    }
   
    System.out.println("Sesion Cluster test__receive request ：key="+key);
    
    %>
     
    <br>
             添加：
	<form action="sessionCluster.jsp" method="post">
			<input type="hidden" name="type" value="add" />
    	key:<input type="text" name="key">value:<input type="text" name="value"><input type="submit" value="添加">
    </form>
             移除：
    <form action="sessionCluster.jsp" method="post">
    		<input type="hidden" name="type" value="del" />
    	key:<input type="text" name="key"><input type="submit" value="移除">
    </form>
    
    
    
    
</body>
</html>