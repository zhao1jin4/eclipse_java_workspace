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