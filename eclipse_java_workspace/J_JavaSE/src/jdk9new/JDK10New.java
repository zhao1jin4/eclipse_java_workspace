package jdk9new;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;

public class JDK10New {

	public static void main(String[] args) throws Exception { 
		
//		copyOf();
		
		
//		Optional x=Optional.empty();
//		x.orElseThrow(exceptionSupplier)
		
		
//		Parallel Full GC for G1 
		
		FileInputStream  fileInput=new FileInputStream (new File("c:/tmp/out.txt"));
//		fileInput.close();//Èç²»close
		
		
		//Java Improvements for Docker Containers 
//		-XX:-UseContainerSupport
//		-XX:ActiveProcessorCount=count
	}
	public  static void copyOf()
	{
		//copyOf
				List<String> list=new ArrayList<>();
				list.add("one");
				List<String> newList=List.copyOf(list);
				System.out.println(newList);

				Set<String> set =new HashSet<>();
				set.add("one");
				Set<String> newSet=Set.copyOf(set);
				System.out.println(newSet);
				

				Map<String,Object> map=new HashMap<>();
				map.put("one", "11");
				Map<String,Object> newMap=Map.copyOf(map);
				System.out.println(newMap);
	}
	public  void jdk8Collectors()
	{


//		Collectors.toUnmodifiableList();
//		Collectors.toUnmodifiableSet();
//		Collectors.toUnmodifiableMap(keyMapper, valueMapper)();
		
		List<String> list=new ArrayList<>();
		list.add("one");
		
		
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
