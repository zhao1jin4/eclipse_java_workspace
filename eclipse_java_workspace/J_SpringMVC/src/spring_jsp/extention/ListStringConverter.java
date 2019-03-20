package spring_jsp.extention;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//Employee childIds�Ķ��ŷָ��������ʹ������
@Component
public class ListStringConverter implements Converter< List<String>, String> {
	@Override
	public String convert(  List<String>  source) { 
		if(source==null || source.size()==0)
			return "";
		String strIds=	StringUtils.join(source,",");
		return strIds;
	}
}