package spring_batch.flat;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import spring_batch.Student;

public class StudentMapper implements FieldSetMapper<Student> {
	public Student mapFieldSet(FieldSet fs) throws BindException {
	    Student u = new Student();
		u.setName(fs.readString(0));
		u.setAge(fs.readInt(1));
		return u;
	}
}