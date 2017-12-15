package myservlet.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class MyBodyTag extends BodyTagSupport
{
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED; //必须是EVAL_BODY_BUFFERED才可用bodyContent对象
		//Servlet容器会缓存实例
	}
	public int doEndTag() throws JspException {
		JspWriter writer=bodyContent.getEnclosingWriter();//TagSupport的成员 
		try {
			writer.println(bodyContent.getString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	
}
