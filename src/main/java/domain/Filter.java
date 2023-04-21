package domain;

import exception.ComparisonOperatorException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Filter extends ElementOfNotation{
    private int numberColumns;
    private ComparisonOperator comparisonOperator;
    private String notation;
    public Filter(Type type, int numberColumns, ComparisonOperator comparisonOperator, String notation ) {
        super(type);
        this.numberColumns = numberColumns;
        this.comparisonOperator = comparisonOperator;
        this.notation = notation;
    }
    @Override
    public String toString() {
        return "domain.Filter{" +
                "numberColumns=" + numberColumns +
                ", comparisonOperator=" + comparisonOperator +
                ", notation='" + notation + '\'' +
                ", type=" + super.getType() +
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
