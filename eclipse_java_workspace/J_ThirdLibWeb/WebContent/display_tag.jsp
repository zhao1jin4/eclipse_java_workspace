<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

function loadSelect(inSize)
{
	var pagesizeSel = document.getElementById("idpagesize");
	for (var i=0; i<pagesizeSel.options.length; i++) 
	{
		if( pagesizeSel.options[i].value == inSize )
		{
			pagesizeSel.options[i].selected="selected";
			return ;
		}
	}
}

function displaytagURL() {
	var reg = /-p=\d{0,}/;
	var url=document.getElementById("hd").value.replace(reg,"-p=" + document.getElementById("tz").value);
	window.location=url;
}

function dealfoward()
{
	var value = document.getElementById("idpagesize").value;
	if(document.getElementById("hd"))
	{	
		var url=document.getElementById("hd").value.replace(/-p=\d{0,}/,"-p=1");
		if( /\&pageSize=/.test(url) )
			location.href = url.replace(/\&pageSize=\d{0,}/, "&pageSize=" + value);
		else
			location.href = url+"&pageSize=" + value;
	}else
	{
		//for one page show all the record
		var url = window.location.href;  //<FORM method="GET"
		//debugger;
		if( /\&pageSize=/.test(url) )
			url = url.replace(/\&pageSize=\d{0,}/, "&pageSize=" + value);
		else
			url = url+"&pageSize=" + value;
		
		if(/-p=\d{0,}/.test(url))
			url = url.replace(/-p=\d{0,}/,"-p=1");
		else
			url+="&-p=1";
		
		window.location.href=url;
	}
}
</script>
</head>


<body>
	<!-- requestURI="/tablePageServlet.ser?action=query" 
	如去partialList="true"要加commons-collections-3.2.1.jar 
	sort="external"
	export="true"
	-->
<display:table name="myList" id="myTable"  partialList="true"   requestURI="/tablePageServlet.ser?action=init"
	size="resultSize" pagesize="${sessionScope.SESSION_PAGE_SIZE}"  
	 defaultsort="1" defaultorder="descending"
	 export="true"
	 >
	  <display:column title="${title_name}" property="name" style="text-align:center"   
	  	sortName="name" sortable="true" /> <!-- 如加 sortName 就要在display:table中加 sort="external"-->
	  <display:column title="日期" property="date" sortable="true"  group="1" />
	  <display:column title="日期" > ${myTable.date}  </display:column>
</display:table>

<script type="text/javascript">
	loadSelect(${sessionScope.SESSION_PAGE_SIZE});
</script>


<hr/>
URL总是有重复的,应该和requestURI中有参数的原因 <br/>
导出 Excel,中文乱码 ,未测试使用的导出类???


</body>
</html>