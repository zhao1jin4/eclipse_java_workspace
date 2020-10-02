package ioc.depson;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "classSelf")
public class ClassSelf  {
	@Autowired //可以自已注入自己
	private ClassSelf myself;
	
	@Autowired //可以按类型注入List
	private List<? extends Provider> allProvider;
	
	public void printSelf()
	 {
		 System.out.println("myself="+myself);
	 }
	public void printList()
	 {
		 System.out.println("allProvider="+allProvider);
	 }
}
