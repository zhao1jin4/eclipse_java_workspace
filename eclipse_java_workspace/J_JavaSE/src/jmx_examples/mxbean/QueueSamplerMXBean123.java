/**
 * QueueSamplerMXBean.java - MXBean interface describing the management
 * operations and attributes for the QueueSampler MXBean. In this case
 * there is a read-only attribute "QueueSample" and an operation "clearQueue".
 */

package jmx_examples.mxbean;

import javax.management.MXBean;

//��ӿ�������MXBean��β ,��Ҫ��@MXBean
@MXBean
public interface QueueSamplerMXBean123
{
	//get��ͷ�������ص��Ƕ���,JConsole ��[����]��ʾ javax.management.openmbean.CompositeDataSupport,�Ǹ�����������,��˫��չ��
    public QueueSample getQueueSample();
    public void clearQueue();
}
