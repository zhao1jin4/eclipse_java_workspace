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

import servlet_thymeleaf.vo.DateRange;
import servlet_thymeleaf.vo.Employee;
import servlet_thymeleaf.vo.NamValuePair;


@WebServlet("/thymeLeafHtmlServlet")
public class ThymeLeafHTMLServlet extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());        
        response.setCharacterEncoding("utf-8");
        
        context.setVariable("name", "李四");
        //--
        Employee emp=new Employee();
        emp.setEmployee_id(100);
        emp.setFirst_name("张");
        emp.setLast_name("三");
        emp.setSalary(8000.00);

       Calendar calendar= Calendar.getInstance();
       calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
       
        DateRange range=new DateRange();
        range.setBeginTime(calendar.getTime());
        range.setEndTime(new Date());
        emp.setCreateTimeRange(range);
        emp.setHobby("football");
       //--
        context.setVariable("currentUser", emp);
        
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
		context.setVariable("serverHTML", "<p style='color:red'>内容<p>"); 
        engine.process("servletHtml.html", context, response.getWriter());
    }

}