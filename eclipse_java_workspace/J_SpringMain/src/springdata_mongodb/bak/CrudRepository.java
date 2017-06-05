package springdata_mongodb.bak;

import java.io.Serializable;

import org.springframework.data.repository.Repository;
public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID>
{
    <S extends T> S save(S entity);
}