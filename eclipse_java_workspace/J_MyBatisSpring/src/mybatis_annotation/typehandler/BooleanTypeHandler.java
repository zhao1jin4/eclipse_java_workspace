package mybatis_annotation.typehandler;
import java.sql.CallableStatement;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;        
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;        

/**
 * java中的boolean和jdbc中的char之间转换
 * true-Y
 * false-N
 **/ 

@MappedTypes({Boolean.class})
@MappedJdbcTypes({JdbcType.VARCHAR,JdbcType.CHAR})
public class BooleanTypeHandler implements TypeHandler<Boolean> {       
     @Override public Boolean getResult(ResultSet rs, String columnName)  throws SQLException {  
        String str = rs.getString(columnName);  
        Boolean rt = Boolean.FALSE;  
        if (str.equalsIgnoreCase("Y")){  
            rt = Boolean.TRUE;  
        }  
        return rt;   
    }
     @Override
 	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
    	  String str = rs.getString(columnIndex);  
          Boolean rt = Boolean.FALSE;  
          if (str.equalsIgnoreCase("Y")){  
              rt = Boolean.TRUE;  
          }  
          return rt;   
 	}
    @Override public Boolean getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {  
        String b = callableStatement.getString(columnIndex);  
        Boolean rt = Boolean.FALSE;  
        if (b.equalsIgnoreCase("Y")){  
            rt = Boolean.TRUE;  
        }
        return rt;   
    }        
    @Override public void setParameter(PreparedStatement prepare, int i, Boolean param, JdbcType jdbcType) throws SQLException {  
        String value = param == true ? "Y" : "N";  
        prepare.setString(i, value);  
    }
	  
}  