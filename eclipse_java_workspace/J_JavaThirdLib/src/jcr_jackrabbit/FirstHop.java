package jcr_jackrabbit;

import java.io.File;

import javax.jcr.Repository;
import javax.jcr.Session;
import org.apache.jackrabbit.core.TransientRepository;

public class FirstHop {

	public static void main(String[] args) throws Exception {
		Repository repository = new TransientRepository(new File("d:/temp/jackrabit_repos"));
		Session session = repository.login();//÷ª“˝”√ jackrabbit-standalone-2.6.3.jar ≤‚ ‘OK
		try {
			String user = session.getUserID();
			String name = repository.getDescriptor(Repository.REP_NAME_DESC);
			System.out.println("Logged in as " + user + " to a " + name + " repository.");
		} finally {
			session.logout();
		}
	}

}