package spring_boot_security_oauth2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
 
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Bean
	public UserDetailsService userDetailsService()
	{
		return new UserDetailsService()
		{
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// 连接数据库
				return User.withUsername("lis").password("123").authorities("p1").build();
			}
		};
//		InMemoryUserDetailsManager manager=new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("lis").password("123").authorities("p1").build());
//		manager.createUser(User.withUsername("zhangsan").password("456").authorities("p2").build());
//		return manager;
	}
	@Bean
	public PasswordEncoder passwordEncoder()
	{
//		return new BCryptPasswordEncoder();
		return    NoOpPasswordEncoder.getInstance();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()//允许跨域
			.authorizeRequests()
//			.antMatchers("/r1/*").hasAnyAuthority("")  //有 @Secured   @PreAuthorize 可不要这个
//			.antMatchers("/r2/*").hasRole("")
//			.antMatchers("/r3/*").access("hasAuthority('p1') and hasAuthority('p2') ")
			
			.antMatchers("/r/*").authenticated()//这个地址必须登录
			.anyRequest().permitAll() //其它的允许
		.and()
			.formLogin() //可以表单来登录
			.loginPage("/login-view") //登录页
	    	.loginProcessingUrl("/login") //登录页提交的地址
			.successForwardUrl("/login-success")//登录成功地址
//	        .failureUrl("/login.jsp?authentication_error=true")
	 .and().logout()
	 	.logoutUrl("")//注销请求地址
//	 	 .addLogoutHandler(logoutHandler)
//	 	.logoutSuccessHandler(logoutSuccessHandler)//退出前的清理工作 logoutSuccessUrl就无用了
	 	.logoutSuccessUrl(""); //注销后的地址
		
	}
 
}
 
