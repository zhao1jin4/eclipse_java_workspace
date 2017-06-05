
package javaee_concurrency_schedule;
import java.io.IOException;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class org
 */
@WebServlet("/concurrenServ")
public class JavaEEConcurrencyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	@Resource
	ManagedExecutorService executor;
	//--
	@Resource
	ManagedScheduledExecutorService scheduledExecutor;


	@Resource
	ManagedThreadFactory factory;
	    
	
    public JavaEEConcurrencyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//--
		try {
			InitialContext ctx = new InitialContext();
			ManagedExecutorService executor = (ManagedExecutorService) ctx.lookup("java:comp/DefaultManagedExecutorService");
			System.out.println("executor="+executor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
