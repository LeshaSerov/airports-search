package project;

import lombok.extern.slf4j.Slf4j;
import project.domain.Airport;
import project.domain.parser.SearchElement;
import project.parser.SearchStringParser;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

@Slf4j
public class SearchAirportApplication {
    String nameFile;
    AirportSearchTree searchTree;

    public SearchAirportApplication(String nameFile) {
        try {
            this.nameFile = nameFile;
            searchTree = AirportDataLoader.loadAirportSearchTreeFromCsv(Paths.get(nameFile));
        } catch (IOException e) {
            log.atError().log(e.getMessage());
        }
    }

    public void run() {
        boolean repeat = true;
        while (repeat) {
            System.out.println("Введите фильтр: ");
            Scanner scanner = new Scanner(System.in);
//            String searchLine = scanner.nextLine();
//            String searchString = "column[1]>10&(column[5]='GKA'||column[3]>'@')||column[1]<100&column[1]>10&(column[5]='GKA'||column[3]>'@')||column[1]<100";
//            String searchString = "column[1]>10||column[5]='GKA'";
            String searchString = "";

            System.out.println("Введите начало имени аэропорта: ");
//            String startWithNameAirportString = scanner.nextLine();
            String startWithNameAirportString = "B";

            long start = System.nanoTime();
            SortedMap<String, Long> sortedAirportIndexNameMap = searchTree.searchAirports(startWithNameAirportString);

            //TODO Слишком долго
            List<Airport> airportList = AirportDataLoader.loadListAirport(nameFile, sortedAirportIndexNameMap);
            List<SearchElement> searchElementList = new SearchStringParser().parse(searchString);
            airportList = new ReversePolishNotationProcessor().process(searchElementList, airportList);

            long finish = System.nanoTime();
            long elapsed = finish - start;
            System.out.println("Прошло времени, мс: " + (double) elapsed / 1000000);

//            for (Airport airport : airportList) {
//                System.out.println(airport.toString());
//            }

            repeat = false;
        }
    }
}
