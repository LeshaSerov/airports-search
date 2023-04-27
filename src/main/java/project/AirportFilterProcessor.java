package project;

import lombok.extern.slf4j.Slf4j;
import project.domain.Airport;
import project.domain.parser.BracketElement;
import project.domain.parser.ConditionElement;
import project.domain.parser.OperatorElement;
import project.domain.parser.SearchElement;
import project.filter.ColumnValueFilter;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

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
                } else {
                    throw new RuntimeException("Ошибка конвертации в RPN: Обнаружен элемент, который не известно как обрабатывать: " + element);
                }
            }
            while (!stack.isEmpty()) {
                resultList.add(stack.pop());
            }
        } catch (EmptyStackException e) {
            throw new RuntimeException("Ошибка конвертации в RPN: Нарушена логика выражения: Возможно не правильно расставленны элементы, или какие либо из них отсутствуют.", e);
        }
        return resultList;
    }

    /**
     * Обрабатывает фильтрацию списка аэропортов на основе списка элементов фильтра в RPN.
     *
     * @param searchElementList Список элементов фильтра в RPN.
     * @param airportList       Список аэропортов для фильтрации.
     * @return Список аэропортов, отфильтрованных на основе заданных критериев.
     * <p>В случае, если на вход приходит не корректный список элементов фильтра,
     * возращает исходный список аэропортов.</p>
     */
    public List<Airport> process(List<SearchElement> searchElementList, List<Airport> airportList) {
        if (searchElementList.isEmpty() || airportList.isEmpty())
            return airportList;

        try {
            List<SearchElement> elementList = convertExpressionToRPN(searchElementList);
            Stack<List<Airport>> stack = new Stack<>();

            for (SearchElement element : elementList) {
                if (element instanceof ConditionElement) {
                    stack.push(ColumnValueFilter.filter(airportList, (ConditionElement) element));
                } else if (element instanceof OperatorElement) {
                    List<Airport> list1 = stack.pop();
                    List<Airport> list2 = stack.pop();
                    if (element.getType() == OperatorElement.OperatorType.AND) {
                        list1.retainAll(list2);
                        stack.push(list1);
                    } else {
                        stack.push(mergeSortedLists(list1, list2));
                    }
                } else {
                    log.atError().log("Ошибка в работе фильтра: Обнаружен элемент, который не известно как обрабатывать: " + element.getType().getClass().getSimpleName() + ":" + element.getType().toString());
                    return airportList;
                }
            }
            return stack.pop();

        } catch (EmptyStackException e) {
            log.atError().log("Ошибка в работе фильтра: ", e);
            return airportList;

        } catch (RuntimeException e) {
            log.atError().log(e.getMessage(), e);
            return airportList;
        }
    }

    public static List<Airport> mergeSortedLists(List<Airport> list1, List<Airport> list2) {
        List<Airport> result = new ArrayList<>(list1.size() + list2.size());
        int i = 0;
        int j = 0;
        int index = -1;
        boolean notEmpty = false;
        boolean isExit = false;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i).compareTo(list2.get(j)) < 0) {
                if (notEmpty)
                {
                    if (result.get(index).getIndex() != list1.get(i).getIndex()) {
                        result.add(list1.get(i));
                        index++;
                    }
                }
                else{
                    notEmpty = true;
                    result.add(list1.get(i));
                    index++;
                }

                i++;
            } else {
                if (notEmpty) {
                    if (result.get(index).getIndex() != list2.get(j).getIndex()){
                    result.add(list2.get(j));
                    index++;
                    }
                }
                else {
                    notEmpty = true;
                    result.add(list2.get(j));
                    index++;
                }
                j++;
            }
        }
        while (i < list1.size()) {
            if (notEmpty)
            {
                if (isExit || result.get(index).getIndex() != list1.get(i).getIndex()) {
                    result.add(list1.get(i));
                    isExit = true;
                }
            }
            else{
                return new ArrayList<>(list1);
            }
            i++;
        }
        while (j < list2.size()) {
            if (notEmpty) {
                if (isExit || result.get(index).getIndex() != list2.get(j).getIndex()){
                    result.add(list2.get(j));
                    isExit = true;
                }
            }
            else {
                return new ArrayList<>(list2);
            }
            j++;
        }
        return result;
    }
}
