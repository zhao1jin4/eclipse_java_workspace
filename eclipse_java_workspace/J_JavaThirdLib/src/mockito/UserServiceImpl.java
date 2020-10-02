package mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
	@Autowired
	private MyDao myDao;
	
	public void printDao() {
		System.out.println(myDao==null);
	}
}
