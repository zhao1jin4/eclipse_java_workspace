/**
 * QueueSampler.java - MXBean implementation for the QueueSampler MXBean.
 * This class must implement all the Java methods declared in the
 * QueueSamplerMXBean interface, with the appropriate behavior for each one.
 */

package jmx_examples.mxbean;

import java.util.Date;
import java.util.Queue;

public class QueueSampler123 implements QueueSamplerMXBean123//实现类可以不是接口名的前半部分
{
    
    private Queue<String> queue;
    
    public QueueSampler123(Queue<String> queue) {
        this.queue = queue;
    }
    
    public QueueSample getQueueSample() {
        synchronized (queue) {
            return new QueueSample(new Date(), queue.size(), queue.peek());
        }
    }
    
    public void clearQueue() {
        synchronized (queue) {
            queue.clear();
        }
    }
}
