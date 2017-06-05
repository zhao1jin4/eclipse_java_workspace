<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/md5.js"></script>
<script type="text/javascript">
function   generateMd5()
{
	var txtPasswd=document.getElementById("password").value;
	document.getElementById("md5Password").value=hex_md5(txtPasswd)  ;
	return true
}

</script>
</head>
<body>

	<a href="sessionForm.jsp">基本Servlet 测试</a> <br/>
	<a href="tag_jstl.jsp">自定义标签__JSTL</a> <br/>
	<a href="async">Async Servlet 测试</a> <br/>
	<a href="nowebxml">Servlet 3 no web.xml 测试,要建立.jar/META-INF/services</a> <br/>
	<a href="cookieServ">Cookie 测试</a> <br/>
	<a href="download">download 测试</a> <br/>
	<a href="loginWithValidateCode.jsp">验证码 测试</a> <br/>
	<a href="security/develop/index.jsp">develop 部门安全测试</a> <br/>
	<a href="security/market/index.html"> market 部门 安全测试</a> <br/>
	<a href="devIndex"> Dev Servlet  安全测试试</a> <br/>
	
	<a href="crossContext.jsp"> Tomcat crossContext 测试</a> <br/>
	<a href="webSocket.html">webSocket 测试   ,Tomcat 8 才支持  </a> <br/>
	<a href="referer">测试盗链</a> <br/>
	
	<hr/> 第三方库  <br/>
	
	<a href="fileUpload.html">fileUpload  测试</a> <br/>
	<a href="jfreechart.jsp">jfreechart  测试</a> <br/>
	<a href="freemaker">Freemaker 测试</a> <br/>
	
	<a href="tablePageServlet.ser?action=init">Display Tag</a> <br/>
	
	<a href="hessian/client">hession 二进制做webservice </a> <br/>
	
	<a href="sessionCluster.jsp">Tomcat Cluster Session 测试  </a> <br/>
	
	<form action="/J_JavaEE/md5Servlet" onsubmit="return generateMd5() " method="get">
		password 	 <input type="text" name="password" id="password"   /> <br/>
		MD5 password <input type="text" name="md5Password"		id="md5Password"/> <br/>
		<input type="submit">
	</form>
	
	
</body>
</html>