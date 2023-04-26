package project.parser;

import lombok.extern.slf4j.Slf4j;
import project.domain.parser.BracketElement;
import project.domain.parser.ConditionElement;
import project.domain.parser.OperatorElement;
import project.domain.parser.SearchElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс FilterStringParser представляет собой парсер строки фильтра, используемый для преобразования строки поиска
 * в список элементов фильтра, представленных в виде объектов классов-элементов (BracketElement, OperatorElement, ConditionElement).
 */
@Slf4j
public class FilterStringParser {

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

            } else if (workStringBuilder.toString().startsWith(ConditionElement.getStartWishString())) {
                resultList.add(parseCondition(workStringBuilder));

            } else {
                log.atError().log("Ошибка парсинга фильтра: \"" + workStringBuilder + "\"");
                workStringBuilder = new StringBuilder("");
            }
        }

        return resultList;
    }

    /**
     * Метод для парсинга ConditionElement из строки, представленной в виде StringBuilder.
     *
     * @param workStringBuilder StringBuilder, содержащий строку для парсинга
     * @return объект ConditionElement, представляющий условный элемент
     * @
     */
    ConditionElement parseCondition(StringBuilder workStringBuilder) {

        //Парсинг номер столбца
        workStringBuilder.delete(0, ConditionElement.getStartWishString().length());
        int lengthNumber = workStringBuilder.indexOf("]");
        int indexFilter = Integer.parseInt(workStringBuilder.substring(1, lengthNumber));
        workStringBuilder.delete(0, lengthNumber + 1);

        //Парсинг знака операции
        ConditionElement.ConditionType conditionType = ConditionElement.ConditionType.valueOfSymbol(workStringBuilder.substring(0, 1));
        workStringBuilder.delete(0, conditionType.getValue().length());

        //Парсинг условия
        char c = workStringBuilder.charAt(0);
        String textCondition = "";
        if (c == '\'') {
            workStringBuilder.delete(0, 1);
            int lengthCondition = workStringBuilder.indexOf("'");
            textCondition = workStringBuilder.substring(0, lengthCondition);
            workStringBuilder.delete(0, lengthCondition + 1);
        } else {

            //TODO Переделать механизм нахождения минимального расстояния, возможно получить один из символов внутри кавычек
            int distanceToOperatorCONJUNCTION = workStringBuilder.indexOf("&") > 0 ? workStringBuilder.indexOf("&") : Integer.MAX_VALUE;
            int distanceToOperatorDISJUNCTION = workStringBuilder.indexOf("|") > 0 ? workStringBuilder.indexOf("|") : Integer.MAX_VALUE;
            int distanceToClosingBracket = workStringBuilder.indexOf(")") > 0 ? workStringBuilder.indexOf(")") : Integer.MAX_VALUE;
            int lengthCondition = Math.min(distanceToOperatorCONJUNCTION, distanceToOperatorDISJUNCTION);
            lengthCondition = Math.min(lengthCondition, distanceToClosingBracket);
            lengthCondition = Math.min(lengthCondition, workStringBuilder.length());

            textCondition = workStringBuilder.substring(0, lengthCondition).toLowerCase();
            workStringBuilder.delete(0, lengthCondition);
        }
        return new ConditionElement(indexFilter, conditionType, textCondition);
    }

}
