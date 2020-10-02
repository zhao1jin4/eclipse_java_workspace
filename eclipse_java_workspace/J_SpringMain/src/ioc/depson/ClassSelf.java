package ioc.depson;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "classSelf")
public class ClassSelf  {
	@Autowired //��������ע���Լ�
	private ClassSelf myself;
	
	@Autowired //���԰�����ע��List
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
