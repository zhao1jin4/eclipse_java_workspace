package spring_aop_aspectj_anno;

import org.springframework.stereotype.Service;

@Service
public class AnnoService {
	
	@Caches(prefixKey="hello")
	public boolean validateUser(String userId) {
		System.out.println("invoked in validateUser");
		return false;
	}
}
