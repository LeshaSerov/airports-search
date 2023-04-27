package project;

import lombok.extern.slf4j.Slf4j;
import project.domain.Airport;
import project.parser.AirportStringParser;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс AirportDataLoader предоставляет методы для загрузки данных о аэропортах из CSV-файлов.
 */
@Slf4j
public class AirportDataLoader {

   private static  int countByteNextLine(String fileName, String s) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {
            int countNextLine = 1;
            raf.seek(s.length());
            raf.read();

            int numberSymbol = raf.read();
            if (numberSymbol == 10 || numberSymbol == 13) {
                countNextLine++;
            }
            return countNextLine;
        }
        catch (IOException e)
        {
            throw new IOException(e);
        }
    }

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

            int countNextLine = countByteNextLine(filePath.toString(),lines.get(0));

            // Парсинг данных из строк и добавление их в AirportSearchTree
            long bytes = 0;
            for (String str : lines) {
                // Предполагаем, что данные разделены запятыми, берем имя и удаляем кавычки
                String titleAirport = str.split(",")[1].replace("\"", "").toLowerCase();
                airportSearchTree.addAirport(titleAirport, bytes, str.getBytes().length);
                bytes += str.getBytes().length + countNextLine;
            }
        } catch (IOException e) {
            log.atError().log("Ошибка чтения файла: " + filePath, e);
        }
        return airportSearchTree;
    }


    /**
     * Метод для загрузки списка аэропортов из файла с помощью парсинга строк с помощью AirportStringParser.
     *
     * @param fileName                  имя файла
     * @param airportDataForParsingList список названий аэропортов, их местоположение в файле и длину строк для парсинга
     * @return список объектов Airport
     */
    public static List<Airport> loadListAirport(String fileName, List<AirportSearchTree.AirportDataForParsing> airportDataForParsingList) {
        if (airportDataForParsingList.isEmpty())
            return new ArrayList<>();
        List<Airport> airportList = new ArrayList<>();
        List<String> stringList = CsvReader.readDefinedStrings(fileName, airportDataForParsingList);
        try {
            for (String str : stringList) {
                airportList.add(AirportStringParser.parse(str));
            }
        }
        catch (StringIndexOutOfBoundsException|NumberFormatException e)
        {
            //Логика такой обработки заключается в том, что если одну из строк не удалось распарсить,
            // следовательно велика вероятность что и все последующие не получится
            log.atError().log(e.getMessage());
        }
        return airportList;
    }
}
