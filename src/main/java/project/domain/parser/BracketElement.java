package project.domain.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// Класс для представления скобок
@Getter
@Setter
@AllArgsConstructor
@ToString
public class BracketElement implements SearchElement  {
    private BracketType bracketType;

    @Override
    public BracketType getType() {
        return this.getBracketType();
    }

    // Enum для представления скобок
    @Getter
    public enum BracketType {
        OPEN("("),
        CLOSE(")");

        private final String value;

        BracketType(String value) {
            this.value = value;
        }

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
