 package data_jpa_repo_custom;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
	//非标准方法名，定义在@NamedQuery
	User findByTheUsersName(String username);
	//指定JPSQL
	@Query("select u from User u where u.firstname = :firstname")
	List<User> findByFirstname11(String firstname);//变量名传参数
	
}
