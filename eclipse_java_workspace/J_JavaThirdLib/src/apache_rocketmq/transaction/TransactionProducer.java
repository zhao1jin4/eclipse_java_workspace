/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package apache_rocketmq.transaction;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class TransactionProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {
    	String mqGroup = "myGroup";
		String mqIP = "localhost:9876";
		String mqTopic = "myTopic";
		String mqTag = "myTag";
    	
        TransactionCheckListener transactionCheckListener = new TransactionCheckListenerImpl();//自己的类
        
        TransactionMQProducer producer = new TransactionMQProducer(mqGroup);
        producer.setNamesrvAddr(mqIP);
        producer.setCheckThreadPoolMinSize(2);
        producer.setCheckThreadPoolMaxSize(2);
        producer.setCheckRequestHoldMax(2000);
        producer.setTransactionCheckListener(transactionCheckListener);//broker检查发送的回调吗
        producer.start();

        String[] tags = new String[] {mqTag, "TagB", "TagC", "TagD", "TagE"};
        LocalTransactionExecuter tranExecuter = new TransactionExecuterImpl();//自己的类 
        for (int i = 0; i < 100; i++) {
            Message msg =  new Message(mqTopic, tags[i % tags.length], "KEY" + i,
                    ("你好 RocketMQ " + i).getBytes(Charset.forName("UTF-8")));
            SendResult sendResult = producer.sendMessageInTransaction(msg, tranExecuter, null);
            System.out.printf("%s%n", sendResult);
            Thread.sleep(10); 
        }

        for (int i = 0; i < 100000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
}
