
package javaee_jax_rs;

import javaee_jax_rs.intercept.MessageWriter;
import javaee_jax_rs.intercept.ResponseFilter;
import javaee_jax_rs.intercept.ValidCharacterInterceptor;

import javax.json.stream.JsonGenerator;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//http://localhost:8080/J_Java_EE/chat/generator
@ApplicationPath("/chat") //URL的前缘,如为"/",http://localhost:8080/J_Java_EE/generator
public class ConfigApplication extends Application 
{
    public Set<Class<?>> getClasses() 
    {
        Set<Class<?>> set = new HashSet<>();
        set.add(ParserResource.class);
        set.add(GeneratorResource.class);
        set.add(ObjectResource.class);
        set.add(ArrayResource.class);
        set.add(StructureResource.class);
        set.add(AsyncResource.class);
        set.add(ResponseFilter.class);//implements ContainerResponseFilter 
        set.add(ValidCharacterInterceptor.class);//implements ReaderInterceptor ,对方法JS有参数传入到Java方法String的参数时
        set.add(MessageWriter.class);//@Provider XX implements MessageBodyWriter<T> 如果返回的不是Response的自己定义类如何输出
        return set;
    }
    @Override
    public Map<String, Object> getProperties() 
    {
        return new HashMap<String, Object>() {{ put(JsonGenerator.PRETTY_PRINTING, true); }};
    }
}
