package not_web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * spring-web.xx.jar/META-INF/services/javax.servlet.ServletContainerInitializer 中写实现 implements ServletContainerInitializer 全类名
 * 如想做web项目加入这个，package 修改为war 就可以了,是Spring的 WebApplicationInitializer
 */
public class MyWebApplicationInitializer extends SpringBootServletInitializer {
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainApplication.class);
	}

}