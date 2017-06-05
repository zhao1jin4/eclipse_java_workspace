package spring_oxm.object;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
public class Settings {
	private boolean fooEnabled;

	public boolean isFooEnabled() {
		return fooEnabled;
	}

	public void setFooEnabled(boolean fooEnabled) {
		this.fooEnabled = fooEnabled;
	}
}