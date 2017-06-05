package jax_rs_spring;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
//引用自服务端
//引用自服务端

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;

public class SaleClientImpl implements SaleClient 
{

	private static String BASE_ADDRESS = "http://localhost:8080/v1";
	
	SaleService service;
	
	public SaleClientImpl() {
		initProxy();
	}
	
	private SaleService initProxy()
	{
		service = JAXRSClientFactory.create(BASE_ADDRESS, SaleService.class);
		WebClient.client(service).accept(MediaType.APPLICATION_XML);// 一定需要
		return service;
	
	}

	@Override
	public List<Info> getInfos() {
		try {
			return service.getInfos();
		} catch (WebApplicationException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public Info getInfo(String id) {
		try {
			return service.getInfo(id);
		} catch (WebApplicationException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void saveOrUpdateInfo(Info info) {
		try {
			service.saveOrUpdateInfo(info);
		} catch (WebApplicationException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void deleteInfo(String id) {
		try {
			service.deleteInfo(id);
		} catch (WebApplicationException ex) {
			ex.printStackTrace();
		}
	}
}