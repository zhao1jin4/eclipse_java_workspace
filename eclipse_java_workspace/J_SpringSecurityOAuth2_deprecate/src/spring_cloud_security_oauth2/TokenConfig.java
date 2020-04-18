package spring_cloud_security_oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {

	//token的存储
	@Bean
	public TokenStore tokenStore()
	{
//		return new InMemoryTokenStore();//可以使用JWT (JsonWebToken)
		return new JwtTokenStore(accessTokenConverter()); 
	}
	@Bean
	public JwtAccessTokenConverter accessTokenConverter()
	{
		JwtAccessTokenConverter converter=new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}
	 
}
