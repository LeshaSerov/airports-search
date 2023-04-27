package project.domain.parser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс для представления скобок
 */
@Getter
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

    }
}
