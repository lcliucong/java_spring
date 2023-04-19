package com.example.spring.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  RabbitMqConfig
 *  rabbitMq 配置信息
 *  Producer(生产者)： 将消息发送到Exchange
 *  Exchange(交换器)：将从生产者接收到的消息路由到Queue
 *  Queue(队列)：存放供消费者消费的消息
 *  BindingKey(绑定键)：建立Exchange与Queue之间的关系
 *  RoutingKey(路由键)：Producer发送消息与路由键给Exchange，Exchange将判断RoutingKey是否符合BindingKey，如何则将该消息路由到绑定的Queue
 *  Consumer(消费者)：从Queue中获取消息
 */
@Configuration
public class RabbitMQConfig {

    /*
     * 消息队列名称、消息队列路由键、消费者消费队列路由键
     */
    public static final String QUEUE_NAME = "rabbitmq_spring_boot_demo";
    public static final String QUEUE_NAME_API = "rabbitmq_api";

    /*
     * 交换机名称
     */
    public static final String DIRECT_EXCHANGE_NAME_DEMO = "rabbitmq_direct_exchange_demo"; //直连交换机
    public static final String TOPIC_EXCHANGE_NAME_DEMO= "rabbitmq_topic_exchange_demo"; //
    public static final String TOPIC_EXCHANGE_NAME_API= "rabbitmq_topic_exchange_api"; //

    /*
     * 交换机代理的路由键
     */
    public static final String DIRECT_EXCHANGE_ROUT_KEY_DEMO = "rabbitmq.spring.boot.demo";
    public static final String TOPIC_EXCHANGE_ROUT_KEY_DEMO = "rabbitmq.spring.boot.#";
    public static final String TOPIC_EXCHANGE_ROUT_KEY_API  = "rabbitmq.api.#";
    /*
     * 生产者发送路由键
     */
    public static final String QUEUE_SENDER_ROUTING_KEY_DEMO = "rabbitmq.spring.boot.demo";
    public static final String QUEUE_SENDER_ROUTING_KEY_DEMO_2 = "rabbitmq.spring.boot.demo.2";

    /*
     * 定义队列 demo
     */
    @Bean("queueDemo")
    public Queue queueDemo(){
        return new Queue(QUEUE_NAME);
    }

    /*
     * 定义队列 api
     */
    @Bean("queueApi")
    public Queue queueApi(){
        return new Queue(QUEUE_NAME_API);
    }

    /*
     * 定义直连交换机 demo
     * 使用Bean 定义 交换机名称 return出去
     */
    @Bean("directExchangeDemo")
    public DirectExchange directExchangeDemo(){
        //durable 是否持久化, 默认为false, 持久化队列:会被存储到硬盘上, 消息代理重启时仍然存在,  暂存队列: 当前链接有效
        //autoDelete 是否自动删除, 当没有消费者或生产者使用此队列时, 会自动删除
        return new DirectExchange(DIRECT_EXCHANGE_NAME_DEMO, false, true);
    }

    /*
     * 定义主题交换机 demo
     */
    @Bean("topicExchangeDemo")
    public TopicExchange topicExchangeDemo(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME_DEMO,false,true);
    }

    /*
     * 定义主题交换机 api
     */
    @Bean("topicExchangeApi")
    public TopicExchange topicExchangeApi(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME_API,false,true);
    }

    /*
     * 绑定直连交换机与消息队列
     * 通过@Qualifier 注解找到Bean为directExchangeDemo 的交换机，并绑定Bean为queueDemo的方法(此方法返回了最终消费者)
     *
     */
    @Bean
    public Binding bindingDirectExchangeDemo(@Qualifier("queueDemo") Queue queue, //queue实际上指代RabbitMqReceiverController 消费者
                                             @Qualifier("directExchangeDemo") DirectExchange directExchange){  //directExchangeDemo 为一个Bean 指代生产者发送的路由键，在sender中绑定
        return BindingBuilder.bind(queue).to(directExchange).with(DIRECT_EXCHANGE_ROUT_KEY_DEMO);
    }

    /*
     * 绑定主题交换机与消息队列
     */
    @Bean
    public Binding bindingTopicExchangeDemo(@Qualifier("queueDemo") Queue queue,
                                            @Qualifier("topicExchangeDemo") TopicExchange topicExchange) {  //topicExchangeDemon 为一个Bean
        return BindingBuilder.bind(queue).to(topicExchange).with(TOPIC_EXCHANGE_ROUT_KEY_DEMO);
    }

    /*
     * 绑定主题交换机与队列(api)
     *
     * @param queueApi
     * @param topicExchangeApi
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeApi(@Qualifier("queueApi") Queue queueApi,
                                           @Qualifier("topicExchangeApi") TopicExchange topicExchangeApi) {
        return BindingBuilder.bind(queueApi).to(topicExchangeApi).with(TOPIC_EXCHANGE_ROUT_KEY_API);
    }
























































































}
