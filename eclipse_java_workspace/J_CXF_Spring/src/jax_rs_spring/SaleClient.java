package jax_rs_spring;

import java.io.Serializable;
import java.util.List;

public interface SaleClient extends Serializable {

	public List<Info> getInfos();

	public Info getInfo(String id);

	public void saveOrUpdateInfo(Info info);

	public void deleteInfo(String id);

}