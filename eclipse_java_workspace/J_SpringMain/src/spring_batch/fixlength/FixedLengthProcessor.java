package spring_batch.fixlength;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import spring_batch.Student;

@Component("fixedLengthProcessor")
public class FixedLengthProcessor implements
        ItemProcessor<Student, Student> {

    /**
     * ��ȡ�������ݽ��м򵥵Ĵ���
     * 
     * @param student
     *            ����ǰ�����ݡ�
     * @return ���������ݡ�
     * @exception Exception
     *                �����Ƿ������κ��쳣��
     */
    public Student process(Student student) throws Exception {
        /* �ϲ�ID������ */
        student.setName(student.getName()+ "--" );
        /* �����2 */
        student.setAge(student.getAge() + 2);
        return student;
    }

   

}