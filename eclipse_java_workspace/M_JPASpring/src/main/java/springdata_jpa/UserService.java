package springdata_jpa;

public interface UserService {
	public Long createNewAccount(String user, String pwd, Integer init);
}