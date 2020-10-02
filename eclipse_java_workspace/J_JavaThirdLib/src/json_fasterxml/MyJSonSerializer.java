package json_fasterxml;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MyJSonSerializer extends JsonSerializer<ObjectId>{
	@Override
	public void serialize(ObjectId objId, JsonGenerator gen, SerializerProvider provider) throws IOException {
		 if(objId==null)
			 gen.writeNull();
		 else
			 gen.writeString(objId.toString());
	} 
}
