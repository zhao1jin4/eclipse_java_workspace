/**
 * QueueSamplerMXBean.java - MXBean interface describing the management
 * operations and attributes for the QueueSampler MXBean. In this case
 * there is a read-only attribute "QueueSample" and an operation "clearQueue".
 */

package jmx_examples.mxbean.description;

import javax.management.DescriptorKey;

//全部是自定义annotation 
@Author("Mr Bean")//在jconsole 类级的 [描述符]中会新增加属性author,原因是配置 @DescriptorKey("author")
@Version("1.0")
public interface QueueSamplerMXBean {
    @DisplayName("GETTER: QueueSample")//在jconsole 类级的->[属性](因getter)-> [描述符]中会新增加属性displayName,原因是配置 @DescriptorKey("displayName")
    public QueueSample getQueueSample();
    @DisplayName("OPERATION: clearQueue")
    public void clearQueue();
}
