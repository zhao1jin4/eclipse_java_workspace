package spring_batch.csv;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import spring_batch.Student;
 
@Component("csvItemProcessor")
public class CsvItemProcessor implements ItemProcessor<Student, Student> {

    @Override
    public Student process(Student student) throws Exception {
        /* �ϲ�ID������ */
        student.setName("--" + student.getName());
        /* �����2 */
        student.setAge(student.getAge() + 2);
        /* ������10 */
        /* �������Ľ�����ݸ�writer */
        return student;
    }
}