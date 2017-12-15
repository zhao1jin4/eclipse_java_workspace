package jax_rs_spring;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces({ MediaType.APPLICATION_XML })
public interface SaleService extends Serializable {
	@GET
	@Path("/infos")
	public List<Info> getInfos();
	
	@GET
	@Path("/infos/{id}")
	public Info getInfo(@PathParam("id") String id);
	
	@POST
	@Path("/infos")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML,	MediaType.APPLICATION_JSON })
	public void saveOrUpdateInfo(Info info);
	
	@DELETE
	@Path("/infos/{id}")
	public void deleteInfo(@PathParam("id") String id);

}