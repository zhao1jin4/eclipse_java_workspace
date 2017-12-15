package spring_jsp.annotation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import spring_jsp.annotation.form.Account;
import spring_jsp.annotation.form.FileUploadBean;
import spring_jsp.extention.MyPropertyEditor;
@Controller
public class DownloadUploadController {
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public void download(@RequestParam("file_id")int file_id,HttpServletResponse response) throws Exception
	{   
		System.out.println("file_id="+file_id);
		byte[] buffer=new byte[1024];
		ByteArrayInputStream  input =new ByteArrayInputStream ("��������һ����̬���ɵ���������".getBytes());
		response.setContentType("application/x-msdownload");
		//response.setHeader("Content-disposition","inline;filename=workbook.xls");//inline��ʾ���������
		String cn_filename=new String("�����ļ���.txt".getBytes("UTF-8"),Charset.forName("iso8859-1"));
//		response.setCharacterEncoding("UTF-8");//��header���ļ���û�õ�
		response.addHeader("Content-Disposition", "attachment;filename="+cn_filename);//attachment����ʾ����
		ServletOutputStream output=response.getOutputStream();
		int len;
		while((len=input.read(buffer))!=-1)
		{
			output.write(buffer,0,len);
		}
		output.close();//���Թر�
		input.close();
	}
   //MyWebBindingInitializer -> @ControllerAdvice -> @Controller 
	@InitBinder	//��Ҫ����Date��ʱ��,�Զ������������
	public void initBinder(WebDataBinder binder)//Ҫ�� WebDataBinder
	{
		//binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());//��ͼƬ��byte[]�ĸ������ϴ�
		
		//Ϊ��ҳ���ύ���ִ�����ת��ΪDate��
		//binder.registerCustomEditor(Date.class,new  MyPropertyEditor());
 		binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
	}

	@RequestMapping(value="/initUpload",method=RequestMethod.GET)
	public String initUpload(Model model) throws Exception
	{ 
		model.addAttribute("uploadForm", new FileUploadBean());
		return "/company_annotation/upload";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/asyncUpload")
	//web.xml�����е�filter,���õ�servlet�� <async-supported>true</async-supported>
	public Callable<String> processUpload(@RequestParam("img") final MultipartFile file) {
	    return new Callable<String>() {
	        public String call() throws Exception {
	        	byte buff[]=new byte[1024];
	        	OutputStream out=new FileOutputStream(new File("c:/temp/"+file.getOriginalFilename()));
	        	 InputStream input=file.getInputStream(); 
				 while(input.read(buff)!=-1) 
				  {
					  out.write(buff); 
				  }
				 out.close();
				 input.close();
	            return "ok";
	        }
	    };
	}
	
	
	@RequestMapping(value="/submitUpload",method=RequestMethod.POST)
	public void sumbitUpload( @ModelAttribute("uploadForm") FileUploadBean bean) throws Exception
	{   
		String des = bean.getDescription();
		//String des_utf8 = new String(des.getBytes("iso8859-1"), "UTF-8");
		System.out.println("DESPRI:===" + des);
		System.out.println("Date Format:===" + bean.getBirthday());

		/* 
		//��ͼƬ��byte[]�ĸ������ϴ�
		System.out.println("FileUploadBean:Length===" + bean.getImg().length);
		if (bean != null)
		{
			byte[] buff = bean.getImg();
			FileOutputStream out = new FileOutputStream(new File("c:/temp/xx.upload"));//ԭʼ�ļ�����??
			out.write(buff);
			out.close();
		}
		  */
		//------------------------------------------
		//����MultipartFile�ķ�ʽ
		//�����ļ���OK
		  byte[] buff=new byte[1024];
		  FileOutputStream out=new FileOutputStream(new File("c:/temp/"+bean.getImg().getOriginalFilename()));
		  InputStream  input=bean.getImg().getInputStream(); 
		  while(input.read(buff)!=-1) 
		  {
			  out.write(buff); 
		  }
		  out.close();
		  input.close(); 
		 for( MultipartFile photo:bean.getPhotos())
		 {
			 out=new FileOutputStream(new File("c:/temp/"+photo.getOriginalFilename()));
			 input=photo.getInputStream(); 
//				 photo.getContentType();
//				 photo.getSize();
			 while(input.read(buff)!=-1) 
			  {
				  out.write(buff); 
			  }
			 out.close();
			 input.close();
		 }
	}
}
