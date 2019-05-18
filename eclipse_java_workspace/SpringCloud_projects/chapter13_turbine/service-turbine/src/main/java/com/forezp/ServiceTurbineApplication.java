package com.forezp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;


@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableTurbine

public class ServiceTurbineApplication {
	/*
	依次开启eureka-server、service-hi、service-lucy、service-turbine工程
	 http://localhost:8764/turbine.stream 有返回
	
	: ping
	data: {"reportingHostsLast10Seconds":0,"name":"meta","type":"meta","timestamp":1557926404506}
	
	
	//依次请求
    http://localhost:8762/hi?name=lisi
	http://localhost:8763/hi?name=lisi

	打开   http://localhost:8763/hystrix,输入监控流  http://localhost:8764/turbine.stream
 */
	public static void main(String[] args) {
		  SpringApplication.run( ServiceTurbineApplication.class, args );
	}
/*
@Bean
public ServletRegistrationBean getServlet() {
    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
    registrationBean.setLoadOnStartup(1);
    registrationBean.addUrlMappings("/hystrix.stream");
    registrationBean.setName("HystrixMetricsStreamServlet");
    return registrationBean;
}
*/
	
	
}
