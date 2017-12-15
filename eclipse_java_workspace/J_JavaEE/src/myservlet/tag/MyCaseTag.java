package myservlet.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class MyCaseTag  extends TagSupport{
	private boolean cond=false;
	
	public void setCond(boolean cond) {
		this.cond = cond;
	}
	public int doStartTag() throws JspException {
		Tag parent=getParent();
		if(!((MySwitchTag)parent).getPermission())
			return SKIP_BODY;
		if(cond)
		{
			((MySwitchTag)parent).subTagSuccessed();
			return EVAL_BODY_INCLUDE;
		}else
			return SKIP_BODY;
	}
	public void release() {
		cond=false;
	}
	
}
