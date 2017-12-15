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
		ByteArrayInputStream  input =new ByteArrayInputStream ("这是下载一个动态生成的下载内容".getBytes());
		response.setContentType("application/x-msdownload");
		//response.setHeader("Content-disposition","inline;filename=workbook.xls");//inline显示在浏览器中
		String cn_filename=new String("中文文件名.txt".getBytes("UTF-8"),Charset.forName("iso8859-1"));
//		response.setCharacterEncoding("UTF-8");//对header的文件名没用的
		response.addHeader("Content-Disposition", "attachment;filename="+cn_filename);//attachment会提示下载
		ServletOutputStream output=response.getOutputStream();
		int len;
		while((len=input.read(buffer))!=-1)
		{
			output.write(buffer,0,len);
		}
		output.close();//可以关闭
		input.close();
	}
   //MyWebBindingInitializer -> @ControllerAdvice -> @Controller 
	@InitBinder	//需要处理Date的时候,自动调用这个方法
	public void initBinder(WebDataBinder binder)//要用 WebDataBinder
	{
		//binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());//对图片是byte[]的附件做上传
		
		//为了页面提交的字串可以转换为Date类
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
	//web.xml中所有的filter,和用的servlet加 <async-supported>true</async-supported>
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
		//对图片是byte[]的附件做上传
		System.out.println("FileUploadBean:Length===" + bean.getImg().length);
		if (bean != null)
		{
			byte[] buff = bean.getImg();
			FileOutputStream out = new FileOutputStream(new File("c:/temp/xx.upload"));//原始文件名是??
			out.write(buff);
			out.close();
		}
		  */
		//------------------------------------------
		//对于MultipartFile的方式
		//中文文件名OK
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
