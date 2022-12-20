package nem.com.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        ErrorMessage errorMessage = new ErrorMessage("NOT_FOUND", exception.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UniqueFieldException.class)
    public ResponseEntity<?> uniqueFieldExceptionHandler(UniqueFieldException exception) {
        ErrorMessage errorMessage = new ErrorMessage("UNIQUE_FIELD", exception.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LimitQuantityException.class)
    public ResponseEntity<?> uniqueFieldExceptionHandler(LimitQuantityException exception) {
        ErrorMessage errorMessage = new ErrorMessage("LIMIT_QUANTITY", exception.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IsEmptyException.class)
    public ResponseEntity<?> uniqueFieldExceptionHandler(IsEmptyException exception) {
        ErrorMessage errorMessage = new ErrorMessage("EMPTY", exception.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginInvalidException.class)
    public ResponseEntity<?> uniqueFieldExceptionHandler(LoginInvalidException exception) {
        ErrorMessage errorMessage = new ErrorMessage("LOGIN_INVALID", exception.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserInactiveException.class)
    public ResponseEntity<?> userInactiveException(UserInactiveException exception) {
        ErrorMessage errorMessage = new ErrorMessage("INACTIVE", exception.getMessage(), new Date());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
