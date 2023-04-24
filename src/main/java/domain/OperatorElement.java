package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


// Класс для представления логических операторов
@Getter
@Setter
@AllArgsConstructor
@ToString
public class OperatorElement implements SearchElement {
    private OperatorType operator;

    @Override
    public OperatorType getType() {
        return this.getOperator();
    }

    // Enum для представления логических операторов
    @Getter
    public enum OperatorType {
        AND("&"),
        OR("||");

        private final String value;

        OperatorType(String value) {
            this.value = value;
        }

        public static OperatorType valueOfSymbol(String operatorSymbol) {
            for (OperatorType operatorType : OperatorType.values()) {
                if (operatorType.value.equals(operatorSymbol)) {
                    return operatorType;
                }
            }
            throw new IllegalArgumentException("Invalid symbol: " + operatorSymbol);
        }
    }
}
