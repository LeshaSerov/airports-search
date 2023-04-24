import newdomain.BracketElement;
import newdomain.FilterElement;
import newdomain.OperatorElement;
import newdomain.SearchElement;

import java.util.ArrayList;
import java.util.List;

public class SearchParser {

    public List<SearchElement> parse(String searchString) {

        List<SearchElement> resultList = new ArrayList<>();
        StringBuilder workStringBuilder = new StringBuilder(searchString.toLowerCase());

        while (workStringBuilder.length() != 0) {

            if (workStringBuilder.toString().startsWith(BracketElement.BracketType.OPEN.getValue())) {
                resultList.add(new BracketElement(BracketElement.BracketType.OPEN));
                workStringBuilder.delete(0, BracketElement.BracketType.OPEN.getValue().length());

            } else if (workStringBuilder.toString().startsWith(BracketElement.BracketType.CLOSE.getValue())) {
                resultList.add(new BracketElement(BracketElement.BracketType.CLOSE));
                workStringBuilder.delete(0, BracketElement.BracketType.CLOSE.getValue().length());

            } else if (workStringBuilder.toString().startsWith(OperatorElement.OperatorType.AND.getValue())) {
                resultList.add(new OperatorElement(OperatorElement.OperatorType.AND));
                workStringBuilder.delete(0, OperatorElement.OperatorType.AND.getValue().length());

            } else if (workStringBuilder.toString().startsWith(OperatorElement.OperatorType.OR.getValue())) {
                resultList.add(new OperatorElement(OperatorElement.OperatorType.OR));
                workStringBuilder.delete(0, OperatorElement.OperatorType.OR.getValue().length());

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
        FilterElement.FilterType filterType = FilterElement.FilterType.valueOfSymbol(workStringBuilder.substring(0, 1));
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
            //TODO:Переделать
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
