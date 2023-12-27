package exception;
public class NoSuchCompanyFormatException extends RuntimeException{
    public NoSuchCompanyFormatException(String msg) {
        super(msg);
    }

    public NoSuchCompanyFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
