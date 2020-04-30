<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Freemarker Test</title>
<script src="jquery-3.5.0.min.js"></script>
<script>
$(function (){

	$("#searchBtn").click(function (){
		alert('oh clicke me ');
	});
});
</script>
</head>
<body>

welcom:${user} <br/>



<img src='search.jpg' id="searchBtn"/>

<br/>

<#if allUser?exists>
	<#-- _has_next , _index-->
	<#list allUser as item> 
		<#if item_has_next> 
			正常数据行
		 <#else>
			在最后一行
		 </#if>
		 ,
		 <#if item_index % 2 == 0>
			在偶数行
		<#else>
			在奇数行
		</#if>
		,${item.username}<br/>
	</#list>
</#if>

<BR/>
国际化消息:<@spring.message "try"/> <BR/>
<#assign seq = ['王', '2018']> 
带参数的国际化消息:<@spring.messageArgs "welcome", seq/> <BR/> 


</body>
</html>
