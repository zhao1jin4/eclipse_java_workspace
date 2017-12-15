package mypackage.test;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {

	public static void main(String[] args) {
		//Locale locale=Locale.CHINA;
		Locale locale=Locale.ENGLISH;
		ResourceBundle bundle=ResourceBundle.getBundle("mypackage/test/message",locale);
		String submit=bundle.getString("submit");
		System.out.println(submit);
		String res=MessageFormat.format(bundle.getString("canNotEmpty"), "username");
		System.out.println(res);
	}

}
