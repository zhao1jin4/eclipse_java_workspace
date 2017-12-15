<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
 .file-box{ position:relative;width:340px}
.txt{ height:22px; border:1px solid #cdcdcd; width:180px;}
.btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
.file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px }
</style>

</head>
<body>

 input type file 格式化
<div class="file-box">
  <form action="submitUpload.mvc" method="post" enctype="multipart/form-data">
	<input type='text' name='textfield' id='textfield' class='txt' />  
	 <input type='button' class='btn' value='浏览...' />
	 <input type="file" name="img" class="file" id="fileField" size="28" onchange="document.getElementById('textfield').value=this.value" />
	 <input type="submit" name="submit" class="btn" value="上传" />
  </form> 
</div>
  
<!-- commandName modelAttribute  -->
<form:form action="submitUpload.mvc" method="post" modelAttribute="uploadForm" enctype="multipart/form-data">
													　
	image:<input type="file" name="img" ><BR>
	photo1:<input type="file" name="photos"><BR>
	photo2:<input type="file" name="photos"><BR>
	DES:<input type="text" name="description">
	Date <input type="text"  name="birthday" value="2014-11-11"/>
	<input type="submit" value=" 提交"/>
</form:form>

<br/>asyncUpload <br/>
<form:form action="asyncUpload.mvc" method="post"   enctype="multipart/form-data">
	image:<input type="file" name="img"><BR>
	<input type="submit" value=" 提交"/>
</form:form>

<br>
<spring:bind path="uploadForm.*">
	<c:forEach items="${status.errorMessages}" var="item">
	${item}
	</c:forEach>
 </spring:bind>
 
 
 
</body>
</html>