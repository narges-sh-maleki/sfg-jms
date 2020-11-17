package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.exceptions.MyCustomException;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate ;
    private final ObjectMapper objectMapper;

   // @Scheduled(fixedRate = 2000)
    public void SendMessage(){
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello World")
                .build();

        System.out.println("I am sending message...");
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE,message);
        System.out.println("Message Sent");


    }

    @Scheduled(fixedRate = 2000)
    public void SendAndReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        System.out.println("I am sending message...");
        Message reply = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RSV_QUEUE, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException{

                try {
                    TextMessage msg = session.createTextMessage(objectMapper.writeValueAsString(message));
                    msg.setStringProperty("_type",HelloWorldMessage.class.getName());
                    return msg;

                }catch (JsonProcessingException e) {
                    throw new MyCustomException( "my message",e);


                }

            }
        });


        System.out.println(reply.getBody(String.class));



    }


}
