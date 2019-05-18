package org.zh.base;

import static org.zh.base.Constant.DEF_PAGE_SIZE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes({SessionKey.PAGE_SIZE})//将ModelMap中的  PAGE_SIZE 放在session中
public abstract class PageController 
{
	public void initPage(ModelMap model)
	{
		model.addAttribute("pageNO", 1);
		model.addAttribute("pageCount", 1);
		model.addAttribute("allPageSize",this.generatePageSize());
		this.getSessionPageSize(0,model);//存储session PAGE_SIZE
	}
	//调用submitPage取session要在方法参数中有 ModelMap
	public long[] submitPage(int reqPageNO,int reqPageSize,long totalCount,ModelMap model)
	{
		int pageSize=this.getSessionPageSize(reqPageSize,model); //存取 session PAGE_SIZE
		int pageCount=(int)(totalCount/pageSize) + 1;
		int pageNO=1;
		if(reqPageNO > 0)
			pageNO= reqPageNO;
		if(pageNO>pageCount)
			pageNO=pageCount;
		model.addAttribute("pageNO", pageNO);
		model.addAttribute("pageCount", pageCount);
		model.addAttribute("allPageSize",generatePageSize());
		//为Oracle传参数
		long start=(pageNO-1)*pageSize+1;//pageNO*pageSize-pageSize+1
		long end=pageNO*pageSize;
		long[] range=new long[2];
		range[0]=start;
		range[1]=end;
		return range;
	}
	
    private int  getSessionPageSize(int reqPageSize,ModelMap model)
	{
    	 int pageSize=DEF_PAGE_SIZE;
    	//优先使用reqPageSize,
	    if(reqPageSize > 0 )//传了
	    {
	    	pageSize=reqPageSize;
	    	model.addAttribute(SessionKey.PAGE_SIZE,reqPageSize);
	    } else//其次使用session的
	    {
	    	Object sessionPage =model.get(SessionKey.PAGE_SIZE);
	 	    if(sessionPage!=null)
	 	    	pageSize=Integer.parseInt(sessionPage.toString());
	 	    else
	 	    	model.addAttribute(SessionKey.PAGE_SIZE,pageSize);
	    }
	    return pageSize;
	}
    private List<Integer> generatePageSize()
	{
		List<Integer> allPageSize=new ArrayList<Integer>();
		allPageSize.add(DEF_PAGE_SIZE-10);
		allPageSize.add(DEF_PAGE_SIZE);
		allPageSize.add(DEF_PAGE_SIZE+10);
		allPageSize.add(DEF_PAGE_SIZE+30);
		return allPageSize;
	}
}
