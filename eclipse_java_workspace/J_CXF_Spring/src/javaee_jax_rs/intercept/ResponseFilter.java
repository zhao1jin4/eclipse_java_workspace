package javaee_jax_rs.intercept;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class ResponseFilter implements ContainerResponseFilter 
{
    private final AtomicReference<Date> date = new AtomicReference<Date>();
    @Override
    public void filter(ContainerRequestContext containerRequestContext,
                       ContainerResponseContext containerResponseContext) throws IOException {
        Date currentDate = new Date();
        final Date lastDate = date.getAndSet(currentDate);
        containerResponseContext.getHeaders().add("previous-response", lastDate == null ? "this is the first response":lastDate.toString());
        containerResponseContext.getHeaders().add("this-response", currentDate.toString());
    }
}
