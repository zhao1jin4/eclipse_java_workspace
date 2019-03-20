package spring_freemarker;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FreemarkerUtil {
	public static DBComment parseComment(String str)
	{
		DBComment dbComment=new DBComment();
		if(str.contains("|"))
		{
			String [] arrays=str.split("\\|");
			String comment=arrays[0];
			String json=arrays[1];
			
			ObjectMapper mapper = new  ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);//�����л�����δ֪���Բ����쳣
//			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//����ʹ��δ�����ŵ��ֶ���
//			mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //����ʹ�õ�����
	 
			try {
				dbComment=mapper.readValue(json, DBComment.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dbComment.setComment(comment);
		}else
			dbComment.setComment(str);
		return dbComment;
	}
	public static void main(String ...  args)
	{
		String str="123,455";
		System.out.println(str.split(",")[0]);
		System.out.println(str.split(",")[1]);
		
		Pattern x;
		str="123|455";
		System.out.println(str.split("\\|")[0]);
		System.out.println(str.split("(\\|)")[0]);
	}
}
