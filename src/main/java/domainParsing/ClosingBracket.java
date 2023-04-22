package domainParsing;

import lombok.Getter;

@Getter
public class ClosingBracket extends AbstractElementOfNotation {
    static private String value = ")";

    public static String getValue() {
        return value;
    }
}
