package jdk9new.flow.demo1;

import java.util.Random;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
public class PeriodicPublisher {
    final static int MAX_SLEEP_DURATION = 3;
    // Used to generate sleep time
    final static Random sleepTimeGenerator = new Random();
    public static void main(String[] args) {
        SubmissionPublisher<Long> pub = new SubmissionPublisher<>();
        // Create three subscribers
        SimpleSubscriber sub1 = new SimpleSubscriber("S1", 2);
        SimpleSubscriber sub2 = new SimpleSubscriber("S2", 5);
        SimpleSubscriber sub3 = new SimpleSubscriber("S3", 6);
        SimpleSubscriber sub4 = new SimpleSubscriber("S4", 10);
        // Subscriber to the publisher
        pub.subscribe(sub1);//会调用 Subscriber的onSubcribe方法
        pub.subscribe(sub2);
        pub.subscribe(sub3);
        // Subscribe the 4th subscriber after 2 seconds
        subscribe(pub, sub4, 2);//后面订阅的，收不到前面的消息
        // Start publishing items
        Thread pubThread = publish(pub, 5);
        try {
            // Wait until the publisher is finished
            pubThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static Thread publish(SubmissionPublisher<Long> pub, long count) {
        Thread t = new Thread(() -> {
            for (long i = 1; i <= count; i++) {
                pub.submit(i);//会调用 Subscriber的onNext方法
                sleep(i);
            }
            // Close the publisher
            pub.close();//会调用 Subscriber的onComplete方法
        });
        // Start the thread
        t.start();
        return t;
    }
    private static void sleep(Long item) {
        // Wait for 1 to 3 seconds
        int sleepTime = sleepTimeGenerator.nextInt(MAX_SLEEP_DURATION) + 1;
        try {
            System.out.printf("Published %d. Sleeping for %d sec.%n", item, sleepTime);
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void subscribe(SubmissionPublisher<Long> pub, Subscriber<Long> sub,
                                  long delaySeconds) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(delaySeconds);
                pub.subscribe(sub);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }            
        }).start();
    }
}