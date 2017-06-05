package mybatis_spring;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class DaoImpl extends SqlSessionDaoSupport  implements Dao {
	
	public Employee getEmployeeByName(String username)
	{
		return (Employee) getSqlSession().selectOne("EmployeeMapper.selectEmployeeByName", username);
	}
	public Employee getEmployeeById(int id)
    {
        return (Employee) getSqlSession().selectOne("EmployeeMapper.selectEmployeeById", id);
    }
	public int addAge(int emp_id) {
		return  getSqlSession().update("EmployeeMapper.addAgeEmployeeById",emp_id);
	}

	public int plusAge(int emp_id) {
		return  getSqlSession().update("EmployeeMapper.plusAgeEmployeeById",emp_id);
	}
}
