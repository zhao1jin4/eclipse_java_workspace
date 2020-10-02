 package apache_camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

import apache_camel.service.StudentService;
import apache_camel.vo.CommonResponse;
import apache_camel.vo.Student;

@SpringBootApplication
public class MainCamelRest extends SpringBootServletInitializer {

 

   /*
camel:
  springboot:
    name: CamelRestJpa
  component:
    servlet:
      mapping:
        contextPath: /camel-rest-jpa/*
        
     Method:post
     Content-Type: application/json
     http://localhost:8888/camel-rest-jpa/student/getById/1
     http://localhost:8888/camel-rest-jpa/student/save1
     http://localhost:8888/camel-rest-jpa/student/asyncGen
       
    */
    public static void main(String[] args) {
        SpringApplication.run(MainCamelRest.class, args);
    }
    
    @Component
    class RestApi extends RouteBuilder
    {
    	@Autowired 
    	private StudentService studentService;

        @Override
        public void configure() {
       	 restConfiguration()
       	 .contextPath("/camel-rest-jpa")
	     // .component("servlet")
       	 
       	 //swagger ,maven有camel-swagger-java，仿问  http://localhost:8888/camel-rest-jpa/api-doc 就有json返回了
       	 .apiContextPath("/api-doc")
       	 .apiProperty("api.title","User API")
       	 .apiProperty("api.version", "1.2.3")
       	 //enable cors
       	 .apiProperty("cors","true")
       	 
       	 
	      .bindingMode(RestBindingMode.json);

		//全局异常处理 ，会自动找和抛出异常继承关系最近的
		onException(Exception.class).handled(true).process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				Exception ex=exchange.getProperty(Exchange.EXCEPTION_CAUGHT,Exception.class);
				System.out.println("处理了异常");
				ex.printStackTrace();
			}
		 }).setBody(simple("{\"errorType\":${exception.class.name},\"errorMessaage\":${exception.message}}")).end(); 
		//exception是抛出的异常类

		onException(RuntimeException.class).handled(true)
		.setHeader(Exchange.HTTP_RESPONSE_CODE,constant(400))
		.setBody(simple("{\"errorType\":${exception.class.name},\"errorMessaage\":${exception.message}}"))
		// \"stackTrace\":${exception.stackTrace}是不行的
		.end();
		
		
		rest("/student").id("student_id").description("student api desc") .consumes("application/json").produces("application/json")
			.post("/save").description("save student").type(Student.class).outType(CommonResponse.class).to("bean:studentService?method=save(${body})")
			.post("/save1").description("save student").type(Student.class).outType(CommonResponse.class)
								.route().bean(studentService,"save").endRest()
			.post("/getById/{stuId}").description("get student").type(Student.class).outType(CommonResponse.class).to("direct:getById")
			.post("/asyncGen").description("asyncGen  ").
				param().name("regenerate").type(RestParamType.query).dataType("boolean").defaultValue("true").endParam()
				.type(Student.class).outType(CommonResponse.class).to("seda:asyncGen")
			; //还可继续.type()
			 //inOnly过时了 
		
		from("direct:getById").to("bean:studentService?method=getById(${header.stuId})");//也可用全部参数 ${headers}
		from("seda:asyncGen").to("bean:studentService?method=asyncGen(${body},${header.regenerate})");
		
        }
    }

}