package spring_batch.xml;

import java.util.Date;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * XML�ļ������ࡣ
 */
@Component("xmlProcessor")
public class XMLProcessor implements ItemProcessor<Goods, Goods> {

    /**
     * XML�ļ����ݴ���
     * 
     */
    @Override
    public Goods process(Goods goods) throws Exception {
        // �������ڱ��
        goods.setBuyDay(new Date());
        // �˿���Ϣ���
        goods.setCustomer(goods.getCustomer() + "�˿�!");
        // ISIN���
        goods.setIsin(goods.getIsin() + "IsIn");
        // �۸���
        goods.setPrice(goods.getPrice() + 1000.112);
        // �������
        goods.setQuantity(goods.getQuantity() + 100);
        // ���������ݷ���
        return goods;
    }
}