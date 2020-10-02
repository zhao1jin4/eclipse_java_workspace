<html>
<head>
<title>Hello FreeMarker</title>
 
<#include "component/html_head.ftl"/>
 
	<style type="text/css">
		.mytext
		{
			background-color:grey;
		}
		.myselect 
		{
			background-color:yellow;
		}
	</style>
	<script type="text/javascript" src="${webRoot}/js/jquery-2.0.3.js"></script>
</head>
<body>
	在FreeMarker页中
	<hr/>
	国际化消息:<@spring.message "title"/> <BR/>
	<#assign seq = ['王1', '张1']> 
          带参数的国际化消息:<@spring.messageArgs "title", seq/> <BR/>
	带默认值的国际化消息:<@spring.messageText "_title_", "这是默认值"/> <BR/>
	<hr/>
	
<form method="POST" action="submitRegister.mvc">
	用  户  名:<@spring.formInput "form.username" 'class="mytext"'/> * <@spring.showErrors "<BR/>" "color:red" /> <br>
	密         码:<@spring.formPasswordInput  "form.password"/> <br>
	确认密码:<input type="password" name="confirm_password"  /> <br>
	出生日期:<@spring.bind "form.birthday" />
			<input type="text" name="${spring.status.expression}"  value="${spring.status.value}"> <br>
	年   龄:<@spring.formInput	 "form.age" 'size="2" maxlength="2"'/>	  <br>
	描   述:<@spring.formTextarea	 "form.remark" 'rows="6" cols="30"' />	  <br>
	<#assign genders =  {"T":"男","F":"女"} >
	性   别:<@spring.formRadioButtons "form.gender" genders "<br/>"/>	  <br> <!-- 数据必须是Map<String,String>才行,gender的属性可以是boolean-->
		<@spring.formSingleSelect "form.gender" genders /> * <@spring.showErrors "<BR/>" "color:red" /> <br/>
		
	<#-- 
	HTML所在班级:<select name="clazz_id" >
				<#list allClazz?keys as key> 
					<option value="${key}">${allClazz[key]}</option>
				</#list>
			</select>  <br>
	-->		
	Freemarker所在班级 :<@spring.formSingleSelect "form.clazz_id" allClazz />  <br><!--clazz_id的属性可以是int -->
	
<@spring.formHiddenInput "form.id" /> <br>

<#-- 
	List 没有实现  Serializable,测试验证时多选的验证无效
-->	
	兴趣爱好:<@spring.formCheckboxes	 "form.interest_ids" allInterests "<br/>" 'class="mytext"'/>  <br>
	多选 选修课程 :<@spring.formMultiSelect "form.course_ids" allCourses 'class="myselect"'/>  <br>
	
	<br/>
	
	<#list otherData as item> 
	 	<#if item_has_next>　 
		 	正常数据行
		 <#else>
			在最后一行
		 </#if>
		 ,
		 <#if item_index % 2=0> 
			在偶数行
		<#else>
			在奇数行
		</#if>
		${item.value} = ${item.label}<br/>
	</#list>
	
	<br/>
		
	<input type="submit" value="提交" name="B1"> 
</form>

</body>
</html>
