package spring_batch.pattern;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import spring_batch.Student;

@SuppressWarnings("unchecked")
public class MultiItemWriter<T> implements ItemWriter<T> {
    
    javax.batch.api.chunk.ItemWriter  x;
    
    
    /** д���� */
    private List<ItemWriter<? super T>> delegates;
    
    public void setDelegates(List<ItemWriter<? super T>> delegates) {
        this.delegates = delegates;
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        // ѧ����Ϣ��Writer
        ItemWriter studentWriter = (ItemWriter) delegates.get(0);
        // ��Ʒ��Ϣ��Writer
        ItemWriter goodsWriter = (ItemWriter) delegates.get(1);
        // ѧ����Ϣ
        List<Student> studentList = new ArrayList<Student>();
        // ��Ʒ��Ϣ
        List<Goods> goodsList = new ArrayList<Goods>();
        // ������������Ϣ���ղ�ͬ��������ӵ���ͬ��List��
        for (int i = 0; i < items.size(); i++) {
            if ("Student".equals(items.get(i).getClass().getSimpleName())) {
                studentList.add((Student) items.get(i));
            } else {
                goodsList.add((Goods) items.get(i));
            }
        }
        // ���ѧ��List�������ݣ���ִ��ѧ����Ϣ��д
        if (studentList.size() > 0) {
            studentWriter.write(studentList);
        }
        // �����ƷList�������ݣ���ִ����Ʒ��Ϣ��д
        if (goodsList.size() > 0) {
            goodsWriter.write(goodsList);
        }
    }
}