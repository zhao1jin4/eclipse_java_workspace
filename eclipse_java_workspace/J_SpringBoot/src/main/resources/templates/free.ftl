<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Freemarker Test</title>
<script src="jquery-3.2.1.min.js"></script>
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
</body>
</html>
