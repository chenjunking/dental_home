package com.dental.home.app.config;

import com.dental.home.app.handler.MqttCallbackHandler;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MqttConfig
 * MQTT配置
 **/
//@Configuration
public class MqttConfig {
    private static final byte[] WILL_DATA;
    static {
        WILL_DATA = "offline".getBytes();
    }
    /**
     * mqtt订阅者使用信道名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";
    /**
     * mqtt发布者信道名称
     */
    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";
    /**
     * mqtt发送者用户名
     */
    @Value("${spring.mqtt.username}")
    private String username;
    /**
     * mqtt发送者密码
     */
    @Value("${spring.mqtt.password}")
    private String password;
    /**
     * mqtt发送者url
     */
    @Value("${spring.mqtt.url}")
    private String hostUrl;
    /**
     * mqtt发送者客户端id
     */
    @Value("${spring.mqtt.client.id}")
    private String clientId;
    /**
     * mqtt发送者主题
     */
    @Value("${spring.mqtt.default.topic}")
    private String msgTopic;
    /**
     * mqtt发送者超时时间
     */
    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout ;
    /**
     * @author liujianfu
     * @description
     */
    @Value("${spring.mqtt.keepAliveInterval}")
    private int keepAliveInterval;
    /**
     * @author liujianfu
     * @description
     */
    @Value("${spring.mqtt.connectionTimeout}")
    private int connectionTimeout;

    @Autowired
    private MqttCallbackHandler mqttCallbackHandler;

    /**
     * 新建MqttConnectionOptionsBean  MQTT连接器选项
     * @return
     */
    @Bean
    public MqttConnectOptions getSenderMqttConnectOptions(){
        MqttConnectOptions options=new MqttConnectOptions();
        // 设置连接的用户名
        if(!username.trim().equals("")){
            //将用户名去掉前后空格
            options.setUserName(username);
        }
        // 设置连接的密码
        options.setPassword(password.toCharArray());
        // 转化连接的url地址
        String[] uris={hostUrl};
        // 设置连接的地址
        options.setServerURIs(uris);
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(completionTimeout);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(keepAliveInterval);
        // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
        //设置超时时间
        options.setConnectionTimeout(connectionTimeout);
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        return options;
    }
    /**
     *创建MqttPathClientFactoryBean
     */
    @Bean
    public MqttPahoClientFactory senderMqttClientFactory() {
        //创建mqtt客户端工厂
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        //设置mqtt的连接设置
        factory.setConnectionOptions(getSenderMqttConnectOptions());
        return factory;
    }
    /**
     * 发布者-MQTT信息通道（生产者）
     */
    @Bean(name = CHANNEL_NAME_OUT)
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }


    /**
     * 发布者-MQTT消息处理器（生产者）  将channel绑定到MqttClientFactory上
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        //创建消息处理器
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                clientId+"_pub",
                senderMqttClientFactory());
        //设置消息处理类型为异步
        messageHandler.setAsync(true);
        //设置消息的默认主题
        messageHandler.setDefaultTopic(msgTopic);
        messageHandler.setDefaultRetained(false);
        //1.重新连接MQTT服务时，不需要接收该主题最新消息，设置retained为false;
        //2.重新连接MQTT服务时，需要接收该主题最新消息，设置retained为true;
        return messageHandler;
    }

    /************消费者，订阅者的消费信息*****/
    /**
     * MQTT信息通道（消费者）
     */
    @Bean(name = CHANNEL_NAME_IN)
    public MessageChannel mqttInboundChannel() {
        return new DirectChannel();
    }


    /**
     * MQTT消息订阅绑定（消费者）
     */
    @Bean
    public MqttPahoMessageDrivenChannelAdapter inbound() {
        System.out.println("topics:"+msgTopic);
        List<String> topicList = new ArrayList<>();
        topicList.addAll(Arrays.asList(msgTopic.trim().split(",")));
        topicList.add("abc");
        String [] topicArr = new String[topicList.size()];
        topicList.toArray(topicArr);

        // 可以同时消费（订阅）多个Topic
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        clientId+"_sub", senderMqttClientFactory(), topicArr);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(0);
        // 设置订阅通道
        adapter.setOutputChannel(mqttInboundChannel());
        return adapter;
    }

    /**
     * MQTT消息处理器（消费者）
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_IN)
    public MessageHandler handler() {
        return message -> {
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
            String payload = message.getPayload().toString();
            mqttCallbackHandler.handle(topic,payload);
        };
    }
}
