 
package jax_rs_spring2;

import java.net.URI;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyExceptionMapper implements ExceptionMapper<Exception> {
    public Response toResponse(Exception exception) {
    	System.out.println("----in ExceptionMapper");
    	exception.printStackTrace();
        return Response.status(Response.Status.EXPECTATION_FAILED).
        		location(URI.create("/hello/echo/1"))
        		.build();
    }
}
