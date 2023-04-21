package exception;

public class ComparisonOperatorException extends Exception {
    public ComparisonOperatorException() { super(); }
    public ComparisonOperatorException(String message) { super(message); }
    public ComparisonOperatorException(String message, Throwable cause) { super(message, cause); }
    public ComparisonOperatorException(Throwable cause) { super(cause); }
}
