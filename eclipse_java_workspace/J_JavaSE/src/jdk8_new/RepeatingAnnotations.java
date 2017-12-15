package jdk8_new;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.atomic.LongAdder;
 
 
 
public class RepeatingAnnotations {
 
   @Target( ElementType.TYPE )
   @Retention( RetentionPolicy.RUNTIME )
   public @interface Filters {
       Filter[] value();
   }
 
   @Target( ElementType.TYPE )
   @Retention( RetentionPolicy.RUNTIME )
   @Repeatable( Filters.class )   //新功能
   public @interface Filter {
       String value();
   };
 
 
   @Filter( "filter1" )
   @Filter( "filter2" )
   public interface Filterable {
 
   }
 
   public static void main(String[] args) {
 
       for( Filter filter: Filterable.class.getAnnotationsByType( Filter.class ) ) //新功能
       {
           System.out.println( filter.value() );
       }

		LongAdder x =null;  //比  AtomicLong 的好  ,将把一个value拆分成若干cell，把所有cell加起来，就是value
		//初始情况，LongAdder与AtomicLong是相同的，只有在CAS失败时，才会将value拆分成cell，每失败一次，都会增加cell的数量
   }
 
}
