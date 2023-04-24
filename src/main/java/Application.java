import domain.SearchElement;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        try {
//            Scanner scanner = new Scanner(System.in);
//            String searchLine = scanner.nextLine();
            long start = System.nanoTime();

            String searchString = "column[1]>10&(column[5]='GKA'||column[3]>'@')||column[1]<100";


            //            List<AbstractElementOfSearchLine> result = new SearchStringParser().parse(searchLine);
//
//            ReversePolishNotation reversePolishNotation = new ReversePolishNotation();
//            List<AbstractElementOfSearchLine> convertExpression = reversePolishNotation.convertExpression(result);


//            for (AbstractElementOfSearchLine i : result) {
//                System.out.println(i.toString());
//            }
//
////            System.out.println("");
//            System.out.println("");
////            System.out.println("");
////
//            for (AbstractElementOfSearchLine i : convertExpression) {
//                System.out.println(i.toString());
//            }
            List<SearchElement> list = new SearchStringParser().parse(searchString);


            long finish = System.nanoTime();
            Long elapsed = finish - start;
            System.out.println("Прошло времени, мс: " + elapsed.doubleValue() / 1000000);

            for (SearchElement element : list){
                System.out.println(element);
            }

//            ArrayList<Integer> integerArrayList = new ArrayList<>();
//            for (int i = 1; i < 1000; i++)
//                integerArrayList.add(i*3000);
//            List<String> strings = CsvFilesReader.readDefinedStrings("airports.csv", integerArrayList);


        } catch (Exception e) {
            e.printStackTrace();
        }


//
//
//            for (String s :
//                    strings) {
//                System.out.println(s);
//            }

//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
