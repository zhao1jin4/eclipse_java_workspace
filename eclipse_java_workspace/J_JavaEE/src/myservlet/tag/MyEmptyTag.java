package myservlet.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

//类必须在包下
public class MyEmptyTag implements Tag
{
	private PageContext pageContext;
	private Tag parent;
	public void setPageContext(PageContext page) {
		pageContext=page;
	}
	public void setParent(Tag tag) {//如没父标签设置为null
		parent=tag;
	}
	public Tag getParent() {
		return parent;
	}
	public int doStartTag() throws JspException {
		return Tag.SKIP_BODY;
	}
	public int doEndTag() throws JspException {
		JspWriter writer=pageContext.getOut();
		try {
			writer.println("我的自定义标签的输出");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//不要writer.close();
		return Tag.EVAL_PAGE;//JSP的剩余部分继续执行
	}
	public void release() {
	}
}
