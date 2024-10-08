package kg.nar.HomeChiefBack.exception;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handlerNotFoundException(NotFoundException e) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handlerNotFoundException(BadCredentialsException e) {

        return new ExceptionResponse(
                HttpStatus.FORBIDDEN,
                e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handlerNotFoundException(BadRequestException e) {

        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage());
    }
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handlerUnAuthorizedException(UnauthorizedException e) {

        return new ExceptionResponse(
                HttpStatus.UNAUTHORIZED,
                e.getMessage());
    }

    @ExceptionHandler(BlockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleBlockedException(BlockedException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

}