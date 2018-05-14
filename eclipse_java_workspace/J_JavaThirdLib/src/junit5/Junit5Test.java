package junit5;

/* */
import org.junit.jupiter.api.Test; //Junit 5  
import static org.junit.jupiter.api.Assertions.*;//Junit 5 
public class Junit5Test {
	  
   @Test
   public void method1() {
    System.out.println("in method1");
    assertEquals("123","12"+3);
   }
}
