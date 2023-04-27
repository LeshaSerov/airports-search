package project.filter;

import lombok.extern.slf4j.Slf4j;
import project.domain.Airport;
import project.domain.parser.ConditionElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс ColumnValueFilter предоставляет статический метод для фильтрации списка объектов Airport на основе условия, заданного в объекте ConditionElement.
 */
@Slf4j
public class ColumnValueFilter {
    /**
     * Метод filter осуществляет фильтрацию списка объектов Airport на основе условия, заданного в объекте ConditionElement.
     *
     * @param list             Список объектов Airport, который требуется отфильтровать. Не изменяет исходный список.
     * @param conditionElement Объект ConditionElement, содержащий условие для фильтрации.
     * @return Отфильтрованный список объектов Airport, удовлетворяющих условию.
     */
    public static List<Airport> filter(List<Airport> list, ConditionElement conditionElement) {
        List<Airport> resultList = new ArrayList<>();
        try {
            if (!list.isEmpty()) {
                int columnName = conditionElement.getColumnNumber();
                String textCondition = conditionElement.getTextCondition();
                ConditionElement.ConditionType conditionType = conditionElement.getConditionType();

                for (Airport airport : list) {
                    Object value = airport.getValueForColumn(columnName);
                    if (value != null) {
                        switch (value.getClass().getSimpleName()) {
                            case "String":
                                String stringValue = (String) value;
                                switch (conditionType) {
                                    case LESS_THAN:
                                        if (stringValue.compareToIgnoreCase(textCondition) < 0) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case GREATER_THAN:
                                        if (stringValue.compareToIgnoreCase(textCondition) > 0) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case EQUAL_TO:
                                        if (stringValue.compareToIgnoreCase(textCondition) == 0) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case NOT_EQUAL_TO:
                                        if (stringValue.compareToIgnoreCase(textCondition) != 0) {
                                            resultList.add(airport);
                                        }
                                        break;
                                }
                                break;
                            case "Double":
                                double doubleValue = (double) value;
                                double limit = Double.parseDouble(textCondition);
                                switch (conditionType) {
                                    case LESS_THAN:
                                        if (doubleValue < limit) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case GREATER_THAN:
                                        if (doubleValue > limit) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case EQUAL_TO:
                                        if (doubleValue - limit < 0.000000000001) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case NOT_EQUAL_TO:
                                        if (doubleValue - limit >= 0.000000000001) {
                                            resultList.add(airport);
                                        }
                                        break;
                                }
                                break;
                            case "Integer":
                                int intValue = (int) value;
                                int intLimit = Integer.parseInt(textCondition);
                                switch (conditionType) {
                                    case LESS_THAN:
                                        if (intValue < intLimit) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case GREATER_THAN:
                                        if (intValue > intLimit) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case EQUAL_TO:
                                        if (intValue == intLimit) {
                                            resultList.add(airport);
                                        }
                                        break;
                                    case NOT_EQUAL_TO:
                                        if (intValue != intLimit) {
                                            resultList.add(airport);
                                        }
                                        break;
                                }
                                break;
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Ошибка проверки на соотвествие условию: "
                    + conditionElement.getClass().getSimpleName()
                    + "["
                    + conditionElement.getColumnNumber() + ":"
                    + conditionElement.getConditionType().getValue() + ":"
                    + conditionElement.getTextCondition() + "]"
            );
        }
        return resultList;
    }
}


