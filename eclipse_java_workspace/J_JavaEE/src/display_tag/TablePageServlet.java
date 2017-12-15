package display_tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

@WebServlet("/tablePageServlet.ser")
public class TablePageServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(request.getParameter("action"));//总是init
		
		int pageNo=getPageNO(request,"myTable");
		int pageSize=getSessionPageSize(request);
		
		// 获取外部排序列 
		String strSortName = new ParamEncoder("myTable").encodeParameterName(TableTagParameters.PARAMETER_SORT);
		String sortName = request.getParameter(strSortName);
		String strOrder = new ParamEncoder("myTable").encodeParameterName(TableTagParameters.PARAMETER_ORDER);
		String order = request.getParameter(strOrder);//order为升序还是降序(1为升序  2为降序)
		String dbOrder="";
		if("1".equals(order))
			dbOrder="asc";
		else if("2".equals(order))
			dbOrder="desc";
				
		List data=generateData(pageNo,pageSize,sortName,dbOrder);
		request.setAttribute("myList", data);
		request.setAttribute("resultSize",data.size());
		request.setAttribute("title_name","姓名");
		
		request.getRequestDispatcher("display_tag.jsp").forward(request, response);
	}
	public List  generateData(int pageNo,int pageSize,String sortName,String  dbOrder)
	{
		List dataList=new ArrayList();
		for(int i=pageNo;i<pageNo + pageSize + 3 ;i++)
		{
			VO vo=new VO();
			vo.setName("名"+i);
			vo.setDate("公元"+i);
			dataList.add(vo);
		}
		return dataList;
	}
	//---放入基类中
	protected int  getPageNO(HttpServletRequest request,String tableId)
	{
		int pageNo=1;
		String name = new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE);//服务端接收传来的页号
		if(request.getParameter(name)!=null)
		{
			try{
				pageNo = Integer.parseInt(request.getParameter(name));//display tag 中分页按钮请求才有
			}catch(NumberFormatException e)
			{
				pageNo=1;
			}
		}
		return pageNo;
	}
	protected int  getSessionPageSize(HttpServletRequest request)
	{
		HttpSession session=request.getSession();
		final String SESSION_PAGE_SIZE="SESSION_PAGE_SIZE";
		int pageSize=20;
		String reqSize=request.getParameter("pageSize");
	    if(reqSize!=null && ! "".equals(reqSize))
	    {
	    	pageSize=Integer.parseInt(reqSize.toString());
	    	session.setAttribute(SESSION_PAGE_SIZE,pageSize);
	    }else
	    {
	    	 Object sessionPage =session.getAttribute(SESSION_PAGE_SIZE);
	 	    if(sessionPage!=null)
	 	    	pageSize=Integer.parseInt(sessionPage.toString());
	 	    else
	 	    	session.setAttribute(SESSION_PAGE_SIZE,pageSize);
	    }
	    return pageSize;
	}
}