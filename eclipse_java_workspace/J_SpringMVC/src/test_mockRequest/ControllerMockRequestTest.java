package test_mockRequest;
 
import static org.mockito.Mockito.mock;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import spring_jsp.annotation.JSONController;
import spring_jsp.annotation.form.Employee;
import spring_jsp.annotation.form.EmployeeResult;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test_mockRequest/spring-test.xml"})
/**
XML文件中只有
<context:component-scan base-package="spring_jsp.annotation" /> 
<mvc:annotation-driven  validator="validator"   />
 */
public class ControllerMockRequestTest 
{
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockServletContext context;
    @Autowired
    private JSONController jsonController;
    
    @Before  
    public void setUp()
    {
    	String contextPath="/J_SpringMVC";
		context=new MockServletContext();
		context.setContextPath(contextPath);
         
        request = new MockHttpServletRequest(context);    
        request.setCharacterEncoding("UTF-8");
        
        String rootPath=request.getServletContext().getRealPath("/");
        System.out.println(rootPath);
        
        String reqContextPath=request.getServletContext().getContextPath();
        System.out.println(reqContextPath);
        
        request.setRequestURI("http://127.0.0.1:/J_SpingMVC/page.mvc");
        System.out.println(request.getRequestURI());//如不set就返回空串
       
        request.setContextPath(contextPath); 
        response = new MockHttpServletResponse();
        
        //web.xml中有filter,listener怎么办？
        
    }
    @Test
    public void test() {
        HttpSession session = request.getSession(true);
        session.setAttribute("currentDate", new Date());
        Employee emp=new Employee ();
        
        System.out.println(  request.getLocale() );
        EmployeeResult res= jsonController.queryEmployeeVO(request, emp);
        System.out.println(res);
    }
}