<%@ page  isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	Servlet 4 <br/>
	<img src="img/bing.png"/>
	<br/>
	js生成MD5
	<form action="/J_JavaEE/md5Servlet" onsubmit="return generateMd5() " method="get">
		password 	 <input type="text" name="password" id="password"   /> <br/>
		MD5 password <input type="text" name="md5Password"		id="md5Password"/> <br/>
		<input type="submit">
	</form>
</body>
</html>