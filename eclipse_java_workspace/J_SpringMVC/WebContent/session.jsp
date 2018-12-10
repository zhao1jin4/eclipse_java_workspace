<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.net.InetAddress" %>
<html>
<body>
<h2>Hello World!</h2>

<%
out.println("ip="+InetAddress.getLocalHost().getHostAddress()+"port="+request.getLocalPort());
out.println("\r\n <br/> session id=" +request.getSession().getId());
%>
<br/>
login_user:  ${sessionScope.login_user}

</body>
</html>
