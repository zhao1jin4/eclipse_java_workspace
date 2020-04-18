package security.bcrypt;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptDemo {
	public static void main(String[] args) {
		 //Bcrypt Java ʵ�� jBCrypt,Spring Security ʹ��bcrypt
		
		// Hash a password for the first time
		String password = "testpassword";
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		System.out.println(hashed);
		// gensalt's log_rounds parameter determines the complexity
		// the work factor is 2**log_rounds, and the default is 10
		String hashed2 = BCrypt.hashpw(password, BCrypt.gensalt(12));
		System.out.println(hashed2);
		// Check that an unencrypted password matches one that has
		// previously been hashed
		String candidate = "testpassword";
		// String candidate = "wrongtestpassword";
		if (BCrypt.checkpw(candidate, hashed))
			System.out.println("It matches");
		else
			System.out.println("It does not match");
	}
}