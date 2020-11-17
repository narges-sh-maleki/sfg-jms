package guru.springframework.sfgjms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@JsonTest
class SfgJmsApplicationTests {

    @Autowired
    private  ObjectMapper myObjectMapper;

    @Test
    void contextLoads() throws JsonProcessingException {
        HelloWorldMessage obj = HelloWorldMessage.builder().message("narges").build();
        String myJsonString =  myObjectMapper.writeValueAsString(obj);

        System.out.println("Json String: " + myJsonString);
        //System.out.println("Object.toString: " + obj.toString());

        HelloWorldMessage myObj = myObjectMapper.readValue("{\"id\":null,\"message\":\"narges\"}",HelloWorldMessage.class);
        System.out.println(myObj.toString());
    }

}
