package servlet_freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

public class FeemarkerMain {

	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS); 
		StringTemplateLoader stringTemplateLoader=new StringTemplateLoader();
		stringTemplateLoader.putTemplate("strTmp", "欢迎：${user}");
		cfg.setTemplateLoader(stringTemplateLoader); 
		cfg.setDefaultEncoding("UTF-8"); 

		Template template =  cfg.getTemplate("strTmp","utf-8");

		Map root = new HashMap(); 
		root.put("user", "李四"); 

		StringWriter writer = new StringWriter(); 
		template.process(root, writer); 
		System.out.println(writer.toString());  

	}

}
