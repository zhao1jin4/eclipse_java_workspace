package spring_db_transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class StudentDaoAnnotation implements StudentDao //@Transactional�ڽӿ���д
{
	private JdbcTemplate template;
	
	public void setJdbcTemplate(JdbcTemplate template )
	{
		this.template=template;
	}
	
	 //spring��@Transactional����javax��
	@Transactional(rollbackFor=Exception.class,noRollbackFor=RuntimeException.class,
			propagation=Propagation.REQUIRED,isolation=Isolation.REPEATABLE_READ) //������,isolationĬ������������, propagationĬ����Propagation.REQUIRED.
	public void updateStudentAge() throws Exception
	{
		template.update("update student set age=25  where name='lisi��'");
		System.out.println("end updateStudentAge");//����������ʱ,����ִ��select * from student;��
		//throw new Exception("checked ҵ���쳣");//checked�쳣Ĭ�ϲ���ع�,�ɼ�rollbackFor=Exception.class
		//throw new RuntimeException("unchecked ҵ���쳣");//unchecked(RuntimeException)�쳣Ĭ�ϻ�����ع�
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true,timeout=20)//����Ҫ����,����һ��ʹ��@Transactional
	public Student getStudent()
	{
		return template.queryForObject("select * from student where name='listi��'", Student.class);
	}
	
}
