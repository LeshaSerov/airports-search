package domain.parsing;

public class Conjunction extends AbstractElementOfSearchLine {
    static private String value = "&";

    public static String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Conjunction{}";
    }
}
