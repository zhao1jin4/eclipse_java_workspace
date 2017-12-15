package spring_batch.flat;

import org.springframework.batch.item.ItemProcessor;

import spring_batch.Student;

public class MessagesItemProcessor implements ItemProcessor<Student, Message> {

	public Message process(Student stu) throws Exception {
		Message m = new Message();
		m.setContent("Hello " + stu.getName()
				+ ",please pay promptly at the end of this month.");
		return m;
	}

}