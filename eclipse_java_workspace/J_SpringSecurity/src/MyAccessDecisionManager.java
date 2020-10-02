import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
/*
AccessDecisionManager的默认实现有3个
AffirmativeBased  只要有一个通过，就通过，全部弃权（abstain）也通过
ConSensusBased 少数服从多数 如相同并不等于0，根据属性决定
UnanimousBased   有一个拒绝就算拒绝，全部弃权根据属性决定
 */
public class MyAccessDecisionManager implements AccessDecisionManager
{
	//authentication用户信息，有权限 
	//object 仿问的资源 ，
	//configAttributes 资源要哪些权限
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

}
