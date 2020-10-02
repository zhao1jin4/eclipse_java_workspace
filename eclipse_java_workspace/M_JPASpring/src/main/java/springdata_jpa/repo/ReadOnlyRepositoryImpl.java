package springdata_jpa.repo;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
@org.springframework.transaction.annotation.Transactional(readOnly = true) //(readOnly=true) ,是可以被子类覆盖的
public class ReadOnlyRepositoryImpl   {
	
}
