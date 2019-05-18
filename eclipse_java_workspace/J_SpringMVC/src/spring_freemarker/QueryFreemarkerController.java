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
	
	@InitBinder	//��Ҫ����Date��ʱ��,�Զ������������
	public void initBinder(WebDataBinder binder)//Ҫ�� WebDataBinder
	{
		System.out.println("����initBinder");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
    @RequestMapping("/initQuery")
    public ModelAndView initQuery() 
    {
    	ModelAndView mv=new ModelAndView();
    	super.initPage(mv.getModelMap());//��ҳ
    	
    	RegisterBean form=new RegisterBean();
    	form.setBirthday(new Date());
    	mv.addObject("form",form );
    	
//    	Map<String,String> allGender=new TreeMap<>();
//    	allGender.put("", "--����--");
//    	allGender.put("M", "��");
//    	allGender.put("F", "Ů");
//    	mv.addObject("allGender",allGender);
    	
    	//mv.addObject("allData", new ArrayList());//freemarker ʹ�� <#if allData?exists> 
    	mv.setViewName("query");
    	return mv;
    }
    
    @RequestMapping(value="/submitQuery",method={RequestMethod.POST,RequestMethod.GET})
    public String submitQuery(@ModelAttribute("form") RegisterBean form,
    			@RequestParam(value="pageNO",required=false)int pageNO,
    			@RequestParam(value="pageSize",required=false)int pageSize,
    			ModelMap modelMap //��ҳʹ��sessionҪModelMap�ڲ�����
    			//,@ModelAttribute(SessionKey.PAGE_SIZE)int sesPages//OK
    			)
    {
    	 System.out.println("�õ�Username:"+form.getUsername());
    	 System.out.println("�õ�Birthday:"+form.getBirthday());
    	 System.out.println("�õ�Gender:"+form.getGender());
    	 System.out.println("�õ�pageSize:"+pageSize);
    	 
    	 //------��ҳ
    	long totalCount=100;//��DB��������������м�¼��
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
	   		bean.setUsername("����"+i);
	   		bean.setRemark("��������,��������,��������,��������,��������,��������,��������,��������,��������,");
	   		allData.add(bean);
	   	}
    	modelMap.addAttribute("allData", allData);
    	
    	Map<String,String> allGender=new TreeMap<>();
    	allGender.put("", "--����--");
    	allGender.put("M", "��");
    	allGender.put("F", "Ů");
    	modelMap.addAttribute("allGender", allGender);
    	//modelMap.addAttribute("form", form);//���ü�
    	return "query";
    }
    
}