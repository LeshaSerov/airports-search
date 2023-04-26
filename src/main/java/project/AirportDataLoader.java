package project;

import lombok.extern.slf4j.Slf4j;
import project.domain.Airport;
import project.parser.AirportStringParser;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс AirportDataLoader предоставляет методы для загрузки данных о аэропортах из CSV-файлов.
 */
@Slf4j
public class AirportDataLoader {

    /**
     * Метод для загрузки данных из CSV-файла и добавления их в AirportSearchTree.
     *
     * @param filePath путь к CSV-файлу
     * @return объект AirportSearchTree с загруженными данными
     */
    public static AirportSearchTree loadAirportSearchTreeFromCsv(Path filePath) {
        AirportSearchTree airportSearchTree = new AirportSearchTree();
        try {
            // Чтение всех строк из CSV-файла
            List<String> lines = Files.readAllLines(filePath);

            RandomAccessFile raf = new RandomAccessFile(filePath.toString(), "r");
            raf.seek(lines.get(0).length());
            raf.read();
            int countNextLine = 1;
            int numberSymbol = raf.read();
            if (numberSymbol == 10 || numberSymbol == 13)
            {
                countNextLine++;
            }

            // Парсинг данных из строк и добавление их в AirportSearchTree
            long bytes = 0;
            for (String str : lines) {
                // Предполагаем, что данные разделены запятыми, берем имя и удаляем кавычки
                String titleAirport = str.split(",")[1].replace("\"", "").toLowerCase();
                airportSearchTree.addAirport(titleAirport, bytes, str.getBytes().length);
                bytes += str.getBytes().length + countNextLine;
            }
        } catch (IOException ioException) {
            log.atError().log("Ошибка чтения файла: " + filePath);
        }
        return airportSearchTree;
    }

    /**
     * Метод для загрузки списка аэропортов из файла с помощью парсинга строк с помощью AirportStringParser.
     *
     * @param nameFile                  имя файла
     * @param airportDataForParsingList список названий аэропортов, их местоположение в файле и длину строк для парсинга
     * @return список объектов Airport
     */
    public static List<Airport> loadListAirport(String nameFile, List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList) {
        if (airportDataForParsingList.isEmpty())
            return new ArrayList<>();
        List<Airport> airportList = new ArrayList<>();
        List<String> stringList = CsvReader.readDefinedStrings(nameFile, airportDataForParsingList);
        for (String str : stringList) {
            airportList.add(AirportStringParser.parse(str));
        }
        return airportList;
    }

}
