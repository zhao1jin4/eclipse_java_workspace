package spring_batch.jdbc;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

/**
 * �Զ���Skip�����ࡣ
 */
public class MySkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, int skipCount)
            throws SkipLimitExceededException {
        return false;
    }
}