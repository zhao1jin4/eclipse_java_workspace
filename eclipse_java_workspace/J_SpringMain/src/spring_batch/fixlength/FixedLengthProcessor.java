package spring_batch.fixlength;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import spring_batch.Student;

@Component("fixedLengthProcessor")
public class FixedLengthProcessor implements
        ItemProcessor<Student, Student> {

    /**
     * 对取到的数据进行简单的处理。
     * 
     * @param student
     *            处理前的数据。
     * @return 处理后的数据。
     * @exception Exception
     *                处理是发生的任何异常。
     */
    public Student process(Student student) throws Exception {
        /* 合并ID和名字 */
        student.setName(student.getName()+ "--" );
        /* 年龄加2 */
        student.setAge(student.getAge() + 2);
        return student;
    }

   

}