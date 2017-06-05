package servlet3_new.nowebxml;
import java.lang.reflect.Modifier;    
import java.util.Set;    
    
import javax.servlet.ServletContainerInitializer;    
import javax.servlet.ServletContext;    
import javax.servlet.ServletException;    
import javax.servlet.annotation.HandlesTypes;    


//(不能是web项目的META-INF) WEB-INF/lib/xxx.jar/META-INF/services/javax.servlet.ServletContainerInitializer 中写实现 implements ServletContainerInitializer全类名
@HandlesTypes(WebParameter.class)    
public class WebConfiguration implements ServletContainerInitializer {    
    @Override    
    public void onStartup(Set<Class<?>> webParams, ServletContext servletCtx)  throws ServletException 
    //webParams 的值为项目中所有实现  @HandlesTypes(WebParameter 的类
    {    
        if (webParams != null)
        {    
            for (Class<?> paramClass : webParams) 
            {    
                if (!paramClass.isInterface() && !Modifier.isAbstract(paramClass.getModifiers()) &&    
                        WebParameter.class.isAssignableFrom(paramClass))  //是多余的判断
                {    
                    try {    
                        ((WebParameter) paramClass.newInstance()).loadInfo(servletCtx);    
                    }    
                    catch (Throwable ex) {    
                        throw new ServletException("Failed to instantiate WebParam class", ex);    
                    }    
                }    
            } 
        } 
    } 
}    