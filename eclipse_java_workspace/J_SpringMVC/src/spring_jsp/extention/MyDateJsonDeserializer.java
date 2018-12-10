package spring_jsp.extention;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class MyDateJsonDeserializer extends JsonDeserializer<Date>
{    
    public Date deserialize(JsonParser jp, DeserializationContext ctxt)  
    {    
        try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(jp.getText());
		} catch (Exception e) {
			e.printStackTrace();
		} 
        return null;
    }    
}    