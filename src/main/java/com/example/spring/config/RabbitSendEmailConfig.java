package com.example.spring.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitSendEmailConfig {

    /*
     * 队列名称
     */
    //topic 绑定的第一个队列
    public static final String QUEUE_SEND_EMAIL_NAME = "rabbitmq_send_email_queue";
    //topic 绑定的第二个队列
    public static final String QUEUE_SEND_EMAIL_NAME_2 = "rabbitmq_send_email_queue_2";
    public static final String QUEUE_SEND_EMAIL_NAME_3 = "rabbitmq_send_email_queue_3";
    public static final String QUEUE_SEND_EMAIL_SQL  = "rabbitmq_send_email_sql";
    /*
     * 交换机名称
     */
    public static final String QUEUE_TOPIC_NAME = "rabbitmq_topic_send_email";
    /*
     * 交换机路由键
     */
    public static final String TOPIC_EXCHANGE_ROUTE_KEY = "rabbitmq.spring.email.#";

    /*
     * 生产者路由键
     */
    public static final String QUEUE_SENDER_ROUTE_KEY = "rabbitmq.spring.email";
    public static final String QUEUE_SENDER_ROUTE_KEY_2 = "rabbitmq.spring.email.2";
    public static final String QUEUE_SENDER_ROUTE_KEY_3 = "rabbitmq.spring.email.send_3";
    public static final String QUEUE_SENDER_ROUTE_KEY_SQL = "rabbitmq.spring.email.sql";

    /*
     * 定义队列名称
     */
    @Bean("QueueSendEmail") //topic 绑定的第一个队列
    public Queue queueSendEmail(){
        return new Queue(QUEUE_SEND_EMAIL_NAME);
    }
    @Bean("QueueSendEmail2") //topic 绑定的第二个队列
    public Queue queueSendEmail2(){
        return new Queue(QUEUE_SEND_EMAIL_NAME_2);
    }
    @Bean("QueueSendEmail3")
    public Queue queueSendEmail3(){
        return new Queue(QUEUE_SEND_EMAIL_NAME_3);
    }
    @Bean("QueueSendEmailSql")
    public Queue queueSendEmailSql(){
        return new Queue(QUEUE_SEND_EMAIL_SQL);
    }
    /*
     * 定义主题交换机
     */
    @Bean("QueueTopicExchange")
    public TopicExchange queueTopicExchange(){
        return new TopicExchange(QUEUE_TOPIC_NAME,false, true);
    }

    /*
     * 绑定主题交换机与队列1
     */
    @Bean
    public Binding bindTopicExchangeToQueue(@Qualifier("QueueSendEmail") Queue queueSendEmail,   //一个声明队列1名称的Bean
                                            @Qualifier("QueueTopicExchange") TopicExchange exchangeSendEmail){   //一个声明主题交换机的Bean
        //三个参数:  bind(队列1名称的Bean).to(主题交换机名称的Bean).with(生产者1 路由键名称)
        return BindingBuilder.bind(queueSendEmail).to(exchangeSendEmail).with(QUEUE_SENDER_ROUTE_KEY);
    }

    /*
     * 绑定主题交换机与队列2
     */
    @Bean
    public Binding bindTopicExchangeToQueue2(@Qualifier("QueueSendEmail2") Queue sendEmailQueue2,  //一个声明队列2名称的Bean
                                             @Qualifier("QueueTopicExchange") TopicExchange topicExchangeSendEmail){   //声明主题交换机名称的Bean
        //三个参数:  bind(队列2名称的Bean).to(主题交换机名称的Bean).with(生产者2 路由键名称)
        return BindingBuilder.bind(sendEmailQueue2).to(topicExchangeSendEmail).with(QUEUE_SENDER_ROUTE_KEY_2);
    }

    /*
     * 绑定主题交换机与队列3
     */
    @Bean
    public Binding bindTopicExchangeToQueue3(@Qualifier("QueueSendEmail3") Queue sendEmailQueue3,  //一个声明队列3名称的Bean
                                             @Qualifier("QueueTopicExchange") TopicExchange topicExchangeSendEmail){   //声明主题交换机名称的Bean
        //三个参数:  bind(队列3名称的Bean).to(主题交换机名称的Bean).with(生产者3 路由键名称)
        return BindingBuilder.bind(sendEmailQueue3).to(topicExchangeSendEmail).with(QUEUE_SENDER_ROUTE_KEY_3);
    }

    /*
     * 绑定主题交换机与队列4
     */
    @Bean
    public Binding bindTopicExchangeToQueueSql(@Qualifier("QueueSendEmailSql") Queue sendEmaiLQueueSql,
                                               @Qualifier("QueueTopicExchange") TopicExchange topicExchangeSendEmail){
        return BindingBuilder.bind(sendEmaiLQueueSql).to(topicExchangeSendEmail).with(QUEUE_SENDER_ROUTE_KEY_SQL);
    }





















}
