package mybatis_xml;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapperAnno
{
	@Select("SELECT * employee user WHERE id = #{id}")
	Employee getEmployeer(@Param("id") String id);
}