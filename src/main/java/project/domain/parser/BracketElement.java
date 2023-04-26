package project.domain.parser;

import lombok.*;

/**
 * Класс для представления скобок
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class BracketElement implements SearchElement {
    private BracketType bracketType;

    @Override
    public BracketType getType() {
        return this.getBracketType();
    }

    /**
     * Enum для представления скобок
     */
    @Getter
    public enum BracketType {
        OPEN("("),
        CLOSE(")");

        private final String value;

        BracketType(String value) {
            this.value = value;
        }

        /**
         * Метод valueOfSymbol() возвращает тип скобок по символу оператора.
         *
         * @param bracketSymbol символ оператора
         * @return BracketType
         * @throws IllegalArgumentException если символ оператора не соответствует ни одному из известных типов
         */
        public static BracketType valueOfSymbol(String bracketSymbol) {
            for (BracketType bracketType : BracketType.values()) {
                if (bracketType.value.equals(bracketSymbol)) {
                    return bracketType;
                }
            }
            throw new IllegalArgumentException("Unknown symbol: " + bracketSymbol);
        }
    }
}
