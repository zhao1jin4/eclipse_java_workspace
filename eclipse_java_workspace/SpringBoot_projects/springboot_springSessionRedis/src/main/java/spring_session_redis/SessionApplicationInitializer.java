package spring_session_redis;

import javax.servlet.ServletContext;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class SessionApplicationInitializer extends AbstractHttpSessionApplicationInitializer {
    @Override
    protected void afterSessionRepositoryFilter(ServletContext servletContext) {
       // servletContext.addListener(new HttpSessionEventPublisher());
    }
}