package servlet_thymeleaf;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import servlet_thymeleaf.vo.DateRange;
import servlet_thymeleaf.vo.Employee;


@WebServlet("/firstThymeLeaf")
public class IndexServlet extends HttpServlet {
 
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
       //--
        context.setVariable("currentUser", emp);
        
        context.setVariable("welcome", "hi {0} ,now  is {1}");
        
        engine.process("index.html", context, response.getWriter());
    }

}