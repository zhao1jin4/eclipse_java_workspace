package spring_batch.jdbc;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import spring_batch.Student;

@Component("jdbcProcessor")
public class JDBCProcessor implements  ItemProcessor<Student, Student> {

    public Student process(Student student) throws Exception {

        student.setId(student.getId()+9000);
        student.setName(student.getName()+ "--" );
        student.setAge(student.getAge() + 2);
        return student;
    }


}