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
	<br/>
	
	 
	解析数据库comment字段格式  姓名|{"type":"date","query":true,"show":true} 
	<br/>
	<#assign dbComment="姓名|{\"type\":\"date\",\"query\":true,\"show\":true}">
	result:${(freemarkerBean.parseComment(dbComment))!}  <br/>
	result2:${(freemarkerBean.parseComment(dbComment)).comment} <br/>
	<#assign com=freemarkerBean.parseComment(dbComment)>
	result3: ${com.comment} <br/>
	 freemarker调用java状态方法 <br/>
	currentTimeMillis：  ${statics["java.lang.System"].currentTimeMillis()} <br/>
	result4： ${statics["spring_freemarker.FreemarkerUtil"].parseComment(dbComment).comment} br/> 
	
	特殊变量前加. 版本=${.version} <br/>
	eval  : ${"1+2"?eval} <br/> 
	<#assign text="{'name':'lisi','age':'30+','addr':'上海'}" />
	<#assign data=text?eval /> 
	eval json res:${data.name} <br/>
	
	  
	<#if "abc|123"?contains("|")>
		split:${"abc|123"?split("|")[0]}
	</#if>
	 <br/>
	
	<#function parseComment dbComment>
		<#if dbComment?contains("|")>
			<#return dbComment?split("|")[0]>  
		<#else>
			<#return dbComment>
		</#if>
	</#function>
	<#function parseType dbComment>
		<#if dbComment?contains("|")>
			<#return dbComment?split("|")[1]>  
		<#else>
			<#return ''>
		</#if>
	</#function>
	
	<#assign myComment=	"姓名|{'type':'date','query':true,'show':true}">
	<#-- 
	"姓名|{type:'date',query:true,show:true}"
	"姓名|{'type':'date','query':true,'show':true}"
	"姓名|{\"type\":\"date\",\"query\":true,\"show\":true}" -->
	 函数parseComment的结果：${parseComment(myComment)}  <br/>
	 函数parseType 的结果：${parseType(myComment)}   <br/>
	 函数parseType的json_string 结果：${parseType(myComment)?json_string}  <br/>
	 <#import "function.ftl" as func>
	 <#assign myJson=func.parseType(myComment)?eval>
	 函数parseType的 json eval 结果：${myJson.type}  <br/> 
	
	<#--
	<#assign spStatic= "new  freemarker.ext.beans.BeansWrapperBuilder(freemarker.template.Configuration.VERSION_2_3_21).build().getStaticModels()"?eval>
	不行的
	 -->
 
</body>
</html>

