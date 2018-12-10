package nosql_neo4j_ogm.domain;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.StartNode;
import org.neo4j.ogm.annotation.Transient;
 

@NodeEntity(label="lbl_Movie") 
public class Movie {
 
	@Id @GeneratedValue
	private Long id;

	@Property(name="one_title")
	private String title;
	
	 @Transient //���洢
	private int released;

	@Relationship(type = "ACTS_IN",  
			 direction=Relationship.INCOMING
			 )
	Set<Actor> actors=new HashSet<>();

	 
	public Movie() { //������пյĹ��й�����
	}

	public Movie(String title, int year) {
		this.title = title;
		this.released = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReleased() {
		return released;
	}

	public void setReleased(int released) {
		this.released = released;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

}