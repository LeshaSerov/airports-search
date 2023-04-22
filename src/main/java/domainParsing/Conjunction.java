package domainParsing;

import lombok.Getter;

@Getter
public class Conjunction extends AbstractElementOfNotation{
    static private String value = "&";

    public static String getValue() {
        return value;
    }
}
