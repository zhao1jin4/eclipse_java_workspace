package nosql_neo4j_ogm.domain;
 
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

//关系 方式 二
@RelationshipEntity(type = "ACT_IN_2")
public class ActIn 
{
	@Id @GeneratedValue
	private Long id; 
	
	@StartNode
	private Movie movie;

	@EndNode
	private Actor actor;

	
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Movie getMovie()
	{
		return movie;
	}

	public void setMovie(Movie movie)
	{
		this.movie = movie;
	}

	public Actor getActor()
	{
		return actor;
	}

	public void setActor(Actor actor)
	{
		this.actor = actor;
	}

}