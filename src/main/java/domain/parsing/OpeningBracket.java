package domain.parsing;

public class OpeningBracket extends AbstractElementOfSearchLine {
    static private String value = "(";

    public static String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "OpeningBracket{}";
    }
}
