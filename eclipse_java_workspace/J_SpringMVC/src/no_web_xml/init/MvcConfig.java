package no_web_xml.init;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan({"spring_jsp.annotation" ,"myservlet.spring",})
public class MvcConfig
{
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver()
	{
		InternalResourceViewResolver view=new InternalResourceViewResolver();
		//view.setViewClass(JstlView.class); 
		view.setPrefix("/WEB-INF/jsp/");
		view.setSuffix(".jsp");
		return view;
	}
	
	//文件上传   必须是 id="multipartResolver" 
	@Bean
	public CommonsMultipartResolver multipartResolver()
	{
		CommonsMultipartResolver multi=new CommonsMultipartResolver();
		multi.setDefaultEncoding("UTF-8");
		multi.setMaxUploadSize(10000000);//单位是 bytes
		return multi;
	}
	 

	//i18n
//	@Bean
//	public ResourceBundleMessageSource messageSource()
//	{
//		ResourceBundleMessageSource msg=new ResourceBundleMessageSource();
//		msg.setBasename("");
//		return msg;
//	}
	
	//JSON
	@Bean
	public MethodInvokingFactoryBean objectMapper()
	{
		ObjectMapper mapper=new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));//对Timestamp类型
		mapper.setSerializationInclusion( JsonInclude.Include.NON_NULL);//不显示null
		/*
		//自定义null值如何显示
		DefaultSerializerProvider.Impl iml=new DefaultSerializerProvider.Impl();
		iml.setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException
			{
				gen.writeString("");  
			}});
		mapper.setSerializerProvider(iml);
		*/ 
		//代理
		MethodInvokingFactoryBean proxy=new MethodInvokingFactoryBean();
		proxy.setTargetObject(mapper);
		proxy.setTargetMethod("configure");
		proxy.setArguments(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);//false=反序列化遇到未知属性不报异常
		return  proxy;
	}
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(@Autowired ObjectMapper objectMapper)
	{
		MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);
		return converter;
	}
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter(@Autowired MappingJackson2HttpMessageConverter jsonConverter)
	{
		RequestMappingHandlerAdapter handler=new RequestMappingHandlerAdapter();
		handler.setMessageConverters(Arrays.asList(jsonConverter,
				new Jaxb2RootElementHttpMessageConverter(),//xml
				new StringHttpMessageConverter()));
		handler.setCacheSeconds(0);
		//handler.setWebBindingInitializer(webBindingInitializer);
		return handler;
		
	}
}	 
