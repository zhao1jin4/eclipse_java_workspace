package apache_shiro_bak;

import javax.security.auth.Subject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

//ΪJWT��δ���� 
//�ŵ�ShiroFilterFactoryBean�е�filter������ ��/**=authc �޸�Ϊ���jwt,���������õ�����������Ҫ�޸�/�����µ�
//���еĴ��� Subject currentUser = SecurityUtils.getSubject().getPrincipal();��Ч������
// �ֻ���token�� �������cookieֻ�ֿܷ����룿����
public class NoSessionFilter extends BasicHttpAuthenticationFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) 
	{
		try {
		
//		String[] values=(String[])mappedValue;//������ /xx=role[read] ʱ
		HttpServletRequest req=(HttpServletRequest)request;
		String token =req.getParameter("token");//Ҳ�ɷ���http header��
		Subject userInfo=null;//���token���ڴ������𣿵�¼�ɹ����Map(Redis��ʧЧʱ��/DB)�У�keyΪ����UUID��tokenֵ��valueΪsubject
		if(userInfo==null)
		{
			System.out.println("δ��¼");
			return false;
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("��Ȩ��");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write("{\"code\":503}");
		return false;
	}
}
