package spring_jsp.extention;
 
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
@Configuration 
public class  Configuration implements WebMvcConfigurer { 
    @Override
    public void addInterceptors(InterceptorRegistry registry) { 
        registry.addInterceptor(new LogInterceptor()); 
    }
}

 */
public class LogInterceptor implements HandlerInterceptor {
    Logger log= LoggerFactory.getLogger(HandlerInterceptor.class); 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle:handler={} ,ParameterMap={},ContentLength={}", handler,
                ToStringBuilder.reflectionToString(request.getParameterMap()),request.getContentLength());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle handler={} modelAndView={},ContentType={}",handler,modelAndView,response.getContentType());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
