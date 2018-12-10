package mybatis_xml;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class MyOgnlUtil {
	public static Map<String,String> map=new HashMap<>();//要public

	static {
		map.put("j","Java");
	}
	public static boolean isNotBlank(String str)
	{
		return StringUtils.isNotBlank(str);
	}
}
