package myservlet;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequestWrapper extends HttpServletRequestWrapper{

	public MyRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public String getParameter(String name) { //防用户在留言版中输入HTML
		
		String val= super.getParameter(name);
		if(null!=val)
			val=toHtml(val);
		return val;
	}
	
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

}
