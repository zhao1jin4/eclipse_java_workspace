 
<html>
<head>
<title>Welcome!</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
message =
<#if message?exists> 
	${message}
 </#if>  

<br/>


animals 有
<u1> 
<#if animals?exists>
	<#list animals as being> 
	   <li>${being} <br>
	</#list> 
</#if>  
<u1>


<#if myDate?exists>
   ${myDate?string("yyyy-MM-dd HH:mm:ss")} <#-- FreeMarker自带显示的方法 -->
</#if>


</body> 
</html>