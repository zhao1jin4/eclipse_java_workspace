package jax_rs_spring2;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.multipart.Multipart;


 @Path("/hello")
 public class HelloWorld {
	 
     @GET
     @Path("/echo/{input}")
     @Produces("text/plain")
     public String ping(@PathParam("input") String input) {
         return input + ":in server!";
     }

     
     
     
     
     
     
     
     
     //@POST  //使用firfox的poster
     @Produces(MediaType.APPLICATION_JSON ) //"application/json"
    // @Consumes(MediaType.APPLICATION_JSON )
     @Path("/jsonBean")
     public Response modifyJson() {// JsonBean in
    	 JsonBean input =new JsonBean();
    	 input.setParam(new HashMap<String, Object>());
            input.setCommand(222);
            input.getParam().put("content", "welcome to server!");
           return Response.ok().entity(input).build();
     }
     

	 //@POST  //使用firfox的poster
	 @Produces(MediaType.APPLICATION_XML ) //"application/xml"
	 //@Consumes(MediaType.APPLICATION_XML )
	 @Path("/changeXMLResponse")
	 public Response changeXMLResponse() //
	 { 
//		 JsonBean in
//		 System.out.println(in.getPlatformType());
		 JsonBean input =new JsonBean();
		 input.setParam(new HashMap<String, Object>());
	        input.setCommand(222);
	        input.getParam().put("content", "welcome to server!");
	       return Response.ok().entity(input).build();
	 }
	 @Path("/changeXMLObject")
	 @Produces(MediaType.APPLICATION_XML ) //"application/xml"
	 public JsonBean changeXMLObject() 
	 { 
		 JsonBean input =new JsonBean();
		 input.setParam(new HashMap<String, Object>());
	        input.setCommand(222);
	        input.getParam().put("content", "welcome to server!");
	    return input;
	 }
	 
     @POST
     @Consumes(MediaType.MULTIPART_FORM_DATA)
     public void upload(@Multipart InputStream is) {
    	 
     }
  
     @GET
 	@Path(value="/{emailAddress:.+@.+\\.[a-z]+}")
 	public void getByEmailAddress(@PathParam(value="emailAddress")  String emailAddress) {
 	}
     
     
     
     
}
  
 
@Provider
@Consumes("text/xml")
 class MyStreamProvider implements MessageBodyReader<Object> 
 {
		@Override
		public boolean isReadable(Class<?> cls, Type arg1, Annotation[] arg2,
				MediaType arg3) {
			return InputStream.class.isAssignableFrom(cls) || Reader.class.isAssignableFrom(cls);
		}

		@Override
		public Object readFrom(Class<Object> arg0, Type arg1,
				Annotation[] arg2, MediaType arg3,
				MultivaluedMap<String, String> arg4, InputStream arg5)
				throws IOException, WebApplicationException {
			return null;
		}}