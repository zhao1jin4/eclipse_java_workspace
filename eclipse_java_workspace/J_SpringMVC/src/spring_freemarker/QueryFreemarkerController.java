package spring_freemarker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.zh.base.PageController;

@Controller
@RequestMapping("/freemarkerQuery")
public class QueryFreemarkerController  extends PageController
{
	protected final Log logger = LogFactory.getLog(getClass()); 
	
	@InitBinder	//需要处理Date的时候,自动调用这个方法
	public void initBinder(WebDataBinder binder)//要用 WebDataBinder
	{
		System.out.println("调用initBinder");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
    @RequestMapping("/initQuery")
    public ModelAndView initQuery() 
    {
    	ModelAndView mv=new ModelAndView();
    	super.initPage(mv.getModelMap());//分页
    	
    	RegisterBean form=new RegisterBean();
    	form.setBirthday(new Date());
    	mv.addObject("form",form );
    	
//    	Map<String,String> allGender=new TreeMap<>();
//    	allGender.put("", "--所有--");
//    	allGender.put("M", "男");
//    	allGender.put("F", "女");
//    	mv.addObject("allGender",allGender);
    	
    	//mv.addObject("allData", new ArrayList());//freemarker 使用 <#if allData?exists> 
    	mv.setViewName("query");
    	return mv;
    }
    
    @RequestMapping(value="/submitQuery",method={RequestMethod.POST,RequestMethod.GET})
    public String submitQuery(@ModelAttribute("form") RegisterBean form,
    			@RequestParam(value="pageNO",required=false)int pageNO,
    			@RequestParam(value="pageSize",required=false)int pageSize,
    			ModelMap modelMap //分页使用session要ModelMap在参数中
    			//,@ModelAttribute(SessionKey.PAGE_SIZE)int sesPages//OK
    			)
    {
    	 System.out.println("得到Username:"+form.getUsername());
    	 System.out.println("得到Birthday:"+form.getBirthday());
    	 System.out.println("得到Gender:"+form.getGender());
    	 System.out.println("得到pageSize:"+pageSize);
    	 
    	 //------分页
    	long totalCount=100;//从DB查符合条件的所有记录数
    	long[] range= super.submitPage(pageNO, pageSize, totalCount, modelMap);
    	long start=range[0];
    	long end=range[1];
    	//----
        
       List<RegisterBean> allData=new ArrayList<>();
	   	for (long i=start;i<=end;i++)
	   	{
	   		RegisterBean bean=new RegisterBean();
	   		bean.setAge((int)(20+i));
	   		bean.setBirthday(new Date());
	   		bean.setId((int)(1000+i));
	   		bean.setGender("F");
	   		bean.setUsername("李四"+i);
	   		bean.setRemark("描述测试,描述测试,描述测试,描述测试,描述测试,描述测试,描述测试,描述测试,描述测试,");
	   		allData.add(bean);
	   	}
    	modelMap.addAttribute("allData", allData);
    	
    	Map<String,String> allGender=new TreeMap<>();
    	allGender.put("", "--所有--");
    	allGender.put("M", "男");
    	allGender.put("F", "女");
    	modelMap.addAttribute("allGender", allGender);
    	//modelMap.addAttribute("form", form);//不用加
    	return "query";
    }
    
}