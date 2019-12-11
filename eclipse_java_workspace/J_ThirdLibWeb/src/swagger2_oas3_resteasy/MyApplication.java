package swagger2_oas3_resteasy;
 
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import swagger2_oas3_resteasy.user_store.PetStoreResource;
import swagger2_oas3_resteasy.user_store.UserResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationPath("/sample") //和web.xml对应
//在swagger-ui中请示是 http://localhost:8080/sample/user/user1 
//如何设置webContext???
public class MyApplication extends Application { 
	org.yaml.snakeyaml.events.Event y; //openapi.yaml 要这个 
    @Override
    public Set<Class<?>> getClasses() {
        return Stream.of(PetResource.class, 
        		PetStoreResource.class,UserResource.class,
        		OpenApiResource.class//就可以请求${pageContext.request.contextPath}/sample/openapi.json(.yaml) 为swagger-ui使用
        		).collect(Collectors.toSet());
    }
}
