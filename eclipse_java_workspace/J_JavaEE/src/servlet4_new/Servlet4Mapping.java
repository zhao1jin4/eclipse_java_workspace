package servlet4_new;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.MappingMatch;

@WebServlet({"/path/*", "*.ext"})
public class Servlet4Mapping extends HttpServlet {
 
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) 
                         throws IOException 
    {
    	System.out.println("Servlet4Mapping" );
        HttpServletMapping mapping = request.getHttpServletMapping();
        MappingMatch extension= MappingMatch.EXTENSION;
        MappingMatch path= MappingMatch.PATH;
        String map = mapping.getMappingMatch().name();
        //如请求是 path/servlet4 值是PATH
        //如请求是 servlet4.ext 值是EXTENSION
        
        String value = mapping.getMatchValue();
        //如请求是 path/servlet4 值是 servlet4
        //如请求是 servlet4.ext  值是 servlet4
        
        String pattern = mapping.getPattern();
        //如请求是 path/servlet4 值是 /path/*
        //如请求是 servlet4.ext 值是*.ext
        
        String servletName = mapping.getServletName();//servlet4_new.Servlet4Mapping
        
   }
 
}