package spring3_annotation_config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import com.gs.collections.api.annotation.Beta;
public class UserService  extends BaseService
{
	//@Resource(name="userDao")//Ҳ�ɷ���setXxx����ǰ
	@Autowired(required=true) @Qualifier("userDAO")//@AutowiredĬ���ǰ�����,required=true��ʾ����ע��,ʹ��@Qualifier��ʾ������
	public UserDAO userDao;
	
}
