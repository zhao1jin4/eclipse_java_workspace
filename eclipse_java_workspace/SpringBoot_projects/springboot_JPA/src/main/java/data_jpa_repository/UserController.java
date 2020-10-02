package data_jpa_repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	 @Autowired 
	 private UserRepository  userRepo; 
	
	 //http://127.0.0.1:8888/save/lisi
	 @PostMapping("/save/{username}")
	 public String saveUser(@PathVariable String username  )
	 {
		   userRepo.save(new User(username));
		   return "ok";
	 }
	 
	 //http://127.0.0.1:8888/simple/1
	 //@DeleteMapping
	 @GetMapping("/simple/{id}")
	 public User findById(@PathVariable Long id)
	 {
		 return   userRepo.findById(id).orElse(null);
		 
	 }
	 
	 
}
