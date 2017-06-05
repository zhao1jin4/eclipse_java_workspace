<%@ page  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="myservlet.tag.*,java.util.*" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<%@taglib  prefix="my" uri="/WEB-INF/tlds/customTag.tld" %>
<%@taglib  prefix="you" uri="http://zhaojin.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
-------自定义标签--<br/>
会自动搜索.war/WEB-INF/的所有*.tld 和 .jar/META-INF/所有的*.tld
<hr/>

<my:hello/>	<br/>
<my:max num2="22" num1="33"/> <br/>
<%! int i=10; 
%>
<my:greet>
	<font size="<%=i++%>"> 你好</font>
</my:greet><br/>

  switch---case--defalt标签:
  <%
  	pageContext.setAttribute("name", "wang");
  %>
<my:switch>
	<my:case cond="${name eq 'lisi'}">lisi</my:case>
	<my:case cond="${name eq 'wang'}">wang</my:case>
	<my:case cond="${name eq  null}">null</my:case>
	<my:default>other</my:default>
</my:switch>
<br/>
<%
List list=new ArrayList(); //不能使用 <>,???

ValueLabelBean bean=new ValueLabelBean();
bean.setLabel("足球");
bean.setValue(22);
list.add(bean);

bean=new ValueLabelBean();
bean.setLabel("蓝球");
bean.setValue(33);
list.add(bean);

pageContext.setAttribute("allBean", list);
%>
<my:iterator var="item" items="${allBean}">
	${item["label"]}  ==  <jsp:getProperty property="value" name="item"/>  <br/>
</my:iterator>

<br/>JSP2.0<br/>
<my:welcom name="张三">你好</my:welcom> <br/>
<my:max_ex num1="11" num2="22" num3="8"/>
最大值是: <%=max %>
<br/>
自定义的EL表达式函数toGBK:${you:toGBK("你好abc123","ISO8859-1")}<br/>
自定义的EL表达式函数toUTF8:${you:toUTF8("你好abc123","ISO8859-1")}<br/>
	 
	

<hr/>
====================JSTL标签--<br/>
------------------------C标签 <br/>
<%
	session.setAttribute("user_id", "abcs");
	
	ValueLabelBean mybean =new ValueLabelBean();
	pageContext.setAttribute("bean", mybean);
	
	pageContext.setAttribute("myMap", new HashMap());
	
	List  dataSet=new ArrayList();
	for(int i=0;i<5;i++)
	{
		Map row =new HashMap();
		row.put("username", "lisi_"+i);
		row.put("password", "123_"+i);
		row.put("age", "10__"+i);
		dataSet.add(row);
	}
	pageContext.setAttribute("dataSet", dataSet);
	
%>
<c:out value="${sessionScope.user_id}"></c:out>
<c:out value="<div>test</div>" escapeXml="false"></c:out>

<c:set var="myVar" value="abc" scope="page"></c:set>
<c:out value="${myVar}" />

<c:set target="${bean}" property="label" value="男"> </c:set>
<c:out value="${bean.label}"></c:out>

<c:set target="${myMap}" property="lisi" value="李四"/> <!-- 可以对Map -->
<c:set target="${myMap}" property="wang" value="王五"/>
<c:out value="${myMap.wang}"></c:out>

<br/>删除后:
<c:remove var="myMap" scope="page"/>
<c:out value="${myMap.wang}"></c:out>

<c:catch var="ex">
	<%
		int i=5;
	int j=0;
	int k=0;
	k=j/k;
	%>
</c:catch>
<br/>异常信息为:
<c:out value="${ex.message}"></c:out> 
<br/>

if的结果:
<c:if test="${sessionScope.user_id =='lisi' }">
	是李四
</c:if>
<br/>

choose的结果: 
<c:choose>
	<c:when test="${sessionScope.user_id =='lisi' }">是李四</c:when>
	<c:when test="${sessionScope.user_id =='wang' }">是王五</c:when>
	<c:otherwise>即不是李四,也不是王五</c:otherwise>
</c:choose>
c:forEach i的值: 
<c:forEach begin="0" end="10" step="1" var="i" >
	<c:out value="${i}"></c:out> <br/>
</c:forEach>

<c:forEach items="a,b,c,d,e,f" var="i" varStatus="status" step="2">
	 ${status.index },<c:out value="${i}"></c:out> <br/>
</c:forEach>


<table border="1">
	<tr>
		<td>行号</td>
		<td>用户名</td>
		<td>密码</td>
		<td>年龄</td>
	</tr>
	<!-- varStatus的值是 LoopTagStatus  
		status.index,status.count,status.first,status.last, 
		status.begin,status.end, status.current-->
	<c:forEach items="${dataSet}" var="row" varStatus="status"  >
		<tr>
			<td>${status.index}</td> 
			<td>${row['username']}</td><!-- 对Map -->
			<td>${row.password}</td>
			<td><c:out value="${row.age}"/></td>
		</tr>
	</c:forEach>
