package neo4j;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

@NodeEntity(label="lbl_Movie") 
public class Movie {
 
	@Id @GeneratedValue
	private Long id;

	@Property(name="one_title")
	private String title;
	
	 @Transient //不存储
	private int released;

	@Relationship(type = "ACTS_IN",  
			 direction=Relationship.INCOMING
			 )
	Set<Actor> actors=new HashSet<>();

	 
	public Movie() { //类必须有空的公有构造器
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

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", released=" + released + ", actors=" + actors + "]";
	}
	 
}
 
