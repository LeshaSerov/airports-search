package project.domain.parser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс для представления условий
 */
@Getter
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

        public static int maxLength() {
            int maxValue = 0;
            for (ConditionType conditionType : ConditionType.values()) {
                int length = conditionType.value.length();
                if (length > maxValue) {
                    maxValue = length;
                }
            }
            return maxValue;
        }

        /**
         * Метод valueOfSymbol() возвращает тип условия фильтрации по символу оператора.
         *
         * @param conditionSymbol символ оператора
         * @return ConditionType
         * @throws IllegalArgumentException если символ оператора не соответствует ни одному из известных типов
         */
        public static ConditionType valueOfSymbol(String conditionSymbol) {
            StringBuilder stringBuilder = new StringBuilder(conditionSymbol);
            while (stringBuilder.length() != 0) {
                for (ConditionType conditionType : ConditionType.values()) {
                    if (conditionType.value.equals(stringBuilder.toString())) {
                        return conditionType;
                    }
                }
                stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            }
            throw new IllegalArgumentException("Неизвестный символ: " + conditionSymbol);
        }
    }
}
