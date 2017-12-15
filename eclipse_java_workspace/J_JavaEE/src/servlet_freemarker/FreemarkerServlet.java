package servlet_freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@WebServlet("/freemaker")
public class FreemarkerServlet extends HttpServlet {
	private Configuration cfg;

	public void init() {
		cfg = new Configuration();
		cfg.setServletContextForTemplateLoading(getServletContext(),"WEB-INF/freemarkers");//放在WEB-INF下才安全
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
		{
		Map<String,Object> root = new HashMap<>();
		root.put("message", "Hello FreeMarker!");
		root.put("myDate", new java.util.Date());
		
		List<String> allAnimals=new ArrayList<>();
		allAnimals.add("猫");
		allAnimals.add("狗 ");
		root.put("animals",allAnimals);
		
		Template t = cfg.getTemplate("test.ftl");
		response.setContentType("text/html; charset=" + t.getEncoding());
		Writer out = response.getWriter();
		try {
			t.process(root, out);
		} catch (TemplateException e) {
			throw new ServletException("处理Template模版中出现错误", e);
		}
	}
}
