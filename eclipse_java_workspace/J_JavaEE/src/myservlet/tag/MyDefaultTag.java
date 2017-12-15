package myservlet.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class MyDefaultTag  extends TagSupport{

	public int doStartTag() throws JspException {
		Tag parent=getParent();
		if(!((MySwitchTag)parent).getPermission())
			return SKIP_BODY;
		((MySwitchTag)parent).subTagSuccessed();
		return EVAL_BODY_INCLUDE;
	}
	
}
