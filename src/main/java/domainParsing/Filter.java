package domainParsing;

import exception.ComparisonOperatorException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filter extends AbstractElementOfNotation {
    static private String value = "column";

    public static String getValue() {
        return value;
    }
    private int numberColumns;
    private ComparisonOperator comparisonOperator;
    private String notation;
    public Filter(int numberColumns, ComparisonOperator comparisonOperator, String notation ) {
        this.numberColumns = numberColumns;
        this.comparisonOperator = comparisonOperator;
        this.notation = notation;
    }
    public String toString() {
        return "domainParsing.Filter{" +
                "numberColumns=" + numberColumns +
                ", comparisonOperator=" + comparisonOperator +
                ", notation='" + notation + '\'' +
                '}';
    }
    public enum ComparisonOperator {
        EQUAL_TO("="),
        NOT_EQUAL_TO("<>"),
        LESS_THAN("<"),
        GREATER_THAN(">");
        private final String value;
        ComparisonOperator(final String value) {
            this.value = value;
        }
        public static ComparisonOperator fromValue(String value) throws ComparisonOperatorException {
            for (final ComparisonOperator comparisonOperator : values()) {
                if (comparisonOperator.value.equalsIgnoreCase(value)) {
                    return comparisonOperator;
                }
            }
            throw new ComparisonOperatorException(value);
        }
    }
}
