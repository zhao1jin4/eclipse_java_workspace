package servlet3_new.nowebxml;
import javax.servlet.ServletContext;    
import javax.servlet.ServletException;    
import javax.servlet.ServletRegistration;    
    
public class ServletParameter implements WebParameter {    
    @Override    
    public void loadInfo(ServletContext servletContext) throws ServletException {    
        ServletRegistration.Dynamic testServlet=servletContext.addServlet("test","servlet3_new.nowebxml.TestServlet");    
        testServlet.setLoadOnStartup(1);    
        testServlet.addMapping("/nowebxml");    
    }    
}