<html>
<head>
<title>查询</title>

<#include "component/html_head.ftl"/>

<style type="text/css" >
	fieldset {  border:0; }
	/*label, input { display:block; }
	 label { float:left; }
      */
    #accordion {
		width: 250px;
	}
	#accordion > div{
		padding: 1px;
	}
	/*.ui-menu { width: 250px; } */
	
</style>
<script type="text/javascript">
	$(function() {
		$( "#accordion" ).accordion({
			heightStyle: "content"
		});
		$( "#menuConfig" ).menu();
		$( "#menuChange" ).menu();
		
		$( "#birthday" ).datepicker({dateFormat: "yy-mm-dd"});
		//滚动条动时图像也动
		$( window ).scroll(function() {
			 if($("#loadingImage").css("display") == "block")
			 {
			 		var vtop = (document.body.clientHeight  - $("#loadingImage").height())/2; 
					var vleft = (document.body.clientWidth - $("#loadingImage").width())/2; 
					var sTop = $(document).scrollTop(); 
					var sLeft = $(document).scrollLeft(); 
					$("#loadingImage").offset( { top: vtop+sTop , left: vleft+sLeft});
			 }
		});
	});
	//window.onscroll = function(){ }
	
	function showPopupWindow(parentId)
	{
		//----加不可操作背景
		var bgGreyDiv=$("<div></div>");
		bgGreyDiv.css("z-index","2").css("position","absolute").css("background-color","grey").css("opacity",0.2)
		.css("width",$(document).width()).css("height",$(document).height());
		$(document.body).append(bgGreyDiv);
		bgGreyDiv.offset( { top: 0, left:0});
		 
		/* 
		var bgGreyDiv=$("<div style='background:grey;opacity:0.2;z-index:2;position:absolute;left:0;top:0'></div>");
		bgGreyDiv.css("width",$(document).width()).css("height",$(document).height());
		$(document.body).append(bgGreyDiv);
		*/
		
		//----屏幕中心
		$("#loadingImage").css("z-index","3").css("position","absolute");
		var vtop = ( document.body.clientHeight  - $("#loadingImage").height())/2; 
		var vleft = (document.body.clientWidth - $("#loadingImage").width())/2; 
		var sTop = $(document).scrollTop(); 
		var sLeft = $(document).scrollLeft(); 
		$("#loadingImage").offset( { top: vtop+sTop , left: vleft+sLeft});
		$("#loadingImage").css("display","block");
		//----
		/*
		//一个页面中不能有两次 jQuery.js的引入,
		$.ajax
		({
			type:"GET",
			url:'${webRoot}/freemarkerRegister/initRegister.mvc',
			async:false,//是否异步
			//data:"id="+parentId,
			//dataType:"xml",
			success:function (data)
			{
				$("#dialog").html(data);
			},
			error:function(err){
				alert(err.responseText);
			}
		});
		*/
		 $("#dialog").html("非ajax");
		$("#loadingImage").css("display","none");
		bgGreyDiv.css("display","none");
		$("#dialog").css("display","block");
		$("#dialog").dialog({
				height: 600,
				width: 1024,
				modal: true
				});
		//window.open('${webRoot}/freemarkerRegister/initRegister.mvc' ,'','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=750,height=470,left=80,top=40');
	}
	
	function goURL(url)
	{
		//window.location=url;
		window.location.href=url;
	}
		
</script>
</head>
<body>
 
 <div id="accordion" >
		<h3> 系统配置 </h3>
		<div  >
			 <ul id="menuConfig">
				<li><a href="#"> A 管理</a></li>
				<li><a href="#"> A 管理</a></li>
			</ul> 
		</div>
		<h3> 系统修改 </h3>
		<div>
			 <ul id="menuChange">
				<li><a href='#'> 修改 A 状态  </a></li>
				<li><a href='#'> 修改 B 状态  </a></li>
			</ul> 
		</div>
	</div>
	
	
<div id="dialog" title="弹出窗口" style="display:none" > 
</div>
<image src="${webRoot}/img/loading.gif" id="loadingImage"  style="display:none"   /> 
<!-- GET 和  POST 都OK  -->
<form method="POST" action="submitQuery.mvc"   >    
 <#--
<fieldset>
	<label for="username">username:</label>
	<@spring.formInput "form.username" " class='text ui-widget-content ui-corner-all'" /> 

	<label for="gender">gender:</label>
	<@spring.formInput "form.gender" "size='1' maxlength='1'  class='text ui-widget-content ui-corner-all'"  />
</fieldset>
-->

<#assign allGender =  {"":"--所有--","M":"男","F":"女"} >

<table border="1" align="center" width="1000" bgcolor="yellow">
	<tr>
		<td align="right" >username:</td>
		<td width="150px"> <@spring.formInput "form.username" 'class="text ui-widget-content ui-corner-all"'/> </td>
		<td align="right">birthday:</td>
		<td> <@spring.formInput "form.birthday"  'readonly="readonly" size="10" class="text ui-widget-content ui-corner-all"'/>  <!--disabled='disabled'--></td>
	

		<td align="right">gender:</td>
		<td>
			<@spring.formSingleSelect "form.gender" allGender 'class="text ui-widget-content ui-corner-all" ' />
		</td>
		<#-- <@spring.formHiddenInput "pageNo" /> -->
		<td colspan="4" align="center"><input type="submit" value="查询"/><input type="reset" value="重置"></td>
	</tr>
</table>
 
<br/>

<table border="1" align="center" width="1000">
	<thead>
		<tr  class="ui-widget-header ">
			<td>ID</td>
			<td>username</td>
			<td>age</td>
			<td>birthday</td>
			<td>gender</td>
			<td>remark</td>
			<td >操作</td>
		</tr>
	</thead>
	<tbody>
		<#if allData?exists> 
		<#list allData as item> 
		 	<#if item_index % 2 == 0> 
				<tr bgcolor="white">
			<#else>
				<tr bgcolor="#eeeee">
			</#if>
			<td>${item.id}</td>
			<td>${item.username!"&nbsp;"}</td>
			<td>${item.age}</td>
			<td>${item.birthday?string('yyyy-MM-dd')}</td>
			<td> 
				<#if item.gender == 'M'> 男
				<#elseif item.gender == 'F'>女
				<#else>未知
				</#if>
				--
				<#switch item.gender> 
					<#case "M">男<#break> <!--可以是字串 -->
					<#case "F"> 女<#break>
					<#default> 未知
				</#switch>
			</td>
			<td>${item.remark!""}</td>
			<td> 
				<button type="button" onclick="showPopupWindow('initEdit?id=${item.id}')" >弹出窗口 </button>
				<button type="button" onclick="goURL('initEdit?id=${item.id}')" >修改配置 </button>
			 </td>
		</tr>
		</#list>
		</#if>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7" align="center"  bgcolor="yellow">
				<#include "component/page.ftl">
			</td>
		</tr>
	</tfoot>
</table>
</form> <#-- form在这终止是为了 翻页时也把查询条件带上  -->

	
</body>
</html>