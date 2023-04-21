package domain;

import exception.ElementOfNotationTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
public class ElementOfNotation {
    private Type type;
    @Override
    public String toString() {
        return "domain.ElementOfNotation{" +
                "type=" + type +
                '}';
    }
    @Getter
    public enum Type {
        OPENING_BRACKET("("),
        CLOSING_BRACKET(")"),
        CONJUNCTION("&"),
        DISJUNCTION("||"),
        FILTER("column");
        private final String value;
        Type(final String value) {
            this.value = value;
        }
        public static Type fromValue(String value) throws ElementOfNotationTypeException {
            for (final Type type : values()) {
                if (type.value.equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new ElementOfNotationTypeException(value);
        }
    }
}
