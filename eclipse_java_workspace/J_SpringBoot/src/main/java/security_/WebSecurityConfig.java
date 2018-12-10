//package security_;
//import java.util.Arrays;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@EnableGlobalMethodSecurity(securedEnabled=false //@Securej 是否生效
//)
//@EnableWebSecurity //里有 @EnableGlobalMethodSecurity
//public class WebSecurityConfig implements WebMvcConfigurer {
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//	     return new BCryptPasswordEncoder();
//	 }
//	
//	@Bean
//	public UserDetailsService userDetailsService() throws Exception {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		String encPass=passwordEncoder().encode("password");
//		User	user = new User("user",encPass, 
//				true, true, true, true,  Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))); 
//		
//		//UserDetails	ori=null;
//		//UserDetails	old = User.withUserDetails(ori).username("user").password("password").roles("USER").build();
// 
//		
//		manager.createUser(user);
//		return manager;
//	}
//}