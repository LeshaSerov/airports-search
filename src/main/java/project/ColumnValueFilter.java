package project;

import project.domain.Airport;
import project.domain.parser.FilterElement;

import java.util.List;

public class ColumnValueFilter {
    public static List<Airport> filterForColumn(List<Airport> list, FilterElement filterElement) {
        // Удаление элементов с нулями из списка результатоов
        list.removeIf(e -> e.getValueForColumn(filterElement.getColumnNumber()) == null);
        // Проверка типа по первому элементу и реализация фильтрации
        if (!list.isEmpty()) {
            Object returnObject = list.get(0).getValueForColumn(filterElement.getColumnNumber()).getClass();

            if (String.class.equals(returnObject)) {
                switch (filterElement.getFilterType()) {
                    case LESS_THAN: {
                        list.removeIf(e -> (((String) e.getValueForColumn(filterElement.getColumnNumber())).compareToIgnoreCase(filterElement.getFilterValue()) >= 0));
                        break;
                    }
                    case GREATER_THAN: {
                        list.removeIf(e -> (((String) e.getValueForColumn(filterElement.getColumnNumber())).compareToIgnoreCase(filterElement.getFilterValue()) <= 0));
                        break;
                    }
                    case EQUAL_TO: {
                        list.removeIf(e -> (((String) e.getValueForColumn(filterElement.getColumnNumber())).compareToIgnoreCase(filterElement.getFilterValue()) != 0));
                        break;
                    }
                    case NOT_EQUAL_TO: {
                        list.removeIf(e -> (((String) e.getValueForColumn(filterElement.getColumnNumber())).compareToIgnoreCase(filterElement.getFilterValue()) == 0));
                        break;
                    }
                }
            } else if (Double.class.equals(returnObject)) {
                double limit = Double.parseDouble(filterElement.getFilterValue());
                switch (filterElement.getFilterType()) {
                    case LESS_THAN: {
                        list.removeIf(e -> (((double) e.getValueForColumn(filterElement.getColumnNumber())) >= limit));
                        break;
                    }
                    case GREATER_THAN: {
                        list.removeIf(e -> (((double) e.getValueForColumn(filterElement.getColumnNumber())) <= limit));
                        break;
                    }
                    case EQUAL_TO: {
                        list.removeIf(e -> (((double) e.getValueForColumn(filterElement.getColumnNumber())) != limit));
                        break;
                    }
                    case NOT_EQUAL_TO: {
                        list.removeIf(e -> (((double) e.getValueForColumn(filterElement.getColumnNumber())) == limit));
                        break;
                    }
                }
            } else if (Integer.class.equals(returnObject)) {
                int limit = Integer.parseInt(filterElement.getFilterValue());
                switch (filterElement.getFilterType()) {
                    case LESS_THAN: {
                        list.removeIf(e -> (((int) e.getValueForColumn(filterElement.getColumnNumber())) >= limit));
                        break;
                    }
                    case GREATER_THAN: {
                        list.removeIf(e -> (((int) e.getValueForColumn(filterElement.getColumnNumber())) <= limit));
                        break;
                    }
                    case EQUAL_TO: {
                        list.removeIf(e -> (((int) e.getValueForColumn(filterElement.getColumnNumber())) != limit));
                        break;
                    }
                    case NOT_EQUAL_TO: {
                        list.removeIf(e -> (((int) e.getValueForColumn(filterElement.getColumnNumber())) == limit));
                        break;
                    }
                }
            }
        }
        return list;
    }
}
