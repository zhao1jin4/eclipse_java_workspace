package data_jpa_repo_custom;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("jdbc") // 打开这个就是JDBC的实现
public class RepositoryCustomTests {
	@Autowired UserRepository repository;
	@Test
	public void saveAndFindByLastNameAndFindByUserName() {
		User user = new User();
		user.setUsername("foobar");
		user.setLastname("lastname");
		user.setFirstname("li");
		user = repository.save(user);
		List<User> users2 = repository.findByFirstname11("li");
		assertThat(users2).contains(user);
		assertThat(user).isEqualTo(repository.findByTheUsersName("foobar"));
	}
	@Test
	public void testCustomMethod() {
		User user = new User();
		user.setUsername("username");
		user = repository.save(user);
		List<User> users = repository.myCustomBatchOperation();
		assertThat(users).contains(user);
	}
}
