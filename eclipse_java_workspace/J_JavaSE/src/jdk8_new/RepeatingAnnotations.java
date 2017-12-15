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
   @Repeatable( Filters.class )   //�¹���
   public @interface Filter {
       String value();
   };
 
 
   @Filter( "filter1" )
   @Filter( "filter2" )
   public interface Filterable {
 
   }
 
   public static void main(String[] args) {
 
       for( Filter filter: Filterable.class.getAnnotationsByType( Filter.class ) ) //�¹���
       {
           System.out.println( filter.value() );
       }

		LongAdder x =null;  //��  AtomicLong �ĺ�  ,����һ��value��ֳ�����cell��������cell������������value
		//��ʼ�����LongAdder��AtomicLong����ͬ�ģ�ֻ����CASʧ��ʱ���ŻὫvalue��ֳ�cell��ÿʧ��һ�Σ���������cell������
   }
 
}
