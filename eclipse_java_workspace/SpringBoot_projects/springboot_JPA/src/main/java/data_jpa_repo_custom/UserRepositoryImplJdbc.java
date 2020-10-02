package data_jpa_repo_custom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
 
@Profile("jdbc") //对于复杂的查询，用JPA不好做的情况，直接用JDBC，还可以mapping
@Component("userRepositoryImpl")
class UserRepositoryImplJdbc extends JdbcDaoSupport implements UserRepositoryCustom {
	private static final String COMPLICATED_SQL = "SELECT * FROM User";
	@Autowired
	public UserRepositoryImplJdbc(DataSource dataSource) {
		setDataSource(dataSource);
	}
	public List<User> myCustomBatchOperation() {
		return getJdbcTemplate().query(COMPLICATED_SQL, new UserRowMapper());
	}
	private static class UserRowMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User(rs.getLong("id"));
			user.setUsername(rs.getString("username"));
			user.setLastname(rs.getString("lastname"));
			user.setFirstname(rs.getString("firstname"));
			return user;
		}
	}
}
