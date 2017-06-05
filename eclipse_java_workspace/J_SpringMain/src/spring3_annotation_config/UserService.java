package spring3_annotation_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.gs.collections.api.annotation.Beta;
public class UserService  extends BaseService
{
	//@Resource(name="userDao")//也可放在setXxx方法前
	@Autowired(required=true) @Qualifier("userDAO")//@Autowired默认是按类型,required=true表示必须注入,使用@Qualifier表示按名称
	public UserDAO userDao;
	
}
