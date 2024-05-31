package kg.nar.HomeChiefBack.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
