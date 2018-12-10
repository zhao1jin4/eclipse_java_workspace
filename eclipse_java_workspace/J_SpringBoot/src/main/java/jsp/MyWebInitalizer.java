package jsp;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
 
// SpringBootServletInitializer 实现 Spring自己的  WebApplicationInitializer  类
//spring-web-4.0.6.RELEASE.jar/META-INF/services/javax.servlet.ServletContainerInitializer 中是 SpringServletContainerInitializer (实现了标准的servlet类 ServletContainerInitializer ),
// 内部读取了 实现 WebApplicationInitializer  类(spring自己 为不使用web.xml设计)
public class MyWebInitalizer extends SpringBootServletInitializer {
    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(JSPApplication.class);
    }
}
