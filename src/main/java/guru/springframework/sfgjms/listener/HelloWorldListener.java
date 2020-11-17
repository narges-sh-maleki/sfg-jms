package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloWorldListener {


   private final  JmsTemplate jmsTemplate;
    //@JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload  HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message){

        System.out.println("I got a message");
        System.out.println(helloWorldMessage);
        throw  new RuntimeException();

    }

    @JmsListener(destination = JmsConfig.MY_SEND_RSV_QUEUE)
    public void listenAndReply(@Payload  HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders messageHeaders,
                       Message message) throws JMSException {

        System.out.println("I got a message");
        System.out.println(helloWorldMessage);
        HelloWorldMessage replyMessage = HelloWorldMessage.builder().
                id(UUID.randomUUID()).message("World").build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(),replyMessage);
        System.out.println("I replied");

    }
}
