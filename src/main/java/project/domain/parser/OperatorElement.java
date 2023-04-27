package project.domain.parser;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Класс для представления логических операторов
 */
@Getter
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

    }
}
