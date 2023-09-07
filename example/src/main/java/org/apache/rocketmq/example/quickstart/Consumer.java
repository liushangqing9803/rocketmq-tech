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
package org.apache.rocketmq.example.quickstart;

import java.util.Date;
import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * This example shows how to subscribe and consume messages using providing {@link DefaultMQPushConsumer}.
 */
public class Consumer {

    public static void main(String[] args) {

        try{
            /*
             * Instantiate with specified consumer group name.
             */
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("lsqGroup");
            consumer.setNamesrvAddr("127.0.0.1:9876");


            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

            /*
             * Subscribe one more more topics to consume.
             */
            consumer.subscribe("lsqTopic1", "*");

            /*
             *  Register callback to execute on arrival of messages fetched from brokers.
             */
            consumer.registerMessageListener(new MessageListenerConcurrently() {

                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    try{
                        for (MessageExt messageExt:msgs){
                            System.out.println(new Date(messageExt.getBornTimestamp()).toLocaleString());
                            System.out.println(new Date().toLocaleString());
                            System.out.println(messageExt.getMsgId());
                            System.out.println(messageExt.getReconsumeTimes());
                            System.out.println(new String(messageExt.getBody()));

                            System.out.println(messageExt.getUserProperty("mylsq"));
                            System.out.println(messageExt.getUserProperty("test"));
                            System.out.println(messageExt.getProperties());
                        }
                    }catch (Exception e ){
                        System.out.println(e);
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;

                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });

            /*
             *  Launch the consumer instance.
             */
            consumer.start();

            System.out.printf("Consumer Started.%n");
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
