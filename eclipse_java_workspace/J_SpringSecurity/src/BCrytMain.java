import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCrytMain {
	public static void main(String[] args) {
		
		//UserDao为中也有
		
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
