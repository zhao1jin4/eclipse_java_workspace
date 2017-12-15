package jdk8_new;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {
	public static void testString(String ... numbers) {
		  List<String> l = Arrays.asList(numbers);
	        //������ͨ����˲��д���
		  Stream s=  ((Stream) l.stream().sequential());
		  
	        List<Integer> r = l.stream() //Stream<Integer> ,�м䷽����Զ���ص���Stream
	                .map(e -> Integer.parseInt(e))//Ҫ��Function�ӿ�(�Ǳ� @FunctionalInterface),-> ��apply����
	                .filter(e -> e > 2)
	                .distinct()
	                .collect(Collectors.toList());
	        System.out.println("testInt result is: " + r);
	}
	public static void testInt(Integer... numbers) {
        List<Integer> l = Arrays.asList(numbers);
        //������ͨ����˲��д���
        List<Integer> r = l.stream() //Stream<Integer> ,�м䷽����Զ���ص���Stream
                .map(e -> new Integer(e))//Ҫ��Function�ӿ�(�Ǳ� @FunctionalInterface),-> ��apply����
                .filter(e -> e > 2)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("testInt result is: " + r);
    }
	public static void main(String[] args) {
		//testInt(2, 3, 4, 2, 3, 5, 1);
		testString("2", "3", "4", "2", "3", "5", "1");
	}

}
