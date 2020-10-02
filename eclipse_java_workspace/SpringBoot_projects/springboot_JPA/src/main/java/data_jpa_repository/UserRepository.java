package data_jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	//findBy开头 官方文档 搜索Supported keywords   参考 https://docs.spring.io/spring-data/jpa/docs/2.3.1.RELEASE/reference/html/#reference 
}
