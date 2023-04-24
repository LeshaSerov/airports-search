package domain.parsing;

public class ClosingBracket extends AbstractElementOfSearchLine {
    static private String value = ")";

    public static String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ClosingBracket{}";
    }
}
