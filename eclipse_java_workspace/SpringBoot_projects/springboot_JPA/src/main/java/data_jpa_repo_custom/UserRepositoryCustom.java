package data_jpa_repo_custom;

import java.util.List;

 
interface UserRepositoryCustom {
	List<User> myCustomBatchOperation();
}
