import domain.ElementOfNotation;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        try {
    //        Scanner console = new Scanner(System.in);
  //          String searchLine = console.nextLine();

            long start = System.currentTimeMillis();
//            List<ElementOfNotation> result = new SearchLineParser().parse(searchLine);

            ArrayList<Integer> integerArrayList = new ArrayList<>();
            for (int i = 1; i < 1000; i++)
                integerArrayList.add(i*3000);
            List<String> strings = CsvFilesReader.readDefinedStrings("airports.csv", integerArrayList);
            long finish = System.currentTimeMillis();
            long elapsed = finish - start;
//
      //      for (Object i : result) {
          //      System.out.println(i.toString());
        //    }
            System.out.println("Прошло времени, мс: " + elapsed);
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
