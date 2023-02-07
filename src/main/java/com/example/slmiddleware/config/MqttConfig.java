package com.example.slmiddleware.config;


import com.example.slmiddleware.service.CommunicationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

@Configuration // 스프링에서 설정 클래스에 연결됨.
@RequiredArgsConstructor // 생성자를 사용하여 CommunicationService 객체를 인터페이스.
public class MqttConfig { // mqtt 통신을 위한 채널 구성
    private final CommunicationService comunicationService;
//    private static final String BROKER_URL = "tcp://118.41.132.222:1883";
//    // mqtt 브로커의 url
//    private static final String MQTT_CLIENT_ID = MqttAsyncClient.generateClientId();
//    // mqtt 브로커의 url
//    private static final String TOPIC_FILTER = "homenet/Sensor1/#";
//    // 토픽, mqtt 메시지 수신에 관심이 있는 토픽

    private static final String BROKER_URL = "tcp://localhost:1883";
    private static final String MQTT_CLIENT_ID = MqttAsyncClient.generateClientId();
    private static final String TOPIC_FILTER = "every";

    //토픽은 공장별로 다르게 한다.
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }
    // mqtt 메시지를 수신할 채널 생성

    @Bean
    public MessageProducer inboundChannel() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(BROKER_URL, MQTT_CLIENT_ID, TOPIC_FILTER);
        // mqtt 메시지를 수신할 어댑터, url, 클라이언트 ID, 토픽 사용자 설정.
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        // adapter 객체에 DefaultPahoMessageConverter 객체 설정.
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    // mqttInputChannel을 입력 채널로 사용하는 MessageHandler Bean 생성
    public MessageHandler inboundMessageHandler() {
        return message -> { // message를 수신하면 수행.
            //에러 핸들러 message 상태코드를 확인해서 오류일경우 오류 로그 남기고, 오류데이터 저장 후 공정데이터 저장하지않고 종료
            String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
            System.out.println("Topic:" + topic);
            System.out.println("Payload:" + message.getPayload());
//            if(message.getHeaders().get("statuscode"))
            try {
                comunicationService.test(message.getPayload());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        };
    }


    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory(){

        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] {BROKER_URL});
        options.setUserName("username");
        options.setPassword("1234".toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(){
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler("testClient",mqttPahoClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(1);
        messageHandler.setDefaultTopic("testTopic");
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel(){
        return new DirectChannel();
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MyGateway {
        void sendToMqtt(String data);
    }
}
