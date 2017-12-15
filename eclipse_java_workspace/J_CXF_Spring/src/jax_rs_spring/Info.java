package jax_rs_spring;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Info")
public class Info implements Serializable {
	private String id;
	private String name;

	private String description;

	@XmlElement(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name = "DESCRIPTION")
	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

}