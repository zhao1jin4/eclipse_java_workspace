package jdk8_new;
//import org.junit.Test; //junit 4 

import org.junit.jupiter.api.Test; //junit 5 
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

  

public class LamdabUseful {
	public static void main(String[] args) 
	{
		{	 
			//��Է���boolean�ĵ�����������
			Predicate<String> predicate = (s) -> s.length() > 0;
			
			predicate.test("foo");              // true
			predicate.negate().test("foo");     // false
			
			Predicate<Boolean> nonNull = Objects::nonNull;
			Predicate<Boolean> isNull = Objects::isNull;
			
			Predicate<String> isEmpty = String::isEmpty;
			Predicate<String> isNotEmpty = isEmpty.negate();
		}
		{
			//<�������ͣ���������>
			Function<String, Integer> toInteger = Integer::valueOf;
			Function<String, String> backToString = toInteger.andThen(String::valueOf);
			
			backToString.apply("123");     // "123"
		}
		{
			//�޲Σ��·�������
			Supplier<Person> personSupplier = Person::new;
			personSupplier.get();   // new Person
		}
		{
			//��һ���Σ��޷���
			Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
			greeter.accept(new Person("Luke"));
		}
		{
			Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
	
			Person p1 = new Person("John");
			Person p2 = new Person("Alice");
	
			comparator.compare(p1, p2);             // > 0
			comparator.reversed().compare(p1, p2);  // < 0
		}
		{
			Optional<String> optional = Optional.of("bam");
	
			optional.isPresent();           // true
			optional.get();                 // "bam"
			optional.orElse("fallback");    // "bam"
	
			optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
		}
	}
}
