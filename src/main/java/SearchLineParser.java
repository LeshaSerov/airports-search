import domainParsing.*;
import exception.ComparisonOperatorException;
import exception.StringIsNotValidateException;

import java.util.ArrayList;
import java.util.List;

public class SearchLineParser {

    public List<AbstractElementOfNotation> parse(String searchLine) throws StringIsNotValidateException, ComparisonOperatorException {
        List<AbstractElementOfNotation> resultList = new ArrayList<>();
        StringBuilder workString = new StringBuilder(searchLine.toLowerCase());

        while (workString.length() != 0) {
            if (workString.toString().startsWith(OpeningBracket.getValue())) {
                resultList.add(new OpeningBracket());
                workString.delete(0, OpeningBracket.getValue().length());

            } else if (workString.toString().startsWith(ClosingBracket.getValue())) {
                resultList.add(new ClosingBracket());
                workString.delete(0, ClosingBracket.getValue().length());

            } else if (workString.toString().startsWith(Conjunction.getValue())) {
                resultList.add(new Conjunction());
                workString.delete(0, Conjunction.getValue().length());

            } else if (workString.toString().startsWith(Disjunction.getValue())) {
                resultList.add(new Disjunction());
                workString.delete(0, Disjunction.getValue().length());

            } else if (workString.toString().startsWith(Filter.getValue())) {
                workString.delete(0, Filter.getValue().length());
                resultList.add(parseFilter(workString));

            } else {
                throw new StringIsNotValidateException(workString.toString());
            }
        }
        return resultList;
    }

    //Парсинг тела фильтра
    Filter parseFilter(StringBuilder workString) throws ComparisonOperatorException {
        //Парсинг номер столбца
        int lengthNumber = workString.indexOf("]");
        int indexFilter = Integer.parseInt(workString.substring(1, lengthNumber));
        workString.delete(0, lengthNumber + 1);

        //Парсинг знака операции
        Filter.ComparisonOperator comparisonOperatorFilter = Filter.ComparisonOperator
                .fromValue(workString.substring(0, 1));
        workString.delete(0, 1);

        //Парсинг условия
        char c = workString.charAt(0);
        String conditionFilter = "";
        if (c == '\'') {
            workString.delete(0, 1);
            int lengthCondition = workString.indexOf("'");
            conditionFilter = workString.substring(0, lengthCondition);
            workString.delete(0, lengthCondition + 1);
        } else {
            int distanceToOperatorCONJUNCTION = workString.indexOf("&") > 0 ? workString.indexOf("&") : Integer.MAX_VALUE;
            int distanceToOperatorDISJUNCTION = workString.indexOf("|") > 0 ? workString.indexOf("|") : Integer.MAX_VALUE;
            int lengthCondition = Math.min(distanceToOperatorCONJUNCTION, distanceToOperatorDISJUNCTION);
            lengthCondition = Math.min(lengthCondition, workString.length());
            conditionFilter = workString.substring(0, lengthCondition);
            workString.delete(0, lengthCondition);
        }
        return new Filter(indexFilter, comparisonOperatorFilter, conditionFilter);
    }

}

//    domainParsing.Filter parseFilter(Integer iterator, String workString) throws ComparisonOperatorException {
//        //Парсинг номер столбца
//        iterator++;
//        int lengthNumber = workString.indexOf("]");
//        int indexFilter = Integer.parseInt(workString.substring(iterator, lengthNumber));
//        iterator = lengthNumber + 1;
//
//        //Парсинг знака операции
//        domainParsing.Filter.ComparisonOperator comparisonOperatorFilter = domainParsing.Filter.ComparisonOperator
//                .fromValue(workString.substring(iterator, ++iterator));
//
//        //Парсинг условия
//        char c = workString.charAt(iterator);
//        String conditionFilter = "";
//        if (c == '\'') {
//            iterator++;
//            String str = workString.substring(iterator);
//            int lengthCondition = str.indexOf("'");
//            conditionFilter = str.substring(0, lengthCondition);
//            iterator += lengthCondition + 1;
//        } else {
//            String str = workString.substring(iterator);
//            int distanceToOperatorCONJUNCTION = str.indexOf("&") > 0 ? str.indexOf("|") : Integer.MAX_VALUE;
//            int distanceToOperatorDISJUNCTION = str.indexOf("|") > 0 ? str.indexOf("|") : Integer.MAX_VALUE;
//            int lengthCondition = Math.min(distanceToOperatorCONJUNCTION, distanceToOperatorDISJUNCTION);
//            lengthCondition = Math.min(lengthCondition,str.length());
//            conditionFilter = workString.substring(iterator, iterator + lengthCondition);
//            iterator += lengthCondition;
//        }
//        return new domainParsing.Filter(domain.FILTER, indexFilter, comparisonOperatorFilter, conditionFilter);
//    }
//}


