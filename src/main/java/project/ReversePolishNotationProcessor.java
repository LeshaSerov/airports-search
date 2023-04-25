package project;

import org.apache.commons.collections4.CollectionUtils;
import project.ColumnValueFilter;
import project.domain.Airport;
import project.domain.parser.BracketElement;
import project.domain.parser.FilterElement;
import project.domain.parser.OperatorElement;
import project.domain.parser.SearchElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

public class ReversePolishNotationProcessor {
    private List<SearchElement> convertExpression(List<SearchElement> searchElementList) {
//        if (searchElementList.isEmpty())
//            return searchElementList;
        List<SearchElement> resultList = new ArrayList<>();
        Stack<SearchElement> stack = new Stack<>();

        for (SearchElement element : searchElementList) {
            if (element instanceof FilterElement) {
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
        return resultList;
    }

    public List<Airport> process(List<SearchElement> searchElementList, List<Airport> airportList) {
        List<SearchElement> elementList = convertExpression(searchElementList);
//        if (elementList.isEmpty())
//            return airportList;
        Stack<Collection<Airport>> stack = new Stack<>();
        for (SearchElement element : elementList) {
            if (element instanceof FilterElement) {
                stack.push(ColumnValueFilter.filterForColumn(  new ArrayList<>(airportList), (FilterElement) element));
            } else if (element instanceof OperatorElement) {
                Collection<Airport> list1 = stack.pop();
                Collection<Airport> list2 = stack.pop();
                if (element.getType() == OperatorElement.OperatorType.AND) {
                    stack.push(CollectionUtils.intersection(list1, list2));
                } else {
                    stack.push(CollectionUtils.union(list1, list2));
                }
            }
        }
        return new ArrayList<>(stack.pop());
    }
}