</table>
	
<c:forTokens items="zhang;li;wang" delims=";" var="item">
	${item} <br/>
</c:forTokens>

<div style="border-style:solid;border-width:5pt; border-color:blue" >
	<c:import url="sessionForm.jsp"></c:import>
</div>


<%-- 不行????????
<div style="border-style:solid;border-width:5pt; border-color:blue" >
	<c:import url="http://www.baidu.com"></c:import> <!-- 不能使用 <c:param -->
</div>		

<div  style="border-style:solid;border-width:5pt; border-color:gray">
	<!-- 比jsp:include 强,可外部项目,url和context的值都要以/开头,Tomcat的 <Context 加 crossContext="true" -->
	 <!--/jfreechart.jsp, 不能是/myhttp.action ,java.io.FileNotFoundException: The requested resource (/J_Struts2/myhttp.action) is not availabl -->
	<c:import url="/jfreechart.jsp" context="/J_Struts2" >
		<c:param name="myParam" value="test123" />  
	</c:import>
</div>
--%>

<c:url value="life" var="myUrl"><!--,如以/开头,自动加项目名, 会话跟踪 不行的????esponse.encodeURL(-->
	<c:param name="username" value="李"></c:param>
</c:url>
<a href="${myUrl}">去</a>

<br/>
<%-- 
<c:redirect url="/myhttp.action" context="/J_Struts2">  <!--Tomcat的 <Context 加 crossContext="true" -->
	<c:param name="myParam" value="test123" />  
</c:redirect> <!-- 后面的不会被执行 -->
<%
	System.out.println("JPS的尾部");
%>
 --%>
------------------------fmt标签 <br/>
<fmt:setLocale value="en_US"/>
<fmt:setLocale value="zh_CN"/>


<fmt:bundle basename="message">
	国际化:<fmt:message key="username"></fmt:message>
</fmt:bundle>
<br/>
<fmt:setBundle basename="message" scope="session" var="mybindle"/>
${mybindle.locale}
<br/>

<fmt:message bundle="${mybindle}" key="CanNotEmpty">
	<fmt:param>
		<fmt:message bundle="${mybindle}" key="username"/>
	</fmt:param>
</fmt:message>

<fmt:requestEncoding value="UTF-8"/>

<%--
不行???
<fmt:timeZone  value="GMT+8:00" ></fmt:timeZone>
 --%>
<fmt:setTimeZone value="GMT+8:00"/> <br/>

<fmt:formatNumber value="12.3" pattern=".000" /> <!-- DecimalFormat -->   <br/>
<fmt:formatNumber value="123456.789" pattern="#,#00.0#"/> <!--整数最少两位,小数最少1位 -->
  <br/>
number:<fmt:parseNumber value="456.78" integerOnly="true" type="number"></fmt:parseNumber>   <br/>
currency:<fmt:parseNumber value="$456.78" type="currency" parseLocale="en_US"></fmt:parseNumber><br/>
percent:<fmt:parseNumber value="75%" type="percent" ></fmt:parseNumber>  <br/>

<fmt:formatDate value="<%=new java.util.Date() %>" pattern="yyyy-MM-dd HH:mm:ss"/> <br/>
<fmt:parseDate value="2012-11-22 12:33:22" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"></fmt:parseDate> <br/>

------------------------sql标签 <br/>
<%--
<sql:setDataSource  url="jdbc:h2:tcp://localhost/~/test" driver="org.h2.Driver"  user="sa" password="" var="myDataSource"/>
<c:catch var="sqlEx">
	<sql:update sql="drop  table student" dataSource="${myDataSource}" />
</c:catch>
<sql:update sql="create table student (id int,name varchar2(20),age int,birthday date)" dataSource="${myDataSource}" />

<c:forEach begin="1" end="5" step="1" var="i">
	<sql:update sql="insert into  student (id,name,age ,birthday )values(?,?,?,?)" dataSource="${myDataSource}"  >
		<sql:param value="${i}"></sql:param>
		<sql:param value="lisi__${i}"></sql:param>
		<sql:param value="${22+i}"></sql:param>
		<sql:dateParam value="${myDate}"></sql:dateParam>
	</sql:update>
</c:forEach>

<!-- javax.servlet.jsp.jstl.sql.Result -->
<sql:query sql="select * from student where id>? or birthday <?" dataSource="${myDataSource}" maxRows="20" var="students">
	<sql:param value="1"></sql:param>
	<sql:dateParam  value="${myDate}"></sql:dateParam>
</sql:query>

<c:forEach var="row" items="${students.rows}"> <!--rows, rowsByIndex -->
	<c:out value="${row.name}"/> ,
	<c:out value="${row.age}"/> ,
	<c:out value="${row.birthday}"/> <br/>
</c:forEach>
<sql:transaction dataSource="${myDataSource}" isolation="serializable">
	<sql:update sql="update student set age=age+1 where id=1"/>
	<sql:update sql="update student set age=age-1 where id=2"/>
</sql:transaction>
 --%>
------------------------xml标签 <br/>
<c:import url="student.xml" var="xmlDoc"></c:import>
<x:parse  doc="${xmlDoc}" var="studentDoc"></x:parse>
<x:out select="$studentDoc/students/student[@id>1]/teacher"/> <br/>
<x:out select="$studentDoc//*[name()='teacher'][1]" escapeXml="false"/> <!-- 1开始 -->
 
<br/> set:
<x:set var="teacher" select="$studentDoc//teacher"/>
<x:out select="$teacher"/>

<br/> if:
<x:if select="$studentDoc//student[@name='lisi']">
	<x:out select="$studentDoc/students/student/@name"/>
</x:if>

<br/> choose:
<x:choose>
	<x:when select="$studentDoc//student[@name='lisi']">lisi</x:when>
	<x:when select="$studentDoc//student[@name='wang']">wang</x:when>
	<x:otherwise>其它的</x:otherwise>
</x:choose>

<br/> forEach:
<x:forEach select="$studentDoc//student" var="student" varStatus="status">
	${status.index} ,<x:out select="$student/@name"/> <br/>
</x:forEach> 

<br/>transform:
<c:set var="xmltext">
  <books>
    <book>
      <name>Padam History</name>
      <author>ZARA</author>
      <price>100</price>
    </book>
    <book>
      <name>Great Mistry</name>
      <author>NUHA</author>
      <price>2000</price>
    </book>
  </books>
</c:set>

<c:import url="book.xsl" var="xslt"/>
<x:transform xml="${xmltext}" xslt="${xslt}"/>
<hr/>
<c:import url="book.xml" var="xml"/>
<x:transform doc="${xml}" xslt="${xslt}"/> <!-- doc=和xml=是一样的 -->

------------------------fn标签 <br/>
contains:${fn:contains("zhangsan","san")} <br/>
containsIgnoreCase:${fn:containsIgnoreCase("zhangsan","SAN")}<br/>
startsWith :${fn:startsWith("zhangsan","zhan")} <br/>
endsWith :${fn:endsWith("zhangsan","san")} <br/>
indexOf :${fn:indexOf("zhangsan","san")} <br/>
replace :${fn:replace("zhangsan","san","123")} <br/>
substring :${fn:substring("zhangsan",7,-1)} <br/>
substringBefore :${fn:substringBefore("zhangsan","sa")} <br/>
substringAfter :${fn:substringAfter("zhangsan","ang")} <br/>
split :<c:set value='${fn:split("zhang;li",";")}' var="names" />
	<c:forEach items="${names}" var="item">
		${item}<br/>
	</c:forEach> <br/>
<%
	String[] welcome=new String[]{"wlcome","you","to","china"};
request.setAttribute("array",welcome);
%>
join :${fn:join(array," ")} <br/> 	
toLowerCase :${fn:toLowerCase("Good")} <br/> 	
toUpperCase :${fn:toUpperCase("Good")} <br/>
trim :${fn:trim(" do it ")} <br/>  
escapeXml :${fn:escapeXml("<br/>")} <br/>  
length :${fn:length("zhang")} <br/> 
length :${fn:length(array)} <br/>  <!-- 也可是List -->

<hr/>
==================== 标签 文件--<br/>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tg" %>

扩展名是*.tag,*.tagx(使用XML语法) ,.war会自动搜索/WEB-INF/tags的子目录,.jar是/META-INF/tags/所有*.tag,*.tagx<br/>

hello: <tg:hello /> <br/> 	<!-- hello是hello.tag文件名的前部分 -->
welcome:
<tg:welcome >
	<jsp:attribute name="user">李四</jsp:attribute>
	<jsp:body>欢迎</jsp:body>
</tg:welcome> <br/>

escapeXml: 
<tg:toHTML escapeHtml="true">
	<font color="red" >这是true</font>
</tg:toHTML><br/>
<tg:toHTML escapeHtml="false">
	<font color="red" >这是false</font>
</tg:toHTML><br/>

<%--  --%>
 variable 测试:
 <tg:my_var num1="100" num2="2002" num3="303" >
    <jsp:attribute name="great">
        <font color="red">SUM：${sum}</font>
    </jsp:attribute>
    <jsp:attribute name="less">
        <font color="blue">SUM：${sum} </font>
    </jsp:attribute>
</tg:my_var>


</body>
</html>