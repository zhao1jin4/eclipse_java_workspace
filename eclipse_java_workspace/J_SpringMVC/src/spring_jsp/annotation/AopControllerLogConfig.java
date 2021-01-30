package spring_jsp.annotation;
/*
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;


@Component  
@Aspect  
public class AopControllerLogConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AopControllerLogConfig.class);


    @Pointcut("execution(* com.xx.controller.*.*(..))")
    private void allController() {

    }

    @Around(value = "allController()")
    public Object recordLog(ProceedingJoinPoint proceedingJoinPoint) {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        //nginx代理 request.getHeader("X-Real-IP")
//        log.info("请求ip{} ", request.getRemoteAddr());

        MethodSignature targetMethod = (MethodSignature) proceedingJoinPoint.getSignature();
        LOG.info("--Controller收到请求url={}, method={} ,function={}," ,uri,method, targetMethod.toString());
       

   Enumeration<String> enumeration=request.getHeaderNames();
    Map<String,String> map=new HashMap<>();
    while(enumeration!=null && enumeration.hasMoreElements()){
      String header=enumeration.nextElement(); 
      map.put(header, request.getHeader(header));
     }
    LOG.info("--Controller收到http头: {}", map);
    
    
        Object[] args = proceedingJoinPoint.getArgs();
        HashSet<String> set = new HashSet<>();
        for (Object o : args) {
            set.add(String.valueOf(o));
        }
        LOG.info(""--Controlle收到参数{}", set);
        Object object;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            //控制台打印
            throwable.printStackTrace();
            //日志打印详细异常信息
            LOG.info("服务异常 {}",throwable.getMessage(),throwable);
            //controller层统一异常处理
//           //return "error";
            //return Result.error(throwable.getMessage());
        }
        LOG.info("--Controller返回{}", object);
        return object;
    } 
}
*/

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
