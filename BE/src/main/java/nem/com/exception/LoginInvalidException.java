package nem.com.exception;

public class LoginInvalidException extends RuntimeException {
    public LoginInvalidException(String message) {
        super(message);
    }
}
