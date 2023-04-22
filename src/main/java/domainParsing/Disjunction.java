package domainParsing;

import lombok.Getter;

@Getter
public class Disjunction extends AbstractElementOfNotation {
    static private String value = "||";

    public static String getValue() {
        return value;
    }
}
