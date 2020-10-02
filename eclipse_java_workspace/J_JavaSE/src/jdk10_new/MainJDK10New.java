package jdk10_new;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

public class MainJDK10New {
	public static void main(String[] args) {
		
		
		var str=new String("abc123");//var �����ƶ�
		
		StringReader reader=new StringReader(str);
		StringWriter writer=new StringWriter();
		try {
			reader.transferTo(writer);//JDK 10 new 
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(writer.toString());
		System.out.println(Runtime.version());//JDK 9
		System.out.println(Runtime.version().feature());//���JDK�汾��JDK 10
		System.out.println(Runtime.version().update());//���JDK���£�JDK 10
		System.out.println(Runtime.version().interim());
		System.out.println(Runtime.version().patch());
		
		
		//copyOf  toUnmodifiableXxx
		var list=new ArrayList<String>();
		list.add("one");
		var cloneList=List.copyOf(list);//JDK 10
		
		List<String> list2 = cloneList.stream()
				  // .map(Person::getName)
				   .collect(Collectors.toUnmodifiableList());//JDK 10
		//list2.add("two");//����
		
		var set =new HashSet<String>();
		set.add("one");
		var cloneSet=Set.copyOf(set);//JDK 10
		var set2=cloneSet.stream().collect(Collectors.toUnmodifiableSet() );//JDK 10 
		
		var map =new HashMap<String,Object>();
		map.put("one", "һ");
		var cloneMap=Map.copyOf(map);//JDK 10
		
		
		Charset utf8=Charset.forName("UTF-8");
		 
		 String encStr=URLEncoder.encode("�й�", utf8);//JDK 10
		 System.out.println(encStr);
		 String decStr=URLDecoder.decode(encStr, utf8);//JDK 10
		 System.out.println(decStr);

		//G1 ���л�
		//ɾ javah
		//java doc ��ǿ
		//��ǿ Docker  Ĭ�ϴ򿪣��ɽ���-XX:-UseContainerSupport  CPU��  -XX:ActiveProcessorCount=count
		 // -XX:InitialRAMPercentage  -XX:MaxRAMPercentage -XX:MinRAMPercentage
		
		 StampedLock stampedLock=new StampedLock();
		 long stamp = stampedLock.writeLock();
		 
		 StampedLock.isLockStamp(stamp);//JDK 10
		 StampedLock.isOptimisticReadStamp(stamp);
		 StampedLock. isReadLockStamp(stamp);
		 StampedLock. isWriteLockStamp(stamp);
		 
		StackWalker.getInstance( StackWalker.Option.RETAIN_CLASS_REFERENCE).forEach(System.out::println);//JDK9
		 
		StackWalker.StackFrame stackFrame =null;//JDK 9
		stackFrame.getDescriptor();//JDK 10
		stackFrame.getMethodType();//JDK
		
		Optional optional=null;
		OptionalDouble optionalDbl=null;
		
		optional.orElseThrow();//JDK 10
  
	
		 
		
		
	}
}
