package spring_jsp.annotation.form;
import org.springframework.transaction.annotation.Transactional;   
@Transactional  
public interface AccountService {   
    Account read(String username, String password);   
    Account read(int id);   
}  