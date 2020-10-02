import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *	<security:password-encoder   ref=""/> 
 * @author zhaojin
 * 
 *
 */
public class MyPassWordEncoder implements   PasswordEncoder {
 
	/** 对外部系统验证  不做加解密，匹配直接返回true*/
	@Override
	public String encode(CharSequence rawPassword) { 
		System.out.println("PasswordEncoder encode invoked!");
		return  rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) { 
		System.out.println("PasswordEncoder matches invoked!");
		return true;
	}
 

}
