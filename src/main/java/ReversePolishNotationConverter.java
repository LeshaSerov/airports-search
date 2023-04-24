public class ReversePolishNotationConverter {
    
    public List<AbstractElementOfSearchLine> convertExpression(List<AbstractElementOfSearchLine> elementOfSearchLines) throws PairBracketException {
        //Реализация алгоритма Дейкстра
        List<AbstractElementOfSearchLine> resultList = new ArrayList<>();
        Stack<AbstractElementOfSearchLine> stack = new Stack<>();
        for (AbstractElementOfSearchLine element : elementOfSearchLines) {

            if (element instanceof Filter) {
                resultList.add(element);

            } else if (element instanceof OpeningBracket) {
                stack.push(element);

            } else if (element instanceof ClosingBracket) {
                while (!(stack.peek() instanceof OpeningBracket)) {
                    resultList.add(stack.pop());
                }
                stack.pop();

            } else if (element instanceof Conjunction) {
                if (!stack.isEmpty()) {
                    while (stack.peek() instanceof Conjunction) {
                        resultList.add(stack.pop());
                    }
                }
                stack.add(element);

            } else if (element instanceof Disjunction) {
                if (!stack.isEmpty()) {
                    while ((stack.peek() instanceof Conjunction) || (stack.peek() instanceof Disjunction)) {
                        resultList.add(stack.pop());
                    }
                }
                stack.add(element);
            }
        }
        while (!stack.isEmpty()) {
            resultList.add(stack.pop());
        }
        return resultList;
    }

//    public List<Airport> counting(List<AbstractElementOfSearchLine> elementOfSearchLines, List<Airport> airportList) {
//        Stack<List<Airport>> stack = new Stack<>();
//
//        for (AbstractElementOfSearchLine element : elementOfSearchLines)
//        {
//            if (element instanceof Filter)
//            {
//                List<Airport> result;
//
//                    switch (((Filter) element).getNumberColumns())
//                    {
//                        case 1:
//                        {
//                            result = airportList.stream()
//                                    .filter(p -> p.getColumn1() ).collect(Collectors.toList());
//                            break;
//                        }
//                        case 3:
//                        {
//                            break;
//                        }
//                        case 4:
//                        {
//                            break;
//                        }
//                        case 5:
//                        {
//                            break;
//                        }
//                        case 6:
//                        {
//                            break;
//                        }
//                        case 7:
//                        {
//                            break;
//                        }
//                        case 8:
//                        {
//                            break;
//                        }
//                        case 9:
//                        {
//                            break;
//                        }
//                        case 10:
//                        {
//                            break;
//                        }
//                        case 11:
//                        {
//                            break;
//                        }
//                        case 12:
//                        {
//                            break;
//                        }
//                        case 13:
//                        {
//                            break;
//                        }
//                        case 14:
//                        {
//                            break;
//                        }
//
//
//                    }
//
//
//                stack.push();
//            }
//            else
//            {
//                if (element instanceof Conjunction)
//                {
//
//                }
//
//            }
//        }
//
//    }
}
