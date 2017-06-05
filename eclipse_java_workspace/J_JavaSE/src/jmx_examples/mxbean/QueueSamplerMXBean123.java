/**
 * QueueSamplerMXBean.java - MXBean interface describing the management
 * operations and attributes for the QueueSampler MXBean. In this case
 * there is a read-only attribute "QueueSample" and an operation "clearQueue".
 */

package jmx_examples.mxbean;

import javax.management.MXBean;

//如接口名不以MXBean结尾 ,就要加@MXBean
@MXBean
public interface QueueSamplerMXBean123
{
	//get开头方法返回的是对象,JConsole 中[属性]显示 javax.management.openmbean.CompositeDataSupport,是复杂数据类型,可双击展开
    public QueueSample getQueueSample();
    public void clearQueue();
}
