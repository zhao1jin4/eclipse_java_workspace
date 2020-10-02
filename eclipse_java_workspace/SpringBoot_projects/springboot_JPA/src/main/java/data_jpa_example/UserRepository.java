 package data_jpa_example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

 
public interface UserRepository extends CrudRepository<User, Long>, QueryByExampleExecutor<User> {}
