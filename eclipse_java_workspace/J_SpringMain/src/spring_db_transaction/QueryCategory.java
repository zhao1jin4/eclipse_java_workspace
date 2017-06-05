package spring_db_transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

public class QueryCategory extends MappingSqlQuery<Category>
{

	public QueryCategory(DataSource datasource)
	{
		this.setDataSource(datasource);
		this.setSql("select * from category where id =?");
		this.declareParameter(new SqlParameter(Types.INTEGER));
		this.compile();
	}
	protected Category mapRow(ResultSet rs, int i) throws SQLException
	{
		Category cate=new Category();
		cate.setId(rs.getInt("id"));
		cate.setName(rs.getString("name"));
		return cate;
	}
}
