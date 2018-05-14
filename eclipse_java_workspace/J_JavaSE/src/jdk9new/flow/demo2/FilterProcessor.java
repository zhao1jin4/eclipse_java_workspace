package jdk9new.flow.demo2;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Processor;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;

// Processor 即是Publisher 又是   Publisher,可以中作为转换器
public class FilterProcessor<T> extends SubmissionPublisher<T> implements Processor<T,T>{
    private Predicate<? super T> filter;
    public FilterProcessor(Predicate<? super T> filter) {
        this.filter = filter;
    }
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        // Request an unbounded number of items
        subscription.request(Long.MAX_VALUE);
    }
    @Override
    public void onNext(T item) {
        // If the item passes the filter publish it. Otherwise, no action is needed.
        System.out.println("Filter received: " + item);
        if (filter.test(item)) {            
            this.submit(item);
        }
    }
    @Override
    public void onError(Throwable t) {
        // Pass the onError message to all subscribers asynchronously        
        this.getExecutor().execute(() -> this.getSubscribers()
                                             .forEach(s -> s.onError(t)));
    }
    @Override
    public void onComplete() {
        System.out.println("Filter is complete.");
        // Close this publisher, so all its subscribers will receive a onComplete message
        this.close();
    }
}