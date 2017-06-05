package instrument;

public class InstrumentMain {
	public static void main(String[] args) {
		   System.out.println("enter main"); 
		   System.out.println(new TransClass().getNumber()); 
	}
}
//jar -cvfm myinsturment.jar META-INF/MANIFEST.MF  instrument  打包一定要加m
//jar -cvfm myinsturment.jar instrument/META-INF/MANIFEST.MF  instrument
// java -javaagent:myinsturment.jar  -jar myinsturment.jar 
 

