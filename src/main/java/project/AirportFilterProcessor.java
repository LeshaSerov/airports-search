package project;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import project.domain.Airport;
import project.domain.parser.BracketElement;
import project.domain.parser.ConditionElement;
import project.domain.parser.OperatorElement;
import project.domain.parser.SearchElement;
import project.filter.ColumnValueFilter;

import java.util.*;

/**
 * Класс AirportFilterProcessor предназначен для обработки фильтрации аэропортов на основе заданных критериев поиска.
 * Фильтрация происходит на основе постфиксной нотации (Reverse Polish Notation - RPN).
 * Класс содержит методы для конвертации выражения в RPN и обработки фильтрации списка аэропортов.
 */
@Slf4j
public class AirportFilterProcessor {
    /**
     * Конвертирует выражение в RPN (постфиксную нотацию) на основе списка элементов фильтра.
     *
     * @param searchElementList Список элементов фильтра.
     * @return Список элементов фильтра в RPN.
     */
    private List<SearchElement> convertExpressionToRPN(List<SearchElement> searchElementList) {
        List<SearchElement> resultList = new ArrayList<>();
        Stack<SearchElement> stack = new Stack<>();
        try {
            for (SearchElement element : searchElementList) {
                if (element instanceof ConditionElement) {
                    resultList.add(element);
                } else if (element instanceof BracketElement) {
                    if (element.getType() == BracketElement.BracketType.OPEN) {
                        stack.push(element);
                    } else if (!stack.isEmpty()) {
                        while (stack.peek().getType() != BracketElement.BracketType.OPEN) {
                            resultList.add(stack.pop());
                        }
                        stack.pop();
                    }
                } else if (element instanceof OperatorElement) {
                    if (!stack.isEmpty()) {
                        if (element.getType() == OperatorElement.OperatorType.AND) {
                            while (!stack.isEmpty() && stack.peek().getType() == OperatorElement.OperatorType.AND) {
                                resultList.add(stack.pop());
                            }
                        } else {
                            while (!stack.isEmpty() && (stack.peek().getType() == OperatorElement.OperatorType.AND
                                    || stack.peek().getType() == OperatorElement.OperatorType.OR)) {
                                resultList.add(stack.pop());
                            }
                        }
                    }
                    stack.push(element);
                }
            }
            while (!stack.isEmpty()) {
                resultList.add(stack.pop());
            }
        } catch (EmptyStackException e) {
            log.atError().log("Ошибка конвертации в RPN: Нарушена логика выражения.");
        }
        return resultList;
    }

    /**
     * Обрабатывает фильтрацию списка аэропортов на основе списка элементов фильтра в RPN.
     *
     * @param searchElementList Список элементов фильтра в RPN.
     * @param airportList       Список аэропортов для фильтрации.
     * @return Список аэропортов, отфильтрованных на основе заданных критериев.
     */
    public List<Airport> process(List<SearchElement> searchElementList, List<Airport> airportList) {
        if (searchElementList.isEmpty())
            return airportList;
        List<SearchElement> elementList = convertExpressionToRPN(searchElementList);
        Stack<Collection<Airport>> stack = new Stack<>();
        try {
            for (SearchElement element : elementList) {
                if (element instanceof ConditionElement) {
                    stack.push(ColumnValueFilter.filter(airportList, (ConditionElement) element));
//                stack.push(ColumnValueFilter.filterForColumn(  new ArrayList<>(airportList), (ConditionElement) element));
                } else if (element instanceof OperatorElement) {
                    Collection<Airport> list1 = stack.pop();
                    Collection<Airport> list2 = stack.pop();
                    if (element.getType() == OperatorElement.OperatorType.AND) {
                        stack.push(CollectionUtils.intersection(list1, list2));
                    } else {
                        stack.push(CollectionUtils.union(list1, list2));
                    }
                } else {
                    log.atError().log("Недопустимый элемент в фильтре: " + element.getType().getClass().getSimpleName() + ":" + element.getType().toString());
                    return airportList;
                }
            }
            return new ArrayList<>(stack.pop());
        } catch (EmptyStackException e) {
            log.atError().log("Ошибка Фильтра");
        }
        return airportList;
    }
}
