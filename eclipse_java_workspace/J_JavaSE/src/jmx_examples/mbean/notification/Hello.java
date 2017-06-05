/* Hello.java - MBean implementation for the Hello World MBean.
   This class must implement all the Java methods declared in the
   HelloMBean interface, with the appropriate behavior for each one.  */

package jmx_examples.mbean.notification;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import javax.management.*;

public class Hello
	extends NotificationBroadcasterSupport implements HelloMBean {
	//MBeanҲ�ܷ�֪ͨ,����ʵ�� NotificationBroadcaster �ӿ� ���� �ӽӿ� NotificationEmitter ��ʵ���� NotificationBroadcasterSupport 
	//jconsole�����л��һ��[֪ͨ],��Subscriber(�����е�֪ͨ),��������Է��ı�,[֪ͨ]�л���ʾ��������Ϣ,��һ��[���к�]��������
    public void sayHello() {
	System.out.println("hello, world");
    }

    public int add(int x, int y) {
	return x + y;
    }

    /* Getter for the Name attribute.  The pattern shown here is
       frequent: the getter returns a private field representing the
       attribute value.  In our case, the attribute value never
       changes, but for other attributes it might change as the
       application runs.  Consider an attribute representing
       statistics such as uptime or memory usage, for example.  Being
       read-only just means that it can't be changed through the
       management interface.  */
    public String getName() {
	return this.name;
    }

    /* Getter for the CacheSize attribute.  The pattern shown here is
       frequent: the getter returns a private field representing the
       attribute value, and the setter changes that field.  */
    public int getCacheSize() {
	return this.cacheSize;
    }

    /* Setter for the CacheSize attribute.  To avoid problems with
       stale values in multithreaded situations, it is a good idea
       for setters to be synchronized.  */
    public synchronized void setCacheSize(int size) {
	int oldSize = this.cacheSize;
	this.cacheSize = size;

	/* In a real application, changing the attribute would
	   typically have effects beyond just modifying the cacheSize
	   field.  For example, resizing the cache might mean
	   discarding entries or allocating new ones.  The logic for
	   these effects would be here.  */
	System.out.println("Cache size now " + this.cacheSize);

	/* Construct a notification that describes the change.  The
	   "source" of a notification is the ObjectName of the MBean
	   that emitted it.  But an MBean can put a reference to
	   itself ("this") in the source, and the MBean server will
	   replace this with the ObjectName before sending the
	   notification on to its clients.

	   For good measure, we maintain a sequence number for each
	   notification emitted by this MBean.
	
	   The oldValue and newValue parameters to the constructor are
	   of type Object, so we are relying on Tiger's autoboxing
	   here.  */
//	Notification n = new AttributeChangeNotification(this,
//					    sequenceNumber++,
//					    System.currentTimeMillis(),
//					    "CacheSize changed",
//					    "CacheSize",
//					    "int",
//					    oldSize,
//					    this.cacheSize);

	DateFormat format=DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.CHINA);
	//String x=format.format(new Date());
	//System.out.println(x);//2012-8-15 11:46:19
	Date date=null;
	try {
		date=format.parse("2012-12-12 22:22:22");
	} catch (ParseException e) {
		e.printStackTrace();
	}
	Notification n =new AttributeChangeNotification(this,//�ĸ����������
		    sequenceNumber++,//˳���,��ʾ��jconsole��[���к�]��������
		    date.getTime(),//��ʾ��jconsole��[ʱ���]��������
		    "CacheSize���Ըı���", //��Ϣ
		    "CacheSize", //����,getCacheSize()
		    "int",//��������
		    oldSize,//���Ըı�ǰ�ľ�ֵ
		    this.cacheSize);//��ֵ 
	
	
	/* Now send the notification using the sendNotification method
	   inherited from the parent class
	   NotificationBroadcasterSupport.  */
	sendNotification(n);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo()//��д��
    {
	String[] types = new String[] {
	    AttributeChangeNotification.ATTRIBUTE_CHANGE// ATTRIBUTE_CHANGE = "jmx.attribute.change";
	};
	String name = AttributeChangeNotification.class.getName();
	String description = "An attribute of this MBean has changed";
	MBeanNotificationInfo info =
	   // new MBeanNotificationInfo(types, name, description);
		new MBeanNotificationInfo(types, "��ʾ�����е�����", "����");
	return new MBeanNotificationInfo[] {info};
    }

    private final String name = "Reginald";
    private int cacheSize = DEFAULT_CACHE_SIZE;
    private static final int DEFAULT_CACHE_SIZE = 200;

    private long sequenceNumber = 1;
}
