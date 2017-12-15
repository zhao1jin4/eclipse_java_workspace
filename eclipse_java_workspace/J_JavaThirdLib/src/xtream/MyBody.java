package xtream;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MyBody {
	@XStreamAlias("id")
	private String id;
	
	@XStreamAlias("name")
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
