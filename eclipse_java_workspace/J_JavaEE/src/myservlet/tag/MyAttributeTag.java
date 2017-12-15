package myservlet.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
public class MyAttributeTag extends  TagSupport //实现了IterationTag,Tag
{
	//还有 BodyTag 接口
	private int num1;
	private int num2;
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public int doEndTag() throws JspException {
		JspWriter writer=pageContext.getOut();//TagSupport的成员 
		try {
			writer.println(num1>num2?num1:num2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Tag.EVAL_PAGE;
	}
	
	
}
