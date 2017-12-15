package spring_jsp.annotation;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

 
import java.sql.Statement;
import org.springframework.jdbc.core.StatementCallback;

@Repository("databaseCommandDao")
public class DatabaseCommandDao 
{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int execute(final String sql)
	{
            int effectRow=jdbcTemplate.execute(new StatementCallback<Integer>(){
                @Override
                public Integer doInStatement(Statement stmt) throws SQLException, DataAccessException
                {
                  return stmt.executeUpdate(sql);
                }
            });
            return effectRow;
	}
	public List<Map<String,Object>>  query(String sql)
	{
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		SqlRowSet rowSet=jdbcTemplate.queryForRowSet(sql);
		while(rowSet.next())
		{
			Map<String,Object> rowMap=new HashMap<String,Object>();
			
			String columns[]=rowSet.getMetaData().getColumnNames();
			//int rowNum=rowSet.getRow();
			for(int i=1;i<= columns.length;i++)
			{
				rowMap.put(columns[i-1], rowSet.getObject(i));
			}
			
			result.add(rowMap);
		}
		return result;
	}
	public void execute(String sql,final List<Object> params)
	{
		jdbcTemplate.execute(sql,new PreparedStatementCallback<Boolean>()
		{
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException
			{
				int i=1;
				for(Object param:params)
				{
					ps.setObject(i, param);
					i++;
				}
				return ps.execute();
			}
		});
	}
	 
 
}
