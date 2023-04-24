import domain.BracketElement;
import domain.FilterElement;
import domain.OperatorElement;
import domain.SearchElement;

import java.util.ArrayList;
import java.util.List;

import static domain.BracketElement.BracketType.*;
import static domain.OperatorElement.OperatorType.*;
import domain.FilterElement.FilterType;

public class SearchStringParser {

    public List<SearchElement> parse(String searchString) {

        List<SearchElement> resultList = new ArrayList<>();
        StringBuilder workStringBuilder = new StringBuilder(searchString.toLowerCase());

        while (workStringBuilder.length() != 0) {

            if (workStringBuilder.toString().startsWith(OPEN.getValue())) {
                resultList.add(new BracketElement(OPEN));
                workStringBuilder.delete(0, OPEN.getValue().length());

            } else if (workStringBuilder.toString().startsWith(CLOSE.getValue())) {
                resultList.add(new BracketElement(CLOSE));
                workStringBuilder.delete(0, CLOSE.getValue().length());

            } else if (workStringBuilder.toString().startsWith(AND.getValue())) {
                resultList.add(new OperatorElement(AND));
                workStringBuilder.delete(0, AND.getValue().length());

            } else if (workStringBuilder.toString().startsWith(OR.getValue())) {
                resultList.add(new OperatorElement(OR));
                workStringBuilder.delete(0, OR.getValue().length());

            } else if (workStringBuilder.toString().startsWith(FilterElement.getStartWishString())) {
                resultList.add(parseFilter(workStringBuilder));

            } else {
                throw new IllegalArgumentException(workStringBuilder.toString());
            }
        }
        return resultList;
    }

    //Парсинг тела фильтра
    FilterElement parseFilter(StringBuilder workStringBuilder) {
        //Парсинг номер столбца
        workStringBuilder.delete(0, FilterElement.getStartWishString().length());
        int lengthNumber = workStringBuilder.indexOf("]");
        int indexFilter = Integer.parseInt(workStringBuilder.substring(1, lengthNumber));
        workStringBuilder.delete(0, lengthNumber + 1);

        //Парсинг знака операции
        FilterType filterType = FilterType.valueOfSymbol(workStringBuilder.substring(0, 1));
        workStringBuilder.delete(0, filterType.getValue().length());

        //Парсинг условия
        char c = workStringBuilder.charAt(0);
        String conditionFilter = "";
        if (c == '\'') {
            workStringBuilder.delete(0, 1);
            int lengthCondition = workStringBuilder.indexOf("'");
            conditionFilter = workStringBuilder.substring(0, lengthCondition);
            workStringBuilder.delete(0, lengthCondition + 1);
        } else {
            //TODO Переделать механизм нахождения минимального.
            int distanceToOperatorCONJUNCTION = workStringBuilder.indexOf("&") > 0 ? workStringBuilder.indexOf("&") : Integer.MAX_VALUE;
            int distanceToOperatorDISJUNCTION = workStringBuilder.indexOf("|") > 0 ? workStringBuilder.indexOf("|") : Integer.MAX_VALUE;
            int distanceToClosingBracket = workStringBuilder.indexOf(")") > 0 ? workStringBuilder.indexOf(")") : Integer.MAX_VALUE;
            int lengthCondition = Math.min(distanceToOperatorCONJUNCTION, distanceToOperatorDISJUNCTION);
            lengthCondition = Math.min(lengthCondition, distanceToClosingBracket);
            lengthCondition = Math.min(lengthCondition, workStringBuilder.length());
            conditionFilter = workStringBuilder.substring(0, lengthCondition);
            workStringBuilder.delete(0, lengthCondition);
        }
        return new FilterElement(indexFilter, filterType, conditionFilter);
    }

}
