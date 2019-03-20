package websocket.p2p_;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	registry.addViewController("/ws").setViewName("webSocket");
	registry.addViewController("/login").setViewName("login");
	registry.addViewController("/chat").setViewName("chat");
	}
	
}
 