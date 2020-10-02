package springdata_jpa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import springdata_jpa.entity.User;
import springdata_jpa.repo.NotRepositoryImpl;
import springdata_jpa.repo.UserRepository;

public class MainSpringDataJPA {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		UserRepository userRepo= context.getBean(UserRepository.class);
//		userRepo.save(new User("lisi"));
//		 User res=userRepo.findByUsername(("lisi")).orElse(null);
//		 System.out.println(res);
//		 
//		 User res1=userRepo.myQuery("lisi");
//		 System.out.println(res1);
		 
		 
		
		
		//spring with querydsl Not success
		NotRepositoryImpl notRep= context.getBean(NotRepositoryImpl.class);
		notRep.queryUseDSLupdateUseSQL();
//		 
	}
	
	
}
