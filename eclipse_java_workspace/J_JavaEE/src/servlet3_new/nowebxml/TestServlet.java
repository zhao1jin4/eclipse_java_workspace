package servlet3_new.nowebxml;
import java.io.IOException;    
    
import javax.servlet.ServletException;    
import javax.servlet.http.HttpServlet;    
import javax.servlet.http.HttpServletRequest;    
import javax.servlet.http.HttpServletResponse;    
public class TestServlet extends HttpServlet {    
    public void doGet(HttpServletRequest req, HttpServletResponse resp){    
        System.out.println("-------Some client access once");   
        try {
			resp.getOutputStream().write("OK".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }    
}  