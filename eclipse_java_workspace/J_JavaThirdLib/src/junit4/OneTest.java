package junit4;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class OneTest {
  @Test
  public void method1() {
    System.out.println("in method1");
    assertEquals("123","12"+3);
  }
  
  @Test
  public void method2() {
    System.out.println("in method2");
  }
  
}