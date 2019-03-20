package elastic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 

@RestController
public class  BookController  {
	private BookDao bookDao;

	@RequestMapping("/getBookById/{id}")
	public String getById(@PathVariable("id") String id)
	{
		Optional<BookEntity> opt=bookDao.findById(id);
		BookEntity book= opt.get();
		return book.getId();
	}
	
	@RequestMapping("/search/{key}")
	public List<BookEntity> search(@PathVariable("key") String key)
	{
		List<BookEntity> list=bookDao.getByMessage(key);
 		return list;
	}
	@RequestMapping("/searchPage/{key}/{page}")
	public List<BookEntity> searchPage(@PathVariable("key") String key,@PathVariable("page") int page)
	{
		PageRequest pageReq=PageRequest.of(page, 20);
 
		List<BookEntity> list=bookDao.getByMessage(key);
 		return list;
	}
	
}
