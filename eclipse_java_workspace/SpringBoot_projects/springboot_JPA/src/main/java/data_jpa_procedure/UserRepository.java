 package data_jpa_procedure;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

 
public interface UserRepository extends CrudRepository<User, Long> {//这的User好像没什么用
	@Procedure
	Integer plus1inout(Integer arg);
}
