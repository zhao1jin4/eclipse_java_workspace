 package org.apache.camel.example.spring.boot.rest.jpa;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    private Environment env;

 //http://localhost:8888/camel-rest-jpa/books ,  http://localhost:8888/camel-rest-jpa/books/order/1
   /*
camel:
  springboot:
    name: CamelRestJpa
  component:
    servlet:
      mapping:
        contextPath: /camel-rest-jpa/*
        
    */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
            restConfiguration()
                .contextPath("/camel-rest-jpa")
                
                	//swagger ,maven有camel-swagger-java，仿问  http://localhost:8888/camel-rest-jpa/api-doc 就有json返回了
                	.apiContextPath("/api-doc")
                    .apiProperty("api.title", "Camel REST API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true") 
                    
                    
                    .apiContextRouteId("doc-api")
                    .port(env.getProperty("server.port", "8080"))
                .bindingMode(RestBindingMode.json);

            rest("/books").description("Books REST service")
                .get("/").description("The list of all the books")
                    .route().routeId("books-api")
                    .bean(Database.class, "findBooks")
                    .endRest()
                .get("order/{id}").description("Details of an order by id")
                    .route().routeId("order-api")
                    .bean(Database.class, "findOrder(${header.id})");
        }
    }

}