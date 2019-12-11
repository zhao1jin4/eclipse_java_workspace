package swagger2_oas3_resteasy;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
//好像没有什么用
@Provider
public class JsonProvider implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    public JsonProvider() {
        objectMapper = Json.mapper();
    };

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
} 
