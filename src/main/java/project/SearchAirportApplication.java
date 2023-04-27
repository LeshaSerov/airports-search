package project;

import lombok.extern.slf4j.Slf4j;
import project.domain.Airport;
import project.domain.parser.SearchElement;
import project.parser.FilterStringParser;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

@Slf4j
public class SearchAirportApplication {
    String fileName;
    AirportSearchTree searchTree;

    public SearchAirportApplication(String fileName) {
        this.fileName = fileName;
        searchTree = AirportDataLoader.loadAirportSearchTreeFromCsv(Paths.get(fileName));
    }

    public void run() {
        do {
            System.out.print("Введите фильтр: ");
            Scanner scanner = new Scanner(System.in);
            String searchString = scanner.nextLine();
            if (searchString.equals("!quit")) {
                break;
            }

            System.out.print("Введите начало имени аэропорта: ");
            String startWithNameAirportString = scanner.nextLine();

            long start = System.nanoTime();
            List<Airport> airportList = process(searchString, startWithNameAirportString);
            long finish = System.nanoTime();
            long elapsed = finish - start;

            for (Airport airport : airportList) {
                System.out.println(airport.toString());
            }
            System.out.println("Количество найденных строк: " + airportList.size());
            System.out.println("Время, затраченное на поиск: " + (double) elapsed / 1000000 + " мс");

            System.out.println();
        }
        while (true);
    }

//                String searchString = "column[1]>10&(column[5]='GKA'||column[3]>'@')||column[1]<100&column[1]>10&(column[5]='GKA'||column[3]>'@')||column[1]<100";
//            String searchString = "column[1]>10(&)column[5]='GKA'";
//            String searchString = "a";

    /**
     * Метод обработки поиска аэропортов на основе заданных фильтров.
     *
     * @param searchString               Строка поиска, содержащая фильтры для поиска аэропортов.
     * @param startWithNameAirportString Строка, содержащая начало имени аэропорта, для ограничения поиска только на аэропорты с соответствующим началом имени.
     * @return Список аэропортов, удовлетворяющих заданным фильтрам.
     */
    public List<Airport> process(String searchString, String startWithNameAirportString) {
        SortedMap<String, AirportSearchTree.AirportDataForParsing> sortedAirportIndexNameMap = searchTree.searchAirports(startWithNameAirportString.toLowerCase());
        List<Airport> airportList = AirportDataLoader.loadListAirport(fileName, new ArrayList<>(sortedAirportIndexNameMap.values()));
        if (!searchString.isEmpty()) {
            List<SearchElement> searchElementList = new FilterStringParser().parse(searchString);
            airportList = new AirportFilterProcessor().process(searchElementList, airportList);
        }
        return airportList;
    }

}

