package org.liukai.tutorial.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.liukai.tutorial.domain.DbUser;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserDao {

	protected static Logger logger = Logger.getLogger("dao");

	public DbUser getDatabase(String username) {

		List<DbUser> users = internalDatabase();

		for (DbUser dbUser : users) {
			if (dbUser.getUsername().equals(username) == true) {
				logger.debug("User found");
				return dbUser;
			}
		}
		logger.error("User does not exist!");
		throw new RuntimeException("User does not exist!");

	}

	/**
	 * 初始化数据
	 */
	private List<DbUser> internalDatabase() {

		List<DbUser> users = new ArrayList<DbUser>();
		DbUser user = null;

		user = new DbUser();
		user.setUsername("admin");
		// "admin"经过MD5加密后
		//user.setPassword("21232f297a57a5a743894a0e4a801fc3");
		// "admin"经过bcrypt 加密后
		String adminBcrypt="$2a$10$wdPgE/GODVeARl1WGumnIegNW0O0OQ//GRlgQfd4QIctjRS9e./ee";//admin
//		user.setPassword("{bcrypt}"+adminBcrypt);
		user.setPassword(adminBcrypt);
		user.setAccess(1);
		users.add(user);

		user = new DbUser();
		user.setUsername("user");
		// "user"经过MD5加密后
		//user.setPassword("ee11cbb19052e40b07aac0ca060c23ee");
		// "user"经过 bcrypt 加密后
		String userBcrypt="$2a$10$7L49D48WyQgMFn0yAbniX.WYRs5RkTPErPhpEVV91R1x0mx0ak76y";//user
//		user.setPassword("{bcrypt}"+userBcrypt);
		user.setPassword(userBcrypt);
		user.setAccess(2);//！=1  ，0也可
		users.add(user);

		return users;

	}
	public static void main(String[] args) {
		//bcrypt 每次生成的hash值都是不同的m字符长度比较长，有60位
		String bcryptPass= org.springframework.security.crypto.bcrypt.BCrypt.hashpw("user", BCrypt.gensalt());
		System.out.println(bcryptPass);
		String userBcrypt="$2a$10$7L49D48WyQgMFn0yAbniX.WYRs5RkTPErPhpEVV91R1x0mx0ak76y"; 
		boolean isOK=BCrypt.checkpw("user", userBcrypt);
		System.out.println(isOK);
		
		String adminBcrypt="$2a$10$wdPgE/GODVeARl1WGumnIegNW0O0OQ//GRlgQfd4QIctjRS9e./ee";
		isOK=BCrypt.checkpw("admin",adminBcrypt);
		System.out.println(isOK);
		
	}
}
