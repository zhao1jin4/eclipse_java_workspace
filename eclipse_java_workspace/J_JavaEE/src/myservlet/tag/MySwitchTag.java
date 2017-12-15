package myservlet.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class MySwitchTag  extends TagSupport{
	private boolean subTagExecute=false;
	public synchronized void subTagSuccessed()
	{
		subTagExecute=true;
	}
	public synchronized boolean getPermission()
	{
		return !subTagExecute;
	}
	public void release() {
		subTagExecute=false;
	}
	public int doStartTag() throws JspException {
		subTagExecute=false;
		return EVAL_BODY_INCLUDE;
	}
}
