package apache_shiro_bak;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

public class JWTCloseSession {

	public static void main(String[] args) {
	 DefaultWebSecurityManager manager =new DefaultWebSecurityManager();
//	 manager.setRealm(realm);
	 
	 //�ر�sessionΪ�ֻ���û��cookie��δ���� 
	 DefaultSubjectDAO subjectDAO =new DefaultSubjectDAO();
	 DefaultSessionStorageEvaluator sessionStorageEvaluator=new DefaultSessionStorageEvaluator();
	 sessionStorageEvaluator.setSessionStorageEnabled(false);
	 subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
	 manager.setSubjectDAO(subjectDAO);
	 manager.setSubjectFactory(subjectFactory());//DefaultSubjectFactory
	}
	public static DefaultSubjectFactory subjectFactory( ) {
		//��ȷ������ʲô ����
		 DefaultSubjectFactory factory=new DefaultSubjectFactory();
		return  factory;
	}
}
