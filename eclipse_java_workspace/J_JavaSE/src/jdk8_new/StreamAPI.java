package jdk8_new;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {
	
	//抄Scala 
	public static void testString(String ... numbers) {
		  List<String> l = Arrays.asList(numbers);
	        //大数据通过多核并行处理
		  	Stream<String> s=    l.stream().sequential();
		  
	        List<Integer> r = l.stream() //Stream<Integer> ,中间方法永远返回的是Stream
	                .map(e -> Integer.parseInt(e))//要求Function接口(是被 @FunctionalInterface),-> 是apply方法
	                .filter(e -> e > 2)
	                .distinct()
	                .collect(Collectors.toList());
	        System.out.println("testInt result is: " + r);
	}
	public static void testInt(Integer... numbers) {
        List<Integer> l = Arrays.asList(numbers);
        //大数据通过多核并行处理
        List<Integer> r = l.stream() //Stream<Integer> ,中间方法永远返回的是Stream
                .map(e -> Integer.valueOf(e))//要求Function接口(是被 @FunctionalInterface),-> 是apply方法
                .filter(e -> e > 2)
                .distinct()
//                .anyMatch(predicate)
//                .allMatch(predicate)
//                .noneMatch(predicate)
//                .sorted()
//                .count()
//               .reduce(accumulator)
                .collect(Collectors.toList());
        System.out.println("testInt result is: " + r);
    }
	public static void main(String[] args) {
		//testInt(2, 3, 4, 2, 3, 5, 1);
		testString("2", "3", "4", "2", "3", "5", "1");
		
		/*
		 *  
		 //JDK 8
		  
		   
		// Accumulate names into a List
		 List<String> list = people.stream()
		   .map(Person::getName)
		   .collect(Collectors.toList());

		 // Accumulate names into a TreeSet
		 Set<String> set = people.stream()
		   .map(Person::getName)
		   .collect(Collectors.toCollection(TreeSet::new));

		 // Convert elements to strings and concatenate them, separated by commas
		 String joined = things.stream()
		   .map(Object::toString)
		   .collect(Collectors.joining(", "));

		 // Compute sum of salaries of employee
		 int total = employees.stream()
		   .collect(Collectors.summingInt(Employee::getSalary));

		 // Group employees by department
		 Map<Department, List<Employee>> byDept = employees.stream()
		   .collect(Collectors.groupingBy(Employee::getDepartment));

		 // Compute sum of salaries by department
		 Map<Department, Integer> totalByDept = employees.stream()
		   .collect(Collectors.groupingBy(Employee::getDepartment,
		                                  Collectors.summingInt(Employee::getSalary)));

		 // Partition students into passing and failing
		 Map<Boolean, List<Student>> passingFailing = students.stream()
		   .collect(Collectors.partitioningBy(s -> s.getGrade() >= PASS_THRESHOLD));
		   */
	}

}
