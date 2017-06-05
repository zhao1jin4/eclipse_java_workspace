package security.mytest;
import java.io.File;
import java.io.FilePermission;


public class SecurityMainApp {

	public static void main(String[] args) {
		File currentDir=new File("./");
		System.out.println("currentDir="+currentDir.getAbsolutePath());//C:\My\all_code_workspace\eclipse_java_workspace\J_JavaSE\.
		
		
		//����ʱ������  -Djava.security.manager �� System.getSecurityManager();���ط�null
		// -Djava.security.policy=bin/security/mytest/test.policy
		
		//java -Djava.security.manager  -Djava.security.policy=bin/security/mytest/test.policy security.mytest.SecurityMainApp
				
		
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			FilePermission fp= new FilePermission("c:/temp/echo.bat", "read");
			sm.checkPermission(fp);
		}
	}

}
