package apache_commons;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommonsCli {
	//commons-cli-1.4.jar
	
	//java CommonsCli -c=CN -t
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption("t", false, "display current time"); //false表示没有参数值
		options.addOption("c", true, "country code");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse( options, args);
		
		if(cmd.hasOption("t")) {
			System.out.println("have t");
		} 
		String countryCode = cmd.getOptionValue("c");
		if(countryCode != null)  {
			System.out.println("countryCode="+countryCode);
		}
	}
}
