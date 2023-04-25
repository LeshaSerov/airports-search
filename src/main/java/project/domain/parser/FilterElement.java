package project.domain.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// Класс для представления фильтра
@Getter
@Setter
@AllArgsConstructor
@ToString
public class FilterElement implements SearchElement {
    private int columnNumber;
    private FilterType filterType;
    private String filterValue;

    final static private String startWishString = "column";

    public static String getStartWishString() {
        return startWishString;
    }

    @Override
    public FilterType getType() {
        return this.getFilterType();
    }

    // Enum для представления фильтров
    @Getter
    public enum FilterType {
        LESS_THAN("<"),
        GREATER_THAN(">"),
        EQUAL_TO("="),
        NOT_EQUAL_TO("<>");

        private final String value;

        FilterType(String value) {
            this.value = value;
        }

        public static FilterType valueOfSymbol(String operatorSymbol) {
            for (FilterType filterType : FilterType.values()) {
                if (filterType.value.equals(operatorSymbol)) {
                    return filterType;
                }
            }
            throw new IllegalArgumentException("Unknown symbol: " + operatorSymbol);
        }
    }
}
