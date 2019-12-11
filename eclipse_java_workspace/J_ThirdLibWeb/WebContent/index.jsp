<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>J_ThirdLibWeb</title>
 
</head>
<body> 
	
	 
	<hr/> 第三方库    <br/>
	<a href="freemaker">Freemaker 测试 ( SpringMVC 中有不少)</a> <br/> 
	
	<a href="thymeLeafHtmlServlet"> Thymeleaf HTML Servlet </a> <br/>
	<a href="thymeLeafTextServlet"> Thymeleaf Text Servlet  </a> <br/>
	 
	---- Thymeleaf Spring <br/>
	<a href="thymeleafSpring/first.mvc"> Thymeleaf Spring  first</a> <br/> 
	
	----  <br/>
	<a href="hessian/client">hession Web 二进制做webservice </a> <br/>
	<a href="commonsFileUpload.html">commons FileUpload  测试</a> <br/>
 	
	
	<hr/>不常用的  <br/>

	<a href="jfreechart.jsp">jfreechart  测试 (web.xml)</a> <br/>
 	
 	
 	<hr/> 第三方库 和  SpringMVC  <br/>
 	
 	<a href="http://127.0.0.1:8080/J_ThirdLibWeb/sdoc.jsp">swagger1.x(老版本) sdoc.jsp </a>   <br/>
	swagger1.x页面中使用 http://127.0.0.1:8080/J_ThirdLibWeb/api-docs.mvc  或者 修改web.xml  url-pattern /  <br/>
 	
 	<br/>
	Pluto 在not offen中？ JSF portlet,SpringMVC portlet

	<hr/>
	swagger 2.0.x (openAPI 3.0) Use RestEasy <br/>
	<a href="http://127.0.0.1:8080/J_ThirdLibWeb/sample/pet/1"> sample/pet/1 (get metthod,Accept头 application/xml,application/json)</a>   <br/>
 
	<a href="http://127.0.0.1:8080/J_ThirdLibWeb/sample/user/user1">sample/user/user1 (delete method)</a>   <br/>
	<a href="http://127.0.0.1:8080/J_ThirdLibWeb/sample/user/user1">sample/user/user1 (get method)</a>   <br/>
 	 
 	<a href="sample/openapi.json"> openapi.json供 swagger-ui 生成文档  </a>   <br/>
 	<a href="sample/openapi.yaml"> openapi.yaml供 swagger-ui 生成文档  </a>   <br/>
 	 
 	 node_modules/swagger-ui-dist 的内容 (或者源码目录/dist)复制过来的，有修改index.html指向 openapi.json(.yaml)<br/>
	 <!--
	  META-INF目录下加context.xml文件    <Context crossContext="true" /> 
 	 跨域 cross-origin (CORS)  通用的Filter名为 ApiOriginFilter<br/>
	 请求json使用fetch api, 返回没有响应头？？？ 浏览器GET 请求就有
	   -->
	 <a href="swagger-ui-v2/swagger-ui-dist/index.html">swagger-ui 生成的html  </a>   <br/> 

	 异常转json没用  ？？？ @Provider xxx implements ExceptionMapper<br/>
	 
	<a href="swagger-ui-v2/swagger-ui_unpkg/index.html">swagger-ui (unpkg js)  不可用？？</a>   <br/>
	 
	
 	 
 	 
</body>
</html>
