package project.domain.parser;

import lombok.*;

/**
 * Класс для представления логических операторов
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OperatorElement implements SearchElement {
    private OperatorType operatorType;

    @Override
    public OperatorType getType() {
        return this.getOperatorType();
    }

    /**
     * Enum для представления логических операторов
     */
    @Getter
    public enum OperatorType {
        AND("&"),
        OR("||");

        private final String value;

        OperatorType(String value) {
            this.value = value;
        }

        /**
         * Метод valueOfSymbol() возвращает тип оператора фильтрации по символу оператора.
         *
         * @param operatorSymbol символ оператора
         * @return OperatorType
         * @throws IllegalArgumentException если символ оператора не соответствует ни одному из известных типов
         */
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
