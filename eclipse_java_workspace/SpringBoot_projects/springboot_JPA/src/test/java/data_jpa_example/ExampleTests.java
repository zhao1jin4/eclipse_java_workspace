
package data_jpa_example;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.ExampleMatcher.matching;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExampleTests { 

	//官方示例
	@Autowired UserRepository repository;

	User skyler, walter, flynn, marie, hank;

	@BeforeEach
	public void setUp() {
		repository.deleteAll();
		this.skyler = repository.save(new User("Skyler", "White", 45));
		this.walter = repository.save(new User("Walter", "White", 50));
		this.flynn = repository.save(new User("Walter Jr. (Flynn)", "White", 17));
		this.marie = repository.save(new User("Marie", "Schrader", 38));
		this.hank = repository.save(new User("Hank", "Schrader", 43));
	}
	@Test
	public void countBySimpleExample() {
		Example<User> example = Example.of(new User(null, "White", null));
		assertThat(repository.count(example)).isEqualTo(3L);
	}
	@Test
	public void ignorePropertiesAndMatchByAge() { 
		Example<User> example = Example.of(flynn, matching().  
				withIgnorePaths("firstname", "lastname")); 
		assertThat(repository.findOne(example)).contains(flynn);
	}
	@Test
	public void substringMatching() {

		Example<User> example = Example.of(new User("er", null, null), matching(). //
				withStringMatcher(StringMatcher.ENDING));

		assertThat(repository.findAll(example)).containsExactly(skyler, walter);//在数据库中过虑
	}
	@Test
	public void matchStartingStringsIgnoreCase() {

		Example<User> example = Example.of(new User("Walter", "WHITE", null), matching(). //
				withIgnorePaths("age"). //
				withMatcher("firstname", startsWith()). //
				withMatcher("lastname", ignoreCase()));//SQL是lower(user0_.lastname)这种性能差
		assertThat(repository.findAll(example)).containsExactlyInAnyOrder(flynn, walter);
	}
	@Test
	public void valueTransformer() {
		Example<User> example = Example.of(new User(null, "White", 99), matching(). //
				withMatcher("age", matcher -> matcher.transform(value -> Optional.of(Integer.valueOf(50)))));//常量 user0_.age=50 覆盖了99
		assertThat(repository.findAll(example)).containsExactly(walter);
	}
}
