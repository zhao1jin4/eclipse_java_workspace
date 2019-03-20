package elastic;

import java.util.List;

import org.springframework.data.repository.CrudRepository; 
public interface  BookDao extends CrudRepository<BookEntity,String>{
	 public List<BookEntity> getByMessage(String key);
	
}
