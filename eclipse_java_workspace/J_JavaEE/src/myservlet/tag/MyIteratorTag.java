package myservlet.tag;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class MyIteratorTag  extends TagSupport{

	private Iterator items;
	private String itemId;
	private Object item;
	public void setItems(Collection c)
	{
		if(c!=null && c.size()>0)
			items=c.iterator();
	}
	public void setVar(String var){
		itemId=var;
	}
	public void putVariable() {
		if(null==item)
			pageContext.removeAttribute(itemId,PageContext.PAGE_SCOPE);
		else
			pageContext.setAttribute(itemId,item);
	}
	public int doStartTag() throws JspException {
		if(items.hasNext())
			item=items.next();
		else
			return SKIP_BODY;
		putVariable();
		return EVAL_BODY_INCLUDE;
	}
	public int doAfterBody() throws JspException {
		if(items.hasNext())
			item=items.next();
		else
			return SKIP_BODY;
		putVariable();
		return EVAL_BODY_AGAIN;
	}
	public void release() {
		items=null;
	}
	
	
}
