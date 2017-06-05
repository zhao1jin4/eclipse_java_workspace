package apache_velocity;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
public class MainVelocity 
{
	public static void test1() throws Exception 
	{
		Properties p = new Properties();
	    p.setProperty("file.resource.loader.path", "D:/Program/eclipse_java_workspace/J_JavaThirdLib/src/apache_velocity/");
	    Velocity.init(p);
		VelocityContext context = new VelocityContext();
		context.put( "name", new String("Velocity") );
		Template  template = Velocity.getTemplate("mytemplate.vm","UTF-8");
		StringWriter sw = new StringWriter();
		template.merge( context, sw );
		System.out.println(sw);
	}
	public static void main(String[] args) throws Exception {
		test1();
	}
}
