package spring_jsp.extention;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

//Employee childIds的逗号分隔多个不好使？？？
@Component
public class StringListConverter implements Converter<String, List<String>> {
	@Override
	public  List<String> convert(String source) {
		String ids[] = source.split(",");
		List<String> listIds = Arrays.asList(ids);
		return listIds;
		//return new ArrayList<String>(listIds);
	}
}