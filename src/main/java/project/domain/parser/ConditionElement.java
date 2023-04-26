package project.domain.parser;

import lombok.*;

/**
 * Класс для представления условий
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ConditionElement implements SearchElement {
    private int columnNumber;
    private ConditionType conditionType;
    private String textCondition;

    final static private String startWishString = "column";

    public static String getStartWishString() {
        return startWishString;
    }

    @Override
    public ConditionType getType() {
        return this.getConditionType();
    }

    /**
     * Enum для представления фильтров
     */
    @Getter
    public enum ConditionType {
        LESS_THAN("<"),
        GREATER_THAN(">"),
        EQUAL_TO("="),
        NOT_EQUAL_TO("<>");

        private final String value;

        ConditionType(String value) {
            this.value = value;
        }

        /**
         * Метод valueOfSymbol() возвращает тип условия фильтрации по символу оператора.
         *
         * @param conditionSymbol символ оператора
         * @return ConditionType
         * @throws IllegalArgumentException если символ оператора не соответствует ни одному из известных типов
         */
        public static ConditionType valueOfSymbol(String conditionSymbol) {
            for (ConditionType conditionType : ConditionType.values()) {
                if (conditionType.value.equals(conditionSymbol)) {
                    return conditionType;
                }
            }
            throw new IllegalArgumentException("Unknown symbol: " + conditionSymbol);
        }
    }
}
