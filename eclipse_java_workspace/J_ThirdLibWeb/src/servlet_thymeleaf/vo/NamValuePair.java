package servlet_thymeleaf.vo;
import java.io.Serializable;

public class NamValuePair implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String value;

	public NamValuePair(String name, String value) { 
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NamValuePair [name=" + name + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		if (value == null)
			return 0;
		else
			return value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof NamValuePair))
			return false;
		NamValuePair param = (NamValuePair) obj;
		if (this.getValue() != null)
			return this.getValue().equals(param.getValue());
		else
			return this.getValue() == param.getValue();

	}

}
