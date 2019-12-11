package servlet_noxml_config;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/r/*").authenticated()//这个地址必须登录
			.anyRequest().permitAll() //其它的允许
		.and()
			.formLogin() //可以表单来登录
			.successForwardUrl("")//登录成功地址
    	.loginProcessingUrl("/login") 
        .loginPage("/login.jsp")
        .failureUrl("/login.jsp?authentication_error=true")
        .permitAll();
	}
 
}
*/
