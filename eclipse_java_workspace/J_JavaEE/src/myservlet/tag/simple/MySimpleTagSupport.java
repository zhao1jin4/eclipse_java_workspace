package myservlet.tag.simple;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class MySimpleTagSupport  extends SimpleTagSupport{//JSP2.0,Servlet容器不会缓存
	private JspFragment body;
	private String name;
	public void setName(String name) {
		this.name = name;
	}
	public void setParent(JspTag parent) {//如没有父不会被调用
	}
	public void setJspBody(JspFragment jspBody) {//如没有体不会被调用
		this.body=jspBody;
	}
	public void doTag() throws JspException, IOException {
		JspContext context=getJspContext();//SimpleTagSupport中的方法
		JspWriter writer=context.getOut();
		writer.println(name);
		writer.println(",");
		body.invoke(null);//null是当前输出流
	}
}
