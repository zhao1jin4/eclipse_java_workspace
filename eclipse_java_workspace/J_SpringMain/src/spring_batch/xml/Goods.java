package spring_batch.xml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ��Ʒ��Ϣ��.
 */
public class Goods {
    /** isin�� */
    private String isin;
    /** ���� */
    private int quantity;
    /** �۸� */
    private double price;
    /** �ͻ� */
    private String customer;
    /** �������� */
    private Date buyDay;
    public String getIsin()
    {
        return isin;
    }
    public void setIsin(String isin)
    {
        this.isin = isin;
    }
    public int getQuantity()
    {
        return quantity;
    }
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    public double getPrice()
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price = price;
    }
    public String getCustomer()
    {
        return customer;
    }
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    public Date getBuyDay()
    {
        return buyDay;
    }
    public void setBuyDay(Date buyDay)
    {
        this.buyDay = buyDay;
    } 
    
}