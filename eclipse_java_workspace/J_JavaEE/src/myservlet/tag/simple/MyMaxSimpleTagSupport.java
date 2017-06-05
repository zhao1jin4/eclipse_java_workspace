package myservlet.tag.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class MyMaxSimpleTagSupport  extends SimpleTagSupport //JSP2.0,Servlet容器不会缓存
				implements DynamicAttributes
{
	private List<String> list=new ArrayList<>();
	public void doTag() throws JspException, IOException {
		JspContext context=getJspContext();//SimpleTagSupport中的方法
		int max=Integer.parseInt(list.get(0));
		int size=list.size();
		int num;
		for(int i=1;i<size;i++)
		{
			num=Integer.parseInt(list.get(i));
			max=max>num?max:num;
		}
		context.setAttribute("max",max);//对应于<name-given>max</name-given>
	}
	
	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		list.add((String)value);
	}
}
