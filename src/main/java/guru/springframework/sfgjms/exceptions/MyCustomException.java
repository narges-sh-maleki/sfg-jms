package guru.springframework.sfgjms.exceptions;

import lombok.Getter;

@Getter
public class MyCustomException extends RuntimeException {



    public MyCustomException(String message, Throwable cause) {
        super( message , cause);

    }


}
