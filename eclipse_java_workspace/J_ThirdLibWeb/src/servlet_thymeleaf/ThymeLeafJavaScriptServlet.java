package servlet_thymeleaf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import servlet_thymeleaf.vo.DateRange;
import servlet_thymeleaf.vo.Employee;
import servlet_thymeleaf.vo.NamValuePair;


@WebServlet("/thymeLeafJavaScriptServlet")
public class ThymeLeafJavaScriptServlet extends HttpServlet {
 
	//https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#textual-template-modes
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
    {
    	
    	   ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(request.getServletContext());
           resolver.setPrefix("/WEB-INF/thymeleaf/");
           //resolver.setSuffix(".html");
           resolver.setCharacterEncoding("UTF-8");
         // resolver.setTemplateMode(TemplateMode.HTML);
         //二选一
         //resolver.setTemplateMode(TemplateMode.TEXT);
           resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
           
           resolver.setCacheable(false);//默认是true,即缓存的,false只用于开发使用
           //resolver.getCacheablePatternSpec().addPattern("/users/*");
           //resolver.setCacheTTLMs(60000L);// 1 minute

           TemplateEngine engine = new TemplateEngine();
           engine.setTemplateResolver(resolver);
           
       response.setCharacterEncoding("utf-8");
       //response.setContentType("text/plain");
       response.setContentType("application/x-javascript"); 
       
        WebContext context = new WebContext(request, response, request.getServletContext());        
        context.setVariable("name", "李四");
        //--
//        Employee emp=new Employee();
//        emp.setEmployee_id(100);
//        emp.setFirst_name("张");
//        emp.setLast_name("三");
//        emp.setSalary(8000.00);
//
       Calendar calendar= Calendar.getInstance();
       calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
//       
        DateRange range=new DateRange();
        range.setBeginTime(calendar.getTime());
        range.setEndTime(new Date());
//        emp.setCreateTimeRange(range);
//        emp.setHobby("football");
//       //--
//        context.setVariable("currentUser", emp);
//        
        List<NamValuePair> hobbys=new ArrayList<>();
		hobbys.add(new NamValuePair("足球","football"));
		hobbys.add(new NamValuePair("篮球","basketball")); 
        context.setVariable("hobbys", hobbys);
        
        List<Employee> emps=new ArrayList< >();
		for(int i=0;i<10;i++)
		{
			Employee empTmp=new Employee();
			empTmp.setEmployee_id(10+i);
			empTmp.setFirst_name("张"+i);
			empTmp.setLast_name("三"+i);
			empTmp.setSalary(8000.00+i); 
	        empTmp.setCreateTimeRange(range);
			emps.add(empTmp);
		} 
		context.setVariable("allEmps", emps);
        
//        context.setVariable("serverHTML", "<p style='color:red'>内容<p>"); 
        //engine.process("index.html", context, response.getWriter());
        //二选一
        //engine.process("servletText.csv", context, response.getWriter());
        engine.process("servletJS.js", context, response.getWriter());
    }

}