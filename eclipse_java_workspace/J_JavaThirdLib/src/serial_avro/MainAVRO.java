package serial_avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class MainAVRO {
/*
 
 <dependency>
  <groupId>org.apache.avro</groupId>
  <artifactId>avro</artifactId>
  <version>1.8.2</version>
</dependency>
<dependency>
    <groupId>org.codehaus.jackson</groupId>
    <artifactId>jackson-mapper-asl</artifactId>
    <version>1.9.13</version>
</dependency>

jackson-core-asl-1.9.13.jar
jackson-mapper-asl-1.9.13.jar

java -jar /path/to/avro-tools-1.8.2.jar compile schema user.avsc .
 */
	
	
	public static void main(String[] args) throws Exception {
		
		binaryFormat();
		jsonFormat();
		      
	}
	public static void binaryFormat()  throws Exception {
		
	}
	public static void jsonFormat()  throws Exception {

		 org.codehaus.jackson.JsonParseException x;
		User user1 = new User();
		user1.setName("Alyssa");
		user1.setFavoriteNumber(256);
		// Leave favorite color null

		// Alternate constructor
		User user2 = new User("Ben", 7, "red");

		// Construct via builder
		User user3 = User.newBuilder()
		             .setName("Charlie")
		             .setFavoriteColor("blue")
		             .setFavoriteNumber(null)
		             .build();

		
		File file=new File("/tmp/users.avro");
		
		// Serialize user1, user2 and user3 to disk
		DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
		DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
		dataFileWriter.create(user1.getSchema(), file);
		dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.append(user3);
		dataFileWriter.close();//保存的文件内容就是JSON schema
		
		 
		// Deserialize Users from disk
		DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
		DataFileReader<User> dataFileReader = new DataFileReader<User>(file, userDatumReader);
		User user = null;
		while (dataFileReader.hasNext()) { 
			user = dataFileReader.next(user);
			System.out.println(user);
		}
	}
}
