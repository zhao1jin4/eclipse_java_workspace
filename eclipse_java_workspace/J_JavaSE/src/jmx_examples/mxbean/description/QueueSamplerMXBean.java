/**
 * QueueSamplerMXBean.java - MXBean interface describing the management
 * operations and attributes for the QueueSampler MXBean. In this case
 * there is a read-only attribute "QueueSample" and an operation "clearQueue".
 */

package jmx_examples.mxbean.description;

import javax.management.DescriptorKey;

//ȫ�����Զ���annotation 
@Author("Mr Bean")//��jconsole �༶�� [������]�л�����������author,ԭ�������� @DescriptorKey("author")
@Version("1.0")
public interface QueueSamplerMXBean {
    @DisplayName("GETTER: QueueSample")//��jconsole �༶��->[����](��getter)-> [������]�л�����������displayName,ԭ�������� @DescriptorKey("displayName")
    public QueueSample getQueueSample();
    @DisplayName("OPERATION: clearQueue")
    public void clearQueue();
}
