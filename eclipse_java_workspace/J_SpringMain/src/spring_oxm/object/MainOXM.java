package spring_oxm.object;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainOXM {

	public static void main(String[] args) throws Exception {

			ApplicationContext appContext = new ClassPathXmlApplicationContext("spring_oxm/object/oxm_beans.xml");
			SettingsOXM settingsOXM = (SettingsOXM) appContext.getBean("settingsOXM");
			Settings settings=new Settings();
			settings.setFooEnabled(true);
			settingsOXM.saveObject(settings);
			Settings loaded =settingsOXM.loadObject();
			System.out.println(loaded.isFooEnabled());
			
	}

}
