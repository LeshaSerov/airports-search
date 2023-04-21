package exception;

public class StringIsNotValidateException extends Exception {
    public StringIsNotValidateException() { super(); }
    public StringIsNotValidateException(String message) { super(message); }
    public StringIsNotValidateException(String message, Throwable cause) { super(message, cause); }
    public StringIsNotValidateException(Throwable cause) { super(cause); }
}
