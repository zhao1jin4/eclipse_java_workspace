<html>
<head>
<title>Hello FreeMarker</title>
 
 <#include "component/html_head.ftl"/>
 
</head>
<body>
	在FreeMarker页中
	<hr/>
	成功
	<br/>
	<a href="<@spring.url "/registerFreemarker.mvc" />">再做一次,以/开头的带项目名</a>
</body>
</html>

