package exception;

public class InvalidJsonFormatException extends RuntimeException {
    public InvalidJsonFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

