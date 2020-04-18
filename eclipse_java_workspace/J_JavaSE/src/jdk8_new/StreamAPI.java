package jdk8_new;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
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
	                //.parallel()//并行  内部使用ForkJoinPool,默认线程数是处理器数
	                .filter(e -> e > 2)
	                //.sequential()//串行
	                .distinct()
	                .collect(Collectors.toList());
	        System.out.println("testInt result is: " + r);
	        
	       List<Integer> list1=Arrays.stream(numbers).mapToInt( Integer::parseInt).mapToObj(Integer::new).collect(Collectors.toList());
	       List<Integer> list2=Arrays.stream(numbers).map(e -> Integer.parseInt(e)).collect(Collectors.toList());
	       
	         			
	        
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
        
        
        //--reduce
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Integer sumReduce = integers.stream().reduce(100, Integer::sum);//起始种子值
        System.out.println(sumReduce);
        
        List<String> strs = Arrays.asList("H", "E", "L", "L", "O");
        String concatReduce = strs.stream().reduce("START_", String::concat);
        System.out.println(concatReduce); 
        
        String join= strs.stream().collect(Collectors.joining(", "));
        System.out.println(join); 
        
        Optional accResult = Stream.of(1, 2, 3, 4)
                .reduce((acc, item) -> { 
                    acc += item;   
                    return acc;
                });
        System.out.println("accResult: " + accResult.get()); 
       
         
    }
	public static void testObject()
	{
		List<Person> people=Arrays.asList(new Person("li") ,new Person("wang"));
		List<File> things=Arrays.asList(new File("C:"),new File("D:"));
		Employee[] emps=new Employee[] {
					new Employee("java",2000,new Department("IT")),
					new Employee("java",3000,new Department("OPS")),
					new Employee("C",4000,new Department("IT"))};
		
		boolean isEmpty=Arrays.stream(emps).anyMatch(item -> item.getSalary()>3500);//.allMatch 
				
		List<Employee> employees=Arrays.asList(emps);
		List<Student> students=Arrays.asList(new Student(80),new Student(45));
		int PASS_THRESHOLD=60;
		//JDK 8
		
		Map<String,List<Employee>> titleEmp=Arrays.stream(emps).collect(Collectors.groupingBy(Employee::getTitle));
		
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
	 
	}
	
	public static void main(String[] args) {
		
		testInt(2, 3, 4, 2, 3, 5, 1);
		testString("2", "3", "4", "2", "3", "5", "1");
		testObject();
	}	
	
}
class Department {
	private String  name;

	public Department(String name)
	{
		super();
		this.name = name;
	}


	public String getName()
	{
		return name;
	}
 

	@Override
	public int hashCode()
	{
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		return Objects.equals(name, other.name);
	}
	
}
class Employee {
	private Department department;
	private int salary;
	private String  title;
	
	public String getTitle()
	{
		return title;
	}

	public Employee(String title,int salary,Department dept)
	{
		this.title = title;
		this.salary = salary;
		this.department=dept;
	}

	public int getSalary()
	{
		return salary;
	}

	public  Department getDepartment()
	{
		return this.department;
	}
}
class Student {
	private int grade;

	public Student(int grade)
	{
		this.grade=grade;
	}

	public int getGrade()
	{
		return grade;
	}
	
}

