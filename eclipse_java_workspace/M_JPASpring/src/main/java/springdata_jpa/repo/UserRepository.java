package springdata_jpa.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import springdata_jpa.entity.User; 

@Repository  //SimpleJpaRepository 源码 带 @Transactional(readOnly=true)
public interface UserRepository extends JpaRepository<User,Long> {
	public Optional<User> findByUsername(String username);
	//findBy开头 官方文档 搜索Supported keywords   参考 https://docs.spring.io/spring-data/jpa/docs/2.3.1.RELEASE/reference/html/#reference 

	@Query(value="SELECT u.* FROM User u WHERE u.username like %:username%", nativeQuery = true)
	//@Query(value="SELECT u.* FROM User u WHERE u.username =:username", nativeQuery = true)
	public User myQuery(@Param("username")String u);
}
