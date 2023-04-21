package exception;

public class ElementOfNotationTypeException extends Exception{
    public ElementOfNotationTypeException() { super(); }
    public ElementOfNotationTypeException(String message) { super(message); }
    public ElementOfNotationTypeException(String message, Throwable cause) { super(message, cause); }
    public ElementOfNotationTypeException(Throwable cause) { super(cause); }
}
