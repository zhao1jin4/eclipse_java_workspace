
<#-- 些部分在 <form>中       小于号的表示    &lt; lt  -->

<script type="text/javascript">
function goPage(pageNO)
{
	if(/^([0-9])+$/.test(pageNO))
	{	
		//$("form")[0].submit();
		document.getElementsByName('pageNO')[0].value=pageNO;
		document.getElementsByTagName("form")[0].submit();
	}else
		alert('请输入正整数');
}
</script>
<div>
	<span>
		<#if pageNO == 1>
				首页   &nbsp; 上一页    
		<#elseif pageNO &lt;=  pageCount> 
				<input type="button" onclick="goPage(1)" value="首页"/>  &nbsp;
				<input type="button" onclick="goPage(${pageNO-1})" value="上一页"/> 
		</#if>
	</span>
	&nbsp;
	<span>
		<#list -3..3 as i>
			<#if pageNO+i gt 0 && pageNO+i lte pageCount>
		 		<#if i==0>
		 			<font color="yellow" style="background-color:grey">&nbsp; ${pageNO} &nbsp;</font>
		 		<#else>
					&nbsp;  <a href="#" onclick="goPage(${pageNO+i})" > ${pageNO+i} </a>    &nbsp;
		 		</#if>
		 	</#if>
		 </#list>
	</span>
	&nbsp;
	<span>
		<#if  pageNO == pageCount> 
				下一页    &nbsp; 尾页
		<#elseif  pageNO lt pageCount>
			 	<input type="button" onclick="goPage(${pageNO+1})" value="下一页"/>   &nbsp;
			 	<input type="button" onclick="goPage(${pageCount})" value="尾页"/>
		</#if>
	</span>
	&nbsp;&nbsp;&nbsp;
	<span>
		到第<input type="text" name="pageNO" value="${pageNO}" size="2" maxlength="2" class="text ui-widget-content ui-corner-all" /> /${pageCount}页     &nbsp;
	  	 <input type="button" onclick="goPage(document.getElementsByName('pageNO')[0].value)" value="跳页">  &nbsp; 
	</span>
	&nbsp;
	<span>
		每页  <select name="pageSize" class="text ui-widget-content ui-corner-all" onchange="document.getElementsByTagName('form')[0].submit()" >
			<#list allPageSize as item> 	
				 <option value="${item}"  <#if item == PAGE_SIZE> selected="selected" </#if> >${item}</option>
			</#list>
			</select>条
	</span>
</div>