//    public List<Object> parse(String searchLine) throws exception.StringIsNotValidateException {
//        ArrayList<Object> result = new ArrayList<>();
//        String workString = searchLine.toLowerCase();
//        int iterator = 0;
//        int length = workString.length();
//        while (iterator < length) {
//            StringBuilder s = new StringBuilder(workString.substring(iterator, iterator + 1));
//            iterator++;
//            switch (s.toString()) {
//                case "{": {
//                    result.add(domain.domainParsing.ElementOfNotation.OPENING_BRACKET);
//                    break;
//                }
//                case "}": {
//                    result.add(domain.domainParsing.ElementOfNotation.CLOSING_BRACKET);
//                    break;
//                }
//                case "&": {
//                    result.add(domain.domainParsing.ElementOfNotation.CONJUNCTION);
//                    break;
//                }
//                case "|": {
//                    s.append(workString.substring(iterator, ++iterator));
//                    if (s.toString().equals("||")) {
//                        result.add(domain.domainParsing.ElementOfNotation.DISJUNCTION);
//                    } else {
//                        throw new exception.StringIsNotValidateException(s + " : " + iterator);
//                    }
//                    break;
//                }
//                case "c": {
//                    s.append(workString.substring(iterator, iterator + 7));
//                    iterator += 7;
//                    if (s.toString().equals("columns[")) {
//
//                        StringBuilder stringNumberColumns = new StringBuilder(workString.substring(iterator, iterator + 1));
//                        iterator++;
//
//                        char c = workString.charAt(iterator);
//                        iterator++;
//
//                        while (c != ']') {
//                            stringNumberColumns.append(c);
//                            c = workString.charAt(iterator);
//                            iterator++;
//
//                        }
//
//                        domainParsing.Filter.ComparisonOperator comparisonOperator = domainParsing.Filter.ComparisonOperator.fromValue(workString.substring(iterator, iterator + 1));
//                        iterator++;
//
//
//                        StringBuilder notation = new StringBuilder(workString.substring(iterator, iterator + 1));
//                        s = new StringBuilder(workString.substring(iterator));
//                        c = workString.charAt(iterator);
//                        iterator++;
//
//                        while (s.indexOf("&") != iterator && s.indexOf("||") != iterator && iterator < length) {
//                            notation.append(c);
//                            c = workString.charAt(iterator);
//                            iterator++;
//
//                        }
//                        result.add(new domainParsing.Filter(Integer.parseInt(stringNumberColumns.toString()), notation.toString(), comparisonOperator));
//                        break;
//                    } else {
//                        throw new exception.StringIsNotValidateException(s + " : " + iterator);
//                    }
//                }
//                default:{
//                    throw new exception.StringIsNotValidateException(s + " : " + iterator);
//                }
//            }
//        }
//        return result;
//    }

//            domain.domainParsing.ElementOfNotation element = domain.domainParsing.ElementOfNotation.fromValue(s.substring(iterator,iterator+1));
//            if (element == domain.domainParsing.ElementOfNotation.FILTER)
//            {
//                iterator++;
//                while (s.substring(iterator).)
//
//
//                result.add(new domainParsing.Filter())
//            }

//            String s = stringBuilder.substring(iterator, iterator++);
//            domain.domainParsing.ElementOfNotation element = domain.domainParsing.ElementOfNotation.fromValue(s);
//            if (element == domain.domainParsing.ElementOfNotation.NOT_FOUND) {
//                s += stringBuilder.substring(iterator, iterator++);
//                if (domain.domainParsing.ElementOfNotation.fromValue(s) == domain.domainParsing.ElementOfNotation.DISJUNCTION) {
//                    result.add(element);
//                }
//                else {
//                    s += stringBuilder.substring(iterator, iterator + 4);
//                    if (domain.domainParsing.ElementOfNotation.fromValue(s) == domain.domainParsing.ElementOfNotation.FILTER) {
//
//                    }
//                }
//            }
//            else {
//                result.add(element);
//            }
