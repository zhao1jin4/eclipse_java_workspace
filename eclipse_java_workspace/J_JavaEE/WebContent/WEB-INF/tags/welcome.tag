<%@tag pageEncoding="UTF-8"  %>
<%@attribute name="user" required="true" fragment="true" %> 
<!-- 如 fragment="true" ,那么rtexprvalue="true" type="javax.servlet.jsp.tagext.JspFragment" 的值是固定的 ,不能被指定-->
<jsp:invoke fragment="user" /> ,<jsp:doBody /> <!-- 只可在.tag文件中使用,都可指定 var或 varReader(只可一个)来保存结果,scope-->
