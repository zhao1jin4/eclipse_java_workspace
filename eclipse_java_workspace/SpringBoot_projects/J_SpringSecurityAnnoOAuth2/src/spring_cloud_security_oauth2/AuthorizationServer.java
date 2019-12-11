package spring_cloud_security_oauth2;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


@Configuration
@EnableAuthorizationServer
//spring-security-oauth2-xx.jar
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
	@Autowired 
	private TokenStore tokenStore;
	
	@Autowired 
	private ClientDetailsService clientDetailsService;
	@Autowired 
	private AuthenticationManager authenticationManager;
	@Autowired 
	private AuthorizationCodeServices authorizationCodeServices;
	@Autowired 
	private JwtAccessTokenConverter accessTokenConverter;
	
	
	@Bean 
	public AuthorizationServerTokenServices tokenServlet()
	{
		DefaultTokenServices service= new DefaultTokenServices();
		service.setClientDetailsService(clientDetailsService);
		service.setSupportRefreshToken(true);
		service.setTokenStore(tokenStore);
		service.setAccessTokenValiditySeconds(7200); //2小时
		service.setRefreshTokenValiditySeconds(259200);//3天
		
		TokenEnhancerChain chain =new TokenEnhancerChain();//JWT
		chain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		service.setTokenEnhancer(chain);
		return service;
	}
	
	
	
	@Override
	//token 安全约束，权限
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("permitAll()")
		.allowFormAuthenticationForClients();
	}

	@Override
	//支持哪些第三方应用
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() //应使用数据库配置
		.withClient("client_id_1") //客户端ID
		.secret(new BCryptPasswordEncoder().encode("secre_"))//客户端 密钥
		.resourceIds("user_res","role_res")  //客户端可以仿问的资源ID
		.authorizedGrantTypes("password","")//共支持5种，如输入密码
		.scopes("scope_flag")
		.autoApprove(false)//false 跳转到页面，true直接发token
		.redirectUris("")
		;
		
	}

	@Override
	//配置token,token Server
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
		.authorizationCodeServices(authorizationCodeServices)
		.allowedTokenEndpointRequestMethods(HttpMethod.POST);
		
	}
	@Bean
	public AuthorizationCodeServices authorizationCodeServices()
	{
		JwtAccessTokenConverter x;
		return new InMemoryAuthorizationCodeServices();
	}
 

}
