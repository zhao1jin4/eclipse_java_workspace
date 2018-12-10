package org.liukai.tutorial.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.liukai.tutorial.dao.UserDao;
import org.liukai.tutorial.domain.DbUser;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 一个自定义的service用来和数据库进行操作. 即以后我们要通过数据库保存权限.则需要我们继承UserDetailsService
 */
public class CustomUserDetailsService implements UserDetailsService {

	protected static Logger logger = Logger.getLogger("service");

	private UserDao userDAO = new UserDao();

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		UserDetails user = null;
		try {
			// 搜索数据库以匹配用户登录名.
			// 我们可以通过dao使用JDBC来访问数据库
			DbUser dbUser = userDAO.getDatabase(username);

			// Populate the Spring User object with details from the dbUser
			// Here we just pass the username, password, and access level
			// getAuthorities() will translate the access level to the correct
			// role type

			user = new User(dbUser.getUsername(), dbUser.getPassword(), 
					true, true, true, true, getAuthorities(dbUser.getAccess())); //自身系统验证
			
//			user = new User(dbUser.getUsername(),"noPassword" ,   //外部部系统验证的假密码
//					true, true, true, true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));//外部系统验证

		} catch (Exception e) {
			logger.error("Error in retrieving user",e);
			throw new UsernameNotFoundException("Error in retrieving user");
		}

		return user;
	}

	/**
	 * 获得访问角色权限
	 */
	public Collection<SimpleGrantedAuthority> getAuthorities(Integer access) {

		List<SimpleGrantedAuthority> authList = new ArrayList<SimpleGrantedAuthority>(2);

		// 所有的用户默认拥有ROLE_USER权限
		logger.debug("Grant ROLE_USER to this user");
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));

		// 如果参数access为1.则拥有ROLE_ADMIN权限
		if (access.compareTo(1) == 0) {
			logger.debug("Grant ROLE_ADMIN to this user");
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}

		return authList;
	}
}
