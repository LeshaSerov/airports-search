package project;

import project.domain.Airport;
import project.parser.AirportStringParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class AirportDataLoader {

    // Метод для загрузки данных из CSV-файла и добавления их в AirportSearchTree
    public static AirportSearchTree loadAirportSearchTreeFromCsv(Path filePath) throws IOException {
        AirportSearchTree airportSearchTree = new AirportSearchTree();
        try {
            // Чтение всех строк из CSV-файла
            List<String> lines = Files.readAllLines(filePath);
            // Парсинг данных из строк и добавление их в AirportSearchTree
            long bytes = 0;
            String lineToFind = "\"";
            for (String str : lines) {
                // Предполагаем, что данные разделены запятыми, берем имя и удаляем кавычки
                String titleAirport = str.split(",")[1].replace("\"", "");
                airportSearchTree.addAirport(titleAirport.toLowerCase(), bytes);
                bytes += str.getBytes().length + System.lineSeparator().getBytes().length;
            }
            return airportSearchTree;
        } catch (IOException ioException) {
            throw new IOException("Ошибка чтения файла: " + filePath, ioException);
        }
    }

    /**
     * Метод для загрузки списка аэропортов из файла с использованием заданного словаря
     * с сортированными индексами и именами аэропортов.
     *
     * @param nameFile                  Имя файла, из которого производится загрузка данных
     * @param sortedAirportIndexNameMap Словарь с сортированными индексами и именами аэропортов
     * @return Список аэропортов, загруженных из файла
     */
    public static List<Airport> loadListAirport(String nameFile, SortedMap<String, Long> sortedAirportIndexNameMap) {
        //быстро
        List<String> stringList = CsvReader.readDefinedStrings(nameFile, sortedAirportIndexNameMap);

        List<Airport> airportList = new ArrayList<>();
        for (String str : stringList)
        {
            airportList.add(AirportStringParser.parse(str));
        }

        return airportList;
    }

}
