package spring_freemarker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("freemarkerRegister")
public class RegisterFreemarkerController
{
	protected final Log logger = LogFactory.getLog(getClass());
	
	@InitBinder	//��Ҫ����Date��ʱ��,�Զ������������
	public void initBinder(WebDataBinder binder)//Ҫ�� WebDataBinder
	{
		System.out.println("����initBinder");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	@RequestMapping("initRegister")
	public ModelAndView initRegister()
	{
		ModelAndView mv=new ModelAndView();
		
		mv.getModelMap().addAllAttributes(generateListData());
		
		RegisterBean initValue=new RegisterBean();
		initValue.setGender("M");//F , M
		initValue.setId(123123);//��ֹ����,
		initValue.setClazz_id(22);
		initValue.setBirthday(new Date());
		mv.getModel().put("form",initValue);
		mv.setViewName("register");
		
		try {
			Thread.sleep(3*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	@RequestMapping("submitRegister")
	public ModelAndView submitRegister(@Valid @ModelAttribute("form") RegisterBean bean ,BindingResult result)//RegisterBean����ʵ�� Serializable,��@Valid�ͱ���Ҫ�� BindingResult
	{
		System.out.println(bean);//stroe in DB
		
		ModelAndView mv=new ModelAndView();
		
		mv.getModelMap().addAllAttributes(generateListData());
		
		
		if(result.hasErrors())
			mv.setViewName("register");//fail
		else
			mv.setViewName("ok");
		return mv;
	}
	

	
	private Map<String,Object>  generateListData()
	{
		Map<String,String> clazzes=new HashMap<>(); //������Map<String,String>
		clazzes.put("11","�����ϵ");
		clazzes.put("22","����ϵ");
		
//		Map<String,String> genders=new HashMap<>();
//		genders.put("T","��");
//		genders.put("F","Ů");
		
		Map<String,String> courses=new HashMap<> ();
		courses.put("1001","����");
		courses.put("1002","Ӣ��");
		courses.put("1003","����");
		
		Map<String,String> interests=new HashMap<>();
		interests.put("2001","����");
		interests.put("2002","����");
		interests.put("2003","����");
		
		Map<String,Object> attribute=new HashMap<String,Object>();
		attribute.put("allInterests", interests);
		attribute.put("allCourses", courses);
//		attribute.put("genders", genders);
		attribute.put("allClazz", clazzes);
		
		//-------------
		List<ValueLablePair> data=new ArrayList<>();
		ValueLablePair clazz1=new ValueLablePair();
		clazz1.setValue(11);
		clazz1.setLabel("����1111");
		data.add(clazz1);
		
		ValueLablePair clazz2=new ValueLablePair();
		clazz2.setValue(22);
		clazz2.setLabel("����22222");
		data.add(clazz2);
		
		attribute.put("otherData", data);	
		//---
				
		return attribute;
	}
	
}
