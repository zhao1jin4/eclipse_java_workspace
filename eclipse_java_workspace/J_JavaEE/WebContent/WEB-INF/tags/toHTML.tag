<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@tag pageEncoding="UTF-8"%>
<%@attribute name="escapeHtml" required="true" type="java.lang.Boolean"%>

<%!
public String toHtml(String str)
	{
		if(str==null)
			return null;
		StringBuffer sb=new StringBuffer();
		int len=str.length();
		for(int i=0;i<len;i++)
		{
			char c= str.charAt(i);
			switch(c)
			{
			case '\'':
				sb.append("&#39;");
				break;
			case '"':
				sb.append("&#34;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

%>
<c:choose>
	<c:when test="${escapeHtml}"> <!-- 这里可以直接得到 @attribute,在jspContext中 -->
		<jsp:doBody var="content"/>
		 <%  out.println(toHtml((String)jspContext.getAttribute("content")));    %>
	</c:when>
	<c:otherwise>
		<jsp:doBody/>
	</c:otherwise>
</c:choose>










