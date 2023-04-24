package exception;

public class PairBracketException extends Exception {
    public PairBracketException() { super(); }
    public PairBracketException(String message) { super(message); }
    public PairBracketException(String message, Throwable cause) { super(message, cause); }
    public PairBracketException(Throwable cause) { super(cause); }
}
