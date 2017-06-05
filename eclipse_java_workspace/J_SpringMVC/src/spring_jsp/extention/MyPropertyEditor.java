package spring_jsp.extention;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyPropertyEditor extends PropertyEditorSupport //表单的日期如何转换,也可以使用CustomDateEditor
{
	SimpleDateFormat dateFormat=new  SimpleDateFormat("yyyy-MM-dd");
	public void setAsText(String text) throws IllegalArgumentException
	{
		try {
			if(! "".equals(text))
				super.setValue(this.dateFormat.parse(text));//调用setValue存
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public String getAsText() 
	{
		Object o=getValue();//getValue调用出
		Date date=(Date)o;
		String str=dateFormat.format(date);
		return str;
	}
}
