<html>
<head>
<title>Hello FreeMarker</title>
<#include "component/html_head.ftl"/>

<script type="text/javascript">
$(function() 
{
	$( "#myDiv" ).html("jQuery init");
});
 function changeDIV()
 {
 	$( "#myDiv" ).html("onclick 修改后");
 }
 </script>
 
</head>
<body>
	在FreeMarker页中
	<hr/>
	国际化消息:<@spring.message "title"/> <BR/>
	现在的时间是：${now} <BR/>
	数字取消显示","  : ${num?string('#')}   -或者 -  <#setting number_format="#" /> ${num}
	
	<br/>
	<div id="myDiv">init </div>
	<form action="freemarkerQuery/initQuery.mvc">
		<button onclick="changeDIV()">未加 type 的button 在 form中的<button>单击会提交表单 </button>
		
		<button type="button" onclick="changeDIV()">type="button" </button>
	</form>
</body>
</html>

